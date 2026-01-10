/*
 * This file is part of AnnoyingVillagers.
 *
 * Contains code/assets adapted from:
 *   Explosive Enhancement: Reforged
 *   Copyright (C) Xylonity
 *
 * The original work is licensed under the GNU General Public License v3.0 (GPL-3.0).
 * This file (and any modifications to the adapted portions) is distributed under the
 * terms of the GPL-3.0 license. You should have received a copy of the GNU GPL v3.0
 * along with this project. If not, see <https://www.gnu.org/licenses/>.
 *
 * Note: Portions of this file include/derive from particle implementation(s)
 * originating in Explosive Enhancement: Reforged.
 */

package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FireballParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    boolean important;

    public static FireballParticle.FireballParticleProvider provider(SpriteSet spriteset) {
        return new FireballParticle.FireballParticleProvider(spriteset);
    }

    FireballParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, double velX, double velY, double velZ) {
        super(world, x, y, z);
        this.sprites = spriteProvider;
        this.lifetime = (int) (9 + Math.floor(velX / 5));
        this.quadSize = (float) velX;
        important = velY == 1;
        this.setParticleSpeed(0D, 0D, 0D);
        this.setSpriteFromAge(spriteProvider);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 15728880;
    }


    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class FireballParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FireballParticleProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new FireballParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

}