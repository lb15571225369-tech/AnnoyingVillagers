package com.pla.annoyingvillagers.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArmorUtil {
    public static void dropArmorSlot(LivingEntity living, EquipmentSlot slot, String preventArmor) {
        ItemStack stack = living.getItemBySlot(slot);
        if (stack.isEmpty()) return;

        if (!living.level().isClientSide) {
            living.spawnAtLocation(stack.copy());
        }

        living.setItemSlot(slot, ItemStack.EMPTY);
        if (living instanceof Player p) {
            p.getInventory().setChanged();
            if (!p.level().isClientSide()) {
                p.displayClientMessage(Component.literal("§eThe " + preventArmor + " rejects this piece!"), true);
            }
        }
    }
}
