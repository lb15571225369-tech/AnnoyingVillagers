package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ECEffectOnEndProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean("kick_x", false);
            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                if ((dynamicanimation instanceof LongHitAnimation || dynamicanimation == Animations.BIPED_COMMON_NEUTRALIZED || dynamicanimation == Animations.BIPED_KNOCKDOWN) && entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    if (!livingentity.level.isClientSide()) {
                        livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.EC.get(), 2, 0, false, false));
                    }
                }
            }

        }
    }
}
