package tkk.epic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.collider.CustomOBBCollider;
import tkk.epic.collider.TkkCustomCollider;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

public class SPCustomCollider {
    public int entity;
    public double[] replace;
    public double[] readyReplace;
    public double[] mainHand;
    public double[] offHand;


    public SPCustomCollider(){

    }
    public SPCustomCollider(LivingEntityPatch patch){
        entity=patch.getOriginal().getId();
        TkkCustomCollider tcc=patch.getAnimator().tkkCustomCollider;
        if(tcc.replace!=null){
            replace=tcc.replace.buildArgs;
        }
        if(tcc.readyReplace!=null){
            readyReplace=tcc.readyReplace.buildArgs;
        }
        if(tcc.mainHand!=null){
            mainHand=tcc.mainHand.buildArgs;
        }
        if(tcc.offHand!=null){
            offHand=tcc.offHand.buildArgs;
        }
    }


    public static SPCustomCollider fromBytes(PacketBuffer buf) {
        SPCustomCollider msg = new SPCustomCollider();
        msg.entity=buf.readInt();
        int length;

        length=buf.readInt();
        if(length!=0){
            msg.replace=new double[length];
            for (int i=0;i<length;i++){
                msg.replace[i]=buf.readDouble();
            }
        }
        length=buf.readInt();
        if(length!=0){
            msg.readyReplace=new double[length];
            for (int i=0;i<length;i++){
                msg.readyReplace[i]=buf.readDouble();
            }
        }
        length=buf.readInt();
        if(length!=0){
            msg.mainHand=new double[length];
            for (int i=0;i<length;i++){
                msg.mainHand[i]=buf.readDouble();
            }
        }
        length=buf.readInt();
        if(length!=0){
            msg.offHand=new double[length];
            for (int i=0;i<length;i++){
                msg.offHand[i]=buf.readDouble();
            }
        }


        return msg;
    }

    public static void toBytes(SPCustomCollider msg, PacketBuffer buf) {
        buf.writeInt(msg.entity);
        if(msg.replace!=null){
            buf.writeInt(msg.replace.length);
            for (double d: msg.replace){
                buf.writeDouble(d);
            }
        }else{
            buf.writeInt(0);
        }
        if(msg.readyReplace!=null){
            buf.writeInt(msg.readyReplace.length);
            for (double d: msg.readyReplace){
                buf.writeDouble(d);
            }
        }else{
            buf.writeInt(0);
        }
        if(msg.mainHand!=null){
            buf.writeInt(msg.mainHand.length);
            for (double d: msg.mainHand){
                buf.writeDouble(d);
            }
        }else{
            buf.writeInt(0);
        }
        if(msg.offHand!=null){
            buf.writeInt(msg.offHand.length);
            for (double d: msg.offHand){
                buf.writeDouble(d);
            }
        }else{
            buf.writeInt(0);
        }

    }

    public static void handle(SPCustomCollider msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entity);
            if (entity != null) {
                EntityPatch<?> patch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if(patch instanceof LivingEntityPatch){
                    Animator animator = ((LivingEntityPatch)patch).getAnimator();
                    //我不知道这是什么原理，在播放动作后同步(update方法 填充replace后)再这样同步，就能正常渲染
                    //此程序依靠bug运行
                    //animator.tkkCustomCollider.readyReplace= CustomOBBCollider.createFromArray(msg.replace);

                    animator.tkkCustomCollider.replace= CustomOBBCollider.createFromArray(msg.replace);
                    animator.tkkCustomCollider.readyReplace= CustomOBBCollider.createFromArray(msg.readyReplace);
                    animator.tkkCustomCollider.mainHand= CustomOBBCollider.createFromArray(msg.mainHand);
                    animator.tkkCustomCollider.offHand= CustomOBBCollider.createFromArray(msg.offHand);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
}
