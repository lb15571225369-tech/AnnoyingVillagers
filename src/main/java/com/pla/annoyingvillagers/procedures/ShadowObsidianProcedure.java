package com.pla.annoyingvillagers.procedures;

import java.util.Random;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ShadowObsidianProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, ItemStack itemstack) {
        if (entity != null) {
            if (levelaccessor instanceof Level) {
                Level level = (Level)levelaccessor;

                if (!level.isClientSide()) {
                    level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.BLOCKS, 0.3F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("annoyingvillagers", "obsidian_place")), SoundSource.BLOCKS, 0.3F, 1.0F, false);
                }
            }

            if (itemstack.hurt(100, AnnoyingVillagers.randomSource, (ServerPlayer)null)) {
                itemstack.shrink(1);
                itemstack.setDamageValue(0);
            }

            if (entity instanceof Player) {
                Player player = (Player)entity;

                player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:shadow_obsidian keep",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            new DelayedTask(1) {
                @Override
                public void run() {
                    Entity entity1 = entity;

                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                        try {
                            entity1.getServer().getCommands().getDispatcher().execute(
                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:shadow_obsidian keep",
                                    entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }

                    new DelayedTask(1) {
                        @Override
                        public void run() {
                            Entity entity2 = entity;

                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                try {
                                    entity2.getServer().getCommands().getDispatcher().execute(
                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:shadow_obsidian keep",
                                            entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }

                            new DelayedTask(1) {
                                @Override
                                public void run() {
                                    Entity entity3 = entity;

                                    if (!entity3.level.isClientSide() && entity3.getServer() != null) {
                                        try {
                                            entity3.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:shadow_obsidian keep",
                                                    entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                            
                                        }
                                    }

                                    new DelayedTask(1) {
                                        @Override
                                        public void run() {
                                            Entity entity4 = entity;

                                            if (!entity4.level.isClientSide() && entity4.getServer() != null) {
                                                try {
                                                    entity4.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:shadow_obsidian keep",
                                                            entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                    
                                                }
                                            }

                                            new DelayedTask(1) {
                                                @Override
                                                public void run() {
                                                    Entity entity5 = entity;

                                                    if (!entity5.level.isClientSide() && entity5.getServer() != null) {
                                                        try {
                                                            entity5.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^7 annoyingvillagers:shadow_obsidian keep",
                                                                    entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                            
                                                        }
                                                    }

                                                    new DelayedTask(1) {
                                                        @Override
                                                        public void run() {
                                                            Entity entity6 = entity;

                                                            if (!entity6.level.isClientSide() && entity6.getServer() != null) {
                                                                try {
                                                                    entity6.getServer().getCommands().getDispatcher().execute(
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^8 annoyingvillagers:shadow_obsidian keep",
                                                                            entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                    
                                                                }
                                                            }

                                                            new DelayedTask(1) {
                                                                @Override
                                                                public void run() {
                                                                    Entity entity7 = entity;

                                                                    if (!entity7.level.isClientSide() && entity7.getServer() != null) {
                                                                        try {
                                                                            entity7.getServer().getCommands().getDispatcher().execute(
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^9 annoyingvillagers:shadow_obsidian keep",
                                                                                    entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
                                                                            
                                                                        }
                                                                    }

                                                                    new DelayedTask(1) {
                                                                        @Override
                                                                        public void run() {
                                                                            Entity entity8 = entity;

                                                                            if (!entity8.level.isClientSide() && entity8.getServer() != null) {
                                                                                try {
                                                                                    entity8.getServer().getCommands().getDispatcher().execute(
                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^ ^10 annoyingvillagers:shadow_obsidian keep",
                                                                                            entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                    
                                                                                }
                                                                            }
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                    };
                                }
                            };
                        }
                    };
                }
            };
        }
    }
}
