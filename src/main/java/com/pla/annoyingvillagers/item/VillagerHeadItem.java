package com.pla.annoyingvillagers.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.procedures.VillagerHeadDangWuPinYouWanJiaDiaoLuoProcedure;
import com.pla.annoyingvillagers.procedures.VillagerHeadDangWuPinZaiBeiBaoZhongMeiKeFaShengProcedure;
import com.pla.annoyingvillagers.procedures.VillagerHeadDangYouJianDianJiKongQiShiProcedure;

public class VillagerHeadItem extends Item {

    public VillagerHeadItem() {
        super((new Properties()).tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u4f2a\u88c5\u6751\u6c11\u7684\u5de5\u5177\uff0c\u53f3\u952e\u4f69\u6234\uff0c\u4e0b\u8e72+\u53f3\u952e\u5207\u6362\u4f2a\u88c5/\u653b\u51fb\u6a21\u5f0f"));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);
        ItemStack itemstack = (ItemStack) interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        VillagerHeadDangYouJianDianJiKongQiShiProcedure.execute(player);
        return interactionresultholder;
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        VillagerHeadDangWuPinZaiBeiBaoZhongMeiKeFaShengProcedure.execute(entity);
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, Player player) {
        VillagerHeadDangWuPinYouWanJiaDiaoLuoProcedure.execute(player);
        return true;
    }
}
