package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.GroundRiseSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class HerobrinePortalProcedure {
    public static final String NBT_RECALL_TIMER = "recall_timer";
    public static final int SHINK_TIME_START = 40;

    public static void spawnHerobrine(LivingEntity livingEntity) {
        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);

//        int recallTicks = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
        int recallTicks = 600;

        livingEntity.getPersistentData().putInt(NBT_RECALL_TIMER, recallTicks);

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            GroundRiseSpawner.spawnRising(serverLevel, livingEntity, livingEntity.getX(), livingEntity.getZ(), 0.03);
        }
    }

    public static Vec3 finalSurfacePos(Entity entity) {
        ServerLevel serverLevel = (ServerLevel) entity.level();
        BlockPos top = serverLevel.getHeightmapPos(
               Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlockPos.containing(entity.getX(), 0, entity.getZ())
        );
        return new Vec3(top.getX() + 0.5, top.getY() + 0.02, top.getZ() + 0.5);
    }
}
