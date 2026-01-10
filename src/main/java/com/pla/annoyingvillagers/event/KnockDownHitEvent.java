package com.pla.annoyingvillagers.event;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@EventBusSubscriber
public class KnockDownHitEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity(), livingattackevent.getSource().getDirectEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (entity.isAlive()) {
                LivingEntityPatch<?> livingEntityPatch;
                AssetAccessor<? extends DynamicAnimation> dynamicanimation;
                livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingEntityPatch != null) {
                    dynamicanimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
                    if (dynamicanimation instanceof KnockdownAnimation || dynamicanimation == AVAnimations.KNOCKDOWN_FORWARD || dynamicanimation == AVAnimations.KNOCKDOWN_RIGHT || dynamicanimation == AVAnimations.KNOCKDOWN_LEFT) {
                        if (Math.random() <= 0.4D) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_RIGHT, 0.0F);
                            }
                        } else if (Math.random() <= 0.4D) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_LEFT, 0.0F);
                            }
                        } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_FORWARD, 0.0F);
                        }
                    }
                }
            }

        }
    }
}
