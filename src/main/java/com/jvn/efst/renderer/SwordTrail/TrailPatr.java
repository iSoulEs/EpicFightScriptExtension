package com.jvn.efst.renderer.SwordTrail;

import com.jvn.efst.tools.Trail;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class TrailPatr {
    public TrailPatr() {

    }
    @OnlyIn(Dist.CLIENT)
    public abstract void draw(MatrixStack matrixStackIn, IRenderTypeBuffer buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, Trail t1, Trail t2);

    @OnlyIn(Dist.CLIENT)
    public abstract void addPart(MatrixStack matrixStackIn, OpenMatrix4f pose, IVertexBuilder vertexBuilder,Trail tt);

    @OnlyIn(Dist.CLIENT)
    public abstract void addBegin(MatrixStack matrixStackIn, OpenMatrix4f pose, IVertexBuilder vertexBuilder,Trail tt);

    @OnlyIn(Dist.CLIENT)
    public abstract void addEnd(MatrixStack matrixStackIn, OpenMatrix4f pose, IVertexBuilder vertexBuilder,Trail tt);
}
