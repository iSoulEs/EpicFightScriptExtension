package tkk.epic.network;

import com.jvn.efst.tools.ParticleTrail;
import com.jvn.efst.tools.TkkTrail;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

public class SPParticleTrailAdd {
    public int entityId;
    public boolean isMainHand;
    public float x;
    public float y;
    public float z;



    public float ex;
    public float ey;
    public float ez;
    public boolean isValue;

    public String particle;
    public String args;
    public float xSpaceBetween,ySpaceBetween,zSpaceBetween;
    public float xSpeed,ySpeed,zSpeed;
    public double xDist,yDist,zDist;
    public int count;
    public SPParticleTrailAdd() {
        this.entityId = 0;
    }

    public SPParticleTrailAdd(int entityId, boolean isMainHand, float x, float y, float z, float ex, float ey, float ez, boolean isValue, String particle, String args, float xSpaceBetween, float ySpaceBetween, float zSpaceBetween, float xSpeed, float ySpeed, float zSpeed, double xDist, double yDist, double zDist, int count) {
        this.entityId = entityId;
        this.isMainHand = isMainHand;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.isValue = isValue;
        this.particle = particle;
        this.args = args;
        this.xSpaceBetween = xSpaceBetween;
        this.ySpaceBetween = ySpaceBetween;
        this.zSpaceBetween = zSpaceBetween;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
        this.xDist = xDist;
        this.yDist = yDist;
        this.zDist = zDist;
        this.count = count;
    }


    public static SPParticleTrailAdd fromBytes(PacketBuffer buf) {
        SPParticleTrailAdd msg = new SPParticleTrailAdd(buf.readInt(),buf.readBoolean(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readBoolean(),
                buf.readUtf(),buf.readUtf(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readDouble(),buf.readDouble(),buf.readDouble(),
                buf.readInt());
        return msg;
    }

    public static void toBytes(SPParticleTrailAdd msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.isMainHand);
        buf.writeFloat(msg.x);
        buf.writeFloat(msg.y);
        buf.writeFloat(msg.z);
        buf.writeFloat(msg.ex);
        buf.writeFloat(msg.ey);
        buf.writeFloat(msg.ez);
        buf.writeBoolean(msg.isValue);
        buf.writeUtf(msg.particle);
        buf.writeUtf(msg.args);
        buf.writeFloat(msg.xSpaceBetween);
        buf.writeFloat(msg.ySpaceBetween);
        buf.writeFloat(msg.zSpaceBetween);
        buf.writeFloat(msg.xSpeed);
        buf.writeFloat(msg.ySpeed);
        buf.writeFloat(msg.zSpeed);
        buf.writeDouble(msg.xDist);
        buf.writeDouble(msg.yDist);
        buf.writeDouble(msg.zDist);
        buf.writeInt(msg.count);




    }

    public static void handle(SPParticleTrailAdd msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entityId);

            if (entity != null) {
                EntityPatch<?> patch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if(patch instanceof LivingEntityPatch){
                    Animator animator = ((LivingEntityPatch)patch).getAnimator();
                    TkkTrail tkkTrail = (msg.isMainHand)? animator.mainHandTrail : animator.offHandTrail;
                    tkkTrail.addParticleTrail(new ParticleTrail.trail(msg.x,msg.y,msg.z,msg.ex,msg.ey,msg.ez,msg.isValue,new ResourceLocation(msg.particle),msg.args,msg.xSpaceBetween,msg.ySpaceBetween,msg.zSpaceBetween
                            ,msg.xSpeed,msg.ySpeed,msg.zSpeed,msg.xDist,msg.yDist,msg.zDist,msg.count
                    ));
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
