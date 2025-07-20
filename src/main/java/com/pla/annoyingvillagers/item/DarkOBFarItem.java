package com.pla.annoyingvillagers.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.DarkOBFarEntity;

import java.util.Random;

public class DarkOBFarItem extends Item {

    public DarkOBFarItem() {
        super((new Properties()).tab((CreativeModeTab) null).durability(100));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer) livingentity;
            double d0 = serverplayer.getX();
            double d1 = serverplayer.getY();
            double d2 = serverplayer.getZ();
            DarkOBFarEntity darkobfarentity = DarkOBFarEntity.shoot(level, serverplayer, (Random) level.getRandom(), 1.0F, 9.0D, 0);

            itemstack.hurtAndBreak(1, serverplayer, (serverplayer1) -> {
                serverplayer1.broadcastBreakEvent(serverplayer.getUsedItemHand());
            });
            darkobfarentity.pickup = Pickup.DISALLOWED;
        }

    }
}
