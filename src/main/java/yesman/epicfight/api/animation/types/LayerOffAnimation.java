package yesman.epicfight.api.animation.types;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.client.animation.JointMask.BindModifier;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Optional;

public class LayerOffAnimation extends DynamicAnimation {
	public LayerOffAnimation(){super();}
	public DynamicAnimation lastAnimation;
	public Pose lastPose;
	public Priority layerPriority;
	
	public LayerOffAnimation(Priority layerPriority) {
		this.layerPriority = layerPriority;
	}
	
	public void setLastPose(Pose pose) {
		this.lastPose = pose;
	}
	
	@Override
	public void end(LivingEntityPatch<?> entitypatch, boolean isEnd) {
		if (entitypatch.isLogicalClient()) {
			entitypatch.getClientAnimator().baseLayer.disableLayer(this.layerPriority);
		}
	}
	
	@Override
	public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
		Pose lowerLayerPose = entitypatch.getClientAnimator().getComposedLayerPoseBelow(this.layerPriority, Minecraft.getInstance().getFrameTime());
		return Pose.interpolatePose(this.lastPose, lowerLayerPose, time / this.totalTime);
	}
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, String joint) {
		return this.lastPose.getJointTransformData().containsKey(joint);
	}
	
	@Override
	public <V> Optional<V> getProperty(AnimationProperty<V> propertyType) {
		return this.lastAnimation.getProperty(propertyType);
	}
	
	public void setLastAnimation(DynamicAnimation animation) {
		this.lastAnimation = animation;
	}
	@OnlyIn(Dist.CLIENT)
	@Override
	public BindModifier getBindModifier(LivingEntityPatch<?> entitypatch, String joint) {
		return this.lastAnimation.getBindModifier(entitypatch, joint);
	}
	
	@Override
	public DynamicAnimation getRealAnimation() {
		return Animations.DUMMY_ANIMATION;
	}
}