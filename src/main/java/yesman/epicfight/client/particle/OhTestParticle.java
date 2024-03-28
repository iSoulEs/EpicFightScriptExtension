package yesman.epicfight.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OhTestParticle extends SpriteTexturedParticle {
    protected final IAnimatedSprite animatedSprite;

    public OhTestParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite) {
        super(world, x, y, z);
        this.quadSize = 2.0f;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.animatedSprite = animatedSprite;
        this.setSpriteFromAge(animatedSprite);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.animatedSprite);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return EpicFightParticleRenderTypes.BLEND_LIGHTMAP_PARTICLE;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            OhTestParticle particle = new OhTestParticle(worldIn, x, y, z, this.spriteSet);
            return particle;
        }
    }
}
