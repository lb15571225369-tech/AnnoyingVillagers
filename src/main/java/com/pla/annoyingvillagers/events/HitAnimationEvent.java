package com.pla.annoyingvillagers.events;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class HitAnimationEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity());
        }

    }

    public static void execute(Entity entity) {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                if (dynamicanimation instanceof HitAnimation) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_LEFT, 0.0F);
                        }
                    }
                } else if (dynamicanimation == AVAnimations.HIT_LEFT) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_LEFT, 0.0F);
                        }
                    }
                } else if (dynamicanimation == AVAnimations.HIT_RIGHT && !entity.level().isClientSide() && entity.getServer() != null) {
                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (livingEntityPatch != null) {
                        livingEntityPatch.playAnimationSynchronized(AVAnimations.HIT_RIGHT, 0.0F);
                    }
                }
            }

        }
    }
}
