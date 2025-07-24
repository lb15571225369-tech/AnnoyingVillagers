package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

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
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    flag = livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    if (d0 >= 35.0D) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "indestructible @s play \"annoyingvillagers:biped/combat/longest_hit\" 0 10",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                                
                            }
                        }
                    } else if (d0 >= 30.0D && !entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                            
                        }
                    }
                }
            }

        }
    }
}
