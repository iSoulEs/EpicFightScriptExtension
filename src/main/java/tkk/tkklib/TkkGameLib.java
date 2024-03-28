package tkk.tkklib;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tkk.epic.js.JsContainer;
import tkk.tkklib.gui.ContainerTypeRegistry;
import tkk.tkklib.network.TkkNetworkManager;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;
import java.util.Set;

//@Mod("tkklib")
public class TkkGameLib implements ITransformationService {
    // Directly reference a log4j logger.
    public static final String MOD_ID="tkklib";
    private static final Logger LOGGER = LogManager.getLogger();
    public static File MOD_DIR= new File(FMLPaths.GAMEDIR.get().toFile().toString()+"//TkkGameLib");
    public static JsContainer mainJS;
    public static MinecraftServer server;
    public static void print(String message){
        LOGGER.log(Level.ERROR,message);
    }
    public TkkGameLib() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doServerStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::FMLLoadCompleteEvent);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ContainerTypeRegistry.CONTAINERS.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        //LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        if (!MOD_DIR.exists()) {
            MOD_DIR.mkdir();
        }
        event.enqueueWork(TkkNetworkManager::registerPackets);


        mainJS=JsTool.getJS("main");
        print("TkkGameLib setup!");
        mainJS.run("FMLCommonSetupEvent",event);
        JSPluginManager.INSTANCE.loaderJSPlugin();
        JSPluginManager.INSTANCE.callPluginRunFN("FMLCommonSetupEvent",event);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        event.enqueueWork(() -> {
            ContainerTypeRegistry.bindContainer();
        });



        mainJS.run("FMLClientSetupEvent",event);
        JSPluginManager.INSTANCE.callPluginRunFN("FMLClientSetupEvent",event);
    }
    private void doServerStuff(final FMLDedicatedServerSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        mainJS.run("FMLDedicatedServerSetupEvent",event);
        JSPluginManager.INSTANCE.callPluginRunFN("FMLDedicatedServerSetupEvent",event);
    }
    private void FMLLoadCompleteEvent(final FMLLoadCompleteEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        mainJS.run("FMLLoadCompleteEvent",event);
        JSPluginManager.INSTANCE.callPluginRunFN("FMLLoadCompleteEvent",event);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        //LOGGER.info("HELLO from server starting");
        mainJS.run("FMLServerStartingEvent",event);
        JSPluginManager.INSTANCE.callPluginRunFN("FMLServerStartingEvent",event);
    }

    @Nonnull
    @Override
    public String name() {
        return "tkklib";
    }

    @Override
    public void initialize(IEnvironment event) {

    }

    @Override
    public void beginScanning(IEnvironment environment) {

    }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {

    }

    @Nonnull
    @Override
    public List<ITransformer> transformers() {
        return null;
    }



}