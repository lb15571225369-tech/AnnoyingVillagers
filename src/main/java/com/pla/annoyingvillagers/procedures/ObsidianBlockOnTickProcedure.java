package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.block.CryingObsidianBlock;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ObsidianBlockOnTickProcedure {
    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        LevelAccessor levelaccessor1 = levelaccessor;

        if (levelaccessor1 instanceof Level) {
            Level level1 = (Level)levelaccessor1;

            if (!level1.isClientSide()) {
                level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.break")), SoundSource.BLOCKS, 1.0F, (float) Mth.nextDouble(RandomSource.create(), 0.9D, 1.0D));
            } else {
                level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.break")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9D, 1.0D), false);
            }
        }
        BlockPos pos = new BlockPos((int) d0, (int) d1, (int) d2);
        BlockState current = levelaccessor.getBlockState(pos);
        int replace = 0;
        if (current.getBlock() instanceof ObsidianBlock && current.hasProperty(ObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(ObsidianBlock.REPLACE_BY_LIQUID);
        }
        if (current.getBlock() instanceof ShadowObsidianBlock && current.hasProperty(ShadowObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(ShadowObsidianBlock.REPLACE_BY_LIQUID);
        }
        if (current.getBlock() instanceof CryingObsidianBlock && current.hasProperty(CryingObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(CryingObsidianBlock.REPLACE_BY_LIQUID);
        }
        BlockState replacement = switch (replace) {
            case 1 -> Blocks.WATER.defaultBlockState();
            case 2 -> Blocks.LAVA.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
        levelaccessor.setBlock(pos, replacement, 3);
    }
}
