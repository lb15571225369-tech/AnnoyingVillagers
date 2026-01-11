package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.emitterinfo.DragonBeamParticleEmitterInfo;
import com.pla.annoyingvillagers.client.emitterinfo.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AAAParticlesUtil {
    public static void sendEnderGlaiveExplosion(Vec3 from, Vec3 to, Level level) {
        new EnderGlaiveExplosionParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive_explosion"))
                .fromTo(from, to,
                        EnderGlaiveExplosionParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f, true)
                .spawnInWorld(level, null);
    }

    public static void sendDragonBeam(Vec3 from, Vec3 to, Level level, HerobrineDragonEntity caster, LivingEntity target) {
        new DragonBeamParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_beam"))
                .fromTo(from, to, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .follow(caster, target, 120, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .spawnInWorld(level, null);
    }

    public static void sendDragonBeamHit(Level level, BlockPos hitBlock) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_beam_hit"))
                        .clone()
                        .position(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ()));
    }

    public static void sendHerobrinePortal(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_portal"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendLitePortal(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "lite_portal"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendWoopieWind(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_sword_wind"))
                        .clone()
                        .position(x, y, z));
    }
}
