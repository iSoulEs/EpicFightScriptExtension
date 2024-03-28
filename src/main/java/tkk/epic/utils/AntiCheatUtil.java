package tkk.epic.utils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import tkk.epic.network.SPVerifyAnimations;
import tkk.epic.network.TkkEpicNetworkManager;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.main.EpicFightMod;

import java.util.HashMap;
import java.util.Map;

public class AntiCheatUtil {
    public static HashMap<String,String> PLAYER_ANIMATIONS_HASHCODE=new HashMap<>();

    public static void verifyAnimationsHashcode(ServerPlayerEntity player){
        TkkEpicNetworkManager.sendToPlayer(new SPVerifyAnimations(),player);
    }


    public static String getAnimationsHash() {
        Map<ResourceLocation, StaticAnimation> nameMap = EpicFightMod.getInstance().animationManager.getNameMap();
        StringBuffer hash = new StringBuffer();
        for (ResourceLocation rl : nameMap.keySet()) {
            hash.append(rl.hashCode());
            StaticAnimation anim=nameMap.get(rl);
            hash.append(anim.convertTime);
            if(anim instanceof AttackAnimation){
                for(AttackAnimation.Phase phase:((AttackAnimation) anim).phases){
                    hash.append(phase.start);
                    hash.append(phase.antic);
                    hash.append(phase.preDelay);
                    hash.append(phase.contact);
                    hash.append(phase.recovery);
                    hash.append(phase.end);
                    hash.append(phase.hand);
                    for(AnimationProperty.AttackPhaseProperty app:phase.properties.keySet()){
                        hash.append(app.getClass().toString().hashCode());
                    }
                }
            }
            Map<String, TransformSheet> jointTransforms = anim.jointTransforms;
            for (String s : jointTransforms.keySet()) {
                {
                    hash.append(s.hashCode());
                    for (Keyframe k : jointTransforms.get(s).getKeyframes()) {
                        hash.append(k.transform().toString().hashCode());
                    }
                }
            }
        }
        return hash.toString().hashCode()+"";
    }
}
