package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.TkkEpic;
import tkk.epic.utils.AntiCheatUtil;

import java.util.function.Supplier;

public class CPVerifyAnimations {
    public String animationsHashcode;
    public CPVerifyAnimations(){
        animationsHashcode=AntiCheatUtil.getAnimationsHash();
    }
    public CPVerifyAnimations(String hashcode){
        animationsHashcode=hashcode;
    }


    public static CPVerifyAnimations fromBytes(PacketBuffer buf) {
        CPVerifyAnimations msg = new CPVerifyAnimations(buf.readUtf());
        return msg;
    }

    public static void toBytes(CPVerifyAnimations msg, PacketBuffer buf) {
        buf.writeUtf(msg.animationsHashcode);
    }


    public static void handle(CPVerifyAnimations msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            AntiCheatUtil.PLAYER_ANIMATIONS_HASHCODE.put(ctx.get().getSender().getName().getString(),msg.animationsHashcode);
            TkkEpic.getInstance().broadcast("§a§l"+ctx.get().getSender().getName().getString()+" animations verify §e"+msg.animationsHashcode);
        });

        ctx.get().setPacketHandled(true);
    }
}
