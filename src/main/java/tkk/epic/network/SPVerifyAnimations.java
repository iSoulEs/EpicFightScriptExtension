package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPVerifyAnimations {
    public SPVerifyAnimations(){
    }


    public static SPVerifyAnimations fromBytes(PacketBuffer buf) {
        SPVerifyAnimations msg = new SPVerifyAnimations();
        return msg;
    }

    public static void toBytes(SPVerifyAnimations msg, PacketBuffer buf) {
    }


    public static void handle(SPVerifyAnimations msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TkkEpicNetworkManager.sendToServer(new CPVerifyAnimations());
        });
        ctx.get().setPacketHandled(true);
    }
}
