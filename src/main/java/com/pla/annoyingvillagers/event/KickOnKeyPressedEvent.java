package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.ObsidianWeaponItem;
import com.pla.annoyingvillagers.item.ShadowObsidianPillarItem;
import com.pla.annoyingvillagers.item.ShadowObsidianWeaponItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Objects;

public class KickOnKeyPressedEvent {
    private static final String NBT_KICK_CD = "KickAttackCooldown";
    private static final String NBT_STUN_ESCAPE_CD = "StunEscapeCooldown";
    private static final String NBT_KICK_COMBO = "KickCombo";

    public static void execute(LevelAccessor levelAccessor, final Entity entity, int strafe) {
        if (entity instanceof LivingEntity) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingEntityPatch != null) {
                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();

                if (EpicfightUtil.isLongHitAnimation(dynamicAnimation)) {
                    if (entity.getPersistentData().getInt(NBT_STUN_ESCAPE_CD) != 0 || (entity instanceof Mob mob && mob.hasEffect(AnnoyingVillagersModMobEffects.HEROBRINE.get()))) return;
                    entity.getPersistentData().putInt(NBT_STUN_ESCAPE_CD, 1);

                    if (strafe < 0) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                    } else if (strafe > 0) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                    }
                    return;
                }

                if (entity.getPersistentData().getInt(NBT_KICK_CD) != 0) return;
                boolean sneaking = entity.isShiftKeyDown();
                boolean sprinting = entity.isSprinting();

                // Kick by weapon
                if (((LivingEntity) entity).getMainHandItem().getItem() instanceof ObsidianWeaponItem
                        || ((LivingEntity) entity).getMainHandItem().getItem() instanceof ShadowObsidianWeaponItem
                        || ((LivingEntity) entity).getMainHandItem().getItem() instanceof ShadowObsidianPillarItem) {
                    entity.getPersistentData().putInt(NBT_KICK_CD, 1);
                    double combo = entity.getPersistentData().contains(NBT_KICK_COMBO)
                            ? entity.getPersistentData().getDouble(NBT_KICK_COMBO)
                            : 0.0;

                    if (combo == 0.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.OBSIDIAN_STRONG_KICK, 0.0F);
                        entity.getPersistentData().putDouble(NBT_KICK_COMBO, 1);
                    } else if (combo == 1.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.OBSIDIAN_KICK_AUTO_3, 0.0F);
                        entity.getPersistentData().putDouble(NBT_KICK_COMBO, 2);
                    } else if (combo == 2.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.OBSIDIAN_KICK_AUTO_1, 0.0F);
                        entity.getPersistentData().putDouble(NBT_KICK_COMBO, 3);
                    } else if (combo == 3.0) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.OBSIDIAN_KICK_AUTO_1, 0.0F);
                        entity.getPersistentData().putDouble(NBT_KICK_COMBO, 4);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.OBSIDIAN_WHIRLWIND_KICK, 0.0F);
                        entity.getPersistentData().remove(NBT_KICK_COMBO);
                    }
                    return;
                }

                // Default kick
                entity.getPersistentData().putInt(NBT_KICK_CD, 3);
                if (sneaking) {
                    if (sprinting && isFistPlayer(entity)) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_COMBO, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_H, 0.0F);
                    }
                    return;
                }

                if (sprinting) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_RUSH, 0.0F);
                    return;
                }

                double combo = entity.getPersistentData().contains(NBT_KICK_COMBO)
                        ? entity.getPersistentData().getDouble(NBT_KICK_COMBO)
                        : 0.0;

                if (combo == 0.0) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_1, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 1);
                } else if (combo == 1.0) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_2, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 2);
                } else if (combo == 2.0) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_3, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 3);
                } else if (combo == 3.0) {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_4, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 4);
                } else {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KICK_C, 0.0F);
                    entity.getPersistentData().remove(NBT_KICK_COMBO);
                }
            }
        }
    }

    private static boolean isFistPlayer(Entity entity) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        if (playerPatch == null) return false;

        return playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST;
    }
}
