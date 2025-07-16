package com.pla.annoyingvillagers.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.LevelAccessor;

public class BlueDemonTridentParticleWhenEntityDiesProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        if (levelaccessor instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;
            ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

            throwntrident.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
//            if (throwntrident instanceof Mob) {
//                Mob mob = (Mob) throwntrident;
//
//                mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(throwntrident.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
//            }

            levelaccessor.addFreshEntity(throwntrident);
        }

    }
}
