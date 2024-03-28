package yesman.epicfight.mixin;

import com.jvn.efst.events.RegParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = StaticAnimation.class, remap = false)
public abstract class MixinAtkAnim {
    //protected Trail trail;

    public StaticAnimation getAtkAnim(){
        if(namespaceId >= 0 && animationId >= 0) return EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId,animationId);
        else return null;
    }

    @Shadow
    private int namespaceId;
    @Shadow
    private int animationId;

    private String prevJoint;
    @Inject(at = @At("HEAD"),method = "begin")
    private void MixinBegin(LivingEntityPatch<?> entitypatch,CallbackInfo cbi){
        //System.out.println("fuck0");
        if(entitypatch.getOriginal().level.isClientSide()){
            //System.out.println("fuck1");
            if(entitypatch instanceof PlayerPatch || entitypatch instanceof HumanoidMobPatch){
                //System.out.println("fuck2");
                StaticAnimation animation = getAtkAnim();
                if(animation == null || !(animation instanceof AttackAnimation)) return;
                double eid = Double.longBitsToDouble(entitypatch.getOriginal().getId());
                double modid = Double.longBitsToDouble(animation.getNamespaceId());
                double animid = Double.longBitsToDouble(animation.getId());
                String jointID = ((AttackAnimation) animation).getPathIndexByTime(0);
                this.prevJoint = jointID;
                //System.out.println(String.format("Particle(mod=%d, anim=%d, joint=%s)",animation.getNamespaceId(),animation.getId(),jointID));
                if(jointID == "Tool_R" || jointID == "Tool_L"){
                    double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex(jointID));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, jointID == "Tool_R" ? 1:-1, 0);
                }
                else {
                    double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_R"));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, 1, 0);
                    jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_L"));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, -1, 0);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"),method = "tick")
    private void MixinTick(LivingEntityPatch<?> entitypatch,CallbackInfo cbi){
        //System.out.println("_fuck0");
        if (entitypatch.getOriginal().level.isClientSide){
            //System.out.println("_fuck1");
            if(entitypatch instanceof PlayerPatch || entitypatch instanceof HumanoidMobPatch){
                //System.out.println("_fuck2");
                StaticAnimation animation = getAtkAnim();
                if(animation == null || !(animation instanceof AttackAnimation)) return;
                String jointID = ((AttackAnimation) animation).getPathIndexByTime(entitypatch.getClientAnimator().getPlayerFor(animation).getElapsedTime());
                if(jointID != prevJoint){
                    //System.out.println("_fuck3");
                    double eid = Double.longBitsToDouble(entitypatch.getOriginal().getId());
                    double modid = Double.longBitsToDouble(animation.getNamespaceId());
                    double animid = Double.longBitsToDouble(animation.getId());
                    //System.out.println(String.format("Particle 2(mod=%d, anim=%d, joint=%s)",animation.getNamespaceId(),animation.getId(),jointID));
                    if(jointID == "Tool_R" || jointID == "Tool_L"){
                        double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex(jointID));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, jointID == "Tool_R" ? -1:1, 0);
                    }
                    else {
                        double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_R"));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, -1, 0);
                        jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_L"));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, 1, 0);
                    }
                }
                this.prevJoint = jointID;
            }
        }
    }
}
