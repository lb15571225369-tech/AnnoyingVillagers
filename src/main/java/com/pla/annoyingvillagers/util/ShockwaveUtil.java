package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.ShockWaveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ShockwaveUtil {
    public static void spawnCircleRing(ServerLevel level, BlockPos centerPos, int radius, LivingEntity owner) {
        double inner = (radius - 0.5D) * (radius - 0.5D);
        double outer = (radius + 0.5D) * (radius + 0.5D);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double dist2 = (double) dx * dx + (double) dz * dz;
                if (dist2 >= inner && dist2 <= outer) {
                    spawnShockWaveBlock(level, centerPos.offset(dx, 0, dz), owner);
                }
            }
        }
    }

    private static void spawnShockWaveBlock(ServerLevel level, BlockPos startPos, LivingEntity owner) {
        final int BLOCK_SEARCH_DEPTH = 256;
        final int ENTITY_GROUND_LIFETIME = 10;

        BlockPos pos = startPos;
        BlockState state = level.getBlockState(pos);

        int minY = level.getMinBuildHeight();

        for (int i = 0; i < BLOCK_SEARCH_DEPTH && pos.getY() > minY && state.getRenderShape() != RenderShape.MODEL; i++) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }

        if (state.getRenderShape() != RenderShape.MODEL) return;

        ShockWaveBlockEntity blockEntity = new ShockWaveBlockEntity(
                level,
                pos.getX() + 0.5D,
                pos.getY() + 1.0D,
                pos.getZ() + 0.5D,
                state,
                ENTITY_GROUND_LIFETIME
        );
        blockEntity.setOwnerUuid(owner.getUUID());
        level.addFreshEntity(blockEntity);
    }
}
