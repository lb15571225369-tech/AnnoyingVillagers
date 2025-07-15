package com.pla.annoyingvillagers.procedures;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AddStarterSkillProcedure {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        CompoundTag data = player.getPersistentData();
        CompoundTag persisted;
        if (!data.contains("PlayerPersisted")) {
            persisted = new CompoundTag();
            data.put("PlayerPersisted", persisted);
        } else {
            persisted = data.getCompound("PlayerPersisted");
        }
        if (!persisted.getBoolean("given_epicfight_skill")) {
            persisted.putBoolean("given_epicfight_skill", true);

            CommandSourceStack source = player.createCommandSourceStack()
                    .withSuppressedOutput()
                    .withPermission(4);

            player.getServer().getCommands().performCommand(
                    source,
                    "epicfight skill add @s dodge epicfight:roll"
            );
            player.getServer().getCommands().performCommand(
                    source,
                    "epicfight skill add @s passive1 annoyingvillagers:clash"
            );
            player.getServer().getCommands().performCommand(
                    source,
                    "epicfight skill add @s guard epicfight:guard"
            );
        }
    }

}
