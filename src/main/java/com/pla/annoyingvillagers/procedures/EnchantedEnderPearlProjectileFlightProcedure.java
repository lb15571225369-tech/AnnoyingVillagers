package com.pla.annoyingvillagers.procedures;


import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.LevelAccessor;

public class EnchantedEnderPearlProjectileFlightProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        levelaccessor.addParticle((SimpleParticleType) AnnoyingVillagersModParticleTypes.ENDER.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
