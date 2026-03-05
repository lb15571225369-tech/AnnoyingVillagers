package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.compat.EpicFightSwordSoaring;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashSet;
import java.util.Set;

public class EpicfightUtil {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                AVAnimations.ENDER_AEGIS_BULL_CHARGE.get().getRegistryName().toString(),
                AVAnimations.YELLOW_TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AVAnimations.ENDER_GLAIVE_NAPOLEON_SHOOT_3.get().getRegistryName().toString(),
                AVAnimations.ENDER_GLAIVE_AGONY_AUTO_1.get().getRegistryName().toString(),
                AVAnimations.AEGIS_SHIELD_SHOOT.get().getRegistryName().toString(),
                AVAnimations.CLONE_NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE_X.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_2.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AnimsRuine.RUINE_PLUNDER.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_LAPSE.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENSION.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENDED_BLACKHOLE.get().getRegistryName().toString(),
                AnimsSatsujin.SATSUJIN_GESSHOKU.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_AUTO_3.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_SPRENGKOPF.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_WIDERSTAND.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECHO.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECLIPSE.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_FULLMOON.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_OBSCURIDAD.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_CREMATORIO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_INFIERNO.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_AUSTERLITZ_SHOOT.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AnimsOrbit.ORBIT_LIGHT_BEAM.get().getRegistryName().toString()
        ));

        if (ModList.get().isLoaded("efn")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightNightFall.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (ModList.get().isLoaded("sword_soaring")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightSwordSoaring.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    public static boolean isAnimationDangerous(AssetAccessor<? extends StaticAnimation> targetDynamicAnimation) {
        if (targetDynamicAnimation != null && targetDynamicAnimation.get().getRegistryName() != null) {
            String animation = targetDynamicAnimation.get().getRegistryName().toString();
            return DANGEROUS_ANIMATIONS.contains(animation);
        }
        return false;
    }

    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static boolean isLongHitAnimationNotExecutedAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return !(dynamicAnimation.get() instanceof ExecutionHitAnimation)
                && (dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFall.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch));
    }

    public static boolean isLongHitAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof ExecutionHitAnimation
                || dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFall.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch);
    }

    public static void dealStaminaDamageByPercentage(DamageSource damageSource, LivingEntityPatch<?> livingEntityPatch, double percentage, boolean playStunAnimation) {
        float decrease = 0.0F;
        if (livingEntityPatch instanceof CEHumanoidPatch) {
            float currentStamina = CEPatchUtils.getStamina(livingEntityPatch);
            float maxStamina = CEPatchUtils.getMaxStamina(livingEntityPatch);
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        }
        dealStaminaDamage(damageSource, decrease, livingEntityPatch, playStunAnimation);
    }

    public static void dealStaminaDamage(DamageSource damageSource, float amount, LivingEntityPatch<?> livingEntityPatch, boolean playStunAnimation) {
        if (livingEntityPatch instanceof CEHumanoidPatch ceHumanoidPatch) {
            if (!ceHumanoidPatch.dealStaminaDamage(damageSource, amount) && playStunAnimation) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.GUARD_BREAK_ATTACK, 0.0F);
            }
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float stamina = playerPatch.getStamina();
            playerPatch.setStamina(stamina - amount);
            if (amount >= stamina) {
                EpicFightDamageSource efSource = damageSource instanceof EpicFightDamageSource ? (EpicFightDamageSource)damageSource : null;
                if (efSource != null) {
                    efSource.setStunType(StunType.NONE);
                    Vec3 sourcePosition = efSource.getInitialPosition();
                    if (sourcePosition != null) {
                        playerPatch.getOriginal().lookAt(EntityAnchorArgument.Anchor.FEET, sourcePosition);
                    }
                }

                if (playerPatch.applyStun(StunType.NEUTRALIZE, 0.0F)) {
                    (playerPatch.getOriginal()).forceAddEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 100), playerPatch.getOriginal());
                    Vec3 eyePosition = (playerPatch.getOriginal()).getEyePosition();
                    Vec3 viewVec = (playerPatch.getOriginal()).getLookAngle().scale(2.0F);
                    Vec3 pos = new Vec3(eyePosition.x + viewVec.x, eyePosition.y + viewVec.y, eyePosition.z + viewVec.z);
                    (playerPatch.getOriginal()).level().addParticle(EpicFightParticles.NEUTRALIZE.get(), pos.x, pos.y, pos.z, (double)0.0F, (double)0.0F, (double)0.0F);
                    playerPatch.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 1.0F, 1.0F);
                }
            }
        }
    }
}
