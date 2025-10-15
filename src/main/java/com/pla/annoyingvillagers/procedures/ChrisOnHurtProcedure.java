package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.PathfinderMobInventory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class ChrisOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final PathfinderMobInventory entity) {
        if (entity != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (entity.getEnderPearlCooldown() == 0) {
                    if (Math.random() <= 0.3D) {
                        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
                        sword.enchant(Enchantments.KNOCKBACK, 5);
                        sword.enchant(Enchantments.UNBREAKING, 5);
                        sword.enchant(AnnoyingVillagersModEnchantments.BREAK_ARMOR.get(), 5);
                        entity.setItemInHand(InteractionHand.MAIN_HAND, sword);
                    } else if (Math.random() <= 0.3D) {
                        ItemStack sword = new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());
                        sword.enchant(Enchantments.SMITE, 4);
                        sword.enchant(Enchantments.SHARPNESS, 4);
                        sword.enchant(Enchantments.SWEEPING_EDGE, 4);
                        entity.setItemInHand(InteractionHand.MAIN_HAND, sword);
                    }

                    if (Math.random() <= 0.3D) {
                        new DelayedTask(20) {
                            public void run() {
                                if (entity.isAlive()) {
                                    CombatBehaviour.throwEnderPearl(entity, 180.0F);
                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 5);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 5);
                                    }
                                    new DelayedTask(80) {
                                        public void run() {
                                            CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                            if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                                entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 5);
                                                entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(AnnoyingVillagersModEnchantments.BREAK_ARMOR.get(), 5);
                                                entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.UNBREAKING, 5);
                                                entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 2);
                                            }
                                        }
                                    };
                                }
                            }
                        };
                    }

                    CombatBehaviour.throwEnderPearl(entity, 180.0F);

                    entity.setSprinting(true);
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (entity.isAlive()) {
                                entity.setSprinting(false);
                            }
                        }
                    };

                    if (Math.random() <= 0.32D) {
                        if (!entity.level().isClientSide()) {
                            entity.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.ESCAPE.get(), 1, 1, false, false));
                        }
                    }

                    entity.setEnderPearlCooldown();
                }
            }

        }
    }
}
