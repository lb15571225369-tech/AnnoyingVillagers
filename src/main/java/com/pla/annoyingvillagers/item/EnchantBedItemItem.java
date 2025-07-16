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
        list.add(new TextComponent("Right-click the Enchanted Bed once to save your respawn point. Upon death, you will not drop your items. After each use, you must right-click the bed again to reactivate the effect. Conflicts with the Totem of Undying."));
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
