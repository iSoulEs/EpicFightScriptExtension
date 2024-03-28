package tkk.epic.network;

import com.jvn.efst.tools.TkkTrail;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

public class SPTkkTrailUpdate {
    public int entityId;
    public boolean isMainHand;
    public boolean isValue;
    public float x;
    public float y;
    public float z;
    public float ex;
    public float ey;
    public float ez;
    public int r;
    public int g;
    public int b;
    public int a;
    public int lifetime;
    public int trailType;
    public SPTkkTrailUpdate() {
        this.entityId = 0;
    }

    public SPTkkTrailUpdate(int entityId,boolean isMainHand,boolean isValue,float x, float y, float z, float ex, float ey, float ez,int r,int g,int b,int a,int lifetime,int trailType) {
        this.entityId = entityId;
        this.isMainHand=isMainHand;
        this.isValue=isValue;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.lifetime = lifetime;
        this.trailType=trailType;
    }


    public static SPTkkTrailUpdate fromBytes(PacketBuffer buf) {
        SPTkkTrailUpdate msg = new SPTkkTrailUpdate(buf.readInt(),buf.readBoolean(),buf.readBoolean(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readInt(),buf.readInt(),buf.readInt(),buf.readInt(),buf.readInt(),buf.readInt());
        return msg;
    }

    public static void toBytes(SPTkkTrailUpdate msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.isMainHand);
        buf.writeBoolean(msg.isValue);
        buf.writeFloat(msg.x);
        buf.writeFloat(msg.y);
        buf.writeFloat(msg.z);
        buf.writeFloat(msg.ex);
        buf.writeFloat(msg.ey);
        buf.writeFloat(msg.ez);
        buf.writeInt(msg.r);
        buf.writeInt(msg.g);
        buf.writeInt(msg.b);
        buf.writeInt(msg.a);
        buf.writeInt(msg.lifetime);
        buf.writeInt(msg.trailType);
    }

    public static void handle(SPTkkTrailUpdate msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entityId);

            if (entity != null) {
                EntityPatch<?> patch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if(patch instanceof LivingEntityPatch){
                    Animator animator = ((LivingEntityPatch)patch).getAnimator();
                    TkkTrail tkkTrail = (msg.isMainHand)? animator.mainHandTrail : animator.offHandTrail;
                    tkkTrail.setReady(true);
                    tkkTrail.setIsValue(msg.isValue);
                    tkkTrail.setX(msg.x);
                    tkkTrail.setY(msg.y);
                    tkkTrail.setZ(msg.z);
                    tkkTrail.setEX(msg.ex);
                    tkkTrail.setEY(msg.ey);
                    tkkTrail.setEZ(msg.ez);

                    tkkTrail.setR(msg.r);
                    tkkTrail.setG(msg.g);
                    tkkTrail.setB(msg.b);
                    tkkTrail.setA(msg.a);
                    tkkTrail.setLifetime(msg.lifetime);
                    tkkTrail.setTrailType(msg.trailType);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
