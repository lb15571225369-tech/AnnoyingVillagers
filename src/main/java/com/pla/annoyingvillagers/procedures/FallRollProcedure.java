package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class FallRollProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getSource(), livinghurtevent.getEntity());
        }

    }

    public static void execute(DamageSource damagesource, Entity entity) {
        execute((Event) null, damagesource, entity);
    }

    private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
        if (entity != null) {
            if (damagesource == entity.level().damageSources().fall() && entity.isShiftKeyDown()) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (livingEntityPatch != null) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_FORWARD, 0.0F);
                    }
                }

                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
            }

        }
    }
}
