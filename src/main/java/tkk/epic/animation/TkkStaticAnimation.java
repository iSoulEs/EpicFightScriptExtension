package tkk.epic.animation;

import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.StateSpectrum;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.JointMask;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.JsonModelLoader;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;

public class TkkStaticAnimation extends StaticAnimation {
    public StaticAnimation sourceAnimation;
    public float convertTimeModifier;

    /*
    public final Map<AnimationProperty<?>, Object> properties = Maps.newHashMap();
    public final StateSpectrum.Blueprint stateSpectrumBlueprint = new StateSpectrum.Blueprint();
    public final ResourceLocation resourceLocation;
    public final Model model;
    public int namespaceId;
    public int animationId;

    public final StateSpectrum stateSpectrum = new StateSpectrum();
    */
    /*
    public StaticAnimation() {
        super(0.0F, false);
        sourceAnimation.namespaceId = -1;
        sourceAnimation.animationId = -1;
        sourceAnimation.resourceLocation = null;
        sourceAnimation.model = null;
    }

    public StaticAnimation(boolean repeatPlay, String path, Model model) {
        sourceAnimation(ConfigurationIngame.GENERAL_ANIMATION_CONVERT_TIME, repeatPlay, path, model);
    }

    public StaticAnimation(float convertTime, boolean isRepeat, String path, Model model) {
        super(convertTime, isRepeat);

        AnimationManager animationManager = EpicFightMod.getInstance().animationManager;
        sourceAnimation.namespaceId = animationManager.getNamespaceHash();
        sourceAnimation.animationId = animationManager.getIdCounter();

        animationManager.getIdMap().put(sourceAnimation.animationId, sourceAnimation);
        sourceAnimation.resourceLocation = new ResourceLocation(animationManager.getModid(), "animmodels/animations/" + path);
        animationManager.getNameMap().put(new ResourceLocation(animationManager.getModid(), path), sourceAnimation);
        sourceAnimation.model = model;
    }

    public StaticAnimation(float convertTime, boolean repeatPlay, String path, Model model, boolean notRegisteredInAnimationManager) {
        super(convertTime, repeatPlay);
        sourceAnimation.namespaceId = -1;
        sourceAnimation.animationId = -1;
        sourceAnimation.resourceLocation = new ResourceLocation(EpicFightMod.getInstance().animationManager.getModid(), "animmodels/animations/" + path);
        sourceAnimation.model = model;
    }


     */
    public static void load(IResourceManager resourceManager, StaticAnimation animation) {
        ResourceLocation extenderPath = new ResourceLocation(animation.resourceLocation.getNamespace(), animation.resourceLocation.getPath() + ".json");
        (new JsonModelLoader(resourceManager, extenderPath)).loadStaticAnimation(animation);
    }

    public static void loadBothSide(IResourceManager resourceManager, StaticAnimation animation) {
        ResourceLocation extenderPath = new ResourceLocation(animation.resourceLocation.getNamespace(), animation.resourceLocation.getPath() + ".json");
        (new JsonModelLoader(resourceManager, extenderPath)).loadStaticAnimationBothSide(animation);
    }

    public void loadAnimation(IResourceManager resourceManager) {
        try {
            int id = Integer.parseInt(sourceAnimation.resourceLocation.getPath().substring(22));
            StaticAnimation animation = EpicFightMod.getInstance().animationManager.findAnimationById(sourceAnimation.namespaceId, id);
            sourceAnimation.jointTransforms = animation.jointTransforms;
            sourceAnimation.setTotalTime(animation.totalTime);
        } catch (NumberFormatException e) {
            load(resourceManager, sourceAnimation);
        }

        sourceAnimation.onLoaded();
    }

    public void onLoaded() {
        sourceAnimation.stateSpectrum.readFrom(sourceAnimation.stateSpectrumBlueprint);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        sourceAnimation.getProperty(AnimationProperty.StaticAnimationProperty.EVENTS).ifPresent((events) -> {
            for (Event event : events) {
                if (event.time == Event.ON_BEGIN) {
                    event.testAndExecute(entitypatch);
                }
            }
        });
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, boolean isEnd) {
        sourceAnimation.getProperty(AnimationProperty.StaticAnimationProperty.EVENTS).ifPresent((events) -> {
            for (Event event : events) {
                if (event.time == Event.ON_END) {
                    event.testAndExecute(entitypatch);
                }
            }
        });
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        sourceAnimation.getProperty(AnimationProperty.StaticAnimationProperty.EVENTS).ifPresent((events) -> {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(sourceAnimation);

            if (player != null) {
                float prevElapsed = player.getPrevElapsedTime();
                float elapsed = player.getElapsedTime();

                for (Event event : events) {
                    if (event.time != Event.ON_BEGIN && event.time != Event.ON_END) {
                        if (event.time < prevElapsed || event.time >= elapsed) {
                            continue;
                        } else {
                            event.testAndExecute(entitypatch);
                        }
                    }
                }
            }
        });
    }

    /*
    @Override
    public final EntityState getState(float time) {
        return sourceAnimation.stateSpectrum.bindStates(time);
    }

     */

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, String joint) {
        if (!super.isJointEnabled(entitypatch, joint)) {
            return false;
        } else {
            boolean bool = sourceAnimation.getProperty(ClientAnimationProperties.JOINT_MASK).map((bindModifier) -> {
                return !bindModifier.isMasked(entitypatch.getCurrentLivingMotion(), joint);
            }).orElse(true);

            return bool;
        }
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public JointMask.BindModifier getBindModifier(LivingEntityPatch<?> entitypatch, String joint) {
        return sourceAnimation.getProperty(ClientAnimationProperties.JOINT_MASK).map((jointMaskEntry) -> {
            List<JointMask> list = jointMaskEntry.getMask(entitypatch.getCurrentLivingMotion());
            int position = list.indexOf(JointMask.of(joint));

            if (position >= 0) {
                return list.get(position).getBindModifier();
            } else {
                return null;
            }
        }).orElse(null);
    }

    @Override
    public int getNamespaceId() {
        return sourceAnimation.namespaceId;
    }

    @Override
    public int getId() {
        return sourceAnimation.animationId;
    }

    public ResourceLocation getLocation() {
        return sourceAnimation.resourceLocation;
    }

    public Model getModel() {
        return sourceAnimation.model;
    }

    public boolean isBasicAttackAnimation() {
        return false;
    }

    @Override
    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        return sourceAnimation.getProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED).orElse(1.0F);
    }

    @Override
    public String toString() {
        String classPath = sourceAnimation.getClass().toString();
        return classPath.substring(classPath.lastIndexOf(".") + 1) + " " + sourceAnimation.getLocation();
    }

    public <V> StaticAnimation addProperty(AnimationProperty.StaticAnimationProperty<V> propertyType, V value) {
        sourceAnimation.properties.put(propertyType, value);
        return sourceAnimation;
    }

    public StateSpectrum.Blueprint getStateSpectrumBP() {
        return sourceAnimation.stateSpectrumBlueprint;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Optional<V> getProperty(AnimationProperty<V> propertyType) {
        return (Optional<V>) Optional.ofNullable(sourceAnimation.properties.get(propertyType));
    }

    @OnlyIn(Dist.CLIENT)
    public Layer.Priority getPriority() {
        return sourceAnimation.getProperty(ClientAnimationProperties.PRIORITY).orElse(Layer.Priority.LOWEST);
    }

    @OnlyIn(Dist.CLIENT)
    public Layer.LayerType getLayerType() {
        return sourceAnimation.getProperty(ClientAnimationProperties.LAYER_TYPE).orElse(Layer.LayerType.BASE_LAYER);
    }




}
