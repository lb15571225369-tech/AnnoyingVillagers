package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;

public class DarkObFarEntityOnHitEntityProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        levelaccessor.setBlock(new BlockPos((int) d0, (int) d1, (int) d2), ((Block) AnnoyingVillagersModBlocks.DARK_OB_UP.get()).defaultBlockState(), 3);
    }
}
