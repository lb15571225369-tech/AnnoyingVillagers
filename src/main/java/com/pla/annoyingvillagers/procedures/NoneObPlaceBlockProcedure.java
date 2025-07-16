package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class NoneObPlaceBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
        new DelayedTask(20) {
            @Override
            public void run() {
                levelaccessor.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
            }
        };
    }
}
