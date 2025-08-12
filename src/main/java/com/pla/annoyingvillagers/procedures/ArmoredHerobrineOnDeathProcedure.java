package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.EquipmentDataLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class ArmoredHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            ServerLevel serverlevel;
            Level level;

            ItemEntity itementity;

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack((ItemLike) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    ItemStack item = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get());
                    item.setDamageValue(EquipmentDataLoader.getRandomDamage(item));
                    itementity = new ItemEntity(level, d0, d1, d2, item);
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    ItemStack item = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get());
                    item.setDamageValue(EquipmentDataLoader.getRandomDamage(item));
                    itementity = new ItemEntity(level, d0, d1, d2, item);
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENDER_EYE));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.ENDER_EYE));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.SPLASH_POTION));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Blocks.DIAMOND_BLOCK));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }

            if (levelaccessor instanceof Level) {
                level = (Level) levelaccessor;
                if (!level.isClientSide()) {
                    itementity = new ItemEntity(level, d0, d1, d2, new ItemStack(Items.IRON_SWORD));
                    itementity.setPickUpDelay(10);
                    level.addFreshEntity(itementity);
                }
            }
        }
    }
}
