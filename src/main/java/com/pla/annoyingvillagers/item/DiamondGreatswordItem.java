package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiamondGreatswordItem extends SwordItem {

    public DiamondGreatswordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 3.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 20;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
            }
        }, 3, -2.5F, (new Properties()));
    }
}

