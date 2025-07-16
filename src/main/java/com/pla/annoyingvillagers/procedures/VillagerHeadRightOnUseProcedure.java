package com.pla.annoyingvillagers.procedures;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class VillagerHeadRightOnUseProcedure {

    public static void execute(Entity entity) {
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
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team join villagers @s");
                }

                entity.getPersistentData().putBoolean("villager_player", true);
                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(new TextComponent("\u4f60\u5df2\u5e26\u4e0a\u6751\u6c11\u5934\u5957\uff0c\u73b0\u5728\u6751\u6c11\u58eb\u5175\u5c06\u4e0d\u4f1a\u653b\u51fb\u4f60"), false);
                    }
                }

                if (entity instanceof Player) {
                    player1 = (Player) entity;
                    if (!player1.level.isClientSide()) {
                        player1.displayClientMessage(new TextComponent("\u4f7f\u7528\u4e0b\u8e72\u952e+\u53f3\u952e\u5207\u6362\u4f2a\u88c5/\u653b\u51fb\u6a21\u5f0f"), false);
                    }
                }
            }

        }
    }
}
