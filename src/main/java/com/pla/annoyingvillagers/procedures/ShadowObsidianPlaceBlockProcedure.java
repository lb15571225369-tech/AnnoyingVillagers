package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ShadowObsidianPlaceBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
        Level level;

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.deepslate.place")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95D, 1.0D));
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "block.deepslate.place")), SoundSource.BLOCKS, 1.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95D, 1.0D), false);
            }
        }

        if (levelaccessor instanceof Level) {
            level = (Level)levelaccessor;
            if (!level.isClientSide()) {
                level.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ob_place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.2D, 1.0D), 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ob_place")), SoundSource.BLOCKS, (float)Mth.nextDouble(RandomSource.create(), 0.2D, 1.0D), 1.0F, false);
            }
        }

        new DelayedTask((int)Mth.nextDouble(RandomSource.create(), 5.0D, 10.0D)) {
            @Override
            public void run() {
                LevelAccessor levelaccessor2 = levelaccessor;

                if (levelaccessor2 instanceof Level) {
                    Level level2 = (Level)levelaccessor2;

                    if (!level2.isClientSide()) {
                        level2.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "pop")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level2.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "pop")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
            }
        };
    }
}
