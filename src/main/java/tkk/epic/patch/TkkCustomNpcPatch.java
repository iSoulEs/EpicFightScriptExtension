package tkk.epic.patch;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import noppes.npcs.entity.EntityCustomNpc;
import tkk.epic.TkkEpic;
import tkk.epic.event.CNpcTryHurtEvent;
import tkk.epic.network.SPTkkNpcUpdata;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.HashMap;
import java.util.Map;

public class TkkCustomNpcPatch <T extends EntityCustomNpc> extends HumanoidMobPatch<T> {
    public ResourceLocation model;
    public static float NPC_SCALE_OFFSET = 0.2f;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    public boolean blockMoving=false;
    public boolean isJump=false;
    public boolean isGuard=false;
    private final HashMap<LivingMotions, StaticAnimation> animatorList;
    //EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId, id)
    public final HashMap<String,int[]> animationIdList;

    public final HashMap<LivingMotions, StaticAnimation> defaultAnimationList;

    public TkkCustomNpcPatch() {
        super(Faction.NEUTRAL);
        model = new ResourceLocation("epicfight", "entity/biped_old_texture");
        animationIdList = new HashMap();
        animatorList = new HashMap();
        defaultAnimationList = new HashMap<>();

        putAnimatorIdList("IDLE",Animations.BIPED_IDLE);
        putAnimatorIdList("WALK",Animations.BIPED_WALK);
        putAnimatorIdList("DEATH",Animations.BIPED_DEATH);
        putAnimatorIdList("JUMP",Animations.BIPED_JUMP);
        putDefaultAnimatorIdList("IDLE",Animations.BIPED_IDLE);
        putDefaultAnimatorIdList("WALK",Animations.BIPED_WALK);
        putDefaultAnimatorIdList("DEATH",Animations.BIPED_DEATH);
        putDefaultAnimatorIdList("JUMP",Animations.BIPED_JUMP);
        scaleX = 1.0f;
        scaleY = 1.0f;
        scaleZ = 1.0f;
        syncAnimator();
    }
    public void putAnimatorIdList(String livingMotions,StaticAnimation animation){
        animationIdList.put(livingMotions,new int[]{animation.getNamespaceId(),animation.getId()});
    }

    public void putDefaultAnimatorIdList(String livingMotions,StaticAnimation animation){
        defaultAnimationList.put(LivingMotions.valueOf(livingMotions),animation);
    }
    public void tkkNpcUpdata() {
        EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(new SPTkkNpcUpdata(this.original), this.original);
    }

    public boolean canAirSlash(){
        return isJump && this.original.getDeltaMovement().y<0;
    }
    public void jump(float jumpPower) {
        float f = jumpPower;
        if (this.original.hasEffect(Effects.JUMP)) {
            f += 0.1F * (float)(this.original.getEffect(Effects.JUMP).getAmplifier() + 1);
        }

        Vector3d vector3d = this.original.getDeltaMovement();
        this.original.setDeltaMovement(vector3d.x, (double)f, vector3d.z);
        if (this.original.isSprinting()) {
            float f1 = this.original.yRot * ((float)Math.PI / 180F);
            this.original.setDeltaMovement(this.original.getDeltaMovement().add((double)(-MathHelper.sin(f1) * 0.2F), 0.0D, (double)(MathHelper.cos(f1) * 0.2F)));
        }

        this.original.hasImpulse = true;
        StaticAnimation jumpAnimation=animatorList.get(LivingMotions.JUMP);
        if(jumpAnimation!=null){this.playAnimationSynchronized(jumpAnimation,0);}
        this.isJump=true;
    }
    @Override
    protected void serverTick(LivingEvent.LivingUpdateEvent event) {
        super.serverTick(event);
        if(isJump){
            if(this.original.isOnGround() && this.original.getDeltaMovement().y<0){
                isJump=false;
            }
        }
    }
    @Override
    protected void initAI() {
    }

    @Override
    public void onStartTracking(ServerPlayerEntity trackingPlayer) {
        /*
        if (!this.getHoldingItemCapability(Hand.MAIN_HAND).isEmpty()) {
            SPSpawnData packet = new SPSpawnData(this.original.getId());
            EpicFightNetworkManager.sendToPlayer(packet, trackingPlayer);
        }

         */
        super.onStartTracking(trackingPlayer);
        //此处同步npc自定义模型数据
        EpicFightNetworkManager.sendToPlayer(new SPTkkNpcUpdata(this.getOriginal()), trackingPlayer);
    }


    @Override
    protected void initAttributes() {
        super.initAttributes();
        //this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(1.0D);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        for (Map.Entry<LivingMotions, StaticAnimation> entry : animatorList.entrySet()) {
            animator.addLivingAnimation(entry.getKey(), entry.getValue());
        }
        //clientAnimator.setCurrentMotionsAsDefault();

    }

    @Override
    public boolean shouldBlockMoving() {
        return blockMoving;
    }
    public void syncAnimator() {
        animatorList.clear();
        for (Map.Entry<String, int[]> entry : animationIdList.entrySet()){
            String key = entry.getKey();
            StaticAnimation value = EpicFightMod.getInstance().animationManager.findAnimationById(entry.getValue()[0],entry.getValue()[1]);
            animatorList.put(LivingMotions.valueOf(key),value);
        }
    }
    @Override
    public void updateHeldItem(CapabilityItem fromCap, CapabilityItem toCap, ItemStack from, ItemStack to, Hand hand) {

        if (hand == Hand.OFF_HAND) {
            if (!from.isEmpty()) {
                from.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ATTACK_DAMAGE.get())::removeModifier);
                from.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_SPEED).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ATTACK_SPEED.get())::removeModifier);
            }
            if (!fromCap.isEmpty()) {
                fromCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.ARMOR_NEGATION.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get())::removeModifier);
                fromCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.IMPACT.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_IMPACT.get())::removeModifier);
                fromCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.MAX_STRIKES.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_MAX_STRIKES.get())::removeModifier);
            }

            if (!to.isEmpty()) {
                to.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ATTACK_DAMAGE.get())::addTransientModifier);
                to.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_SPEED).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ATTACK_SPEED.get())::addTransientModifier);
            }
            if (!toCap.isEmpty()) {
                toCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.ARMOR_NEGATION.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get())::addTransientModifier);
                toCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.IMPACT.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_IMPACT.get())::addTransientModifier);
                toCap.getAttributeModifiers(EquipmentSlotType.MAINHAND, this).get(EpicFightAttributes.MAX_STRIKES.get()).forEach(this.original.getAttribute(EpicFightAttributes.OFFHAND_MAX_STRIKES.get())::addTransientModifier);
            }
        }
        /*
        SPChangeLivingMotion msg = new SPChangeLivingMotion(this.original.getId());
        msg.putEntries(this.getAnimator().getLivingAnimationEntrySet());
        EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(msg, this.original);

         */
    }
    @Override
    public void updateMotion(boolean considerInaction) {
        super.commonAggressiveMobUpdateMotion(considerInaction);
    }

    @Override
    public <M extends Model> M getEntityModel(Models<M> modelDB) {
        M model = modelDB.get(this.model);
        if (model == null) {
            TkkEpic.getInstance().broadcast("error model ResourceLocation: " + this.model);
            return modelDB.bipedOldTexture;
        }
        return model;
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = this.getOriginal().display.getSize() * NPC_SCALE_OFFSET;
        return super.getModelMatrix(partialTicks).scale(scale * this.scaleX, scale * this.scaleY, scale * this.scaleZ);
    }

    @Override
    public void modifyLivingMotionByCurrentItem() {
        /*

        this.getAnimator().resetMotions();
        for (Map.Entry<LivingMotions, StaticAnimation> entry : animatorList.entrySet()) {
            animator.addLivingAnimation(entry.getKey(), entry.getValue());
        }
        SPChangeLivingMotion msg = new SPChangeLivingMotion(this.original.getId());
        msg.putEntries(this.getAnimator().getLivingAnimationEntrySet());
        EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(msg, this.original);

         */
    }
    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        CNpcTryHurtEvent event=new CNpcTryHurtEvent(this,super.tryHurt(damageSource,amount),damageSource,amount);
        MinecraftForge.EVENT_BUS.post(event);
        return event.attackResult;
    }
}

