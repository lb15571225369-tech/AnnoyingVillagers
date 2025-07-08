package com.pla.annoyingvillagers.procedures;

import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.pla.annoyingvillagers.entity.HerobrineEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;

public class HerobrineEffectTickProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        if (entity != null) {
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)entity;

                serverplayer.setGameMode(GameType.SPECTATOR);
            }

            entity.setShiftKeyDown(false);
            if (((<undefinedtype>)(new Object() {
                public boolean checkGamemode(Entity entity1) {
                    if (entity1 instanceof ServerPlayer) {
                        ServerPlayer serverplayer1 = (ServerPlayer)entity1;

                        return serverplayer1.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                    } else if (entity1.level.isClientSide() && entity1 instanceof Player) {
                        Player player = (Player)entity1;

                        return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                    } else {
                        return false;
                    }
                }
            })).checkGamemode(entity)) {
                if (!levelaccessor.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D), (herobrineentity) -> {
                    return true;
                }).isEmpty()) {
                    if (((Entity)levelaccessor.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D), (herobrineentity) -> {
                        return true;
                    }).stream().sorted(((<undefinedtype>)(new Object() {
                        Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                            return Comparator.comparingDouble((entity1) -> {
                                return entity1.distanceToSqr(d3, d4, d5);
                            });
                        }
                    })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null)).isAlive()) {
                        entity.startRiding((Entity)levelaccessor.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D), (herobrineentity) -> {
                            return true;
                        }).stream().sorted(((<undefinedtype>)(new Object() {
                            Comparator<Entity> compareDistOf(double d3, double d4, double d5) {
                                return Comparator.comparingDouble((entity1) -> {
                                    return entity1.distanceToSqr(d3, d4, d5);
                                });
                            }
                        })).compareDistOf(d0, d1, d2)).findFirst().orElse((Object)null));
                    }
                } else {
                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
                    }

                    if (entity instanceof Player) {
                        Player player = (Player)entity;

                        if (!player.level.isClientSide()) {
                            player.displayClientMessage(new TextComponent("\u9644\u8eab\u5931\u8d25\uff0c\u4f60\u56e0\u5b9e\u4f53\u9519\u8bef\u800c\u88ab\u6e38\u620f\u5220\u9664\u4e86"), false);
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity)entity;

                        livingentity.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
                    }

                    Minecraft.getInstance().gameRenderer.shutdownEffect();
                }
            }

        }
    }
}
