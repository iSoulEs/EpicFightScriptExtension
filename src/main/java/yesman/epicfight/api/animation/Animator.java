package yesman.epicfight.api.animation;

import com.google.common.collect.Maps;
import com.jvn.efst.tools.TkkTrail;
import tkk.epic.collider.TkkCustomCollider;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;
import java.util.Set;

public abstract class Animator {
	protected Pose prevPose = new Pose();
	protected Pose currentPose = new Pose();
	protected final Map<LivingMotion, StaticAnimation> livingAnimations = Maps.newHashMap();

	public TkkTrail mainHandTrail=new TkkTrail();
	public TkkTrail offHandTrail=new TkkTrail();
	public void updateTrail(){
		this.mainHandTrail.update();
		this.offHandTrail.update();
		//this.mainHandTrail= RenderConfig.getItemTrailRaw("epicfight:netherite_greatsword");
		//this.offHandTrail= RenderConfig.getItemTrailRaw("epicfight:netherite_greatsword");
	}

	public boolean attackSpeedModifiers=false;
	public Float preDelay_attackSpeedModifiers_set=null;
	public Float preDelay_attackSpeedModifiers_add=null;
	public Float preDelay_attackSpeedModifiers_scale=null;
	public float getPreDelayAttackSpeed(float attackSpeed){
		return ((preDelay_attackSpeedModifiers_set==null)?attackSpeed:preDelay_attackSpeedModifiers_set)*((preDelay_attackSpeedModifiers_scale==null)?1.0f:preDelay_attackSpeedModifiers_scale)+((preDelay_attackSpeedModifiers_add==null)?0.0f:preDelay_attackSpeedModifiers_add);
	}
	public Float contact_attackSpeedModifiers_set=null;
	public Float contact_attackSpeedModifiers_add=null;
	public Float contact_attackSpeedModifiers_scale=null;
	public float getContactAttackSpeed(float attackSpeed){
		return ((contact_attackSpeedModifiers_set==null)?attackSpeed:contact_attackSpeedModifiers_set)*((contact_attackSpeedModifiers_scale==null)?1.0f:contact_attackSpeedModifiers_scale)+((contact_attackSpeedModifiers_add==null)?0.0f:contact_attackSpeedModifiers_add);
	}
	public Float recovery_attackSpeedModifiers_set=null;
	public Float recovery_attackSpeedModifiers_add=null;
	public Float recovery_attackSpeedModifiers_scale=null;
	public float getRecoveryAttackSpeed(float attackSpeed){
		return ((recovery_attackSpeedModifiers_set==null)?attackSpeed:recovery_attackSpeedModifiers_set)*((recovery_attackSpeedModifiers_scale==null)?1.0f:recovery_attackSpeedModifiers_scale)+((recovery_attackSpeedModifiers_add==null)?0.0f:recovery_attackSpeedModifiers_add);
	}
	public void updateAttackSpeed(){
		preDelay_attackSpeedModifiers_set=null;
		preDelay_attackSpeedModifiers_add=null;
		preDelay_attackSpeedModifiers_scale=null;
		contact_attackSpeedModifiers_set=null;
		contact_attackSpeedModifiers_add=null;
		contact_attackSpeedModifiers_scale=null;
		recovery_attackSpeedModifiers_set=null;
		recovery_attackSpeedModifiers_add=null;
		recovery_attackSpeedModifiers_scale=null;
	}

	public final TkkCustomCollider tkkCustomCollider=new TkkCustomCollider();


	protected LivingEntityPatch<?> entitypatch;
	
	public abstract void playAnimation(StaticAnimation nextAnimation, float convertTimeModifier);
	public abstract void playAnimationInstantly(StaticAnimation nextAnimation);
	public abstract void tick();
	/** Standby until the current animation is completely end. Mostly used for link two animations having the same last & first keyframe pose **/
	public abstract void reserveAnimation(StaticAnimation nextAnimation);
	public abstract EntityState getEntityState();
	/** Give a null value as a parameter to get an animation that is highest priority on client **/
	public abstract AnimationPlayer getPlayerFor(DynamicAnimation playingAnimation);
	public abstract void init();
	public abstract void poseTick();
	
	public final void playAnimation(int namespaceId, int id, float convertTimeModifier) {
		this.playAnimation(EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId, id), convertTimeModifier);
	}
	
	public final void playAnimationInstantly(int namespaceId, int id) {
		this.playAnimationInstantly(EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId, id));
	}
	
	public Pose getPose(float partialTicks) {
		return Pose.interpolatePose(this.prevPose, this.currentPose, partialTicks);
	}
	
	public boolean isReverse() {
		return false;
	}
	
	public void playDeathAnimation() {
		this.playAnimation(Animations.BIPED_DEATH, 0);
	}
	
	public void addLivingAnimation(LivingMotion livingMotion, StaticAnimation animation) {
		this.livingAnimations.put(livingMotion, animation);
	}
	
	public Set<Map.Entry<LivingMotion, StaticAnimation>> getLivingAnimationEntrySet() {
		return this.livingAnimations.entrySet();
	}
	
	public void resetMotions() {
		this.livingAnimations.clear();
	}
	
	/** Get binded position of joint **/
	public static OpenMatrix4f getBindedJointTransformByName(Pose pose, Armature armature, String jointName) {
		return getBindedJointTransformByIndex(pose, armature, armature.searchPathIndex(jointName));
	}
	
	/** Get binded position of joint **/
	public static OpenMatrix4f getBindedJointTransformByIndex(Pose pose, Armature armature, int pathIndex) {
		armature.initializeTransform();
		return getBindedJointTransformByIndexInternal(pose, armature.getJointHierarcy(), new OpenMatrix4f(), pathIndex);
	}
	
	private static OpenMatrix4f getBindedJointTransformByIndexInternal(Pose pose, Joint joint, OpenMatrix4f parentTransform, int pathIndex) {
		JointTransform jt = pose.getOrDefaultTransform(joint.getName());
		OpenMatrix4f result = jt.getAnimationBindedMatrix(joint, parentTransform);
		int nextIndex = pathIndex % 10;
		return nextIndex > 0 ? getBindedJointTransformByIndexInternal(pose, joint.getSubJoints().get(nextIndex - 1), result, pathIndex / 10) : result;
	}
}