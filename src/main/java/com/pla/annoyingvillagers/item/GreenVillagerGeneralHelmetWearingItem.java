package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.VillagerHelmetWearingItemOnInventoryTick;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class GreenVillagerGeneralHelmetWearingItem extends Item {

    public GreenVillagerGeneralHelmetWearingItem() {
        super((new Properties()).tab((CreativeModeTab) null).stacksTo(64).rarity(Rarity.COMMON));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        VillagerHelmetWearingItemOnInventoryTick.execute(entity, new ItemStack((ItemLike) AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET_WEARING.get()), new ItemStack(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET.get()));
    }
}

