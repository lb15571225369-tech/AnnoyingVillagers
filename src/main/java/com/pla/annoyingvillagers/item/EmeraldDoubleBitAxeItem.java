package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class EmeraldDoubleBitAxeItem extends SwordItem {

    public EmeraldDoubleBitAxeItem() {
        super(new Tier() {
            public int getUses() {
                return 1980;
            }

            public float getSpeed() {
                return 7.5F;
            }

            public float getAttackDamageBonus() {
                return 5.5F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 18;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.EMERALD));
            }
        }, 3, -2.6F, (new Properties()));
    }
}
