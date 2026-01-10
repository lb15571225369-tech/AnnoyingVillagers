package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.emitterinfo.DragonBeamParticleEmitterInfo;
import com.pla.annoyingvillagers.client.emitterinfo.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
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
}
