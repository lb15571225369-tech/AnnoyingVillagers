package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.PathfinderMobInventory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

public class AlexOnHurtProcedure {

    public static void execute(LevelAccessor levelaccessor, final PathfinderMobInventory entity, Entity attacker) {
        if (entity != null && attacker != null) {
            if (!entity.getPersistentData().getBoolean("kick_x")) {
                if (attacker == ((Mob)entity).getTarget() && entity.isAlive()) {
                    if (entity.getEnderPearlCooldown() == 0) {
                        if (Math.random() <= 0.2D && !entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "tellraw @a {\"text\":\"<Alex> Are you being serious ?\"}",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                        }

                        if (Math.random() <= 0.5D) {
                            if (!entity.level().isClientSide()) {
                                entity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 1, 1, false, false));
                            }
                        }

                        CombatBehaviour.throwEnderPearl(entity, 180.0F);

                        if (Math.random() <= 0.2D) {
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    if (entity.isAlive()) {
                                        CombatBehaviour.throwEnderPearl(entity, 90.0F);
                                    }
                                }
                            };
                        }

                        if (Math.random() <= 0.3D) {
                            new DelayedTask(20) {
                                @Override
                                public void run() {
                                    CombatBehaviour.throwEnderPearl(entity, 180.0F);
                                    entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                                    if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.POWER_ARROWS, 5);
                                        entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.PUNCH_ARROWS, 5);
                                    }
                                    new DelayedTask(80) {
                                        @Override
                                        public void run() {
                                            CombatBehaviour.throwEnderPearl(entity, 0.0F);
                                            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                                            entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.FIRE_ASPECT, 2);
                                            entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.KNOCKBACK, 2);
                                            entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.UNBREAKING, 5);
                                            entity.getItemInHand(InteractionHand.MAIN_HAND).enchant(Enchantments.SHARPNESS, 5);
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
    }
}
