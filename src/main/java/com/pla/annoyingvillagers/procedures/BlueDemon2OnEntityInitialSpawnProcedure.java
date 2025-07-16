package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class BlueDemon2OnEntityInitialSpawnProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "item replace entity @s weapon.mainhand with annoyingvillagers:dan_shou_zhan_shen_zhi_ren");
            }

            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
        }
    }
}
