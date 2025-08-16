package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DemoniacVoltageReaverSwitchToSecondFormProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                Level level;

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:cooldown")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:ender_shot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:bloom")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:bloom")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:dragon_breath")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:dragon_breath")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (levelaccessor instanceof Level) {
                    level = (Level) levelaccessor;
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:big_boom")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:big_boom")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }

                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:portal ^ ^1.5 ^0.8 0 0 0 0.1 100", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;
                    ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_AWAKENED.get());

                    itemstack.setCount(1);
                    livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    if (livingentity instanceof Player) {
                        Player player = (Player) livingentity;

                        player.getInventory().setChanged();
                    }
                }
            }

        }
    }
}
