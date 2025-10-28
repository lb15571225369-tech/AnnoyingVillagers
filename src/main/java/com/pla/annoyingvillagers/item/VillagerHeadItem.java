package com.pla.annoyingvillagers.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.procedures.VillagerHeadWhenDroppedByPlayerProcedure;
import com.pla.annoyingvillagers.procedures.VillagerHeadEveryTickInInventoryProcedure;
import com.pla.annoyingvillagers.procedures.VillagerHeadRightOnUseProcedure;

public class VillagerHeadItem extends Item {

    public VillagerHeadItem() {
        super((new Properties()).stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.villager_head"));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResultHolder<ItemStack> interactionresultholder = super.use(level, player, interactionhand);
        ItemStack itemstack = (ItemStack) interactionresultholder.getObject();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        VillagerHeadRightOnUseProcedure.execute(player);
        return interactionresultholder;
    }

    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
        VillagerHeadEveryTickInInventoryProcedure.execute(entity);
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, Player player) {
        VillagerHeadWhenDroppedByPlayerProcedure.execute(player);
        return true;
    }
}
