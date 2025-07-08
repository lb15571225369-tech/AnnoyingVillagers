package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

public class DarkobitemItem extends Item {

    public DarkobitemItem() {
        super((new Properties()).tab((CreativeModeTab) null).stacksTo(64).rarity(Rarity.COMMON));
    }
}
