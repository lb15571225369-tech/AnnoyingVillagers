package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;

@EventBusSubscriber
public class PlayerLobbyClearProcedure {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent entityjoinworldevent) {
        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity());
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
        if (entity != null) {
            if (entity instanceof Player && levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.PLAYER_SPAWN_CLEAR)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;

                    player.getInventory().clearContent();
                }

                if (!entity.level.isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "clear @s");
                }
            }

        }
    }
}
