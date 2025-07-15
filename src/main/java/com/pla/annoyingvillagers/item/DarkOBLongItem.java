package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkOBLongItem extends Item {

    public DarkOBLongItem() {
        super((new Properties()).tab((CreativeModeTab) null).stacksTo(64).rarity(Rarity.COMMON));
    }
}
