package tkk.epic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

public class SPTkkCustomData {
    private int entityId;
    private boolean tkkEnable;
    private float tkkCustomSpeed;

    public SPTkkCustomData() {
        this.entityId = 0;
        this.tkkEnable=false;
        this.tkkCustomSpeed=1.0F;
    }

    public SPTkkCustomData(int entityId,boolean tkkEnable,float tkkCustomSpeed) {
        this.entityId = entityId;
        this.tkkCustomSpeed=tkkCustomSpeed;
        this.tkkEnable=tkkEnable;
    }

    public float getTkkCustomSpeed() {
        return this.tkkCustomSpeed;
    }
    public boolean getTkkEnable(){return this.tkkEnable;}

    public static SPTkkCustomData fromBytes(PacketBuffer buf) {
        SPTkkCustomData msg = new SPTkkCustomData(buf.readInt(),buf.readBoolean(),buf.readFloat());
        return msg;
    }

    public static void toBytes(SPTkkCustomData msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.tkkEnable);
        buf.writeFloat(msg.tkkCustomSpeed);
    }

    public static void handle(SPTkkCustomData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entityId);

            if (entity != null) {
                EntityPatch<?> patch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if(patch instanceof LivingEntityPatch){
                    ((LivingEntityPatch) patch).setTkkCustomSpeed(msg.tkkCustomSpeed);
                    ((LivingEntityPatch) patch).setTkkEnableCustom(msg.tkkEnable);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
