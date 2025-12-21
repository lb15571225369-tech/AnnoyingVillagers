package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class JevGlassesItem extends Item {

    public JevGlassesItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.jev_glasses"));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == Blocks.AIR.asItem()) {
            player.getInventory().armor.set(3, new ItemStack(AnnoyingVillagersModItems.JEV_GLASSES.get()));
            player.getInventory().setChanged();
            player.setItemInHand(interactionHand, ItemStack.EMPTY);
        }
        return super.use(level, player, interactionHand);
    }
}
