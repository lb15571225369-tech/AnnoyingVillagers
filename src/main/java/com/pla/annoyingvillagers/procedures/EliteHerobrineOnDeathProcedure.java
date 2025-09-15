package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class EliteHerobrineOnDeathProcedure {

    public static void execute(LevelAccessor world, final double x, final double y, final double z, Entity entity, String fromElite) {
        if (entity == null) return;

        dropLoot(world, x, y, z, fromElite);
    }

    private static void dropLoot(LevelAccessor world, double x, double y, double z, String fromElite) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] items = new Item[] {
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().asItem(),
                Items.ENDER_EYE,
                Items.ENDER_EYE,
                Items.ENDER_EYE,
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().asItem(),
                Items.ENCHANTED_BOOK,
                Items.COAL
        };

        for (Item item : items) {
            ItemEntity drop = new ItemEntity(level, x, y, z, new ItemStack(item));
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }

        ItemStack eliteDrop = ItemStack.EMPTY;
        switch (fromElite) {
            case "EnderGlaive" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get());
            case "ObsidianSledgehammer" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get());
            case "EnderSlayerScythe" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get());
            case "EnderAegis" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get());
            case "DemoniacVoltageReaver" -> eliteDrop = new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_HILT.get());
        }

        if (!eliteDrop.isEmpty()) {
            ItemEntity drop = new ItemEntity(level, x, y, z, eliteDrop);
            drop.setPickUpDelay(10);
            level.addFreshEntity(drop);
        }
    }
}
