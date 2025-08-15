package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ReaperHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("The clone has been destroyed, data has been transmitted to the terminal."), false);
            }
            new DelayedTask(20) {
                public void run() {
                    LevelAccessor levelaccessor1 = levelaccessor;
                    Level level;
                    ItemEntity itementity;

                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_EYE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_EYE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_EYE));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike)AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENCHANTED_BOOK));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.COAL));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }

                    levelaccessor1 = levelaccessor;
                    if (levelaccessor1 instanceof Level) {
                        level = (Level)levelaccessor1;
                        if (!level.isClientSide()) {
                            itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
                            itementity.setPickUpDelay(10);
                            level.addFreshEntity(itementity);
                        }
                    }
                }
            };
        }
    }
}
