package com.jvn.efst;

import com.jvn.efst.config.ClientConfig;
import com.jvn.efst.events.RegParticle;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
//@Mod("efst")
public class EpicAddon
{
    //public static final String MODID = "efst";
    public static final String MODID = "epicfight";
    public static EpicAddon instance;
    private static final Logger LOGGER = LogManager.getLogger();
    public EpicAddon()
    {
        instance = this;
        ClientConfig.Load();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegParticle.PARTICLES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static EpicAddon getInstance(){
        return instance;
    }
}
