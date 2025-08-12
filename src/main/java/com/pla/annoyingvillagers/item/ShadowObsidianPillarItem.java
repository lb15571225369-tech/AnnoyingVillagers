package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ShadowObsidianPillarItem extends SwordItem {

    public ShadowObsidianPillarItem() {
        super(new Tier() {
            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 18.0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -1.0F, (new Properties()));
    }
}
