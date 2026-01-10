package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.emitterinfo.EnderGlaiveExplosionParticleEmitterInfo;
import net.minecraft.resources.ResourceLocation;
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
}
