package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianBigItem extends Item {

    public EliteObsidianBigItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
