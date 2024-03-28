package tkk.epic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MovementInput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.event.SpellKeybindingUpEvent;

import java.util.function.Supplier;

public class CPTkkSpellUp {
    public int spellId;
    public boolean isEnable;
    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public CPTkkSpellUp(){}
    public CPTkkSpellUp(int id, boolean isEnable){
        this.spellId=id;
        this.isEnable=isEnable;
        MovementInput input = Minecraft.getInstance().player.input;
        this.forward=input.up;
        this.backward = input.down;
        this.left = input.left;
        this.right = input.right;
    }
    public CPTkkSpellUp(int id, boolean isEnable,boolean forward,boolean backward,boolean left,boolean right){
        this.spellId=id;
        this.isEnable=isEnable;
        this.forward=forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
    }

    public static CPTkkSpellUp fromBytes(PacketBuffer buf) {
        CPTkkSpellUp msg = new CPTkkSpellUp(buf.readInt(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean());
        return msg;
    }

    public static void toBytes(CPTkkSpellUp msg, PacketBuffer buf) {
        buf.writeInt(msg.spellId);
        buf.writeBoolean(msg.isEnable);
        buf.writeBoolean(msg.forward);
        buf.writeBoolean(msg.backward);
        buf.writeBoolean(msg.left);
        buf.writeBoolean(msg.right);
    }

    public static void handle(CPTkkSpellUp msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SpellKeybindingUpEvent event=new SpellKeybindingUpEvent(ctx.get().getSender(), msg.spellId, msg.isEnable, msg.forward, msg.backward,msg.left,msg.right);
            MinecraftForge.EVENT_BUS.post(event);
        });

        ctx.get().setPacketHandled(true);
    }


}
