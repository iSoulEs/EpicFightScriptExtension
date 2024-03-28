package com.jvn.efst.events;

import com.jvn.efst.EpicAddon;
import com.jvn.efst.command.CmdMgr;
import com.jvn.efst.renderer.SwordTrail.BladeTrailParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class ModEvents {
    private static final Logger LOGGER = LogManager.getLogger();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerClientCommands(RegisterCommandsEvent event) {
        CmdMgr.registerClientCommands(event);
    }

}
