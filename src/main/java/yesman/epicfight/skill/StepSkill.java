package yesman.epicfight.skill;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class StepSkill extends DodgeSkill {
	public StepSkill(DodgeSkill.Builder builder) {
		super(builder);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public Object getExecutionPacket(LocalPlayerPatch executer, PacketBuffer args) {
		int forward = args.readInt();
		int backward = args.readInt();
		int left = args.readInt();
		int right = args.readInt();
		int vertic = forward + backward;
		int horizon = left + right;
		int degree = vertic == 0 ? 0 : -(90 * horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon);
		int animation;
		
		if (vertic == 0) {
			if (horizon == 0) {
				animation = 0;
			} else {
				animation = horizon >= 0 ? 2 : 3;
			}
		} else {
			animation = vertic >= 0 ? 0 : 1;
		}
		
		CPExecuteSkill packet = new CPExecuteSkill(this.category.universalOrdinal());
		packet.getBuffer().writeInt(animation);
		packet.getBuffer().writeFloat(degree);
		
		return packet;
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executer, PacketBuffer args) {
		super.executeOnServer(executer, args);
		executer.getOriginal().level.playSound(null, executer.getOriginal().getX(), executer.getOriginal().getY(), executer.getOriginal().getZ(), EpicFightSounds.SKILL_STEP, executer.getOriginal().getSoundSource(), 1.0F, 1.0F);

	}

}
