package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Random;

public class StealthAttackItem extends Item {

    public StealthAttackItem() {
        super((new Properties()).durability(100));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer) livingentity;

            if (serverplayer != null && serverplayer.isAlive()) {
                StealthAttackEntity yinshenentity = StealthAttackEntity.shoot(level, serverplayer, new Random(), 1.0F, 18.0D, 7);

                itemstack.hurtAndBreak(1, serverplayer, (serverplayer1) -> {
                    serverplayer1.broadcastBreakEvent(serverplayer.getUsedItemHand());
                });
                yinshenentity.pickup = Pickup.DISALLOWED;
            }
        }

    }
}

