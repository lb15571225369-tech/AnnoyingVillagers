package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.ArmorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LegendarySwordItem extends SwordItem {

    public LegendarySwordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.32F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide()) {
            ArmorUtil.damageArmor(pTarget, pAttacker, 15);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public boolean isRepairable(@NotNull ItemStack itemStack) {
        return false;
    }

    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> componentList, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
        componentList.add(Component.translatable("tooltip.annoyingvillagers.legendary_sword"));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return true;
    }
}
