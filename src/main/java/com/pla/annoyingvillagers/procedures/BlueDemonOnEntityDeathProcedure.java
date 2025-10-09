package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.BlueDemonStagingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.spawnhandler.AlexData;
import com.pla.annoyingvillagers.spawnhandler.BluedemonData;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelAccessor;

public class BlueDemonOnEntityDeathProcedure {

    public static void execute(LevelAccessor world, Entity entity, Entity entity1) {
        if (entity == null || entity1 == null) return;

        // Summon Blue Demon replacement
        if (world instanceof ServerLevel serverLevel) {
            if (entity instanceof BlueDemonEntity blueDemon && blueDemon.getBbqUUID() != null) {
                Entity bbq = serverLevel.getEntity(blueDemon.getBbqUUID());
                if (bbq instanceof BbqEntity && bbq.isAlive()) {
                    bbq.discard();
                }
            }

            BlueDemonStagingEntity blueDemonStagingEntity = new BlueDemonStagingEntity((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_STAGING.get(), serverLevel);
            blueDemonStagingEntity.moveTo(entity.getX(), entity.getY(), entity.getZ());

            entity.discard();

            BluedemonData bluedemonData = BluedemonData.get(serverLevel);
            bluedemonData.forceClaim(serverLevel, blueDemonStagingEntity.getUUID());
            blueDemonStagingEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blueDemonStagingEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            serverLevel.addFreshEntity(blueDemonStagingEntity);
        }
//
//        entity1.getPersistentData().putBoolean("b_d", true);
//
//        new DelayedTask(120) {
//            @Override
//            public void run() {
//                if (entity1.isAlive()) {
//                    entity1.getPersistentData().putBoolean("b_d", false);
//                }
//            }
//        };
    }
}
