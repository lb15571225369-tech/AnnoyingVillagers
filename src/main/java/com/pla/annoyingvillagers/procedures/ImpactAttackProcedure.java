package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;

@EventBusSubscriber
public class ImpactAttackProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity(), livingattackevent.getSource().getDirectEntity(), livingattackevent.getSource().getEntity(), (double) livingattackevent.getAmount());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1, Entity entity2, double d0) {
        execute((Event) null, levelaccessor, entity, entity1, entity2, d0);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity, Entity entity1, Entity entity2, double d0) {
        if (entity != null && entity1 != null && entity2 != null) {
            LivingEntity livingentity;

            if (entity2 instanceof TamableAnimal) {
                TamableAnimal tamableanimal = (TamableAnimal) entity2;

                livingentity = tamableanimal.getOwner();
            } else {
                livingentity = null;
            }

            if (livingentity == entity && event != null && event.isCancelable()) {
                event.setCanceled(true);
            }

            if (entity1 instanceof ThrownTrident && entity2 instanceof BlueDemonEntity) {
                entity.hurt(entity.level().damageSources().magic(), (float) (d0 * 7.0D));
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "effect give @s annoyingvillagers:electify 5 0 true",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }
            }

        }
    }
}
