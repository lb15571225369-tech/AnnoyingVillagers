package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class EnchantBedItemOnRightClickBlockProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            levelaccessor.setBlock(new BlockPos((int) d0, (int) d1 + 1, (int) d2), ((Block) AnnoyingVillagersModBlocks.ENCHANT_BED.get()).defaultBlockState(), 3);
            if (entity instanceof Player) {
                Player player = (Player) entity;
                ItemStack itemstack = new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get());

                player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                    return itemstack.getItem() == itemstack1.getItem();
                }, 1, player.inventoryMenu.getCraftSlots());
            }

        }
    }
}
