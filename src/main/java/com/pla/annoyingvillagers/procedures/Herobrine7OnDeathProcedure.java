package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Herobrine7OnDeathProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2, Entity entity) {
        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("The clone has been destroyed, data has been transmitted to the terminal."), false);
            }

            dropLoot(levelaccessor, d0, d1, d2);
        }
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENDER_EYE, Items.ENDER_EYE, Items.ENDER_EYE,
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENCHANTED_BOOK,
                Items.COAL,
                AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get(),
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }
}
