package yesman.epicfight.skill;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import tkk.epic.network.SPSpawnParticle;
import tkk.epic.network.TkkEpicNetworkManager;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.Formulars;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class TechnicianSkill extends PassiveSkill {
	private static final UUID EVENT_UUID = UUID.fromString("99e5c782-fdaf-11eb-9a03-0242ac130003");
	private static final SkillDataKey<Boolean> CURRENTLY_ACTIVATED = SkillDataKey.createDataKey(SkillDataManager.ValueType.BOOLEAN);
	public static double DODGE_TIME=1.0;
	public TechnicianSkill(Builder<? extends Skill> builder) {
		super(builder);
	}
	
	@Override
	public void onInitiate(SkillContainer container) {
		container.getDataManager().registerData(CURRENTLY_ACTIVATED);
		container.getExecuter().getEventListener().addEventListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
			if (event.getAnimation() instanceof DodgeAnimation) {
				container.getDataManager().setData(CURRENTLY_ACTIVATED, false);
			}
		});
		
		container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
			ServerPlayerPatch executer = event.getPlayerPatch();
			
			if (executer.getAnimator().getPlayerFor(null).getAnimation() instanceof DodgeAnimation) {
				boolean canTrigger=executer.getAnimator().getPlayerFor(null).getAnimation().getTotalTime()*DODGE_TIME < executer.getAnimator().getPlayerFor(null).getElapsedTime();
				if(canTrigger){return;}
				DamageSource damageSource = event.getDamageSource();
				
				if (executer.getEntityState().invulnerableTo(damageSource)) {
					if (!container.getDataManager().getDataValue(CURRENTLY_ACTIVATED)) {
						float consumption = Formulars.getStaminarConsumePenalty(executer.getWeight(), executer.getSkill(SkillCategories.DODGE).containingSkill.getConsumption(), executer);
						executer.setStamina(executer.getStamina() + consumption);
						container.getDataManager().setData(CURRENTLY_ACTIVATED, true);
						event.setCanceled(true);
						event.setResult(AttackResult.ResultType.FAILED);

						Entity entity = executer.getOriginal();
						World w=executer.getOriginal().level;
						if(w instanceof ServerWorld){
							SPSpawnParticle packet=new SPSpawnParticle();
							packet.addParticle("epicfight:after_image",entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							TkkEpicNetworkManager.sendNearby(executer.getOriginal().getLevel(),executer.getOriginal().blockPosition(),16,packet);
							//TkkEpicNetworkManager.sendToPlayer(packet,executer.getOriginal());
							executer.getOriginal().level.playSound(null, executer.getOriginal().getX(), executer.getOriginal().getY(), executer.getOriginal().getZ(), EpicFightSounds.SKILL_TECHNICIAN, executer.getOriginal().getSoundSource(), 1.0F, 1.0F);
							//TkkEpicNetworkManager.sendToPlayer(packet2,executer.getOriginal());
						}
					}
				}
			}
		});
	}
	
	@Override
	public void onRemoved(SkillContainer container) {
		container.getExecuter().getEventListener().removeListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
	}
}