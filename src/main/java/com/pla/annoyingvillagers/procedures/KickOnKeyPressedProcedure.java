package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import com.pla.annoyingvillagers.animations.AttackBreakAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

import java.util.Objects;

public class KickOnKeyPressedProcedure {
    private static final String NBT_SPECIAL_CD = "SpecialAttackCooldown";

    public static void execute(LevelAccessor levelaccessor, final Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity.getPersistentData().getInt(NBT_SPECIAL_CD) == 0) {
                LivingEntityPatch<?> livingentitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingentitypatch.getAnimator().getPlayerFor(null)).getAnimation();
                    entity.getPersistentData().putInt(NBT_SPECIAL_CD, 1);

                    if (!(dynamicAnimation instanceof AttackBreakAnimation)) {
                        if (!(dynamicAnimation instanceof LongHitAnimation)) {
                            if (entity.isShiftKeyDown()) {
                                if (entity.isSprinting()) {
                                    PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);

                                    if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST && !entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_COMBO, 0.0F);
                                        }
                                    }
                                } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                    if (livingEntityPatch != null) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_H, 0.0F);
                                    }
                                }
                            } else {
                                if (entity.isSprinting()) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch =  EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_RUSH, 0.0F);
                                        }
                                    }
                                } else if (!entity.getPersistentData().contains("KickCombo")) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_1, 0.0F);
                                        }
                                        entity.getPersistentData().putDouble("KickCombo", 1);
                                    }
                                } else if (entity.getPersistentData().getDouble("KickCombo") == 1) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_2, 0.0F);
                                        }
                                        entity.getPersistentData().putDouble("KickCombo", 2);
                                    }
                                } else if (entity.getPersistentData().getDouble("KickCombo") == 2) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_3, 0.0F);
                                        }
                                        entity.getPersistentData().putDouble("KickCombo", 3);
                                    }
                                } else if (entity.getPersistentData().getDouble("KickCombo") == 3) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_4, 0.0F);
                                        }
                                        entity.getPersistentData().putDouble("KickCombo", 4);
                                    }
                                } else if (entity.getPersistentData().getDouble("KickCombo") == 4) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_C, 0.0F);
                                        }
                                        entity.getPersistentData().remove("KickCombo");
                                    }
                                }
                            }
                        } else {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
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
