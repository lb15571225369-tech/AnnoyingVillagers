package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DiamondShearItem extends ShearsItem {

    public DiamondShearItem() {
        super((new Properties()).durability(100));
    }

    public int getEnchantmentValue() {
        return 12;
    }

    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return 8.0F;
    }

    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entity, InteractionHand hand) {
        InteractionResult interactionResult = super.interactLivingEntity(stack, playerIn, entity, hand);
        if (!playerIn.level().isClientSide() && !entity.getActiveEffects().isEmpty()) {
            ServerPlayer serverPlayer = (ServerPlayer) playerIn;
            entity.removeAllEffects();
            playerIn.level().playSound(null, entity.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);
            stack.hurtAndBreak(1, serverPlayer, (serverPlayer1) -> {
                serverPlayer1.broadcastBreakEvent(hand);
            });
        }
        return interactionResult;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal(
                "Right-click to clear all effects from enemy."
        ));
    }
}
