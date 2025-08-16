package com.pla.annoyingvillagers.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DemoniacVoltageReaverHiltItem extends Item {

    public DemoniacVoltageReaverHiltItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("The hilt of the Demoniac Voltage Reavere, forged from blended obsidian"));
    }
}