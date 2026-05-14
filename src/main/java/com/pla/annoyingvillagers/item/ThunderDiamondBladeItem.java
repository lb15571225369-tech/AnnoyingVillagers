package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ThunderDiamondBladeItem extends SwordItem {

    public ThunderDiamondBladeItem() {
        super(new Tier() {
            public int getUses() {
                return 2561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 4.0F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get()));
            }
        }, 3, -2.0F, (new Properties()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, pPlayer);
            if (pPlayer.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get())) {
                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, pPlayer, true);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }
}
