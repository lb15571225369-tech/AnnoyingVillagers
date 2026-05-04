package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                return 3.0F;
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
        }, 3, -1.5F, (new Properties()));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.beta_update"));
    }
}
