// com.pla.annoyingvillagers.client.emitterinfo.GroundPillarEmitterInfo.java
package com.pla.annoyingvillagers.client.emitterinfo;

import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import mod.chloeprime.aaaparticles.client.registry.EffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class GroundPillarEmitterInfo extends ParticleEmitterInfo {
    private BlockPos center;
    private int durationTicks = 170;
    private float yOffset = 0.0F;

    public GroundPillarEmitterInfo(ResourceLocation effek) { super(effek); }

    public GroundPillarEmitterInfo at(BlockPos center, float yOffset) {
        this.center = center;
        this.yOffset = yOffset;
        return this;
    }

    public GroundPillarEmitterInfo duration(int ticks) {
        this.durationTicks = Math.max(1, ticks);
        return this;
    }

    @Override public void spawnInWorld(Level level, Player ignored) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;
        if (center == null) return;

        Optional.ofNullable(EffectRegistry.get(this.effek)).ifPresent(eff -> {
            ParticleEmitter em = this.hasEmitter() ? eff.play(this.emitter) : eff.play();
            em.setPosition(center.getX() + 0.5F, center.getY() + yOffset, center.getZ() + 0.5F);

            final int startTick = level.getGameTime() > Integer.MAX_VALUE ? 0 : (int)level.getGameTime();
            em.addPreDrawCallback((E, partial) -> {
                int now = level.getGameTime() > Integer.MAX_VALUE ? 0 : (int)level.getGameTime();
                if (now - startTick >= durationTicks) E.stop();
            });
        });
    }
}