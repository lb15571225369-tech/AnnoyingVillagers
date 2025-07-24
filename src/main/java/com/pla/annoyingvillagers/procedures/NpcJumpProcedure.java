package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NpcJumpProcedure {

    @SubscribeEvent
    public static void onEntityJump(LivingJumpEvent livingjumpevent) {
        execute(livingjumpevent, livingjumpevent.getEntity());
    }

    public static void execute(Entity entity) {
        execute((Event) null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null) {
            if (!(entity instanceof Player) && !entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "indestructible @s play \"epicfight:biped/living/jump\" 0 1",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

        }
    }
}
