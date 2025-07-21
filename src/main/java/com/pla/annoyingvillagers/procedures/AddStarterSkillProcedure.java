package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

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

            try {
                player.getServer().getCommands().getDispatcher().execute(
                        "epicfight skill add @s dodge epicfight:roll",
                        source
                );
                player.getServer().getCommands().getDispatcher().execute(
                        "epicfight skill add @s passive1 annoyingvillagers:clash",
                        source
                );
                player.getServer().getCommands().getDispatcher().execute(
                        "epicfight skill add @s guard epicfight:guard",
                        source
                );
            } catch (CommandSyntaxException e) {
            }
        }
    }

}
