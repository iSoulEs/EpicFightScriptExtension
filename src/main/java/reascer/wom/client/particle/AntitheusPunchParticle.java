package reascer.wom.client.particle;


import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

public class AntitheusPunchParticle extends HitParticle {
    public AntitheusPunchParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
        this.quadSize = 2.0f;
        this.lifetime = 9;
        setSpriteFromAge(animatedSprite);
        this.oRoll = 0.0f;
        this.roll = 0.0f;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        int i = this.age;
        this.age = i + 1;
        if (i >= this.lifetime) {
            remove();
        } else {
            setSpriteFromAge(this.animatedSprite);
        }
    }

    @OnlyIn(Dist.CLIENT)
    /* loaded from: 旧版[付费的史诗战斗mod]WeaponsOfMinecraft-1.6.jar:reascer/wom/client/particle/AntitheusPunchParticle$Provider.class */
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        /* renamed from: createParticle */
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            if (((Boolean) EpicFightMod.CLIENT_INGAME_CONFIG.offBloodEffects.getValue()).booleanValue()) {
                return null;
            }
            AntitheusPunchParticle particle = new AntitheusPunchParticle(worldIn, x, y, z, this.spriteSet);
            return particle;
        }
    }
}
