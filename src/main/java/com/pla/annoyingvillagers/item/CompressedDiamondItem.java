package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.CompressedDiamondOnCraftedProcedure;
import com.pla.annoyingvillagers.procedures.CompressedDiamondOnDroppedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class CompressedDiamondItem extends Item {

    public CompressedDiamondItem() {
        super((new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).stacksTo(64).rarity(Rarity.EPIC));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("Compressed Diamond: Highest Quality, Maximum Hardness"));
    }

    public void onCraftedBy(ItemStack itemstack, Level level, Player player) {
        super.onCraftedBy(itemstack, level, player);
        CompressedDiamondOnCraftedProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player, itemstack);
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, Player player) {
        CompressedDiamondOnDroppedProcedure.execute(player.level, player);
        return true;
    }
}
