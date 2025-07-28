package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ThrowingPearlKeyPressedProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: ThrowingPearlKeyPressedProcedure execute is called");
            Level level;
            Projectile projectile;

            if (entity instanceof Player) {
                Player player = (Player)entity;

                if (player.getInventory().contains(new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get()))) {
                    AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: ender_pearl_used is {}", entity.getPersistentData().getBoolean("ender_pearl_used"));
                    if (!entity.getPersistentData().getBoolean("ender_pearl_used")) {
                        entity.getPersistentData().putBoolean("ender_pearl_used", true);
                        level = entity.level();
                        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: ThrowingPearlKeyPressedProcedure shooting");
                        projectile = new EnchantedEnderPearlEntity((EntityType) AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), level);
                        projectile.setOwner(entity);
                        ((EnchantedEnderPearlEntity) projectile).setBaseDamage((double)0.0F);
                        ((EnchantedEnderPearlEntity) projectile).setKnockback(0);
                        projectile.setSilent(true);
                        projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                        projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                        level.addFreshEntity(projectile);
                        new DelayedTask(20) {
                            public void run() {
                                entity.getPersistentData().putBoolean("ender_pearl_used", false);
                            }
                        };
                    }

                    return;
                }
            }

            if (entity instanceof Player) {
                Player player1 = (Player)entity;

                if (player1.getInventory().contains(new ItemStack(Items.ENDER_PEARL)) && !entity.getPersistentData().getBoolean("ender_pearl_used")) {
                    entity.getPersistentData().putBoolean("ender_pearl_used", true);
                    level = entity.level();
                    projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                    projectile.setOwner(entity);
                    projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
                    projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5F, 0.0F);
                    level.addFreshEntity(projectile);

                    new DelayedTask(15) {
                        @Override
                        public void run() {
                            entity.getPersistentData().putBoolean("ender_pearl_used", false);
                        }
                    };
                    if (entity instanceof Player) {
                        Player player2 = (Player)entity;
                        ItemStack itemstack1 = new ItemStack(Items.ENDER_PEARL);

                        player2.getInventory().clearOrCountMatchingItems((itemstack2) -> {
                            return itemstack1.getItem() == itemstack2.getItem();
                        }, 1, player2.inventoryMenu.getCraftSlots());
                    }
                }
            }
        }
    }
}

