//package com.pla.annoyingvillagers.util;
//
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
//import net.minecraft.world.phys.Vec3;
//import net.p1nero.ss.SwordSoaring;
//import net.p1nero.ss.entity.SwordConvergenceEntity;
//import yesman.epicfight.gameasset.EpicFightSounds;
//
//public final class SwordConvergenceBurst {
//    private static final int SWORDS_TO_SHOOT = 12;
//    private static final double RING_RADIUS   = 3.0;
//    private static final double Y_OFFSET_BASE = 0.6;
//    private static final double TARGET_DIST   = 12.0;
//
//    private SwordConvergenceBurst() {}
//    public static void shootOnly(ServerPlayer player) {
//        if (player == null || !SwordSoaring.isValidSword(player.getMainHandItem())) {
//            return;
//        }
//
//        Vec3 eye   = player.getEyePosition(1.0F);
//        Vec3 look  = player.getViewVector(1.0F).normalize();
//        Vec3 target = eye.add(look.scale(TARGET_DIST));
//
//        for (int i = 0; i < SWORDS_TO_SHOOT; i++) {
//            double angle = (2.0 * Math.PI * i) / SWORDS_TO_SHOOT;
//
//            double xOff = Math.cos(angle) * RING_RADIUS;
//            double zOff = Math.sin(angle) * RING_RADIUS;
//            double yOff = Y_OFFSET_BASE + (player.level().random.nextDouble() * 0.4);
//
//            double spawnX = player.getX() + xOff;
//            double spawnY = player.getY() + yOff;
//            double spawnZ = player.getZ() + zOff;
//
//            spawnSwordToward(player, spawnX, spawnY, spawnZ, target.x, target.y, target.z);
//        }
//    }
//
//    private static void spawnSwordToward(ServerPlayer player,
//                                         double x, double y, double z,
//                                         double targetX, double targetY, double targetZ) {
//        SwordConvergenceEntity sword = new SwordConvergenceEntity(
//                player.getMainHandItem(),
//                player.level(),
//                new Vec3(targetX, targetY, targetZ)
//        );
//
//        sword.setOwner(player);
//        sword.setNoGravity(true);
//        sword.setBaseDamage((double)0.0F);
//        sword.setNoPhysics(false);
//        sword.pickup = Pickup.DISALLOWED;
//        sword.setKnockback(0);
//        sword.setPierceLevel((byte)5);
//        sword.setPos(x, y, z);
//
//        Vec3 vel = new Vec3(targetX, targetY, targetZ)
//                .subtract(sword.getEyePosition(1.0F))
//                .normalize();
//        sword.setDeltaMovement(vel);
//        player.level().playSound(
//                (Player) null,
//                sword.blockPosition(),
//                EpicFightSounds.ENTITY_MOVE.get(),
//                SoundSource.BLOCKS,
//                0.1F, 0.5F
//        );
//        player.level().addFreshEntity(sword);
//    }
//}
