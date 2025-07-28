package com.pla.annoyingvillagers.item;

import java.util.List;
import java.util.Random;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import com.pla.annoyingvillagers.procedures.EnchantedEnderPearlWhenItemUsedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchantedEnderPearlItem extends Item {

    public EnchantedEnderPearlItem() {
        super((new Properties()).stacksTo(16));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.literal("This is an Ender Pearl enchanted to ignore fall damage"));
    }

    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer) livingentity;
            double d0 = serverplayer.getX();
            double d1 = serverplayer.getY();
            double d2 = serverplayer.getZ();
            EnchantedEnderPearlEntity enchantedEnderPearl = EnchantedEnderPearlEntity.shoot(level, serverplayer, (Random) level.getRandom(), 1.3F, 0.0D, 0);

            itemstack.hurtAndBreak(1, serverplayer, (serverplayer1) -> {
                serverplayer1.broadcastBreakEvent(serverplayer.getUsedItemHand());
            });
            enchantedEnderPearl.pickup = Pickup.DISALLOWED;
            EnchantedEnderPearlWhenItemUsedProcedure.execute(serverplayer, itemstack);
        }

    }
}
