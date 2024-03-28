package yesman.epicfight.client.renderer.patched.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;
import org.apache.logging.log4j.Level;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public abstract class PatchedLivingEntityRenderer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedEntityRenderer<E, T, LivingRenderer<E, M>> {
	public Map<Class<?>, PatchedLayer<E, T, M, ? extends LayerRenderer<E, M>>> patchedLayers = Maps.newHashMap();
	private static Field NPC_RENDERENTITY;
	static {
		try {
			NPC_RENDERENTITY=RenderCustomNpc.class.getDeclaredField("renderEntity");
			NPC_RENDERENTITY.setAccessible(true);
		}catch (Exception e){

		}
	}
	@Override
	public void render(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLight, float partialTicks) {
		super.render(entityIn, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
		
		Minecraft mc = Minecraft.getInstance();
		boolean isVisible = this.isVisible(entityIn, entitypatch);
		boolean isVisibleToPlayer = !isVisible && !entityIn.isInvisibleTo(mc.player);
		boolean isGlowing = mc.shouldEntityAppearGlowing(entityIn);
		RenderType renderType = this.getRenderType(entityIn, entitypatch, renderer, isVisible, isVisibleToPlayer, isGlowing);
		ClientModel model = entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT);
		Armature armature = model.getArmature();
		poseStack.pushPose();
		this.mulPoseStack(poseStack, armature, entityIn, entitypatch, partialTicks);
		OpenMatrix4f[] poseMatrices = this.getPoseMatrices(entitypatch, armature, partialTicks);
		
		if (renderType != null) {
			IVertexBuilder builder = buffer.getBuffer(renderType);
			model.drawAnimatedModel(poseStack, builder, packedLight, 1.0F, 1.0F, 1.0F, isVisibleToPlayer ? 0.15F : 1.0F, this.getOverlayCoord(entityIn, entitypatch, partialTicks), poseMatrices);
		}
		
		if (!entityIn.isSpectator()) {
			this.renderLayer(renderer, entitypatch, entityIn, poseMatrices, buffer, poseStack, packedLight, partialTicks);
		}
		
		if (renderType != null) {
			if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
				for (Layer.Priority priority : Layer.Priority.values()) {
					AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(priority).animationPlayer;
					float playTime = animPlayer.getPrevElapsedTime() + (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime()) * partialTicks;
					animPlayer.getAnimation().renderDebugging(poseStack, buffer, entitypatch, playTime, partialTicks);
				}
			}
		}
		
		poseStack.popPose();
	}
	
	protected void renderLayer(LivingRenderer<E, M> renderer, T entitypatch, E entityIn, OpenMatrix4f[] poses, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLightIn, float partialTicks) {
		List<LayerRenderer<E, M>> layers = Lists.newArrayList();
		LivingRenderer<E,M> temp=renderer;
		E tempB=entityIn;
		if(tempB instanceof EntityCustomNpc){
			EntityCustomNpc npc=(EntityCustomNpc) tempB;
			if(npc.modelData != null && npc.modelData.getEntity(npc) != null) {
				tempB = (E) npc.modelData.getEntity(npc);
			} else {
				tempB = entityIn;
			}
		}
		final E tempBB=tempB;

		if(temp instanceof RenderCustomNpc){
			try {
				temp=(LivingRenderer) NPC_RENDERENTITY.get(temp);

			}catch (Exception e){
				EpicFightMod.LOGGER.log(Level.ERROR,"[2kk2]: noppes.npcs.client.renderer.RenderCustomNpc.renderLayer error "+e);
			}

		}
		temp.layers.forEach(layers::add);
		Iterator<LayerRenderer<E, M>> iter = layers.iterator();
		float f = MathUtils.lerpBetween(tempBB.yBodyRotO, tempBB.yBodyRot, partialTicks);
        float f1 = MathUtils.lerpBetween(tempBB.yHeadRotO, tempBB.yHeadRot, partialTicks);
        float f2 = f1 - f;
		float f7 = tempBB.getViewXRot(partialTicks);
		//EpicFightMod.LOGGER.log(Level.ERROR,"[2kk2]: entity:"+entityIn);
		//EpicFightMod.LOGGER.log(Level.ERROR,"[2kk2]: entity for patch:"+entitypatch.getOriginal());
		//EpicFightMod.LOGGER.log(Level.ERROR,"[2kk2]: render:"+temp);


		while (iter.hasNext()) {
			LayerRenderer<E, M> layer = iter.next();
			Class<?> rendererClass = layer.getClass();
			//EpicFightMod.LOGGER.log(Level.ERROR,"[2kk2]: Class:"+rendererClass);
			
			if (rendererClass.isAnonymousClass()) {
				rendererClass = rendererClass.getSuperclass();
			}


			this.patchedLayers.computeIfPresent(rendererClass, (key, val) -> {
				val.renderLayer(0, entitypatch, tempBB, layer, poseStack, buffer, packedLightIn, poses, f2, f7, partialTicks);
				iter.remove();
				return val;
			});
		}
		
		OpenMatrix4f modelMatrix = new OpenMatrix4f();
		modelMatrix.mulFront(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchJointById(this.getRootJointIndex()).getAnimatedTransform());
		OpenMatrix4f transpose = OpenMatrix4f.transpose(modelMatrix, null);
		
		poseStack.pushPose();
		MathUtils.translateStack(poseStack, modelMatrix);
		MathUtils.rotateStack(poseStack, transpose);
		poseStack.translate(0.0D, this.getLayerCorrection(), 0.0D);
		poseStack.scale(-1.0F, -1.0F, 1.0F);




		layers.forEach((layer) -> {
			layer.render(poseStack, buffer, packedLightIn, tempBB, tempBB.animationPosition, tempBB.animationSpeed, partialTicks, tempBB.tickCount, f2, f7);
		});
		poseStack.popPose();
	}
	
	public RenderType getRenderType(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, boolean isVisible, boolean isVisibleToPlayer, boolean isGlowing) {
		ResourceLocation resourcelocation = this.getEntityTexture(entitypatch, renderer);
		
		if (isVisibleToPlayer) {
			return EpicFightRenderTypes.itemEntityTranslucentCull(resourcelocation);
		} else if (isVisible) {
			return EpicFightRenderTypes.animatedModel(resourcelocation);
		} else {
			return isGlowing ? RenderType.outline(resourcelocation) : null;
		}
	}
	
	protected int getOverlayCoord(E entity, T entitypatch, float partialTicks) {
		return OverlayTexture.pack(0, OverlayTexture.v(entity.hurtTime > 5));
	}
	
	@Override
	public void mulPoseStack(MatrixStack poseStack, Armature armature, E entityIn, T entitypatch, float partialTicks) {
		super.mulPoseStack(poseStack, armature, entityIn, entitypatch, partialTicks);
        
        if (entityIn.isShiftKeyDown()) {
			poseStack.translate(0.0D, 0.15D, 0.0D);
		}
	}
	
	public void addPatchedLayer(Class<?> originalLayerClass, PatchedLayer<E, T, M, ? extends LayerRenderer<E, M>> patchedLayer) {
		this.patchedLayers.put(originalLayerClass, patchedLayer);
	}
	
	protected boolean isVisible(E entityIn, T entitypatch) {
		return !entityIn.isInvisible();
	}
	
	protected int getRootJointIndex() {
		return 0;
	}
	
	protected double getLayerCorrection() {
		return 1.15D;
	}
}