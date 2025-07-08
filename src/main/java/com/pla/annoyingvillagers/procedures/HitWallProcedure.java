package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class HitWallProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity().level, livinghurtevent.getSource(), livinghurtevent.getEntity(), (double) livinghurtevent.getAmount());
        }

    }

    public static void execute(LevelAccessor levelaccessor, DamageSource damagesource, Entity entity, double d0) {
        execute((Event) null, levelaccessor, damagesource, entity, d0);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, DamageSource damagesource, Entity entity, double d0) {
        if (entity != null) {
            if (damagesource == DamageSource.FLY_INTO_WALL) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10");
                }

                entity.hurt(DamageSource.GENERIC, 10.0F);
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "impactful @s shake 20 5 6");
                }
            } else if (damagesource == DamageSource.FALL) {
                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "particle minecraft:campfire_signal_smoke ~ ~ ~ 0 0 0 0.02 5");
                }

                if (d0 >= 10.0D) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/knockdown\" 0 10");
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "impactful @s shake 20 5 6");
                    }
                }
            }

        }
    }
}
