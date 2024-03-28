package tkk.epic;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tkk.epic.capability.CapabilityEventLoader;
import tkk.epic.commands.VerifyAnimationsCommand;
import tkk.epic.gui.hud.hotbar.HotBarManager;
import tkk.epic.key.KeyEventLoader;
import tkk.epic.key.KeybindingsManager;
import tkk.epic.network.TkkEpicNetworkManager;
import tkk.epicrelic.Enr;
import tkk.tkklib.TkkGameLib;

import java.io.File;
import java.util.UUID;

//@Mod("tkkepic")
public class TkkEpic {
    public static final String MODID = "tkkepic";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static File MOD_DIR= new File(FMLPaths.GAMEDIR.get().toFile().toString()+"//TkkEpicNpc");
    public static MinecraftServer Server;
    private static TkkEpic instance;

    public static TkkEpic getInstance() {
        return instance;
    }

    public TkkEpic(){
        instance=this;
        LOGGER.log(Level.INFO,"========================");
        LOGGER.log(Level.INFO,"【tkkepic】 1.0.0");
        LOGGER.log(Level.INFO,"author Praise_suffering");
        LOGGER.log(Level.INFO,"========================");
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::doCommandStuff);
        bus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        new TkkGameLib();
        new Enr();
    }
    public void doCommandStuff(FMLCommonSetupEvent event){
        event.enqueueWork(TkkEpicNetworkManager::registerPackets);
        CapabilityEventLoader.reg();
    }
    public void doClientStuff(final FMLClientSetupEvent event){
        MinecraftForge.EVENT_BUS.register(KeyEventLoader.class);
        KeybindingsManager.keybindingsRegister();
        HotBarManager.managerRegister();
    }
    @SubscribeEvent
    public void setAboutToStart(FMLServerAboutToStartEvent event) {
        Server = event.getServer();
    }
    @SubscribeEvent
    public void onCommandRegistry(final RegisterCommandsEvent event) {
        VerifyAnimationsCommand.register(event.getDispatcher());
    }
    public void broadcast(String message){
        if(Server!=null){
            PlayerList playerList=Server.getPlayerList();
            playerList.broadcastMessage(new TranslationTextComponent("[Server] "+message), ChatType.SYSTEM, UUID.randomUUID());
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            // 在客户端执行的代码
            Minecraft.getInstance().gui.getChat().addMessage(new TranslationTextComponent("[Client] "+message));
        });
    }

}
