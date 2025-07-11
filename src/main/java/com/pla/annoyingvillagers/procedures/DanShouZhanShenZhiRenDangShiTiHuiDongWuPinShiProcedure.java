package com.pla.annoyingvillagers.procedures;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DanShouZhanShenZhiRenDangShiTiHuiDongWuPinShiProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        if (levelaccessor instanceof Level) {
            Level level = (Level) levelaccessor;

            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:woosh_hard")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.1D, 1.0D), (float) Mth.nextDouble(new Random(), 0.5D, 1.0D));
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:woosh_hard")), SoundSource.BLOCKS, (float) Mth.nextDouble(new Random(), 0.1D, 1.0D), (float) Mth.nextDouble(new Random(), 0.5D, 1.0D), false);
            }
        }

    }
}
