package com.pla.annoyingvillagers.item;

import java.util.List;

import net.minecraft.network.chat.Component;
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

public class EnchantBedItem extends Item {

    public EnchantBedItem() {
        super((new Properties()).stacksTo(1).fireResistant().rarity(Rarity.COMMON));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.enchanted_bed"));
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
