package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class HerobrineOnInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity, int recallTicks) {
        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);

        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                String killedName = entity.getPersistentData().getString("killed_name");
                if (!killedName.isEmpty()) { // Herobrine #5, #6
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " has been possessed by §5Herobrine§r."), false);
                } else {
                    if (recallTicks == 0) {
                        recallTicks = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
                        if (entity instanceof Herobrine1Entity herobrine1Entity) {
                            herobrine1Entity.setRecallTicks(recallTicks);
                        }
                    }
                    if (Math.random() <= 0.5D) { // Natural possessed
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r has possessed a new player."), false);
                    } else { // Portal animation
                        if (entity instanceof Herobrine1Entity herobrine1Entity) {
                            herobrine1Entity.setRenderPortal(true);
                            HerobrinePortalProcedure.spawnHerobrine(herobrine1Entity, herobrine1Entity.getRecallTicks());
                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine Clone§r has arrived from the §4Herobrine Vessel Realm§r"), false);
                        }
//                        if (entity instanceof Herobrine2Entity herobrine2Entity) {
//                            herobrine2Entity.setRenderPortal(true);
//                            HerobrinePortalProcedure.spawnHerobrine(herobrine2Entity, herobrine2Entity.getRecallTicks());
//                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine #2§r has arrived from the §4Herobrine Vessel Realm§r"), false);
//                        }
//                        if (entity instanceof Herobrine3Entity herobrine3Entity) {
//                            herobrine3Entity.setRenderPortal(true);
//                            HerobrinePortalProcedure.spawnHerobrine(herobrine3Entity, herobrine3Entity.getRecallTicks());
//                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine #3§r has arrived from the §4Herobrine Vessel Realm§r"), false);
//                        }
//                        if (entity instanceof Herobrine7Entity herobrine7Entity) {
//                            herobrine7Entity.setRenderPortal(true);
//                            HerobrinePortalProcedure.spawnHerobrine(herobrine7Entity, herobrine7Entity.getRecallTicks());
//                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine #7§r has arrived from the §4Herobrine Vessel Realm§r"), false);
//                        }
//                        if (entity instanceof ArmoredHerobrineEntity armoredHerobrineEntity) {
//                            armoredHerobrineEntity.setRenderPortal(true);
//                            HerobrinePortalProcedure.spawnHerobrine(armoredHerobrineEntity, armoredHerobrineEntity.getRecallTicks());
//                            levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Armored Herobrine§r has arrived from the §4Herobrine Vessel Realm§r"), false);
//                        }
                    }
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team add herobrine",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team modify herobrine friendlyFire false",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {

                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "team join herobrine @s",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                    
                }
            }
        }
    }
}
