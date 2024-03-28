package com.jvn.efst.renderer.SwordTrail;


import com.google.common.collect.Lists;
import com.jvn.efst.config.RenderConfig;
import com.jvn.efst.renderer.EpicAddonRenderType;
import com.jvn.efst.tools.ParticleTrail;
import com.jvn.efst.tools.TkkTrail;
import com.jvn.efst.tools.Trail;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;
import tkk.epic.TkkEpic;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@OnlyIn(Dist.CLIENT) // copy from yesman
public class BladeTrailParticle extends SpriteTexturedParticle {
    //private final Joint joint;
    private final int jointid;
    private final Trail trail;
    private final AttackAnimation anim;
    private final List<TrailEdge> Nodes;
    private boolean animationEnd;
    private float startEdgeCorrection = 0.0F;
    private final LivingEntityPatch<?> entitypatch;
    private final int trailType;
    private static final Random random = new Random();
    private ParticleTrail particleTrail;

    private static final int interpolateCount = 7;

    public BladeTrailParticle(ClientWorld level, LivingEntityPatch entitypatch, AttackAnimation anim, int jointid, Trail trail, IAnimatedSprite animatedSprite,int trailType,ParticleTrail particleTrail) {
        super(level,0,0,0);
        //this.joint = joint;
        this.jointid = jointid;
        this.trail = trail;
        this.anim = anim;
        this.hasPhysics = false;
        this.entitypatch = entitypatch;
        this.Nodes = Lists.newLinkedList();
        this.trailType=trailType;
        this.particleTrail=particleTrail;
        this.setSpriteFromAge(animatedSprite);

        Vector3d entityPos = entitypatch.getOriginal().position();
        this.setSize(10.0F, 10.0F);
        this.move(entityPos.x, entityPos.y + entitypatch.getOriginal().getEyeHeight(), entityPos.z);

        ClientAnimator animator = this.entitypatch.getClientAnimator();
        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Vector3d start = new Vector3d(trail.x,trail.y,trail.z);
        Vector3d end = new Vector3d(trail.ex,trail.ey,trail.ez);

        Vector3d prevStartPos = getPosByTick(animator,armature,start,0.0f);
        Vector3d prevEndPos = getPosByTick(animator,armature,end,0.0f);
        Vector3d middleStartPos = getPosByTick(animator,armature,start,0.5f);
        Vector3d middleEndPos = getPosByTick(animator,armature,end,0.5f);
        Vector3d currentStartPos = getPosByTick(animator,armature,start,1.0f);
        Vector3d currentEndPos = getPosByTick(animator,armature,end,1.0f);

        int interpolateCount1 = (int)Math.ceil(middleEndPos.distanceTo(prevEndPos)*interpolateCount);
        int interpolateCount2 = (int)Math.ceil(middleEndPos.distanceTo(currentEndPos)*interpolateCount);

        this.Nodes.add(new TrailEdge(prevStartPos, prevEndPos, this.trail.lifetime));
        for(int i=1;i<interpolateCount1;i++){
            Vector3d interStart = getPosByTick(animator,armature,start,0.5f/interpolateCount1*i);
            Vector3d interEnd = getPosByTick(animator,armature,end,0.5f/interpolateCount1*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(middleStartPos, middleEndPos, this.trail.lifetime));

        for(int i=1;i<interpolateCount2;i++){
            Vector3d interStart = getPosByTick(animator,armature,start,0.5f+0.5f/interpolateCount2*i);
            Vector3d interEnd = getPosByTick(animator,armature,end,0.5f+0.5f/interpolateCount2*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(currentStartPos, currentEndPos, this.trail.lifetime));
    }

    public Vector3d getPosByTick(ClientAnimator animator, Armature armature, Vector3d org, float partialTicks){
        Pose pose = animator.getPose(partialTicks);
        Vector3d pos = this.entitypatch.getOriginal().getPosition(partialTicks);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(partialTicks)));
        OpenMatrix4f JointTf = Animator.getBindedJointTransformByIndex(pose, armature,jointid).mulFront(modelTf);
        return OpenMatrix4f.transform(JointTf,org);
    }

    @Override
    public void tick() {
        if(!this.animationEnd && !(entitypatch!=null && entitypatch.getOriginal()!=null && entitypatch.getOriginal().isAlive())){
            this.animationEnd = true;
        }
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.anim);
        this.Nodes.removeIf(v -> !v.isAlive());

        if (this.animationEnd) {
            if (this.lifetime-- == 0) {
                this.remove();
                return;
            }
        } else {
            if (this.anim != animPlayer.getAnimation().getRealAnimation() || animPlayer.getElapsedTime() > anim.getTotalTime()*0.85f) {
                this.animationEnd = true;
                this.lifetime = this.trail.lifetime;
            }
        }
        boolean needCorrection = this.Nodes.size() == 0;

        if (needCorrection) {
            float startCorrection = (0 - animPlayer.getPrevElapsedTime()) / (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime());
            this.startEdgeCorrection = interpolateCount * startCorrection;
        }

        ClientAnimator animator = this.entitypatch.getClientAnimator();
        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Vector3d start = new Vector3d(trail.x,trail.y,trail.z);
        Vector3d end = new Vector3d(trail.ex,trail.ey,trail.ez);

        Vector3d prevEndPos = getPosByTick(animator,armature,end,0.0f);
        Vector3d middleStartPos = getPosByTick(animator,armature,start,0.5f);
        Vector3d middleEndPos = getPosByTick(animator,armature,end,0.5f);
        Vector3d currentStartPos = getPosByTick(animator,armature,start,1.0f);
        Vector3d currentEndPos = getPosByTick(animator,armature,end,1.0f);

        int interpolateCount1 = (int)Math.ceil(middleEndPos.distanceTo(prevEndPos)*interpolateCount);
        int interpolateCount2 = (int)Math.ceil(middleEndPos.distanceTo(currentEndPos)*interpolateCount);


        //spawnParticle(animator,armature,particleTrail,0.5f);
        //spawnParticle(animator,armature,particleTrail,1.0f);


        for(int i=1;i<interpolateCount1;i++){
            Vector3d interStart = getPosByTick(animator,armature,start,0.5f/interpolateCount1*i);
            Vector3d interEnd = getPosByTick(animator,armature,end,0.5f/interpolateCount1*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
            spawnParticle(animator,armature,particleTrail,0.5f/interpolateCount1*i);
        }

        this.Nodes.add(new TrailEdge(middleStartPos, middleEndPos, this.trail.lifetime));
        spawnParticle(animator,armature,particleTrail,0.5f);


        for(int i=1;i<interpolateCount2;i++){
            Vector3d interStart = getPosByTick(animator,armature,start,0.5f+0.5f/interpolateCount2*i);
            Vector3d interEnd = getPosByTick(animator,armature,end,0.5f+0.5f/interpolateCount2*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
            spawnParticle(animator,armature,particleTrail,0.5f+0.5f/interpolateCount2*i);
        }

        this.Nodes.add(new TrailEdge(currentStartPos, currentEndPos, this.trail.lifetime));
        spawnParticle(animator,armature,particleTrail,1.0f);
    }

    public void spawnParticle(ClientAnimator animator, Armature armature, ParticleTrail particleTrail,float partialTicks){
        if(particleTrail==null){return;}
        for(ParticleTrail.trail trail: particleTrail.list){
            Vector3d start = trail.getStart(particleTrail.WeaponCollider);
            Vector3d end = trail.getEnd(particleTrail.WeaponCollider);


            World world=entitypatch.getOriginal().level;
            IParticleData iParticleData;
            try {
                iParticleData=trail.getIParticleData();
            } catch (Exception e) {
                if(world.isClientSide){
                    Minecraft.getInstance().player.sendMessage(new TranslationTextComponent("§cError Particle Trail getIParticleData():§f"+e), UUID.randomUUID());
                }
                TkkEpic.LOGGER.log(Level.ERROR,"Error Particle Trail getIParticleData():"+e);
                continue;
            }
            if(trail.xSpaceBetween<=0 || trail.ySpaceBetween<=0 || trail.zSpaceBetween<=0){
                String message = "§cError Particle Trail spaceBetween:§fx" + trail.xSpaceBetween + ",y" + trail.ySpaceBetween + ",z" + trail.zSpaceBetween;
                if(world.isClientSide) {
                    Minecraft.getInstance().player.sendMessage(new TranslationTextComponent(message), UUID.randomUUID());
                }
                TkkEpic.LOGGER.log(Level.ERROR, message);

                continue;

            }

            double x,y,z;
            x=start.x;
            do{
                y=start.y;
                do{
                    z=start.z;
                    do{
                        for(int i=0;i<trail.count;i++) {
                            Vector3d pos = getPosByTick(animator,armature,new Vector3d(x,y,z),partialTicks);
                            double a1=random.nextGaussian()*trail.xDist;
                            double a2=random.nextGaussian()*trail.yDist;
                            double a3=random.nextGaussian()*trail.zDist;
                            world.addParticle(iParticleData, pos.x+a1, pos.y+a2, pos.z+a3, random.nextGaussian() * trail.xSpeed, random.nextGaussian() * trail.ySpeed, random.nextGaussian() * trail.zSpeed);
                        }

                        z+=getWhileAdd(start.z,end.z)*trail.zSpaceBetween;
                    }while (whileEnd(start.z,end.z,z));

                    y+=getWhileAdd(start.y,end.y)*trail.ySpaceBetween;
                }while (whileEnd(start.y,end.y,y));

                x+=getWhileAdd(start.x,end.x)*trail.xSpaceBetween;
            }while (whileEnd(start.x,end.x,x));

        }
    }
    public final boolean whileEnd(double start,double end,double now){
        if(start<end){
            return now<end;
        }else if(start>end){
            return now>end;
        }
        return false;
    }
    public final double getWhileAdd(double start,double end){
        double temp=Math.abs(start-end);
        double a=(start>end)?-1:1;
        return a*temp;
    }

    @Override
    public void render(IVertexBuilder vertexConsumer, ActiveRenderInfo camera, float partialTick) {
        if(trail.lifetime == 0){
            this.lifetime = 0;
            return;
        }
        if (this.Nodes.size() < 1) {
            return;
        }
        MatrixStack poseStack = new MatrixStack();
        int light = this.getLightColor(partialTick);
        this.setupPoseStack(poseStack, camera, partialTick);
        Matrix4f matrix4f = poseStack.last().pose();
        int edges = this.Nodes.size() - 1;
        boolean startFade = this.Nodes.get(0).lifetime == 1;
        boolean endFade = this.Nodes.get(edges).lifetime == this.trail.lifetime;

        float startEdge = (startFade ? interpolateCount * partialTick : 0.0F) + this.startEdgeCorrection;
        float endEdge = endFade ? edges - (interpolateCount) * (1.0F - partialTick) : edges - 1;

        float interval = 1.0F / (endEdge - startEdge);
        float partialStartEdge = interval * (startEdge % 1.0F);
        float from = -partialStartEdge;
        float to = -partialStartEdge + interval;

        int start = (int)Math.max(startEdge,0);
        int end = (int)Math.min(endEdge + 1,Nodes.size()-2);
        for (int i = start; i < end; i++) {
            TrailEdge e1 = this.Nodes.get(i);
            TrailEdge e2 = this.Nodes.get(i + 1);
            Vector4f pos1 = new Vector4f((float)e1.start.x, (float)e1.start.y, (float)e1.start.z, 1.0F);
            Vector4f pos2 = new Vector4f((float)e1.end.x, (float)e1.end.y, (float)e1.end.z, 1.0F);
            Vector4f pos3 = new Vector4f((float)e2.end.x, (float)e2.end.y, (float)e2.end.z, 1.0F);
            Vector4f pos4 = new Vector4f((float)e2.start.x, (float)e2.start.y, (float)e2.start.z, 1.0F);

            pos1.transform(matrix4f);
            pos2.transform(matrix4f);
            pos3.transform(matrix4f);
            pos4.transform(matrix4f);

            vertexConsumer.vertex(pos1.x(), pos1.y(), pos1.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e1.lifetime/trail.lifetime).uv(from, 1.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos2.x(), pos2.y(), pos2.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e1.lifetime/trail.lifetime).uv(from, 0.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos3.x(), pos3.y(), pos3.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e2.lifetime/trail.lifetime).uv(to, 0.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos4.x(), pos4.y(), pos4.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e2.lifetime/trail.lifetime).uv(to, 1.0F).uv2(light).endVertex();

            from += interval;
            to += interval;
        }

    }

    protected void setupPoseStack(MatrixStack poseStack, ActiveRenderInfo camera, float partialTicks) {
        Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
        Vector3d vec3 = camera.getPosition();
        float x = (float)-vec3.x();
        float y = (float)-vec3.y();
        float z = (float)-vec3.z();

        poseStack.translate(x, y, z);
        poseStack.mulPose(rotation);
    }

    private void makeTrailEdges(List<Vector3d> startPositions, List<Vector3d> endPositions, List<TrailEdge> dest) {
        for (int i = 0; i < startPositions.size(); i++) {
            dest.add(new TrailEdge(startPositions.get(i), endPositions.get(i), this.trail.lifetime));
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return EpicAddonRenderType.getTrail(this.trailType);
    }

    @OnlyIn(Dist.CLIENT)
    private static class TrailEdge {
        final Vector3d start;
        final Vector3d end;
        int lifetime;

        public TrailEdge(Vector3d start, Vector3d end, int lifetime) {
            this.start = start;
            this.end = end;
            this.lifetime = lifetime;
        }

        boolean isAlive() {
            return --this.lifetime > 0;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;
        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            int eid = (int)Double.doubleToLongBits(x);
            int modid = (int)Double.doubleToLongBits(y);
            int animid = (int)Double.doubleToLongBits(z);
            int jointId = (int)Double.doubleToLongBits(xSpeed);
            //ySpeed -> isOffHand?
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = getEntityPatch(entity, LivingEntityPatch.class);
                StaticAnimation anim = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);
                //
                if (entitypatch != null && anim != null && anim instanceof AttackAnimation) {
                    ItemStack item=entitypatch.getValidItemInHand(ySpeed>=0 ? Hand.MAIN_HAND:Hand.OFF_HAND);
                    CapabilityItem capabilityItem=EpicFightCapabilities.getItemStackCapability(item);
                    Trail trail = RenderConfig.getItemTrail(item);
                    Animator animator=entitypatch.getAnimator();
                    TkkTrail trailA=(ySpeed>=0 ? animator.mainHandTrail:animator.offHandTrail);
                    ParticleTrail particleTrai=null;
                    int trailType=0;
                    if(trailA.isReady()){
                        trailA.setWeaponCollider(entitypatch.getColliderMatching(ySpeed>=0 ? Hand.MAIN_HAND:Hand.OFF_HAND));
                        trail=trailA.getTrail();
                        trailType=trailA.getTrailType();
                        particleTrai=trailA.getParticleTrail();
                    }

                    if(trail == null){
                        return null;
                    }

                    return new BladeTrailParticle(level, entitypatch, (AttackAnimation) anim, jointId, trail, spriteSet,trailType,particleTrai);
                }
            }
            return null;
        }

        public static <T extends EntityPatch> T getEntityPatch(Entity entity, Class<T> type) {
            if (entity != null) {
                EntityPatch<?> entitypatch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse(null);

                if (entitypatch != null && type.isAssignableFrom(entitypatch.getClass())) {
                    return (T)entitypatch;
                }
            }
            return null;
        }
    }
}
