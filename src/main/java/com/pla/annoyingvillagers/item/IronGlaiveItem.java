package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class IronGlaiveItem extends SwordItem {

    public IronGlaiveItem() {
        super(new Tier() {
            public int getUses() {
                return 450;
            }

            public float getSpeed() {
                return 6.5F;
            }

            public float getAttackDamageBonus() {
                return 7.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 8;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.IRON_INGOT)});
            }
        }, 3, -2.6F, (new Properties()));
    }
}
