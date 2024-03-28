package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.utils.SoundUtil;

import java.util.function.Supplier;

public class SPPlaySound {
    private final String name;

    private final BlockPos pos;

    private final float volume;

    private final float pitch;

    public SPPlaySound(String name, BlockPos pos, float volume, float pitch) {
        this.name = name;
        this.pos = pos;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SPPlaySound(PacketBuffer buf) {
        this(buf.readUtf(), buf.readBlockPos(), buf.readFloat(), buf.readFloat());
    }

    public static SPPlaySound fromBytes(PacketBuffer buf) {
        SPPlaySound msg = new SPPlaySound(buf);
        return msg;
    }

    public static void toBytes(SPPlaySound msg, PacketBuffer buf) {
        buf.writeUtf(msg.name);
        buf.writeBlockPos(msg.pos);
        buf.writeFloat(msg.volume);
        buf.writeFloat(msg.pitch);
    }

    public static void handle(SPPlaySound msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SoundUtil.INSTANCE.playSound(msg.name,msg.pos, msg.volume, msg.pitch);

        });

        ctx.get().setPacketHandled(true);
    }
}
