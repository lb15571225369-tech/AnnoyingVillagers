package com.pla.annoyingvillagers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.Heightmap;

public class GroundRiseSpawner {
    public static final String NBT_RISING = "rising";
    public static final String NBT_TARGET_Y = "rise_target_y";
    public static final String NBT_SPEED    = "rise_speed";
    public static final String NBT_TICKS    = "rise_ticks";
    public static final String NBT_MAX_TICKS= "rise_max_ticks";

    public static final String NBT_SINKING       = "sinking";
    public static final String NBT_SINK_TARGET_Y = "sink_target_y";
    public static final String NBT_SINK_SPEED    = "sink_speed";
    public static final String NBT_SINK_TICKS    = "sink_ticks";
    public static final String NBT_SINK_MAX_TICKS= "sink_max_ticks";

    public static <T extends LivingEntity> T spawnRising(ServerLevel level, T entity, double x, double z, double speedPerTick) {
        BlockPos top = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos((int) x, 0, (int) z));
        double groundY = top.getY();

        double startY = groundY - 2.0;
        entity.moveTo(x, startY, z, entity.getYRot(), entity.getXRot());

        entity.noPhysics = true;
        entity.setNoGravity(true);
        entity.setInvulnerable(true);
        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }

        var tag = entity.getPersistentData();
        tag.putBoolean(NBT_RISING, true);
        tag.putDouble(NBT_TARGET_Y, groundY + 0.02);
        tag.putDouble(NBT_SPEED, Math.max(0.01, speedPerTick));
        tag.putInt(NBT_TICKS, 0);
        tag.putInt(NBT_MAX_TICKS, 20 * 5);

        level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 0.6f, 0.8f + level.random.nextFloat() * 0.2f);

        return entity;
    }

    public static <T extends LivingEntity> T sinkIntoGround(ServerLevel level, T entity, double speedPerTick) {
        BlockPos top = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entity.blockPosition());
        double groundY = top.getY();
        double targetY = groundY - 1.2;

        entity.noPhysics = true;
        entity.setNoGravity(true);
        entity.setInvulnerable(true);
        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }

        var tag = entity.getPersistentData();
        tag.putBoolean(NBT_SINKING, true);
        tag.putDouble(NBT_SINK_TARGET_Y, targetY);
        tag.putDouble(NBT_SINK_SPEED, Math.max(0.01, speedPerTick));
        tag.putInt(NBT_SINK_TICKS, 0);
        tag.putInt(NBT_SINK_MAX_TICKS, 20 * 5);

        level.playSound(null, entity.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE, 0.5f, 1.2f + level.random.nextFloat() * 0.2f);

        return entity;
    }
}
