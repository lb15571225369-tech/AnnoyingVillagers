package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class VillagerHeadRightOnUseProcedure {

    public static void execute(Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() == Blocks.AIR.asItem()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    player.getInventory().armor.set(3, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_HEAD.get()));
                    player.getInventory().setChanged();
                } else if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    livingentity1.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_HEAD.get()));
                }

                Player player1;

                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    ItemStack itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_HEAD.get());

                    player1.getInventory().clearOrCountMatchingItems((itemstack2) -> {
                        return itemstack1.getItem() == itemstack2.getItem();
                    }, 1, player1.inventoryMenu.getCraftSlots());
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join villagers @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                }

                entity.getPersistentData().putBoolean("villager_player", true);
                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(Component.literal("You have put on the villager helmet. Villager soldiers will no longer attack you."), false);
                    }
                }

                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(Component.literal("Sneak + Right-Click to toggle Disguise/Attack Mode"), false);
                    }
                }
            }

        }
    }
}
