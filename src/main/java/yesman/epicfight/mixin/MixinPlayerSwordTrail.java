package yesman.epicfight.mixin;


import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


@Mixin(value = PatchedItemInHandLayer.class, remap = false)
public abstract class MixinPlayerSwordTrail<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, LayerRenderer<E, M>> {

    /*
    @Inject(at = @At("TAIL"),method = "renderLayer")
    public void EpicAddon_renderLayer(T entitypatch, E entityliving, LayerRenderer<E, M> originalRenderer, MatrixStack poseStack, IRenderTypeBuffer buffer, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, CallbackInfo callbackInfo){
        if(ClientConfig.cfg.EnableSwordTrail){
            Minecraft mc = Minecraft.getInstance();
            poseStack.pushPose();
            for (Layer.Priority priority : Layer.Priority.values()) {
                AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(priority).animationPlayer;
                float playTime = animPlayer.getElapsedTime();
                DynamicAnimation animation = animPlayer.getAnimation();

                if(animation instanceof AttackAnimation){
                    renderTrail(poseStack, buffer, entitypatch, playTime, partialTicks,(AttackAnimation)animation);
                }
                //animPlayer.getAnimation().renderDebugging(poseStack, buffer, entitypatch, playTime, partialTicks);
            }
            poseStack.popPose();
        }
    }
    //protected static final Logger LOGGER = LogUtils.getLogger();
    private void renderTrail(MatrixStack poseStack, IRenderTypeBuffer buffer, LivingEntityPatch<?> entitypatch, float playTime, float partialTicks, AttackAnimation animation){

        Trail t1 = null;
        Trail t2 = null;

        //ogger LOGGER = LogUtils.getLogger();

        //LOGGER.info(entitypatch.getValidItemInHand(Hand.MAIN_HAND).getItem().getRegistryName().toString());
        t1 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(Hand.MAIN_HAND).getItem().getRegistryName().toString());
        //LOGGER.info((entitypatch).getValidItemInHand(Hand.OFF_HAND).getItem().getRegistryName().toString());
        t2 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(Hand.OFF_HAND).getItem().getRegistryName().toString());

        MutiSwordTrail Trail = new MutiSwordTrail(300);
        Trail.draw(poseStack, buffer, entitypatch, animation, 0.0f, playTime, partialTicks, animation.getPlaySpeed(entitypatch), t1, t2);

    }

     */
}

