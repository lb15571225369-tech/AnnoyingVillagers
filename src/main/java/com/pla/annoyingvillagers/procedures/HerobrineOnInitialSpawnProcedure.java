package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class HerobrineOnInitialSpawnProcedure {

    public static void execute(LevelAccessor levelaccessor, final Entity entity, int recallTicks, MobSpawnType mobSpawnType) {
        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);

        if (entity != null) {
            if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                String killedName = entity.getPersistentData().getString("killed_name");
                if (!killedName.isEmpty()) { // Low Herobrine Clone
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(killedName + " has been possessed by §5Herobrine§r."), false);
                } else {
                    if ((entity instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity && !lowHerobrineCloneEntity.isSummoned()) || (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity && !lowShadowHerobrineCloneEntity.isSummoned())) {
                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r has possessed a new player."), false);
                    } else {
                        if (recallTicks == 0) {
                            recallTicks = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
                            if (entity instanceof HerobrineMob herobrineMob) {
                                herobrineMob.setRecallTicks(recallTicks);
                            }
                        }
                        if (mobSpawnType.equals(MobSpawnType.NATURAL) || mobSpawnType.equals(MobSpawnType.CHUNK_GENERATION)) { // For natural spawn
                            if (Math.random() <= 0.5D) { // Natural possessed
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Herobrine§r has possessed a new player."), false);
                            } else { // Portal animation
                                if (entity instanceof HerobrineMob herobrineMob) {
                                    herobrineMob.setRenderPortal(true);
                                    HerobrinePortalProcedure.spawnHerobrine(herobrineMob);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " has arrived from the §4Herobrine Vessel Realm§r"), false);
                                } else if (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                                    lowShadowHerobrineCloneEntity.setRenderPortal(true);
                                    HerobrinePortalProcedure.spawnHerobrine(lowShadowHerobrineCloneEntity);
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§5Netherite Herobrine§r has arrived from the §4Herobrine Vessel Realm§r"), false);
                                }
                            }
                        } else {
                            if (entity instanceof HerobrineMob herobrineMob) {
                                if (mobSpawnType.equals(MobSpawnType.SPAWN_EGG) || mobSpawnType.equals(MobSpawnType.COMMAND)) {
                                    herobrineMob.setRenderPortal(true);
                                }
                                HerobrinePortalProcedure.spawnHerobrine(herobrineMob);
                                levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal(herobrineMob.getChatName() + " has arrived from the §4Herobrine Vessel Realm§r"), false);
                            } else if (entity instanceof LivingEntity livingEntity) {
                                // This logic is for #5 and #6 ground spawn
                                HerobrinePortalProcedure.spawnHerobrine(livingEntity);
                            }
                        }
                    }
                }
            }

            if (entity instanceof HerobrineChrisEntity) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<§5Herobrine Chris§r> Are you talking about ... me ?"), false);
                }

                if (levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "areyoutalkingaboutme")), SoundSource.BLOCKS, 5.0F, 1.0F);
                    } else {
                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "areyoutalkingaboutme")), SoundSource.BLOCKS, 5.0F, 1.0F, false);
                    }
                }
            }

            if (entity instanceof ArmoredHerobrineEntity) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<§5Armored Herobrine§r> Anyone who stands in my way must die!"), false);
                }

                if (levelaccessor instanceof Level) {
                    Level level = (Level) levelaccessor;

                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "himsaydzlsddds")), SoundSource.BLOCKS, 5.0F, 1.0F);
                    } else {
                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "himsaydzlsddds")), SoundSource.BLOCKS, 5.0F, 1.0F, false);
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

            entity.getPersistentData().putBoolean("a_player", true);
        }
    }
}
