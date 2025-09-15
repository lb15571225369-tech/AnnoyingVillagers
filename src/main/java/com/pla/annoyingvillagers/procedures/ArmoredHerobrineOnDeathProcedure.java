package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.EquipmentDataLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class ArmoredHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(
                    Component.literal("The clone has been destroyed, data has been transmitted to the terminal."),
                    false
            );
        }

        dropLoot(levelaccessor, x, y, z);
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENDER_EYE,
                Items.ENDER_EYE,
                Items.SPLASH_POTION,
                Blocks.DIAMOND_BLOCK.asItem(),
                Items.IRON_SWORD,
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(),
                AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get(),
                AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get()
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }
}
