package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class ChrisOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Chris> Steve, I'm sorry."), false);
        }
        new DelayedTask(20) {
            public void run() {
                LevelAccessor levelaccessor1 = levelaccessor;
                Level level;
                ItemEntity itementity;

                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_HELMET));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_CHESTPLATE));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_BOOTS));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.TOTEM_OF_UNDYING));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.SHIELD));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.SPYGLASS));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.OAK_BOAT));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.CRAFTING_TABLE));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.CRAFTING_TABLE));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.EMERALD));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.DIAMOND_SWORD));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_SWORD));
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
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.IRON_AXE));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }

                levelaccessor1 = levelaccessor;
                if (levelaccessor1 instanceof Level) {
                    level = (Level)levelaccessor1;
                    if (!level.isClientSide()) {
                        itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.WHITE_BED));
                        itementity.setPickUpDelay(10);
                        level.addFreshEntity(itementity);
                    }
                }
            }
        };
    }
}
