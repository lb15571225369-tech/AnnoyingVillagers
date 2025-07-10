//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Comparator;
//
//import com.pla.annoyingvillagers.util.CheckGameMode;
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.GameType;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import com.pla.annoyingvillagers.entity.HerobrineEntity;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
//
//public class HerobrineEffectTickProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        if (entity != null) {
//            if (entity instanceof ServerPlayer) {
//                ServerPlayer serverplayer = (ServerPlayer)entity;
//
//                serverplayer.setGameMode(GameType.SPECTATOR);
//            }
//
//            entity.setShiftKeyDown(false);
//            if (CheckGameMode.isSpectatorGamemode(entity)) {
//                if (!levelaccessor.getEntitiesOfClass(HerobrineEntity.class, AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D), (herobrineentity) -> {
//                    return true;
//                }).isEmpty()) {
//                    if (levelaccessor.getEntitiesOfClass(HerobrineEntity.class,
//                                    AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
//                                    herobrineentity -> true)
//                            .stream()
//                            .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
//                            .findFirst()
//                            .map(Entity::isAlive)
//                            .orElse(false)) {
//
//                        entity.startRiding(
//                                levelaccessor.getEntitiesOfClass(HerobrineEntity.class,
//                                                AABB.ofSize(new Vec3(d0, d1, d2), 2.0D, 2.0D, 2.0D),
//                                                herobrineentity -> true)
//                                        .stream()
//                                        .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(d0, d1, d2)))
//                                        .findFirst()
//                                        .orElse(null)
//                        );
//                    }
//                } else {
//                    if (!entity.level.isClientSide() && entity.getServer() != null) {
//                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "kill @s");
//                    }
//
//                    if (entity instanceof Player) {
//                        Player player = (Player)entity;
//
//                        if (!player.level.isClientSide()) {
//                            player.displayClientMessage(new TextComponent("\u9644\u8eab\u5931\u8d25\uff0c\u4f60\u56e0\u5b9e\u4f53\u9519\u8bef\u800c\u88ab\u6e38\u620f\u5220\u9664\u4e86"), false);
//                        }
//                    }
//
//                    if (entity instanceof LivingEntity) {
//                        LivingEntity livingentity = (LivingEntity)entity;
//
//                        livingentity.removeEffect((MobEffect)AnnoyingVillagersModMobEffects.HEROBRINE_EFFECT.get());
//                    }
//
//                    Minecraft.getInstance().gameRenderer.shutdownEffect();
//                }
//            }
//
//        }
//    }
//}
