package tkk.epicrelic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tkk.epicrelic.attribute.EnrAttribute;
import tkk.epicrelic.attribute.EnrAttributeManager;
import tkk.epicrelic.capability.EnrCapabilityEventLoader;

import java.util.UUID;

//@Mod("enr")
public class Enr {
    public static final String MODID = "enr";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static MinecraftServer Server;
    private static Enr instance;

    public static Enr getInstance() {
        return instance;
    }

    public Enr(){
        instance=this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::doCommandStuff);
        bus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(EnrAttribute::modifyExistingMobs);
        EnrAttribute.ATTRIBUTES.register(bus);
    }
    public void doCommandStuff(FMLCommonSetupEvent event){
        EnrCapabilityEventLoader.reg();
        EnrAttributeManager.reg();
    }
    public void doClientStuff(final FMLClientSetupEvent event){
        //EnrAttributeManager.clientEvent.reg();
    }
    @SubscribeEvent
    public void setAboutToStart(FMLServerAboutToStartEvent event) {
        Server = event.getServer();
    }
    public void broadcast(String message){
        if(Server!=null){
            PlayerList playerList=Server.getPlayerList();
            playerList.broadcastMessage(new TranslationTextComponent(message), ChatType.SYSTEM, UUID.randomUUID());
        }
    }

}
