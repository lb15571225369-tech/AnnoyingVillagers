package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class VillagerScoutCaptainOnPlayerTouchProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (Math.random() == 0.05D) {
                new DelayedTask(40) {
                    @Override
                    public void run() {
                        LivingEntity livingentity;

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            ItemStack itemstack = new ItemStack(Items.BOW);

                            itemstack.setCount(1);
                            livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                            if (livingentity instanceof Player) {
                                Player player = (Player)livingentity;

                                player.getInventory().setChanged();
                            }
                        }

                        ItemStack itemstack1;

                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack1 = livingentity.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemstack1.enchant(Enchantments.POWER_ARROWS, 3);
                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity)entity;
                            itemstack1 = livingentity.getOffhandItem();
                        } else {
                            itemstack1 = ItemStack.EMPTY;
                        }

                        itemstack1.enchant(Enchantments.PUNCH_ARROWS, 3);
                    }
                };
            }

        }
    }
}

