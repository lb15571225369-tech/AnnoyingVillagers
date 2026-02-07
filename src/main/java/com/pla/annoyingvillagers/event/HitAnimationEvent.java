package com.pla.annoyingvillagers.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

@EventBusSubscriber
public class HitAnimationEvent {
    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livingHurtEvent) {
//        if (livingHurtEvent != null && livingHurtEvent.getEntity() != null) {
//            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingHurtEvent.getEntity(), LivingEntityPatch.class);
//            if (livingEntityPatch != null && livingHurtEvent.getEntity().level() instanceof ServerLevel) {
//                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
//                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = animationPlayer.getAnimation();
//                if (dynamicAnimation == Animations.BIPED_HIT_SHORT) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_C, 0.0F);
//                } else if (dynamicAnimation == AVAnimations.HIT_C) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_LEFT, 0.0F);
//                } else if (dynamicAnimation == AVAnimations.HIT_LEFT) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_RIGHT, 0.0F);
//                }
//
//                if (dynamicAnimation == Animations.BIPED_HIT_LONG) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_LEFT, 0.0F);
//                } else if (dynamicAnimation == AVAnimations.KNOCKDOWN_LEFT) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_RIGHT, 0.0F);
//                } else if (dynamicAnimation == AVAnimations.KNOCKDOWN_RIGHT) {
//                    livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_FORWARD, 0.0F);
//                } else if (dynamicAnimation == AVAnimations.KNOCKDOWN_FORWARD) {
//                    livingEntityPatch.applyStun(StunType.KNOCKDOWN, 40.0F);
//                }
//            }
//        }
    }
}
