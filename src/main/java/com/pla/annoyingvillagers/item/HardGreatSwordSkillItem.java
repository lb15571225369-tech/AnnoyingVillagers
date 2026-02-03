package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class HardGreatSwordSkillItem extends Item {
    public HardGreatSwordSkillItem() {
        super((new Properties()).stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
