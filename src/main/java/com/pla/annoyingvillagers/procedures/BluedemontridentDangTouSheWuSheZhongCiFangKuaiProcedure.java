package com.pla.annoyingvillagers.procedures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.LevelAccessor;

public class BluedemontridentDangTouSheWuSheZhongCiFangKuaiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        if (levelaccessor instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;
            ThrownTrident throwntrident = new ThrownTrident(EntityType.TRIDENT, serverlevel);

            throwntrident.moveTo(d0, d1 + 1.0D, d2 - 0.9D, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            levelaccessor.addFreshEntity(throwntrident);
        }

    }
}
