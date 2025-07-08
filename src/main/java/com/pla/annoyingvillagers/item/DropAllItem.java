package com.pla.annoyingvillagers.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.procedures.DropAllDangYouJianDianJiKongQiShiProcedure;

public class DropAllItem extends Item {

    public DropAllItem() {
        super((new Properties()).tab(CreativeModeTab.TAB_TOOLS).stacksTo(64).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u4e0b\u8e72\u53f3\u952e\u5b89\u653e\uff0c\u6b64\u9677\u9631\u4f1a\u89e6\u53d1\u6389\u843d\u8eab\u4e0a\u6240\u6709\u7684\u7269\u54c1"));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);
        ItemStack itemstack = (ItemStack) interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        DropAllDangYouJianDianJiKongQiShiProcedure.execute(level, d0, d1, d2, player);
        return interactionresultholder;
    }
}
