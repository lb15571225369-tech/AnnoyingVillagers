package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

@EventBusSubscriber
public class GreatSwordExecuteEventProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity(), livinghurtevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get()) && entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;

                    if (livingentity1.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.GREAT_SWORD_EXECUTE_BLOCK.get()) && entity1 instanceof LivingEntity) {
                        LivingEntity livingentity2 = (LivingEntity) entity1;

                        if (livingentity2.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.GREAT_SWORD_EXECUTE_BLOCK.get()) && event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }
                    }
                }
            }

        }
    }
}
