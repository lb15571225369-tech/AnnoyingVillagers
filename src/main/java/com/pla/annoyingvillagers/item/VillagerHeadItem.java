package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class VillagerHeadItem extends Item {

    public VillagerHeadItem() {
        super((new Properties()).stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("tooltip.annoyingvillagers.villager_head"));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == Blocks.AIR.asItem()) {
            player.getInventory().armor.set(3, new ItemStack(AnnoyingVillagersModItems.VILLAGER_HEAD.get()));
            TeamUtil.addOrJoinTeam(player, "villagers");
            player.getInventory().setChanged();
            player.setItemInHand(interactionHand, ItemStack.EMPTY);
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("You have put on the villager helmet. Villager soldiers will no longer attack you."), false);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemStack, level, entity, i, flag);
        if (entity instanceof LivingEntity livingEntity
                && livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() != AnnoyingVillagersModItems.VILLAGER_HEAD.get()) {
            if (TeamUtil.isInTeam(entity, "villagers")) {
                TeamUtil.leaveTeam(entity, "villagers");

                if (entity instanceof Player player && !player.level().isClientSide()) {
                    player.displayClientMessage(
                            Component.literal("You have removed your helmet. Villager soldiers will now attack you."),
                            false
                    );
                }
            }
        }
    }

    public boolean onDroppedByPlayer(ItemStack itemstack, Player player) {
        if (TeamUtil.isInTeam(player, "villagers")) {
            TeamUtil.leaveTeam(player, "villagers");
        }
        return true;
    }
}
