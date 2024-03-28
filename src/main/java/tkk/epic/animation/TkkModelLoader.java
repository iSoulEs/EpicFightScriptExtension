package tkk.epic.animation;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.commons.lang3.ArrayUtils;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.main.EpicFightMod;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TkkModelLoader {
    public static final OpenMatrix4f CORRECTION = OpenMatrix4f.createRotatorDeg(-90.0F, Vec3f.X_AXIS);

    private static int[] toIntArray(JsonArray array) {
        List<Integer> result = Lists.newArrayList();
        for (JsonElement je : array) {
            result.add(je.getAsInt());
        }

        return ArrayUtils.toPrimitive(result.toArray(new Integer[0]));
    }

    private static float[] toFloatArray(JsonArray array) {
        List<Float> result = Lists.newArrayList();
        for (JsonElement je : array) {
            result.add(je.getAsFloat());
        }

        return ArrayUtils.toPrimitive(result.toArray(new Float[0]));
    }

    private JsonObject rootJson;

    public TkkModelLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
        try {
            if (resourceManager == null) {
                Class<?> modClass = ModList.get().getModObjectById(resourceLocation.getNamespace()).get().getClass();
                BufferedInputStream inputstream = new BufferedInputStream(modClass.getResourceAsStream("/assets/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath()));
                Reader reader = new InputStreamReader(inputstream, StandardCharsets.UTF_8);
                JsonReader in = new JsonReader(reader);
                in.setLenient(true);
                this.rootJson = Streams.parse(in).getAsJsonObject();
            } else {
                IResource resource = resourceManager.getResource(resourceLocation);
                JsonReader in = new JsonReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                in.setLenient(true);
                this.rootJson = Streams.parse(in).getAsJsonObject();
            }
        } catch (Exception e) {
            EpicFightMod.LOGGER.info("Can't read " + resourceLocation.toString() + " because " + e);
        }
    }

    public boolean isValidSource() {
        return this.rootJson != null;
    }

    /*
    @OnlyIn(Dist.CLIENT)
    public ClientModel.RenderProperties getRenderProperties() {
        JsonObject properties = this.rootJson.getAsJsonObject("render_properties");

        if (properties != null) {
            return ClientModel.RenderProperties.builder()
                    .transparency(properties.has("transparent") ? properties.get("transparent").getAsBoolean() : false)
                    .build();
        } else {
            return ClientModel.RenderProperties.builder().build();
        }
    }

     */
    /*
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getParent() {
        return this.rootJson.has("parent") ? new ResourceLocation(this.rootJson.get("parent").getAsString()) : null;
    }

    @OnlyIn(Dist.CLIENT)
    public Mesh getMesh() {
        JsonObject obj = this.rootJson.getAsJsonObject("vertices");
        JsonObject positions = obj.getAsJsonObject("positions");
        JsonObject normals = obj.getAsJsonObject("normals");
        JsonObject uvs = obj.getAsJsonObject("uvs");
        JsonObject vdincies = obj.getAsJsonObject("vindices");
        JsonObject weights = obj.getAsJsonObject("weights");
        JsonObject drawingIndices = obj.getAsJsonObject("indices");
        JsonObject vcounts = obj.getAsJsonObject("vcounts");
        float[] positionArray = toFloatArray(positions.get("array").getAsJsonArray());

        for (int i = 0; i < positionArray.length / 3; i++) {
            int k = i * 3;
            Vec4f posVector = new Vec4f(positionArray[k], positionArray[k+1], positionArray[k+2], 1.0F);
            OpenMatrix4f.transform(CORRECTION, posVector, posVector);
            positionArray[k] = posVector.x;
            positionArray[k+1] = posVector.y;
            positionArray[k+2] = posVector.z;
        }

        float[] normalArray = toFloatArray(normals.get("array").getAsJsonArray());

        for (int i = 0; i < normalArray.length / 3; i++) {
            int k = i * 3;
            Vec4f normVector = new Vec4f(normalArray[k], normalArray[k+1], normalArray[k+2], 1.0F);
            OpenMatrix4f.transform(CORRECTION, normVector, normVector);
            normalArray[k] = normVector.x;
            normalArray[k+1] = normVector.y;
            normalArray[k+2] = normVector.z;
        }

        float[] uvArray = toFloatArray(uvs.get("array").getAsJsonArray());
        int[] animationIndexArray = toIntArray(vdincies.get("array").getAsJsonArray());
        float[] weightArray = toFloatArray(weights.get("array").getAsJsonArray());
        int[] drawingIndexArray = toIntArray(drawingIndices.get("array").getAsJsonArray());
        int[] vcountArray = toIntArray(vcounts.get("array").getAsJsonArray());

        return new Mesh(positionArray, normalArray, uvArray, animationIndexArray, weightArray, drawingIndexArray, vcountArray);
    }

     */
    /*
    public Armature getArmature() {
        JsonObject obj = this.rootJson.getAsJsonObject("armature");
        JsonObject hierarchy = obj.get("hierarchy").getAsJsonArray().get(0).getAsJsonObject();
        JsonArray nameAsVertexGroups = obj.getAsJsonArray("joints");
        Map<String, Joint> jointMap = Maps.newHashMap();
        Joint joint = this.getJoint(hierarchy, nameAsVertexGroups, jointMap, true);
        joint.setInversedModelTransform(new OpenMatrix4f());
        return new Armature(jointMap.size(), joint, jointMap);
    }

     */

    /*
    public Joint getJoint(JsonObject object, JsonArray nameAsVertexGroups, Map<String, Joint> jointMap, boolean start) {
        float[] floatArray = toFloatArray(object.get("transform").getAsJsonArray());
        OpenMatrix4f localMatrix = new OpenMatrix4f().load(FloatBuffer.wrap(floatArray));
        localMatrix.transpose();
        if (start) {
            localMatrix.mulFront(CORRECTION);
        }

        String name = object.get("name").getAsString();
        int index = -1;

        for (int i = 0; i < nameAsVertexGroups.size(); i++) {
            if (name.equals(nameAsVertexGroups.get(i).getAsString())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalStateException("[ModelParsingError]: Joint name " + name + " not exist!");
        }

        Joint joint = new Joint(name, index, localMatrix);
        jointMap.put(name, joint);

        for (JsonElement children : object.get("children").getAsJsonArray()) {
            joint.addSubJoint(this.getJoint(children.getAsJsonObject(), nameAsVertexGroups, jointMap, false));
        }

        return joint;
    }
    */

    public void loadStaticAnimation(StaticAnimation animation) {
        AttackAnimation.Phase[] phaseArr;
        if (this.rootJson == null) {
            throw new IllegalStateException("[ModelParsingError]Can't find animation path: " + animation);
        }
        JsonArray array = this.rootJson.get("animation").getAsJsonArray();
        boolean action = animation instanceof ActionAnimation;
        boolean attack = animation instanceof AttackAnimation;
        boolean root = true;
        Armature armature = animation.getModel().getArmature();
        if (!action && !attack && FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            return;
        }
        Set<String> allowedJoints = Sets.newLinkedHashSet();
        if (attack) {
            for (AttackAnimation.Phase phase : ((AttackAnimation) animation).phases) {
                Joint joint = armature.getJointHierarcy();
                int pathIndex = armature.searchPathIndex(phase.getColliderJointName());
                while (joint != null) {
                    allowedJoints.add(joint.getName());
                    int nextJoint = pathIndex % 10;
                    if (nextJoint > 0) {
                        pathIndex /= 10;
                        joint = joint.getSubJoints().get(nextJoint - 1);
                    } else {
                        joint = null;
                    }
                }
				/*
				for (Pair<Joint, Collider> colliderInfo : phase.getColliders()) {
					int pathIndex = armature.searchPathIndex(((Joint) colliderInfo.getFirst()).getName());
					while (joint != null) {
						allowedJoints.add(joint.getName());
						int nextJoint = pathIndex % 10;
						if (nextJoint > 0) {
							pathIndex /= 10;
							joint = joint.getSubJoints().get(nextJoint - 1);
						} else {
							joint = null;
						}
					}
				}

				 */
            }
        } else if (action) {
            allowedJoints.add("Root");
        }
        Iterator it = array.iterator();
        while (it.hasNext()) {
            JsonElement element = (JsonElement) it.next();
            JsonObject keyObject = element.getAsJsonObject();
            String name = keyObject.get("name").getAsString();
            if (attack && FMLEnvironment.dist == Dist.DEDICATED_SERVER && !allowedJoints.contains(name)) {
                if (name.equals("Coord")) {
                    root = false;
                }
            } else {
                Joint joint2 = armature.searchJointByName(name);
                if (joint2 == null) {
                    if (name.equals("Coord") && action) {
                        JsonArray timeArray = keyObject.getAsJsonArray("time");
                        JsonArray transformArray = keyObject.getAsJsonArray("transform");
                        int timeNum = timeArray.size();
                        int matrixNum = transformArray.size();
                        float[] times = new float[timeNum];
                        float[] transforms = new float[matrixNum * 16];
                        for (int i = 0; i < timeNum; i++) {
                            times[i] = timeArray.get(i).getAsFloat();
                        }
                        for (int i2 = 0; i2 < matrixNum; i2++) {
                            JsonArray matrixJson = transformArray.get(i2).getAsJsonArray();
                            for (int j = 0; j < 16; j++) {
                                transforms[(i2 * 16) + j] = matrixJson.get(j).getAsFloat();
                            }
                        }
                        TransformSheet sheet = getTransformSheet(times, transforms, new OpenMatrix4f(), true);
                        animation.addSheet(name, sheet);
                        //((ActionAnimation) animation).addProperty((AnimationProperty.ActionAnimationProperty<AnimationProperty.ActionAnimationProperty<TransformSheet>>) AnimationProperty.ActionAnimationProperty.COORD, (AnimationProperty.ActionAnimationProperty<TransformSheet>) sheet);
                        root = false;
                    } else {
                        EpicFightMod.LOGGER.warn("[EpicFightMod] Can't find the joint " + name + " in the animation file, " + animation);
                    }
                } else {
                    JsonArray timeArray2 = keyObject.getAsJsonArray("time");
                    JsonArray transformArray2 = keyObject.getAsJsonArray("transform");
                    int timeNum2 = timeArray2.size();
                    int matrixNum2 = transformArray2.size();
                    float[] times2 = new float[timeNum2];
                    float[] transforms2 = new float[matrixNum2 * 16];
                    for (int i3 = 0; i3 < timeNum2; i3++) {
                        times2[i3] = timeArray2.get(i3).getAsFloat();
                    }
                    for (int i4 = 0; i4 < matrixNum2; i4++) {
                        JsonArray matrixJson2 = transformArray2.get(i4).getAsJsonArray();
                        for (int j2 = 0; j2 < 16; j2++) {
                            transforms2[(i4 * 16) + j2] = matrixJson2.get(j2).getAsFloat();
                        }
                    }
                    TransformSheet sheet2 = getTransformSheet(times2, transforms2, OpenMatrix4f.invert(joint2.getLocalTrasnform(), null), root);
                    animation.addSheet(name, sheet2);
                    animation.setTotalTime(times2[times2.length - 1]);
                    root = false;
                }
            }
        }

    }

    /*
    public void loadStaticAnimationBothSide(StaticAnimation animation) {
        JsonArray array = this.rootJson.get("animation").getAsJsonArray();
        boolean root = true;

        for (JsonElement element : array) {
            JsonObject keyObject = element.getAsJsonObject();
            String name = keyObject.get("name").getAsString();
            Joint joint = animation.getModel().getArmature().searchJointByName(name);

            if (joint == null) {
                throw new IllegalArgumentException("[EpicFightMod] Can't find the joint " + name + " in animation data " + animation);
            }

            JsonArray timeArray = keyObject.getAsJsonArray("time");
            JsonArray transformArray = keyObject.getAsJsonArray("transform");
            int timeNum = timeArray.size();
            int matrixNum = transformArray.size();
            float[] times = new float[timeNum];
            float[] transforms = new float[matrixNum * 16];

            for (int i = 0; i < timeNum; i++) {
                times[i] = timeArray.get(i).getAsFloat();
            }

            for (int i = 0; i < matrixNum; i++) {
                JsonArray matrixJson = transformArray.get(i).getAsJsonArray();
                for (int j = 0; j < 16; j++) {
                    transforms[i * 16 + j] = matrixJson.get(j).getAsFloat();
                }
            }

            TransformSheet sheet = getTransformSheet(times, transforms, OpenMatrix4f.invert(joint.getLocalTrasnform(), null), root);
            animation.addSheet(name, sheet);
            animation.setTotalTime(times[times.length - 1]);
            root = false;
        }
    }


     */
    private static TransformSheet getTransformSheet(float[] times, float[] trasnformMatrix, OpenMatrix4f invLocalTransform, boolean correct) {
        List<Keyframe> keyframeList = new ArrayList<Keyframe>();

        for (int i = 0; i < times.length; i++) {
            float timeStamp = times[i];
            if (timeStamp < 0) {
                continue;
            }

            float[] matrixElements = new float[16];
            for (int j = 0; j < 16; j++) {
                matrixElements[j] = trasnformMatrix[i*16 + j];
            }

            OpenMatrix4f matrix = new OpenMatrix4f().load(FloatBuffer.wrap(matrixElements));
            matrix.transpose();

            if (correct) {
                matrix.mulFront(CORRECTION);
            }

            matrix.mulFront(invLocalTransform);

            JointTransform transform = new JointTransform(matrix.toTranslationVector(), matrix.toQuaternion(), matrix.toScaleVector());
            keyframeList.add(new Keyframe(timeStamp, transform));
        }

        TransformSheet sheet = new TransformSheet(keyframeList);
        return sheet;
    }

}
