package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkObSsOnAttackProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100");
            }

            if (levelaccessor instanceof Level) {
                Level level = (Level) levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos(d0, d1, d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ob_place")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

        }
    }
}
