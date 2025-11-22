package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class PlayerNpcOnHurtProcedure {
    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final PlayerNpcEntity entity, Entity attacker, double amount) {
        if (entity != null && attacker != null) {
            if (entity.getEnderPearlCooldown() == 0) {
                CombatBehaviour.throwEnderPearl(entity, 180.0F);

                if (Math.random() <= 0.5D) {
                    new DelayedTask(20) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                CombatBehaviour.throwEnderPearl(entity, 90.0F);
                            }
                        }
                    };
                }

                if (Math.random() <= 0.1D) {
                    new DelayedTask(20) {

                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                CombatBehaviour.throwEnderPearl(entity, 180.0F);
                                ((Mob) entity).setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                if (!((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                    ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 3);
                                    ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 3);
                                }
                            }

                            new DelayedTask(80) {
                                @Override
                                public void run() {
                                    CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                    ((Mob) entity).setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                    if (!((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                        ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                        ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                    }

                                    if (Math.random() <= 0.2D) {
                                        ((Mob) entity).setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_SPEAR.get()));
                                        if (!((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                            ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                            ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                        }
                                    }

                                    if (Math.random() <= 0.1D) {
                                        ((Mob) entity).setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
                                        if (!((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                            ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 3);
                                            ((Mob) entity).getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SMITE, 3);
                                        }
                                    }
                                }
                            };
                        }
                    };
                }

                entity.setEnderPearlCooldown();
            }

            if (entity.getGapCooldown() == 0 && entity.getHealth() <= ((float) 2/3 * entity.getMaxHealth())) {
                CombatBehaviour.eatingGoldenApple(entity, levelaccessor, amount);
                entity.setGapCooldown();
            }
        }
    }
}

