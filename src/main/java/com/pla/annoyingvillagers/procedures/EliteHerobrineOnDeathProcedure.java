package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class EliteHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity, String fromElite) {
        if (entity != null) {
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
                            if (fromElite.equals("EnderGlaive")) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_GLAIVE.get()));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            } else if (fromElite.equals("ObsidianSledgehammer")) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get()));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            } else if (fromElite.equals("EnderSlayerScythe")) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get()));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            } else if (fromElite.equals("EnderAegis")) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.ENDER_AEGIS.get()));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            } else if (fromElite.equals("DemoniacVoltageReaver")) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack((ItemLike) AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_HILT.get()));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }
                    }
                }
            };
        }
    }
}
