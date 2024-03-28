package reascer.wom.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reascer.wom.particle.WOMEpicFightParticle;

public class WeaponOfMinecraft {
    public static final String CONFIG_FILE_PATH = "wom.toml";
    public static final String MODID = "wom";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public WeaponOfMinecraft() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //bus.addListener(EFAnimations::registerAnimations);
        //bus.addListener(EFSkills::registerSkills);
        //EFECItems.ITEMS.register(bus);
        WOMEpicFightParticle.PARTICLES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

}
