package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class BlueDemonChestplateEventProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            Player player;
            LivingEntity livingentity;

            if (entity instanceof Player) {
                player = (Player) entity;
                player.getInventory().armor.set(0, new ItemStack(Blocks.AIR));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                livingentity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
            }

            if (entity instanceof Player) {
                player = (Player) entity;
                player.getInventory().armor.set(1, new ItemStack(Blocks.AIR));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                livingentity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
            }

            if (entity instanceof Player) {
                player = (Player) entity;
                player.getInventory().armor.set(3, new ItemStack(Blocks.AIR));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                livingentity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
            }

            if (!entity.level.isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("effect give @s annoyingvillagers:electify 1 0 true", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

        }
    }
}
