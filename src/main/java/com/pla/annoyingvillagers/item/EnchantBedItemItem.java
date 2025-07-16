package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.procedures.EnchantBedItemWhenHeldEveryTickProcedure;
import com.pla.annoyingvillagers.procedures.EnchantBedItemOnRightClickBlockProcedure;

public class EnchantBedItemItem extends Item {

    public EnchantBedItemItem() {
        super((new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB).stacksTo(1).fireResistant().rarity(Rarity.COMMON));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(new TextComponent("\u53f3\u952e\u4e00\u6b21\u9644\u9b54\u5e8a\u4ee5\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u6b7b\u4ea1\u5c06\u4e0d\u4f1a\u6389\u843d\u8eab\u4e0a\u7684\u7269\u54c1\uff0c\u6bcf\u6b21\u7528\u5b8c\u4e00\u6b21\u9700\u8981\u518d\u6b21\u53f3\u952e\u9644\u9b54\u5e8a\u624d\u8d77\u6548\uff0c\u4e0e\u4e0d\u6b7b\u56fe\u817e\u76f8\u51b2\u7a81"));
    }

    public InteractionResult useOn(UseOnContext useoncontext) {
        super.useOn(useoncontext);
        EnchantBedItemOnRightClickBlockProcedure.execute(useoncontext.getLevel(), (double) useoncontext.getClickedPos().getX(), (double) useoncontext.getClickedPos().getY(), (double) useoncontext.getClickedPos().getZ(), useoncontext.getPlayer());
        return InteractionResult.SUCCESS;
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        if (flag) {
            EnchantBedItemWhenHeldEveryTickProcedure.execute(entity);
        }

    }
}
