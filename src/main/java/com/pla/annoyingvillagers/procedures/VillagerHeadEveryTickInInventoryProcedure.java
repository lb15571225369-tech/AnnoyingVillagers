package com.pla.annoyingvillagers.procedures;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

public class VillagerHeadEveryTickInInventoryProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            ItemStack itemstack;

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
            } else {
                itemstack = ItemStack.EMPTY;
            }

            if (itemstack.getItem() != AnnoyingVillagersModItems.VILLAGER_HEAD.get() && entity.getPersistentData().getBoolean("villager_player")) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "team leave @s[team=villagers]");
                }

                entity.getPersistentData().putBoolean("villager_player", false);
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    if (!player.level.isClientSide()) {
                        player.displayClientMessage(new TextComponent("\u4f60\u5df2\u53d6\u4e0b\u5934\u5957\uff0c\u73b0\u5728\u6751\u6c11\u58eb\u5175\u5c06\u4f1a\u653b\u51fb\u4f60"), false);
                    }
                }
            }

        }
    }
}
