package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowObsidianBurstItem extends Item {

    public ShadowObsidianBurstItem() {
        super((new Properties()).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    public boolean isCorrectToolForDrops(BlockState blockstate) {
        return true;
    }
}
