package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HerobrineHealingParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static HerobrineHealingParticle.HerobrineHealingParticleProvider provider(SpriteSet spriteset) {
        return new HerobrineHealingParticle.HerobrineHealingParticleProvider(spriteset);
    }

    protected HerobrineHealingParticle(ClientLevel lvl, double x, double y, double z,
                                       double vx, double vy, double vz, SpriteSet sprites) {
        super(lvl, x, y, z);
        this.spriteSet = sprites;
        this.setSize(0.4F, 0.4F);
        this.quadSize *= 0.7F;
        this.lifetime = Math.max(6, 12 + this.random.nextInt(8));

        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = vx; this.yd = vy; this.zd = vz;
        this.setSpriteFromAge(sprites);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age % 8, 8));
            this.xd *= 0.92; this.yd *= 0.92; this.zd *= 0.92;
            this.quadSize *= 0.985F;
        }
    }

    public static class HerobrineHealingParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public HerobrineHealingParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new HerobrineHealingParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
