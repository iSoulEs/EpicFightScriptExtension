package com.jvn.efst.events;

import com.jvn.efst.EpicAddon;
import com.jvn.efst.renderer.SwordTrail.BladeTrailBParticle;
import com.jvn.efst.renderer.SwordTrail.BladeTrailParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//@OnlyIn(Dist.CLIENT)
//@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)

@Mod.EventBusSubscriber(
        modid = EpicAddon.MODID,
        value = { Dist.CLIENT },
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class RegParticle {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EpicAddon.MODID);
    public static final RegistryObject<BasicParticleType> BLADE_TRAIL = PARTICLES.register("blade_trail", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BLADE_TRAIL_B = PARTICLES.register("blade_trail_b", () -> new BasicParticleType(true));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registryParticles(ParticleFactoryRegisterEvent event){
        ParticleManager PE = Minecraft.getInstance().particleEngine;
        //System.out.println("RegPart");
        PE.register(BLADE_TRAIL.get(), BladeTrailParticle.Provider::new);
        PE.register(BLADE_TRAIL_B.get(), BladeTrailBParticle.Provider::new);
    }
}
