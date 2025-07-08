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

public class NpcKickEffectDangXiaoGuoJieShuShiProcedure {

    public static void execute(Entity entity) {
        if (entity != null) {
            if (entity.isAlive()) {
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    flag = livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                    if (livingentitypatch != null) {
                        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                        if ((dynamicanimation instanceof LongHitAnimation || dynamicanimation == Animations.BIPED_COMMON_NEUTRALIZED || dynamicanimation == Animations.BIPED_KNOCKDOWN) && !entity.level.isClientSide() && entity.getServer() != null) {
                            entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/skill/roll_backward\" 0 1");
                        }
                    }
                } else if (entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    if (!livingentity1.level.isClientSide()) {
                        livingentity1.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 3, 0, false, false));
                    }
                }
            }

        }
    }
}
