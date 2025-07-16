package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class GreenVillagerGeneralOnDeathProcedure {
    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            new DelayedTask(20) {
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;
                    Level level;
                    ItemEntity itementity;

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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.NAME_TAG));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.COOKED_BEEF));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.STONE_PICKAXE));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.APPLE));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BOW));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.FIREWORK_ROCKET));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET_WEARING.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.CARROT));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_INGOT));
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
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.WHEAT));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.GRASS_BLOCK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.GRASS_BLOCK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.GRASS_BLOCK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }
                }
            };
            if (Math.random() <= 0.32D) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:\"Black Creeper Firework\"}}}");
                }

                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Purple General> We've encountered an extremely powerful player, requesting support!"), ChatType.SYSTEM, Util.NIL_UUID);
                }

                new DelayedTask(400) {
                    public void run() {
                        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                            levelaccessor.getServer().getPlayerList().broadcastMessage(new TextComponent("<Villager Scout> Reinforcements have arrived!"), ChatType.SYSTEM, Util.NIL_UUID);
                        }

                        Entity entity1 = entity;

                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:villager_scout ^ ^ ^10");
                        }

                        entity1 = entity;
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:villager_scout ^ ^ ^20");
                        }

                        entity1 = entity;
                        if (!entity1.level.isClientSide() && entity1.getServer() != null) {
                            entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "/summon annoyingvillagers:blue_villager_general ^10 ^ ^20");
                        }
                    }
                };
            }

        }
    }
}

