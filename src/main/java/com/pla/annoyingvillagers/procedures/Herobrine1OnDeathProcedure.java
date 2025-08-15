package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Herobrine1OnDeathProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity source) {
        if (source == null) return;

        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("The clone has been destroyed, data has been transmitted to the terminal."), false);
        }

        // Drop loot after 1s
        new DelayedTask(20) {
            @Override
            public void run() {
                dropLoot(world, x, y, z);
            }
        };
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                Items.DIAMOND, Items.DIAMOND, Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.WRITABLE_BOOK, Items.EMERALD, Items.EMERALD, Items.ENCHANTED_GOLDEN_APPLE,
                Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }
}
