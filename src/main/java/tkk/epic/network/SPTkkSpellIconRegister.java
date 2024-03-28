package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.gui.hud.hotbar.HotBarManager;

import java.util.function.Supplier;

public class SPTkkSpellIconRegister {
    public String id;
    public String namespace;
    public String patch;

    public SPTkkSpellIconRegister(){}
    public SPTkkSpellIconRegister(String id,String namespace,String patch){
        this.id = id;
        this.namespace = namespace;
        this.patch = patch;
    }

    public static SPTkkSpellIconRegister fromBytes(PacketBuffer buf) {
        SPTkkSpellIconRegister msg = new SPTkkSpellIconRegister(buf.readUtf(),buf.readUtf(),buf.readUtf());
        return msg;
    }

    public static void toBytes(SPTkkSpellIconRegister msg, PacketBuffer buf) {
        buf.writeUtf(msg.id);
        buf.writeUtf(msg.namespace);
        buf.writeUtf(msg.patch);
    }

    public static void handle(SPTkkSpellIconRegister msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(!HotBarManager.SPELL_BIND_TEXTURES.containsKey(msg.id)){
                ResourceLocation rl=new ResourceLocation(msg.namespace, msg.patch);
                HotBarManager.SPELL_BIND_TEXTURES.put(msg.id, rl);
            }
        });

        ctx.get().setPacketHandled(true);
    }

}
