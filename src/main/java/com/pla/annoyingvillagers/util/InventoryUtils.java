package com.pla.annoyingvillagers.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static void transferInventory(SimpleContainer from, SimpleContainer to) {
        int size = Math.min(from.getContainerSize(), to.getContainerSize());
        for (int i = 0; i < size; i++) {
            ItemStack stack = from.getItem(i);
            if (!stack.isEmpty()) {
                to.setItem(i, stack.copy());
                from.setItem(i, ItemStack.EMPTY);
            }
        }
    }
}
