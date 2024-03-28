package tkk.tkklib.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.tkklib.TkkGameLib;
import tkk.tkklib.gui.TestGui;

import java.util.function.Supplier;

public class SPOpenGui {
    public SPOpenGui(){

    }


    public static SPOpenGui fromBytes(PacketBuffer buf) {
        SPOpenGui msg = new SPOpenGui();
        return msg;
    }

    public static void toBytes(SPOpenGui msg, PacketBuffer buf) {
    }


    public static void handle(SPOpenGui msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().forceSetScreen(new TestGui(new TranslationTextComponent(TkkGameLib.MOD_ID + ".test")));
        });
        ctx.get().setPacketHandled(true);
    }
}
