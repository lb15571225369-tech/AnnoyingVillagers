package com.pla.annoyingvillagers.compat.player_mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class EscapeWaterGoal extends Goal {
    private final Mob mob;

    public EscapeWaterGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return mob.isInWater();
    }

    @Override
    public void start() {
        BlockPos land = findNearestLand(mob.blockPosition(), 30);
        if (land != null) {
            mob.getNavigation().moveTo(land.getX(), land.getY(), land.getZ(), 1.2D);
        }
    }

    private BlockPos findNearestLand(BlockPos start, int radius) {
        for (int dy = 0; dy <= 3; dy++) {
            for (int r = 1; r <= radius; r++) {
                for (int dx = -r; dx <= r; dx++) {
                    for (int dz = -r; dz <= r; dz++) {
                        BlockPos pos = start.offset(dx, dy, dz);
                        BlockState state = mob.level.getBlockState(pos);
                        if (!state.getMaterial().isLiquid() && mob.level.isEmptyBlock(pos.above())) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }
}
