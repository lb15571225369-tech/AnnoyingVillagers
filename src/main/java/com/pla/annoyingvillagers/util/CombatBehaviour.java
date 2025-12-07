package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
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

    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0),
                m.m32 + base.getZ()
        );
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
            Vec3 handPos = getJointWithTranslation(
                    entity,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolL
            );

            new DelayedTask(5) {
                @Override
                public void run() {
                    if (handPos != null) {
                        Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                        projectile.setOwner(entity);
                        projectile.setPos(handPos.x, handPos.y, handPos.z);
                        projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, new Random().nextBoolean() ? 1.0F : 2.0F, 0.0F);
                        level.addFreshEntity(projectile);
                        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }
            };

        }
    }

    private static void performEatingGoldenAppleActionMainHand(Entity entity,
                                                               LevelAccessor levelaccessor,
                                                               LivingEntityPatch<?> livingEntityPatch) {
        var currentAnim = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
        if (currentAnim instanceof AttackAnimation ||
                currentAnim instanceof LongHitAnimation ||
                currentAnim instanceof HitAnimation) {
            return;
        }

        if (!entity.level().isClientSide() && entity.getServer() != null && entity instanceof LivingEntity livingEntity) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_EAT, 0.0F);
        }

        if (levelaccessor instanceof Level level) {
            SoundEvent eat = ForgeRegistries.SOUND_EVENTS.getValue(
                    ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.eat")
            );
            if (eat != null) {
                if (!level.isClientSide()) {
                    level.playSound(null,
                            entity.blockPosition(),
                            eat,
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F);
                } else {
                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                            eat,
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F,
                            false);
                }
            }
        }

        if (!entity.level().isClientSide() && entity.getServer() != null) {
            try {
                entity.getServer().getCommands().getDispatcher().execute(
                        "execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10",
                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException ignored) {}
        }

    }

    private static boolean performDrinkingHealingPotionAction(Entity entity, LevelAccessor levelaccessor, AssetAccessor<? extends DynamicAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        if (!(dynamicAnimation instanceof AttackAnimation) && !(dynamicAnimation instanceof LongHitAnimation) && !(dynamicAnimation instanceof HitAnimation)) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.DRINK_OFFHAND, 0.0F);
            }

            if (levelaccessor instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static void eatingGoldenApple(Entity entity, LevelAccessor levelaccessor, double amount) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

        if (livingEntityPatch != null && entity instanceof LivingEntity livingEntity) {
            if (entity instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing()) {
                return;
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory && pathfinderMobInventory.isHealing()) {
                return;
            }
            if (entity.isPassenger()) {
                entity.stopRiding();
            }
            livingEntity.addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (amount * 2.0D), 2, false, false)
            );
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                if (playerNpcEntity.isHealing()) {
                    return;
                } else {
                    playerNpcEntity.setHealing(true);
                }
            }
            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                if (pathfinderMobInventory.isHealing()) {
                    return;
                } else {
                    pathfinderMobInventory.setHealing(true);
                }
            }
            new DelayedTask(20) {
                @Override
                public void run() {
                    if (!entity.isAlive()) return;

                    LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (patch == null) return;
                    var currentAnim = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getAnimation();
                    if (currentAnim instanceof AttackAnimation ||
                            currentAnim instanceof LongHitAnimation ||
                            currentAnim instanceof HitAnimation) {
                        return;
                    }
                    Runnable bite = () -> performEatingGoldenAppleActionMainHand(entity, levelaccessor, patch);
                    int biteDelay = 4;
                    int totalBites = 7;

                    for (int i = 0; i < totalBites; i++) {
                        int delay = 4 + i * biteDelay;
                        new DelayedTask(delay) {
                            @Override
                            public void run() {
                                if (entity.isAlive()) {
                                    bite.run();
                                }
                            }
                        };
                    }

                    new DelayedTask(4 + totalBites * biteDelay - 1) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;
                            if (levelaccessor instanceof Level level) {
                                SoundEvent burp = ForgeRegistries.SOUND_EVENTS.getValue(
                                        ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp")
                                );
                                if (burp != null) {
                                    if (!level.isClientSide()) {
                                        level.playSound(null,
                                                entity.blockPosition(),
                                                burp,
                                                SoundSource.NEUTRAL,
                                                1.5F, 1.0F);
                                    } else {
                                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                                                burp,
                                                SoundSource.NEUTRAL,
                                                1.5F, 1.0F,
                                                false);
                                    }
                                }
                            }
                        }
                    };
                    new DelayedTask(4 + totalBites * biteDelay) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, playerNpcEntity.getMainWeaponItem());
                            }
                            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, pathfinderMobInventory.getMainWeaponItem());
                            }

                            if (!livingEntity.level().isClientSide()) {
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1));
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
                            }

                            LivingEntityPatch<?> livingEntityPatch1 = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch1.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                            }

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                playerNpcEntity.setHealing(false);
                            }
                            if (entity instanceof PathfinderMobInventory pathfinderMobInventory) {
                                pathfinderMobInventory.setHealing(false);
                            }
                        }
                    };
                }
            };
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
                                                                                                                                                                            level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
                                                                                                                                                                        } else {
                                                                                                                                                                            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
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
