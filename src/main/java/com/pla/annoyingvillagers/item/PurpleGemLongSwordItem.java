package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class PurpleGemLongSwordItem extends SwordItem {

    public PurpleGemLongSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1800;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 10.5F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 25;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack((ItemLike) AnnoyingVillagersModItems.PURPLE_GEM.get())});
            }
        }, 3, -1.6F, (new Properties()));
    }

    public boolean hasContainerItem(ItemStack itemstack) {
        return true;
    }

    public ItemStack getContainerItem(ItemStack itemstack) {
        return new ItemStack(this);
    }

    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }
}
