package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.spawnhandler.GregData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SleepPreventProcedure {
    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;

        ServerLevel serverLevel = server.getLevel(Level.OVERWORLD);
        if (serverLevel == null || !serverLevel.dimension().equals(Level.OVERWORLD)) return;

        GregData gregData = GregData.get(serverLevel);
        UUID gregUUID = gregData.getActiveId();

        if (gregUUID != null) {
            Entity entity = serverLevel.getEntity(gregUUID);
            if (entity instanceof HerobrineGregEntity herobrineGregEntity
                    && herobrineGregEntity.isAlive() && herobrineGregEntity.getSummonTimestamp() >= 0) {
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
                event.getEntity().displayClientMessage(
                        Component.literal("Herobrine is preparing to invade near x: " + herobrineGregEntity.getOnPos().getX() +
                                 " y: " + herobrineGregEntity.getOnPos().getY() + " z: " + herobrineGregEntity.getOnPos().getZ() + ". You cannot sleep now!").withStyle(ChatFormatting.RED),
                        false
                );
            }
        }
    }
}
