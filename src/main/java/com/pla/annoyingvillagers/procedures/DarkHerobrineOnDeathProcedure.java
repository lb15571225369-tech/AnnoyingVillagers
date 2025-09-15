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

public class DarkHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor world, final double x, final double y, final double z, Entity entity) {
        if (entity == null) return;

        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(
                    Component.literal("§5Shadow Herobrine§r has been destroyed."),
                    false
            );
        }
        dropLoot(world, x, y, z);
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),

                Items.ENDER_EYE,
                Items.ENDER_EYE,
                Items.ENDER_EYE,

                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),

                Items.ENCHANTED_BOOK,
                Items.COAL,
                AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get(),
                AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()
        };

        for (Item item : items) {
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }
}
