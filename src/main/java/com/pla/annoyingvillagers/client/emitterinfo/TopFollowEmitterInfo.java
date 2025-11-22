package com.pla.annoyingvillagers.client.emitterinfo;

import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import mod.chloeprime.aaaparticles.client.registry.EffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class TopFollowEmitterInfo extends ParticleEmitterInfo {
    private WeakReference<Entity> casterRef = new WeakReference<>(null);
    private int durationTicks = 60;

    private float upOffset = 0.30f;
    private float fwdOffset = 1.00f;

    public TopFollowEmitterInfo(ResourceLocation effek) { super(effek); }

    public TopFollowEmitterInfo follow(Entity caster, int durationTicks, float yOffset) {
        this.casterRef = new WeakReference<>(caster);
        this.durationTicks = Math.max(1, durationTicks);
        this.upOffset = yOffset;
        this.fwdOffset = 0.0f;
        return this;
    }

    public TopFollowEmitterInfo followHead(Entity caster, int durationTicks, float upOffset, float fwdOffset) {
        this.casterRef = new WeakReference<>(caster);
        this.durationTicks = Math.max(1, durationTicks);
        this.upOffset = upOffset;
        this.fwdOffset = fwdOffset;
        return this;
    }

    private static Vec3 eyeLerped(LivingEntity e, float partial) {
        double x = Mth.lerp(partial, e.xOld, e.getX());
        double y = Mth.lerp(partial, e.yOld, e.getY()) + e.getEyeHeight();
        double z = Mth.lerp(partial, e.zOld, e.getZ());
        return new Vec3(x, y, z);
    }

    private static Vec3 posTopLerped(Entity e, float partial) {
        double x = Mth.lerp(partial, e.xOld, e.getX());
        double y = Mth.lerp(partial, e.yOld, e.getY()) + e.getBbHeight();
        double z = Mth.lerp(partial, e.zOld, e.getZ());
        return new Vec3(x, y, z);
    }

    @Override
    public void spawnInWorld(Level level, Player ignored) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;

        Optional.ofNullable(EffectRegistry.get(this.effek)).ifPresent(eff -> {
            ParticleEmitter em = this.hasEmitter() ? eff.play(this.emitter) : eff.play();

            if (this.hasParameters()) for (DynamicParameter p : this.parameters) em.setDynamicInput(p.index(), p.value());
            if (this.hasTriggers()) this.triggers.forEach(em::sendTrigger);

            final Entity caster0 = casterRef.get();
            if (caster0 == null || !caster0.isAlive()) { em.stop(); return; }
            final int startTick = caster0.tickCount;

            Vec3 p0;
            if (caster0 instanceof LivingEntity le0) {
                Vec3 eye = new Vec3(le0.getX(), le0.getEyeY(), le0.getZ());
                Vec3 fwd = le0.getViewVector(0.0f).normalize();
                p0 = eye.add(0.0, upOffset, 0.0).add(fwd.scale(fwdOffset));
            } else {
                Vec3 top = new Vec3(caster0.getX(), caster0.getY() + caster0.getBbHeight(), caster0.getZ());
                p0 = top.add(0.0, upOffset, 0.0);
            }
            em.setPosition((float)p0.x, (float)p0.y, (float)p0.z);

            em.addPreDrawCallback((Emitter, partial) -> {
                Entity c = casterRef.get();
                if (c == null || !c.isAlive()) { Emitter.stop(); return; }
                if ((c.tickCount - startTick) >= durationTicks) { Emitter.stop(); return; }

                Vec3 pos;
                if (c instanceof LivingEntity le) {
                    Vec3 eye = eyeLerped(le, partial);
                    Vec3 fwd = le.getViewVector(partial).normalize();
                    pos = eye.add(0.0, upOffset, 0.0).add(fwd.scale(fwdOffset));
                } else {
                    Vec3 top = posTopLerped(c, partial);
                    pos = top.add(0.0, upOffset, 0.0);
                }
                Emitter.setPosition((float)pos.x, (float)pos.y, (float)pos.z);
            });
        });
    }
}