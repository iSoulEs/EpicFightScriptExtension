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

public class AntitheusPunchHitParticle extends MetaParticle {
    /* JADX WARN: Type inference failed for: r0v20, types: [double] */
    public AntitheusPunchHitParticle(ClientWorld world, double x, double y, double z, double width, double height, double _null) {
        super(world, x, y, z);
        this.x = x + ((this.random.nextDouble() - 0.5d) * width);
        this.y = y + ((this.random.nextDouble() + height) * 0.5d);
        this.z = z + ((this.random.nextDouble() - 0.5d) * width);
        this.level.addParticle( WOMEpicFightParticle.ANTITHEUS_PUNCH.get(), this.x, this.y, this.z, 0.0d, 0.0d, 0.0d);
        char c = 0;
        for (int i = 0; i < 6; i++) {
            double particleMotionX = this.random.nextDouble() * c;
            double d = c * (this.random.nextBoolean() ? 1.0d : -1.0d);
            double particleMotionZ = this.random.nextDouble() * d;
            c = (char) (d * (this.random.nextBoolean() ? 1.0d : -1.0d));
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y, this.z, particleMotionX, 0.0d, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    /* loaded from: 旧版[付费的史诗战斗mod]WeaponsOfMinecraft-1.6.jar:reascer/wom/client/particle/AntitheusPunchHitParticle$Provider.class */
    public static class Provider implements IParticleFactory<BasicParticleType> {
        /* renamed from: createParticle */
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AntitheusPunchHitParticle particle = new AntitheusPunchHitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}
