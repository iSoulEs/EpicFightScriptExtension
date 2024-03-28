package com.jvn.efst.renderer;

import com.jvn.efst.EpicAddon;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TrailParticle implements IParticleRenderType {
    public final String textures;
    public final String toStr;
    public TrailParticle(String textures, String toStr){
        this.textures=textures;
        this.toStr=toStr;
    }

    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);

        textureManager.bind(GetTextures("particle/"+textures));
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
    }

    @Override
    public void end(Tessellator tesselator) {
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableCull();
    }

    @Override
    public String toString() {
        return this.toStr;
    }
    public static ResourceLocation GetTextures(String path){
        return new ResourceLocation(EpicAddon.MODID, "textures/" + path + ".png");
    }
}
