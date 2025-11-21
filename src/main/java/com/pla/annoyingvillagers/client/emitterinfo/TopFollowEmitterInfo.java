package com.pla.annoyingvillagers.client.emitterinfo;

import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import mod.chloeprime.aaaparticles.client.registry.EffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class TopFollowEmitterInfo extends ParticleEmitterInfo {
    private WeakReference<Entity> casterRef = new WeakReference<>(null);
    private int durationTicks = 60; // default 3s
    private float yOffset = 0.6f;   // extra height above head

    public TopFollowEmitterInfo(ResourceLocation effek) {
        super(effek);
    }

    public TopFollowEmitterInfo follow(Entity caster, int durationTicks, float yOffset) {
        this.casterRef = new WeakReference<>(caster);
        this.durationTicks = Math.max(1, durationTicks);
        this.yOffset = yOffset;
        return this;
    }

    @Override
    public void spawnInWorld(Level level, Player ignored) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;

        Optional.ofNullable(EffectRegistry.get(this.effek)).ifPresent(eff -> {
            ParticleEmitter em = this.hasEmitter() ? eff.play(this.emitter) : eff.play();

            if (this.hasParameters()) for (DynamicParameter p : this.parameters) em.setDynamicInput(p.index(), p.value());
            if (this.hasTriggers())    this.triggers.forEach(em::sendTrigger);

            final Entity caster0 = casterRef.get();
            if (caster0 == null || !caster0.isAlive()) { em.stop(); return; }
            final int startTick = caster0.tickCount;

            float x0 = (float) caster0.getX();
            float y0 = (float) (caster0.getY() + caster0.getBbHeight() + yOffset);
            float z0 = (float) caster0.getZ();
            em.setPosition(x0, y0, z0);

            em.addPreDrawCallback((Emitter, partial) -> {
                Entity c = casterRef.get();
                if (c == null || !c.isAlive()) { Emitter.stop(); return; }
                if ((c.tickCount - startTick) >= durationTicks) { Emitter.stop(); return; }

                float x = (float) Mth.lerp(partial, c.xOld, c.getX());
                float y = (float) (Mth.lerp(partial, c.yOld, c.getY()) + c.getBbHeight() + yOffset);
                float z = (float) Mth.lerp(partial, c.zOld, c.getZ());

                Emitter.setPosition(x, y, z);
            });
        });
    }
}
