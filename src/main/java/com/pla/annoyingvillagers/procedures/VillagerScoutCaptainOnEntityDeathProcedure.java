package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class VillagerScoutCaptainOnEntityDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            if (Math.random() <= 0.2D && !entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "effect give @p annoyingvillagers:wanted 600 0",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            new DelayedTask(20) {
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;
                    Level level;
                    ItemEntity itementity;

                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.DIRT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BREAD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BREAD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BREAD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLD_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_PICKAXE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    if (Math.random() <= 0.3D) {
                        Entity entity1 = entity;

                        if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                            try {
                                entity1.getServer().getCommands().getDispatcher().execute(
                                        "summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}",
                                        entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }

                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Villager Scout> Requesting backup!"), false);
                        }

                        new DelayedTask(400) {
                            public void run() {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Villager Scout> Reinforcements have arrived!"), false);
                                }

                                Entity entity2 = entity;

                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "summon annoyingvillagers:villager_scout ^ ^ ^10",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                        
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "summon annoyingvillagers:villager_scout ^ ^ ^15",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                        
                                    }
                                }

                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "summon annoyingvillagers:blue_villager_general ^10 ^ ^20",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                        
                                    }
                                }
                            }
                        };
                    }
                }
            };
        }
    }
}

