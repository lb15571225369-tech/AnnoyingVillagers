package com.pla.annoyingvillagers.event;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class HitIntoWallEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity().level(), livinghurtevent.getSource(), livinghurtevent.getEntity(), (double) livinghurtevent.getAmount());
        }

    }

    public static void execute(LevelAccessor levelaccessor, DamageSource damagesource, Entity entity, double d0) {
        execute((Event) null, levelaccessor, damagesource, entity, d0);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, DamageSource damagesource, Entity entity, double d0) {
        if (entity != null) {
            if (damagesource.is(DamageTypes.FLY_INTO_WALL)) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (livingEntityPatch != null) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN, 0.0F);
                    }
                }

                entity.hurt(entity.level().damageSources().generic(), 10.0F);
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "impactful @s shake 20 5 6",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }
            } else if (damagesource.is(DamageTypes.FALL)) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "particle minecraft:campfire_signal_smoke ~ ~ ~ 0 0 0 0.02 5",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                }

                if (d0 >= 10.0D) {
                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                        try {
                            entity.getServer().getCommands().getDispatcher().execute(
                                    "impactful @s shake 20 5 6",
                                    entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
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
