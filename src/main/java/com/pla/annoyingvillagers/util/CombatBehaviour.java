package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

public class CombatBehaviour {
    public static final ItemStack HOSTILE_HEALING_POTION;
    public static final ItemStack HEALING_POTION;

    static {
        HOSTILE_HEALING_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING);
        HOSTILE_HEALING_POTION.setCount(1);
        HEALING_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
        HEALING_POTION.setCount(1);
    }

    public static void throwEnderPearl(Entity entity, float xRot) {
        if (xRot != 0.0F) {
            entity.setYRot(0.0F);
            entity.setXRot(xRot);
            entity.setYBodyRot(entity.getYRot());
            entity.setYHeadRot(entity.getYRot());
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.yBodyRotO = livingEntity.getYRot();
            livingEntity.yHeadRotO = livingEntity.getYRot();
        }

        Level level = entity.level();
        if (!level.isClientSide()) {
            Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
            projectile.setOwner(entity);
            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, new Random().nextBoolean() ? 1.0F : 2.0F, 0.0F);
            level.addFreshEntity(projectile);
        }
    }

    private static boolean performEatingGoldenAppleAction(Entity entity, LevelAccessor levelaccessor, AssetAccessor<? extends DynamicAnimation> dynamicanimation, LivingEntityPatch<?> livingEntityPatch) {
        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.EAT_OFFHAND, 0.0F);
            }

            if (levelaccessor instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.eat"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.eat"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }


            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean performDrinkingHealingPotionAction(Entity entity, LevelAccessor levelaccessor, AssetAccessor<? extends DynamicAnimation> dynamicanimation, LivingEntityPatch<?> livingEntityPatch) {
        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.DRINK_OFFHAND, 0.0F);
            }

            if (levelaccessor instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static void eatingGoldenApple(Entity entity, LevelAccessor levelaccessor, double amount) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

        if (livingEntityPatch != null) {
            final AssetAccessor<? extends DynamicAnimation> dynamicAnimation = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }
                ItemStack oldItem = livingEntity.getOffhandItem();
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(amount * 2.0D), 2, false, false));
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            if (!(dynamicAnimation instanceof AttackAnimation) && !(dynamicAnimation instanceof LongHitAnimation) && !(dynamicAnimation instanceof HitAnimation)) {
                                if (!entity.getPersistentData().getBoolean("av_healing")) {
                                    entity.getPersistentData().putBoolean("av_healing", true);
                                    ItemStack offhand = livingEntity.getOffhandItem();
                                    if (offhand.getItem() != Items.GOLDEN_APPLE) {
                                        livingEntity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_APPLE));
                                    }
                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                        new DelayedTask(4) {
                                            @Override
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                        new DelayedTask(4) {
                                                            public void run() {
                                                                if (entity.isAlive()) {
                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                        new DelayedTask(4) {
                                                                            public void run() {
                                                                                if (entity.isAlive()) {
                                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                        new DelayedTask(4) {
                                                                                            public void run() {
                                                                                                if (entity.isAlive()) {
                                                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                        new DelayedTask(4) {
                                                                                                            public void run() {
                                                                                                                if (entity.isAlive()) {
                                                                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                        new DelayedTask(4) {
                                                                                                                            public void run() {
                                                                                                                                if (entity.isAlive()) {
                                                                                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                        new DelayedTask(4) {
                                                                                                                                            public void run() {
                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                    if (performEatingGoldenAppleAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                                        new DelayedTask(3) {
                                                                                                                                                            public void run() {
                                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                                    if (levelaccessor instanceof Level level) {
                                                                                                                                                                        if (!level.isClientSide()) {
                                                                                                                                                                            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                                                        } else {
                                                                                                                                                                            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        };
                                                                                                                                                        new DelayedTask(20) {
                                                                                                                                                            public void run() {
                                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, oldItem);
                                                                                                                                                                    if (!livingEntity.level().isClientSide()) {
                                                                                                                                                                        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
                                                                                                                                                                    }
                                                                                                                                                                    if (!livingEntity.level().isClientSide()) {
                                                                                                                                                                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
                                                                                                                                                                    }
                                                                                                                                                                    livingEntityPatch.playAnimationSynchronized(Animations.BIPED_IDLE, 0.0F);
                                                                                                                                                                    entity.getPersistentData().putBoolean("av_healing", false);
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        };
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        };
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        };
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        };
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        };
                                                                                    }
                                                                                }
                                                                            }
                                                                        };
                                                                    }
                                                                }
                                                            }
                                                        };
                                                    }
                                                }
                                            }
                                        };
                                    }
                                }
                            }
                        }
                    }
                };
            }
        }
    }

    public static void drinkingHealingPotion(Entity entity, LevelAccessor levelaccessor, boolean isHostile, double amount) {
        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            final AssetAccessor<? extends DynamicAnimation> dynamicAnimation = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }
                ItemStack oldItem = livingEntity.getOffhandItem();
                ItemStack potionItem;
                if (isHostile) {
                    potionItem = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING);
                } else {
                    potionItem = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
                }
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(amount * 2.0D), 2, false, false));
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            if (!(dynamicAnimation instanceof AttackAnimation) && !(dynamicAnimation instanceof LongHitAnimation) && !(dynamicAnimation instanceof HitAnimation)) {
                                if (!entity.getPersistentData().getBoolean("av_healing")) {
                                    entity.getPersistentData().putBoolean("av_healing", true);
                                    ItemStack offhand = livingEntity.getOffhandItem();
                                    if (offhand.getItem() != potionItem.getItem()) {
                                        livingEntity.setItemInHand(InteractionHand.OFF_HAND, potionItem);
                                    }
                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                        new DelayedTask(4) {
                                            @Override
                                            public void run() {
                                                if (entity.isAlive()) {
                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                        new DelayedTask(4) {
                                                            public void run() {
                                                                if (entity.isAlive()) {
                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                        new DelayedTask(4) {
                                                                            public void run() {
                                                                                if (entity.isAlive()) {
                                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                        new DelayedTask(4) {
                                                                                            public void run() {
                                                                                                if (entity.isAlive()) {
                                                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                        new DelayedTask(4) {
                                                                                                            public void run() {
                                                                                                                if (entity.isAlive()) {
                                                                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                        new DelayedTask(4) {
                                                                                                                            public void run() {
                                                                                                                                if (entity.isAlive()) {
                                                                                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                        new DelayedTask(4) {
                                                                                                                                            public void run() {
                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                    if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                                        new DelayedTask(3) {
                                                                                                                                                            public void run() {
                                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                                    if (levelaccessor instanceof Level level) {
                                                                                                                                                                        if (!level.isClientSide()) {
                                                                                                                                                                            level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                                                        } else {
                                                                                                                                                                            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        };
                                                                                                                                                        new DelayedTask(20) {
                                                                                                                                                            public void run() {
                                                                                                                                                                if (entity.isAlive()) {
                                                                                                                                                                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, oldItem);
                                                                                                                                                                    if (!livingEntity.level().isClientSide()) {
                                                                                                                                                                        if (isHostile) {
                                                                                                                                                                            livingEntity.addEffect(new MobEffectInstance(MobEffects.HARM, 40, 0));
                                                                                                                                                                        } else {
                                                                                                                                                                            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    if (!livingEntity.level().isClientSide()) {
                                                                                                                                                                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
                                                                                                                                                                    }
                                                                                                                                                                    livingEntityPatch.playAnimationSynchronized(Animations.BIPED_IDLE, 0.0F);
                                                                                                                                                                    entity.getPersistentData().putBoolean("av_healing", false);
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        };
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        };
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        };
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        };
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        };
                                                                                    }
                                                                                }
                                                                            }
                                                                        };
                                                                    }
                                                                }
                                                            }
                                                        };
                                                    }
                                                }
                                            }
                                        };
                                    }
                                }
                            }
                        }
                    }
                };
            }
        }
    }
}
