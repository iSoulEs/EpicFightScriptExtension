package tkk.epic.collider;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import tkk.epic.network.SPCustomCollider;
import tkk.epic.network.TkkEpicNetworkManager;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class TkkCustomCollider {
    public CustomOBBCollider replace;
    public CustomOBBCollider mainHand;
    public CustomOBBCollider offHand;

    public CustomOBBCollider readyReplace;


    public TkkCustomCollider(){}

    public String toString(){
        return "TkkCustomCollider[replace:"+((replace==null)?null:replace.modelCenter)+",readyReplace:"+((readyReplace==null)?null:readyReplace.modelCenter)+"]";
    }
    public final Collider getCollider(Collider source){
       return (replace!=null)?replace:source;
    }
    public final Collider getHandCollider(Hand hand, Collider source){
        if(hand==Hand.MAIN_HAND){
            return (mainHand!=null)?mainHand:source;
        }else{
            return (offHand!=null)?offHand:source;
        }
    }



    //更新
    public final void updateCollider(){
        replace=readyReplace;
        readyReplace=null;
    }

    public final void putReplaceCollider(double posX, double posY, double posZ, double center_x, double center_y, double center_z){
        readyReplace=new CustomOBBCollider(posX,posY,posZ,center_x,center_y,center_z);
    }
    public final void setMainHandCollider(double posX, double posY, double posZ, double center_x, double center_y, double center_z){
        mainHand=new CustomOBBCollider(posX,posY,posZ,center_x,center_y,center_z);
    }
    public final void setOffHandCollider(double posX, double posY, double posZ, double center_x, double center_y, double center_z){
        offHand=new CustomOBBCollider(posX,posY,posZ,center_x,center_y,center_z);
    }
    public final void clearMainHandCollider(){
        mainHand=null;
    }
    public final void clearOffHandCollider(){
        offHand=null;
    }

    public final void sync(LivingEntityPatch patch){
        SPCustomCollider msg=new SPCustomCollider(patch);
        TkkEpicNetworkManager.sendToAllPlayerTrackingThisEntity(msg,patch.getOriginal());
        if(patch.getOriginal() instanceof ServerPlayerEntity){
            TkkEpicNetworkManager.sendToPlayer(msg,(ServerPlayerEntity) patch.getOriginal());
        }
    }
}
