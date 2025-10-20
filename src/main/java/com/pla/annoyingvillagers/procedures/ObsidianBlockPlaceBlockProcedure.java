package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.block.CryingObsidianBlock;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.util.DelayedTask;
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

public class ObsidianBlockPlaceBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
        Level level;

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.stone.place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.7D), 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.stone.place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.0D, 0.7D), 1.0F, false);
            }
        }

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.2D, 0.6D), 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "ob_place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.2D, 0.6D), 1.0F, false);
            }
        }
        new DelayedTask(25) {
            @Override
            public void run() {
                LevelAccessor levelaccessor1 = levelaccessor;

                if (levelaccessor1 instanceof Level) {
                    Level level1 = (Level)levelaccessor1;

                    if (!level1.isClientSide()) {
                        level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.stone.break")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9D, 1.0D));
                    } else {
                        level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "block.stone.break")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9D, 1.0D), false);
                    }
                }
                BlockPos pos = new BlockPos((int) d0, (int) d1, (int) d2);
                BlockState current = levelaccessor.getBlockState(pos);
                int replace = 0;
                if (current.getBlock() instanceof ObsidianBlock && current.hasProperty(ObsidianBlock.REPLACE_BY_LIQUID)) {
                    replace = current.getValue(ObsidianBlock.REPLACE_BY_LIQUID);
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
        };

        new DelayedTask((int)Mth.nextDouble(RandomSource.create(), 5.0D, 15.0D)) {
            @Override
            public void run() {
                LevelAccessor levelaccessor2 = levelaccessor;

                if (levelaccessor2 instanceof Level) {
                    Level level2 = (Level)levelaccessor2;

                    if (!level2.isClientSide()) {
                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "pop")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 1.0D, 1.0D));
                    } else {
                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "pop")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 1.0D, 1.0D), false);
                    }
                }
            }
        };
    }
}
