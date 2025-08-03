package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class HookedGoldenSwordItem extends SwordItem {

    public HookedGoldenSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 120;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 3.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 16;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.GOLD_INGOT)});
            }
        }, 3, -2.5F, (new Properties()));
    }
}