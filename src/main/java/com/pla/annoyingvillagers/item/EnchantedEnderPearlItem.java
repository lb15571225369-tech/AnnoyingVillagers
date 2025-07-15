package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import com.pla.annoyingvillagers.procedures.FumomoyingzhenzhuDangYuanChengWuPinShiYongShiProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
        super((new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).stacksTo(16));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u8fd9\u662f\u4e00\u4e2a\u62e5\u6709\u65e0\u89c6\u6454\u843d\u4f24\u5bb3\u9b54\u5492\u7684\u672b\u5f71\u73cd\u73e0"));
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
            EnchantedEnderPearlEntity fumomoyingzhenzhuentity = EnchantedEnderPearlEntity.shoot(level, serverplayer, level.getRandom(), 1.3F, 0.0D, 0);

            itemstack.hurtAndBreak(1, serverplayer, (serverplayer1) -> {
                serverplayer1.broadcastBreakEvent(serverplayer.getUsedItemHand());
            });
            fumomoyingzhenzhuentity.pickup = Pickup.DISALLOWED;
            FumomoyingzhenzhuDangYuanChengWuPinShiYongShiProcedure.execute(serverplayer, itemstack);
        }

    }
}
