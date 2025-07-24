package com.pla.annoyingvillagers.procedures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.entity.Herobrine2Entity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;

public class HerobrineWhenStruckByLightningProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide()) {
                entity.discard();
            }

            if (levelaccessor instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel) levelaccessor;
                Herobrine2Entity herobrine2entity = new Herobrine2Entity((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), serverlevel);

                herobrine2entity.moveTo(d0, d1, d2, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                if (herobrine2entity instanceof Mob) {
                    Mob mob = (Mob) herobrine2entity;

                    mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(herobrine2entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                }

                levelaccessor.addFreshEntity(herobrine2entity);
            }

        }
    }
}
