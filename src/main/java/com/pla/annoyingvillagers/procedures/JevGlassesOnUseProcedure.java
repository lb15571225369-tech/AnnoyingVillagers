package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class JevGlassesOnUseProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.getInventory().armor.set(3, new ItemStack((ItemLike) AnnoyingVillagersModItems.JEV_GLASSES.get()));
                player.getInventory().setChanged();
            } else if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                livingentity.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.JEV_GLASSES.get()));
            }

            LivingEntity livingentity1;

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                ItemStack itemstack = new ItemStack(Blocks.AIR);

                itemstack.setCount(1);
                livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity1 instanceof Player) {
                    Player player1 = (Player) livingentity1;

                    player1.getInventory().setChanged();
                }
            }

            ItemStack itemstack1;

            if (entity instanceof LivingEntity) {
                livingentity1 = (LivingEntity) entity;
                itemstack1 = livingentity1.getOffhandItem();
            } else {
                itemstack1 = ItemStack.EMPTY;
            }

            if (itemstack1.getItem() == AnnoyingVillagersModItems.JEV_GLASSES.get() && entity instanceof LivingEntity) {
                LivingEntity livingentity2 = (LivingEntity) entity;
                ItemStack itemstack2 = new ItemStack(Blocks.AIR);

                itemstack2.setCount(1);
                livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                if (livingentity2 instanceof Player) {
                    Player player2 = (Player) livingentity2;

                    player2.getInventory().setChanged();
                }
            }

        }
    }
}

