package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class JadeSwordItem extends SwordItem {
    public JadeSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 685;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 2.5F;
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
        }, 3, -2.5F, (new Properties()));
    }
}
