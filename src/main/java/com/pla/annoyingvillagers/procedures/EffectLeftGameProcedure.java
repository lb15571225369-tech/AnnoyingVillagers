package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

@EventBusSubscriber
public class EffectLeftGameProcedure {

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerLoggedOutEvent playerloggedoutevent) {
        execute(playerloggedoutevent, playerloggedoutevent.getPlayer().level, playerloggedoutevent.getPlayer());
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                if (livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get())) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;

                        player.giveExperienceLevels(11);
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "leave @s");
                    }

                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
                    }
                }
            }

            if (levelaccessor.players().size() == 1 && !entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "chunky continue");
            }

            if (entity.getPersistentData().getBoolean("b_d")) {
                entity.getPersistentData().putBoolean("b_d", false);
                entity.getPersistentData().putBoolean("b_d_aim", true);
            }

        }
    }
}
