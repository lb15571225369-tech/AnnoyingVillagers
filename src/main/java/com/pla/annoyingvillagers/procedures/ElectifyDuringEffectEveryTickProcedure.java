package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectifyDuringEffectEveryTickProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.1D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:electric_spark ^ ^ ^ 0.3 1.2 0.3 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                        
                    }
                }

                if (Math.random() <= 0.8D && levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(AnnoyingVillagers.randomSource, 0.05D, 0.5D), (float) Mth.nextDouble(AnnoyingVillagers.randomSource, 0.8D, 1.1D));
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "electify")), SoundSource.NEUTRAL, (float) Mth.nextDouble(AnnoyingVillagers.randomSource, 0.05D, 0.5D), (float) Mth.nextDouble(AnnoyingVillagers.randomSource, 0.8D, 1.1D), false);
                    }
                }
            }

            if (Math.random() <= 0.001D) {
                entity.hurt(DamageSource.GENERIC, 1.0F);
            }

        }
    }
}

