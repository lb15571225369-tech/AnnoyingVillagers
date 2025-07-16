package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.VillagerHelmetWearingItemOnInventoryTick;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class VillagerScoutHelmetWearingItem extends Item {

    public VillagerScoutHelmetWearingItem() {
        super((new Properties()).tab((CreativeModeTab) null).durability(230).rarity(Rarity.COMMON));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        VillagerHelmetWearingItemOnInventoryTick.execute(entity, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET_WEARING.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.STRONG_HEALING));
    }
}
