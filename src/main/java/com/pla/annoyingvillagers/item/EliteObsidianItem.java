package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianItem extends Item {

    public EliteObsidianItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
