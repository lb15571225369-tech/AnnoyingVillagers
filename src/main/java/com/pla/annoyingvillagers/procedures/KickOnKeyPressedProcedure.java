package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.animations.types.AttackBreakAnimation;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

public class KickOnKeyPressedProcedure {
    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!livingEntity.hasEffect(AnnoyingVillagersModMobEffects.EC.get())) {
                if (!entity.getPersistentData().getBoolean("kick_x")) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                        if (!(dynamicanimation instanceof AttackBreakAnimation)) {
                            if (!(dynamicanimation instanceof LongHitAnimation)) {
                                entity.getPersistentData().putBoolean("kick_x", true);
                                new DelayedTask(6) {
                                    @Override
                                    public void run() {
                                        entity.getPersistentData().putBoolean("kick_x", false);
                                    }
                                };
                                if (entity.isShiftKeyDown()) {
                                    if (entity.isSprinting()) {
                                        PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.level().isClientSide() && entity.getServer() != null) {
                                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                            if (livingEntityPatch != null) {
                                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_COMBO, 0.0F);
                                            }
                                        }
                                    } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_H, 0.0F);
                                        }
                                    }
                                } else {
                                    if (entity.isSprinting()) {
                                        if (entity.getPersistentData().getDouble("air_kick") != 1.0D) {
                                            entity.getPersistentData().putDouble("air_kick", 1.0D);
                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                                if (livingEntityPatch != null) {
                                                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_RUSH, 0.0F);
                                                }
                                            }

                                            new DelayedTask(80) {
                                                @Override
                                                public void run() {
                                                    if (entity.getPersistentData().getDouble("air_kick") == 1.0D) {
                                                        entity.getPersistentData().putDouble("air_kick", 0.0D);
                                                    }
                                                }
                                            };
                                        }
                                    } else if (entity.getPersistentData().getDouble("kick") < 1.0D) {
                                        entity.getPersistentData().putDouble("kick", 1.5D);
                                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                            if (livingEntityPatch != null) {
                                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_1, 0.0F);
                                            }
                                        }

                                        entity.getPersistentData().putBoolean("kick_x", true);
                                        new DelayedTask(15) {
                                            @Override
                                            public void run() {
                                                entity.getPersistentData().putDouble("kick", 2.0D);
                                            }
                                        };
                                    } else if (entity.getPersistentData().getDouble("kick") == 2.0D) {
                                        entity.getPersistentData().putDouble("kick", 2.5D);
                                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                            if (livingEntityPatch != null) {
                                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_2, 0.0F);
                                            }
                                        }

                                        entity.getPersistentData().putBoolean("kick_x", true);
                                        new DelayedTask(11) {
                                            @Override
                                            public void run() {
                                                entity.getPersistentData().putDouble("kick", 3.0D);
                                            }
                                        };
                                    } else if (entity.getPersistentData().getDouble("kick") == 3.0D) {
                                        entity.getPersistentData().putDouble("kick", 3.5D);
                                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                                            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                            if (livingEntityPatch != null) {
                                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_3, 0.0F);
                                            }
                                        }

                                        entity.getPersistentData().putBoolean("kick_x", true);
                                        new DelayedTask(14) {
                                            @Override
                                            public void run() {
                                                PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
                                                if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                                    entity.getPersistentData().putDouble("kick", 4.0D);
                                                } else {
                                                    entity.getPersistentData().putDouble("kick", 0.0D);
                                                }
                                            }
                                        };
                                    } else if (entity.getPersistentData().getDouble("kick") == 4.0D) {
                                        PlayerPatch<?> playerpatch1 = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                        if (playerpatch1.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
                                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                                LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                                if (livingEntityPatch != null) {
                                                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_C, 0.0F);
                                                }
                                            }

                                            entity.getPersistentData().putBoolean("kick_x", true);
                                            entity.getPersistentData().putDouble("kick", 4.5D);
                                            new DelayedTask(11) {
                                                @Override
                                                public void run() {
                                                    entity.getPersistentData().putDouble("kick", 0.0D);
                                                }
                                            };
                                        } else {
                                            entity.getPersistentData().putDouble("kick", 0.0D);
                                        }
                                    }
                                }
                            } else {
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
