package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.procedures.CchunduzuanshiDangWuPinBeiHeChengHuoShaoLianShiProcedure;
import com.pla.annoyingvillagers.procedures.CchunduzuanshiDangWuPinYouWanJiaDiaoLuoProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class CchunduzuanshiItem extends Item {

    public CchunduzuanshiItem() {
        super((new Properties()).tab(CreativeModeTab.TAB_MISC).stacksTo(64).rarity(Rarity.EPIC));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u88ab\u538b\u7f29\u7684\u94bb\u77f3\uff0c\u8d28\u91cf\u6700\u597d\uff0c\u786c\u5ea6\u6700\u786c"));
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
