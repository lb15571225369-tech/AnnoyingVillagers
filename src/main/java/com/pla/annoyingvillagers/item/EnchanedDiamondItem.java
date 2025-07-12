package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.procedures.CchunduzuanshiDangWuPinBeiHeChengHuoShaoLianShiProcedure;
import com.pla.annoyingvillagers.procedures.CchunduzuanshiDangWuPinYouWanJiaDiaoLuoProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchanedDiamondItem extends Item {

    public EnchanedDiamondItem() {
        super((new Properties()).tab(CreativeModeTab.TAB_MISC).stacksTo(64).rarity(Rarity.EPIC));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u8d28\u91cf\u6700\u597d\u7684\u94bb\u77f3\u4e4b\u4e00"));
    }

    public void onCraftedBy(ItemStack itemstack, Level level, Player player) {
        super.onCraftedBy(itemstack, level, player);
        CchunduzuanshiDangWuPinBeiHeChengHuoShaoLianShiProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, itemstack);
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, Player player) {
        CchunduzuanshiDangWuPinYouWanJiaDiaoLuoProcedure.execute(player.level, player);
        return true;
    }
}
