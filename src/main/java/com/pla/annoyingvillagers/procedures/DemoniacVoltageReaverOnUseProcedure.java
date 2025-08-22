package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DemoniacVoltageReaverOnUseProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, ItemStack itemStack) {
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

                if (entity instanceof Player player) {
                    ItemCooldowns cooldowns = player.getCooldowns();
                    if (itemStack.getTag().getBoolean("SecondForm")) {
                        if (levelaccessor instanceof Level) {
                            level = (Level) levelaccessor;
                            if (!level.isClientSide()) {
                                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:end_sr")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:end_sr")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                        itemStack.getOrCreateTag().remove("SecondForm");
                        cooldowns.addCooldown(itemStack.getItem(), 100);
                    } else {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:portal ^ ^1.5 ^0.8 0 0 0 0.1 100", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                        itemStack.getOrCreateTag().putBoolean("SecondForm", true);
                        cooldowns.addCooldown(itemStack.getItem(), 100);
                    }
                }
            }
            else {
                if (entity instanceof LivingEntity livingEntity) {
                    if (itemStack.getTag().getBoolean("SecondForm")) {
                        if (itemStack.getTag().getInt("HitCount") == 5) {
                            if (SnakeBladeHit.process(itemStack, livingEntity)) {
                                itemStack.getOrCreateTag().putBoolean("SnakeAnimation", true);
                                itemStack.removeTagKey("HitCount");
                            }

                            if (!entity.level().isClientSide()) {
                                entity.level().playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers:second_form_release")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        } else {
                            itemStack.getTag().putInt("HitCount", (itemStack.getTag().contains("HitCount") ? itemStack.getTag().getInt("HitCount") : 0) + 1);
                        }
                    }
                }
            }

        }
    }
}
