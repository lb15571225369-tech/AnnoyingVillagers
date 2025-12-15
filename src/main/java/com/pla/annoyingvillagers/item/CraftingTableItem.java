package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class CraftingTableItem extends AxeItem {

    public CraftingTableItem() {
        super(new Tier() {
            public int getUses() {
                return 20;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 2F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.CRAFTING_TABLE));
            }
        }, 1.0F, -2.8F, (new Properties()));
    }
}
