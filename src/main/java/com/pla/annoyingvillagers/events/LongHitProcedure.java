package com.pla.annoyingvillagers.events;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class LongHitProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity(), (double) livinghurtevent.getAmount());
        }

    }

    public static void execute(Entity entity, double d0) {
        execute((Event) null, entity, d0);
    }

    private static void execute(@Nullable Event event, Entity entity, double d0) {
        if (entity != null) {
            if (entity.isAlive()) {
                if (d0 >= 35.0D) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(AVAnimations.LONGEST_HIT, 0.0F);
                        }
                    }
                } else if (d0 >= 30.0D && !entity.level().isClientSide() && entity.getServer() != null) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null) {
                            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN, 0.0F);
                        }
                    }
                }
            }

        }
    }
}
