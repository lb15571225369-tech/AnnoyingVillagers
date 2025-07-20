package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class HitAnimProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) throws CommandSyntaxException {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity());
        }

    }

    public static void execute(Entity entity) throws CommandSyntaxException {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) throws CommandSyntaxException {
        if (entity != null) {
            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                if (dynamicanimation instanceof HitAnimation) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"annoyingvillagers:biped/combat/hit_left\" 0 1",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    }
                } else if (dynamicanimation == AVAnimations.HIT_LEFT) {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "indestructible @s play \"annoyingvillagers:biped/combat/hit_right\" 0 1",
                                entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    }
                } else if (dynamicanimation == AVAnimations.HIT_RIGHT && !entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "indestructible @s play \"annoyingvillagers:biped/combat/hit_right\" 0 1",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                }
            }

        }
    }
}
