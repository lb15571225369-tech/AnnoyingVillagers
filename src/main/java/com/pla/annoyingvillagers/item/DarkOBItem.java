package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkOBItem extends Item {

    public DarkOBItem() {
        super((new Properties()).tab((CreativeModeTab) null).stacksTo(64).rarity(Rarity.COMMON));
    }
}
