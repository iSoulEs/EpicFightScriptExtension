package reascer.wom.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.particle.HitParticleType;

public class WOMEpicFightParticle {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "epicfight");
    //public static final DeferredRegister<ParticleType<?>> PARTICLES = EpicFightParticles.PARTICLES;


    public static final RegistryObject<BasicParticleType> ANTITHEUS_CUT = PARTICLES.register("antitheus_cut", () -> new BasicParticleType(true));

    public static final RegistryObject<HitParticleType> ANTITHEUS_HIT = PARTICLES.register("antitheus_hit", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> ANTITHEUS_PUNCH = PARTICLES.register("antitheus_punch", () -> new BasicParticleType(true));

    public static final RegistryObject<HitParticleType> ANTITHEUS_PUNCH_HIT = PARTICLES.register("antitheus_punch_hit", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> KATANA_SHEATHED_CUT = PARTICLES.register("katana_sheathed_cut", () -> new BasicParticleType(true));

    public static final RegistryObject<HitParticleType> KATANA_SHEATHED_HIT = PARTICLES.register("katana_sheathed_hit", () -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, HitParticleType.ZERO));
}
