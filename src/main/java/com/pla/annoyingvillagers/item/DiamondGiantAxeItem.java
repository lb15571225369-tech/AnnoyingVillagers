package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class DiamondGiantAxeItem extends AxeItem {

    public DiamondGiantAxeItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 10.5F;
            }

            public int getLevel() {
                return 6;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 1.0F, -2.8F, (new Properties()));
    }
}
