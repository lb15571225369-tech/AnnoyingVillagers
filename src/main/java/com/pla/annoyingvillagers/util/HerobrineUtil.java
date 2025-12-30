package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public class HerobrineUtil {
    public static boolean isHerobrineFaction(Entity e) {
        return e instanceof HerobrineMob
                || e instanceof HerobrineGregEntity
                || e instanceof LowHerobrineCloneEntity
                || e instanceof LowShadowHerobrineCloneEntity
                || e instanceof InfectedPlayerMobEntity
                || e instanceof InfectedTheMostMoistBurrit0Entity
                || e instanceof InfectedChrisEntity
                || e instanceof NullSwordEntity
                || e instanceof NullAxeEntity
                || e instanceof NullPickaxeEntity
                || e instanceof NullShovelEntity
                || e instanceof NullHoeEntity
                || e instanceof BlockProjectileEntity
                || e instanceof EliteHerobrineKnockedEntity;
    }

    private static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlockAndUpdate(pos, state);
        }
    }

    private static Basis basisFromEntity(Entity e) {
        Vec3 forward = e.getLookAngle().normalize();

        Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0e-6) {
            right = new Vec3(1.0, 0.0, 0.0);
        } else {
            right = right.normalize();
        }
        Vec3 up = right.cross(forward).normalize();
        return new Basis(forward, right, up);
    }

    private record Basis(Vec3 fwd, Vec3 right, Vec3 up) {}

    public static void spawnObsidianEyeLineStaggered(ServerLevel level, Entity entity, BlockState state, int tickGap) {
        if (level == null || entity == null) return;

        Basis b = basisFromEntity(entity);
        Vec3 eye = entity.getEyePosition(1.0F);

        BlockPos[] sequence = new BlockPos[1 + 6];
        sequence[0] = BlockPos.containing(eye.add(b.fwd().scale(1.0)).add(b.up().scale(-1.0)));
        for (int i = 1; i <= 6; i++) {
            sequence[i] = BlockPos.containing(eye.add(b.fwd().scale(i)));
        }

        for (int i = 0; i < sequence.length; i++) {
            final BlockPos pos = sequence[i];
            new DelayedTask(i * Math.max(1, tickGap)) {
                @Override public void run() {
                    placeIfReplaceable(level, pos, state);
                }
            };
        }
    }

    public static void spawnObsidian3x3x3AtBody(ServerLevel level, Entity entity,
                                                BlockState state) {
        if (level == null || entity == null) return;

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        int cy = Mth.clamp(Mth.floor(entity.getY() + entity.getBbHeight() * 0.5D + 1.0D), minY, maxY);
        BlockPos center = BlockPos.containing(entity.getX(), cy, entity.getZ());

        for (int dy = -1; dy <= 1; dy++) {
            int y = cy + dy;
            if (y < minY || y > maxY) continue;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    placeIfReplaceable(level, center.offset(dx, dy, dz), state);
                }
            }
        }
    }
}
