package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

    private static final class Pattern2D {
        final int w, h;
        final int[][] cells;
        Pattern2D(int w, int h, int[][] cells) { this.w = w; this.h = h; this.cells = cells; }
        int centerX() { return w / 2; } // integer center (works fine for w=2 as well)
    }

    private static final Pattern2D[] OBSIDIAN_PATTERNS = new Pattern2D[] {
            new Pattern2D(1, 3, new int[][] { {0,0},{0,1},{0,2} }),
            new Pattern2D(2, 3, new int[][] { {0,0},{0,1},{0,2},{1,2} }),
            new Pattern2D(2, 3, new int[][] { {1,0},{1,1},{1,2},{0,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{2,0},{1,1},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,2},{1,2},{2,2},{1,1},{1,0} }),
            new Pattern2D(3, 3, new int[][] { {1,1},{1,2},{1,0},{0,1},{2,1} }),
            new Pattern2D(3, 4, new int[][] { {1,0},{1,1},{1,2},{1,3},{0,2},{2,2} }),
            new Pattern2D(2, 2, new int[][] { {0,0},{1,0},{0,1},{1,1} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{0,1},{1,1},{2,1},{0,2},{1,2} }),
            new Pattern2D(3, 3, new int[][] { {0,0},{1,0},{1,1},{1,2},{2,2} }),
            new Pattern2D(3, 2, new int[][] { {0,0},{1,0},{2,0},{0,1} }),
    };

    private static boolean hasGroundWithin(ServerLevel level, Entity e, int maxDown) {
        Vec3 start = new Vec3(e.getX(), e.getBoundingBox().minY + 1.0E-3D, e.getZ());
        Vec3 end = start.add(0.0D, -maxDown, 0.0D);

        BlockHitResult hit = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                e
        ));
        return hit.getType() != HitResult.Type.MISS;
    }

    public static void spawnObsidianPatternAtBody(ServerLevel level, Entity entity, BlockState state) {
        if (level == null || entity == null) return;
        if (!hasGroundWithin(level, entity, 3)) return;

        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight() - 1;

        BlockPos feet = BlockPos.containing(entity.getX(), entity.getBoundingBox().minY, entity.getZ());

        var rand = level.getRandom();
        Pattern2D pat = OBSIDIAN_PATTERNS[rand.nextInt(OBSIDIAN_PATTERNS.length)];

        Direction face = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        boolean mirror = rand.nextBoolean();
        BlockPos origin = feet.relative(face);

        Direction side = mirror ? face.getCounterClockWise() : face.getClockWise();
        int cx = pat.centerX();

        for (int[] c : pat.cells) {
            int localX = c[0] - cx;
            int localY = c[1];

            int y = origin.getY() + localY;
            if (y < minY || y > maxY) continue;

            BlockPos p = origin.offset(
                    side.getStepX() * localX,
                    localY,
                    side.getStepZ() * localX
            );

            placeIfReplaceable(level, p, state);
        }
    }
}
