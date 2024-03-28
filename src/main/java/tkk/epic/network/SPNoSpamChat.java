package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.utils.ChatUtil;

import java.util.function.Supplier;

public class SPNoSpamChat {
    public ITextComponent[] chatLines;
    public int id;

    public SPNoSpamChat() {
        this.id=0;
        this.chatLines = new ITextComponent[0];
    }

    public SPNoSpamChat(int id,ITextComponent... lines) {
        this.id=id;
        this.chatLines = lines;
    }

    public static SPNoSpamChat fromBytes(PacketBuffer buf) {
        SPNoSpamChat msg = new SPNoSpamChat(buf.readInt(),new ITextComponent[buf.readInt()]);
        for (int i = 0; i < msg.chatLines.length; i++)
            msg.chatLines[i] = (ITextComponent)ITextComponent.Serializer.fromJsonLenient(buf.readUtf());
        return msg;
    }

    public static void toBytes(SPNoSpamChat msg, PacketBuffer buf) {
        buf.writeInt(msg.id);
        buf.writeInt(msg.chatLines.length);
        for (ITextComponent c : msg.chatLines)
            buf.writeUtf(ITextComponent.Serializer.toJson(c));
    }


    public static void handle(SPNoSpamChat message, Supplier<NetworkEvent.Context> context) {
        ((NetworkEvent.Context)context.get()).enqueueWork(() -> ChatUtil.sendNoSpamMessages(message.chatLines, message.id));
        ((NetworkEvent.Context)context.get()).setPacketHandled(true);
    }
}
