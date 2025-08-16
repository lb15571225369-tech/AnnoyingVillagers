package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class HerobrineWeaponEffectProcedure {
    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.3D) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:pe ^ ^ ^ 0.4 1.1 0.4 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }

                if (Math.random() <= 0.87D) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:pe ^ ^ ^ 0.45 1.5 0.3 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {

                        }
                    }

                    if (levelaccessor instanceof Level) {
                        Level level = (Level) levelaccessor;

                        if (!level.isClientSide()) {
                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.4D), (float) Mth.nextDouble(RandomSource.create(), 0.5D, 1.2D));
                        } else {
                            level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.4D), (float) Mth.nextDouble(RandomSource.create(), 0.5D, 1.2D), false);
                        }
                    }
                }
            }

        }
    }
}
