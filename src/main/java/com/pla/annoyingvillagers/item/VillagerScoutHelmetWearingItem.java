package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.procedures.CunzhentoukuiDangWuPinZaiBeiBaoZhongMeiKeFaShengProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class VillagerScoutHelmetWearingItem extends Item {

    public VillagerScoutHelmetWearingItem() {
        super((new Properties()).tab((CreativeModeTab) null).durability(230).rarity(Rarity.COMMON));
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        CunzhentoukuiDangWuPinZaiBeiBaoZhongMeiKeFaShengProcedure.execute(entity);
    }
}
