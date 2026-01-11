package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.event.NoVfxPortalEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import com.pla.annoyingvillagers.util.ExplosionFxMute;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
    private ClientPacketHandlers() {}

    private static void spawnOmniRings(Level level, RandomSource rand, Vec3 center) {
        int ringPlanes = 6;
        for (int p = 0; p < ringPlanes; p++) {
            Vec3 normal = randomUnit(rand);

            spawnRing3d(level, rand, center, normal, 52, 2.0, 0.10, 0.12, 0.035);
            spawnRing3d(level, rand, center, normal, 60, 2.8, 0.14, 0.11, 0.030);
        }
    }

    private static void spawnRing3d(Level level, RandomSource rand, Vec3 center, Vec3 n,
                                    int points, double radius, double thickness,
                                    double tangentialSpeed, double outwardSpeed) {

        Vec3 normal = n.normalize();
        Vec3 u = normal.cross(new Vec3(0, 1, 0));
        if (u.lengthSqr() < 1.0e-6) u = normal.cross(new Vec3(1, 0, 0));
        u = u.normalize();
        Vec3 v = normal.cross(u).normalize();

        for (int i = 0; i < points; i++) {
            double a = (i / (double) points) * (Math.PI * 2.0) + rand.nextDouble() * 0.10;
            double cos = Math.cos(a);
            double sin = Math.sin(a);

            Vec3 radialDir = u.scale(cos).add(v.scale(sin));
            Vec3 tangentDir = normal.cross(radialDir).normalize();

            double layer = (rand.nextDouble() - 0.5) * 2.0 * thickness;
            Vec3 pos = center.add(radialDir.scale(radius)).add(normal.scale(layer));

            Vec3 vel = tangentDir.scale(tangentialSpeed)
                    .add(radialDir.scale(outwardSpeed))
                    .add((rand.nextDouble() - 0.5) * 0.02, (rand.nextDouble() - 0.5) * 0.02, (rand.nextDouble() - 0.5) * 0.02);

            level.addParticle(AnnoyingVillagersModParticleTypes.ENDER.get(), true,
                    pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

            if ((i & 3) == 0) {
                level.addParticle(ParticleTypes.REVERSE_PORTAL, true,
                        pos.x, pos.y, pos.z, vel.x * 0.35, vel.y * 0.2, vel.z * 0.35);
            }
        }
    }

    private static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0 - 1.0;
        double a = rand.nextDouble() * Math.PI * 2.0;
        double r = Math.sqrt(Math.max(0.0, 1.0 - z * z));
        return new Vec3(r * Math.cos(a), z, r * Math.sin(a));
    }

    public static void handleGlaiveExplosion(ClientboundGlaiveExplosionFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendEnderGlaiveExplosion(msg.from(), msg.to(), level);
        } else {
            Vec3 center = msg.to();
            RandomSource rand = level.getRandom();

            level.addParticle(
                    AnnoyingVillagersModParticleTypes.FIREBALL.get(),
                    true,
                    center.x, center.y, center.z,
                    5.0, 1, 0.0
            );
            spawnOmniRings(level, rand, center);
        }

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.ENDER_SHOT.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    public static void handleMuteExplosionAtPos(ClientboundMuteExplosionAtPos msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        ExplosionFxMute.mark(msg.pos().asLong(), level.getGameTime() + msg.lifetimeTicks());
    }

    public static void handleHerobrinePortalFx(ClientboundHerobrinePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendHerobrinePortal(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            NoVfxPortalEvent.spawn(msg.from(), 60);
        }
    }

    public static void handleLitePortalFx(ClientboundLitePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendLitePortal(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            NoVfxPortalEvent.spawn(msg.from(), 0);
        }
    }

    public static void handleWoopieSwordWind(ClientboundWoopieSwordWindFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendWoopieWind(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            RandomSource rand = level.getRandom();

            int rings = 3;
            int pointsPerRing = 36;
            double baseRadius = 0.9;
            double radiusStep = 0.35;
            double baseY = 0.15;
            double yStep = 0.18;

            double tangentialSpeed = 0.14;
            double outwardSpeed = 0.03;

            for (int r = 0; r < rings; r++) {
                double radius = baseRadius + r * radiusStep;
                double yOff = baseY + r * yStep;

                for (int i = 0; i < pointsPerRing; i++) {
                    double a = (i / (double) pointsPerRing) * Math.PI * 2.0 + rand.nextDouble() * 0.12;
                    double cos = Math.cos(a);
                    double sin = Math.sin(a);

                    double px = msg.from().x + cos * radius;
                    double py = msg.from().y + yOff + (rand.nextDouble() - 0.5) * 0.06;
                    double pz = msg.from().z + sin * radius;
                    double vx = (-sin) * tangentialSpeed + cos * outwardSpeed;
                    double vy = 0.01 + rand.nextDouble() * 0.02;
                    double vz = ( cos) * tangentialSpeed + sin * outwardSpeed;

                    level.addParticle(ParticleTypes.CLOUD, true, px, py, pz, vx, vy, vz);
                    if ((i & 3) == 0) {
                        level.addParticle(ParticleTypes.SMOKE, true, px, py, pz, vx * 0.35, vy * 0.2, vz * 0.35);
                    }
                }
            }

            for (int i = 0; i < 14; i++) {
                double vx = (rand.nextDouble() - 0.5) * 0.25;
                double vy = 0.03 + rand.nextDouble() * 0.18;
                double vz = (rand.nextDouble() - 0.5) * 0.25;
                level.addParticle(ParticleTypes.POOF, true, msg.from().x, msg.from().y + 0.25, msg.from().z, vx, vy, vz);
            }

            level.addParticle(ParticleTypes.EXPLOSION, true, msg.from().x, msg.from().y + 0.35, msg.from().z, 0.0, 0.0, 0.0);
        }

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.WOOPIE_WIND.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }
}
