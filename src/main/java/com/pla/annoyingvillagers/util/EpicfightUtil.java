package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.StaminaStatus;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import net.shelmarow.combat_evolution.gameassets.ExecutionSkillAnimations;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

public class EpicfightUtil {
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

    public static boolean isLongHitAnimation(AssetAccessor<? extends DynamicAnimation> dynamicAnimation) {
        return dynamicAnimation.get() instanceof LongHitAnimation ||
                (dynamicAnimation.get() instanceof LinkAnimation linkAnimation
                        && linkAnimation.getRealAnimation().get() instanceof LongHitAnimation &&
                        (linkAnimation.getRealAnimation() == Animations.BIPED_COMMON_NEUTRALIZED
                                || linkAnimation.getRealAnimation() == Animations.GREATSWORD_GUARD_BREAK
                                || linkAnimation.getRealAnimation() == Animations.BIPED_KNOCKDOWN
                                || linkAnimation.getRealAnimation() == ExecutionSkillAnimations.EXECUTED_FULL));
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
            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] stamina for mob {} is {}", ceHumanoidPatch.getOriginal().getDisplayName().getString() ,CEPatchUtils.getStamina(ceHumanoidPatch));
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
