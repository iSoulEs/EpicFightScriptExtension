package reascer.wom.client.particle;


import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import reascer.wom.particle.WOMEpicFightParticle;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class AntitheusHitParticle extends MetaParticle {
    public AntitheusHitParticle(ClientWorld world, double x, double y, double z, double width, double height, double _null) {
        super(world, x, y, z);
        this.x = x + (this.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.random.nextDouble() + height) * 0.5D;
        this.z = z + (this.random.nextDouble() - 0.5D) * width;
        this.level.addParticle(WOMEpicFightParticle.ANTITHEUS_CUT.get(), this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
        double d = 0.20000000298023224D;
        for (int i = 0; i < 6; i++) {
            double particleMotionX = this.random.nextDouble() * d;
            d *= this.random.nextBoolean() ? 1.0D : -1.0D;
            double particleMotionZ = this.random.nextDouble() * d;
            d *= this.random.nextBoolean() ? 1.0D : -1.0D;
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y, this.z, particleMotionX, 0.0D, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AntitheusHitParticle particle = new AntitheusHitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return (Particle)particle;
        }
    }
}
