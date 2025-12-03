package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class PlayerNpcOnHurtProcedure {
    public static void execute(final PlayerNpcEntity entity, Entity attacker) {
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
                                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 3);
                                    entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 3);
                                }
                            }

                            new DelayedTask(80) {
                                @Override
                                public void run() {
                                    CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                    if (entity.getMainWeaponItem() != ItemStack.EMPTY) {
                                        entity.setItemInHand(InteractionHand.MAIN_HAND, entity.getMainWeaponItem().copy());
                                    } else {
                                        entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                    }
                                }
                            };
                        }
                    };
                }

                entity.setEnderPearlCooldown();
            }
        }
    }
}

