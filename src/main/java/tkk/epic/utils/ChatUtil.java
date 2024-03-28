package tkk.epic.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.Level;
import tkk.epic.TkkEpic;
import tkk.epic.network.SPNoSpamChat;
import tkk.epic.network.TkkEpicNetworkManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class ChatUtil {

    private static Method method;

    private static HashMap<UUID, Set<Integer>> chatList=new HashMap<>();

    public static void sendNoSpamMessages(ITextComponent[] messages, int id) {
        if(method==null){
            try {
                method = NewChatGui.class.getDeclaredMethod("func_146234_a",ITextComponent.class,int.class);
                method.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        NewChatGui chat = (Minecraft.getInstance()).gui.getChat();
        try {
            for (int i = 0; i < messages.length; i++) {
                method.invoke(chat, messages[i], id*100+i);
            }
        }catch (InvocationTargetException e) {
            TkkEpic.LOGGER.log(Level.ERROR,"tkk2:"+e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            TkkEpic.LOGGER.log(Level.ERROR,"tkk3:"+e);
            e.printStackTrace();
        }
    }

    public static ITextComponent wrap(String s) {
        return (ITextComponent)new StringTextComponent(s);
    }

    public static ITextComponent[] wrap(String... s) {
        ITextComponent[] ret = new ITextComponent[s.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = wrap(s[i]);
        return ret;
    }


    public static void sendNoSpam(ServerPlayerEntity player,int id, String... lines) {
        sendNoSpam(player,id, wrap(lines));
    }

    public static void sendNoSpam(ServerPlayerEntity player,int id, ITextComponent... lines) {
        if (lines.length > 0)
            TkkEpicNetworkManager.sendToPlayer(new SPNoSpamChat(id,lines), player);
    }
    public static String localize(String input, Object... format) {
        return I18n.get(input, format);
    }
    public static String[] localizeAll(String[] input) {
        String[] ret = new String[input.length];
        for (int i = 0; i < input.length; i++)
            ret[i] = localize(input[i], new Object[0]);
        return ret;
    }
}
