package reascer.wom.animation;


import net.minecraft.util.Hand;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;

public class BasicMultipleAttackAnimation extends AttackAnimation {
    public BasicMultipleAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        this(convertTime, antic, antic, contact, recovery, collider, index, path, model);
    }

    public BasicMultipleAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public BasicMultipleAttackAnimation(float convertTime, float antic, float contact, float recovery, Hand hand, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, antic, contact, recovery, hand, collider, index, path, model);
    }

    public BasicMultipleAttackAnimation(float convertTime, String path, Model model, AttackAnimation.Phase... phases) {
        super(convertTime, path, model, phases);
    }

    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0.0F);
        if (entitypatch instanceof PlayerPatch) {
            PlayerPatch<?> playerpatch = (PlayerPatch)entitypatch;
            AttackAnimation.Phase phase = getPhaseByTime(playerpatch.getAnimator().getPlayerFor((DynamicAnimation)this).getElapsedTime());
            extTime *= this.totalTime * entitypatch.getTkkModifiersAttackSpeed(playerpatch.getAttackSpeed(phase.getHand()));
        }
        extTime = Math.max(extTime - this.convertTime, 0.0F);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }

    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        if (entitypatch.shouldBlockMoving() && ((Boolean)getProperty((AnimationProperty)AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE).orElse(Boolean.valueOf(true))).booleanValue())
            vec3.scale(0.0F);
        return vec3;
    }

    public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        Pose pose = super.getPoseByTime(entitypatch, time, partialTicks);
        return pose;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }
}




