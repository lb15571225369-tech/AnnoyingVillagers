package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkObSsOnPlaceProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2) {
        if (levelaccessor instanceof Level) {
            Level level = (Level) levelaccessor;

            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }

    }
}
