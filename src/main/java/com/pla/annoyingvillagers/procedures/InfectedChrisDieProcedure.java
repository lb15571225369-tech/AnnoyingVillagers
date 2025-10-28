package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class InfectedChrisDieProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
        dropLoot(levelaccessor, d0, d1, d2);
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModItems.BEDROCK_WEAPON.get(), AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(), Blocks.OAK_SIGN.asItem(), Blocks.OBSIDIAN.asItem(), Blocks.OBSIDIAN.asItem(), Items.NETHERITE_INGOT, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.ENDER_EYE, Items.ENDER_EYE, AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }
}
