/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * This file is part of AnnoyingVillagers.
 * Contains code adapted from Dragon Mounts: Legacy.
 *
 * Copyright (C) 2026 pla_is_me
 * Original authors: Nico Bergemann (BarracudaATA), Kay9, and contributors.
 *
 * Licensed under the GNU General Public License v3.0 or later.
 * See the LICENSE file in the project root for details.
 */

package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DragonOrbitLeaderGoal extends Goal {
    private static final double TWO_PI = Math.PI * 2.0;

    private static final double ORBIT_RING_INNER_FACTOR = 0.80;
    private static final double ORBIT_RING_OUTER_FACTOR = 1.35;

    private final HerobrineDragonEntity dragon;
    private final Level level;

    private final double baseSpeed;
    private final float orbitRadiusMin;
    private final float orbitRadiusMax;
    private final float farCatchUpDistance;

    private LivingEntity leader;

    private int updateCooldownTicks;

    private double orbitAngleRadians;
    private int orbitDirectionSign;

    private double orbitRadiusCurrent;
    private double orbitRadiusDesired;

    private double orbitAngularSpeedCurrent;
    private double orbitAngularSpeedDesired;

    private double orbitBaseHeightCurrent;
    private double orbitBaseHeightDesired;

    private double verticalWaveAmplitude;
    private double verticalWaveSpeed;
    private double verticalWavePhase;

    private int paramsTimeToLiveTicks;

    private double yJitterCurrent;
    private double yJitterDesired;

    public DragonOrbitLeaderGoal(
            HerobrineDragonEntity dragon,
            double baseSpeed,
            float orbitRadiusMin,
            float orbitRadiusMax,
            float farCatchUpDistance
    ) {
        this.dragon = dragon;
        this.level = dragon.level();
        this.baseSpeed = baseSpeed;
        this.orbitRadiusMin = orbitRadiusMin;
        this.orbitRadiusMax = orbitRadiusMax;
        this.farCatchUpDistance = farCatchUpDistance;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private LivingEntity resolveLeader() {
        LivingEntity summoner = dragon.getSummoner();
        if (summoner != null) return summoner;
        return dragon.getOwner();
    }

    @Override
    public boolean canUse() {
        LivingEntity resolved = resolveLeader();
        if (resolved == null) return false;
        if (!resolved.isAlive()) return false;
        if (resolved.isSpectator()) return false;

        if (dragon.isLeashed()) return false;
        if (dragon.isPassenger()) return false;
        if (dragon.hasControllingPassenger()) return false;
        if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;
        if (dragon.isFlyThroughActive()) return false;

        leader = resolved;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity resolved = resolveLeader();
        if (resolved == null) return false;
        if (!resolved.isAlive()) return false;
        if (resolved.isSpectator()) return false;

        if (dragon.isLeashed()) return false;
        if (dragon.isPassenger()) return false;
        if (dragon.hasControllingPassenger()) return false;
        if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;

        leader = resolved;
        return true;
    }

    @Override
    public void start() {
        updateCooldownTicks = 0;

        orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
        orbitDirectionSign = dragon.getRandom().nextBoolean() ? 1 : -1;

        orbitRadiusCurrent = orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);

        orbitAngularSpeedCurrent = orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.045, 0.11);

        orbitBaseHeightCurrent = orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(14);

        verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 7.0);
        verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.018, 0.045);
        verticalWavePhase = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);

        paramsTimeToLiveTicks = 80 + dragon.getRandom().nextInt(140);

        dragon.getNavigation().stop();
        yJitterCurrent = yJitterDesired = Mth.nextDouble(dragon.getRandom(), -6.0, 6.0);
    }

    @Override
    public void stop() {
        leader = null;
        dragon.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (leader == null) return;

        dragon.getLookControl().setLookAt(leader, 10.0F, dragon.getMaxHeadXRot());

        orbitAngleRadians = wrapAngle(orbitAngleRadians + orbitDirectionSign * orbitAngularSpeedCurrent);
        verticalWavePhase = wrapAngle(verticalWavePhase + verticalWaveSpeed);

        if (--paramsTimeToLiveTicks <= 0 || dragon.getRandom().nextInt(220) == 0) {
            rerollOrbitParameters();
        }

        orbitRadiusCurrent = Mth.lerp(0.08, orbitRadiusCurrent, orbitRadiusDesired);
        orbitAngularSpeedCurrent = Mth.lerp(0.08, orbitAngularSpeedCurrent, orbitAngularSpeedDesired);
        orbitBaseHeightCurrent = Mth.lerp(0.08, orbitBaseHeightCurrent, orbitBaseHeightDesired);

        if (--updateCooldownTicks > 0) return;
        updateCooldownTicks = adjustedTickDelay(2);

        Vec3 leaderPosition = leader.position();
        Vec3 dragonOffsetFromLeader = dragon.position().subtract(leaderPosition);

        double distanceToLeader = dragonOffsetFromLeader.length();
        double distanceToLeaderSquared = distanceToLeader * distanceToLeader;
        double farCatchUpDistanceSquared = (double) farCatchUpDistance * (double) farCatchUpDistance;

        if (distanceToLeaderSquared >= farCatchUpDistanceSquared) {
            if (!dragon.isFlying() && dragon.canFly()) {
                dragon.liftOff();
            }

            double catchUpY = computeDesiredY(leaderPosition.x, leaderPosition.z, leaderPosition.y) + 6.0;
            catchUpY = Mth.clamp(catchUpY, minY(), maxY());
            Vec3 catchUpTarget = new Vec3(leaderPosition.x, catchUpY, leaderPosition.z);

            setMoveTarget(catchUpTarget, baseSpeed * 1.65);
            return;
        }

        if (!dragon.isFlying() && dragon.canFly()) {
            if (dragon.onGround() || (leader.getY() - dragon.getY()) > 2.0 || distanceToLeader > orbitRadiusMin) {
                dragon.liftOff();
            }
        }

        double orbitRingMinDistance = orbitRadiusMin * ORBIT_RING_INNER_FACTOR;
        double orbitRingMaxDistance = orbitRadiusMax * ORBIT_RING_OUTER_FACTOR;

        Vec3 targetPosition;

        if (distanceToLeader < orbitRingMinDistance || distanceToLeader > orbitRingMaxDistance) {
            Vec3 outwardDirection = distanceToLeader > 1.0E-4 ? dragonOffsetFromLeader.scale(1.0 / distanceToLeader) : new Vec3(1.0, 0.0, 0.0);
            Vec3 ringPoint = leaderPosition.add(outwardDirection.scale(orbitRadiusDesired));
            double ringY = computeDesiredY(ringPoint.x, ringPoint.z, leaderPosition.y);
            targetPosition = new Vec3(ringPoint.x, ringY, ringPoint.z);
        } else {
            double orbitX = leaderPosition.x + Math.cos(orbitAngleRadians) * orbitRadiusCurrent;
            double orbitZ = leaderPosition.z + Math.sin(orbitAngleRadians) * orbitRadiusCurrent;
            double orbitY = computeDesiredY(orbitX, orbitZ, leaderPosition.y);
            targetPosition = new Vec3(orbitX, orbitY, orbitZ);
        }

        if (!canMoveTo(targetPosition)) {
            Vec3 liftedTarget = targetPosition.add(0.0, 14.0, 0.0);
            if (canMoveTo(liftedTarget)) {
                targetPosition = liftedTarget;
            } else {
                orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
                double fallbackY = Mth.clamp(leaderPosition.y + orbitBaseHeightCurrent + 18.0, minY(), maxY());
                targetPosition = new Vec3(leaderPosition.x, fallbackY, leaderPosition.z);
            }
        }

        setMoveTarget(targetPosition, baseSpeed);
        yJitterCurrent = Mth.lerp(0.05, yJitterCurrent, yJitterDesired);
    }

    private void setMoveTarget(Vec3 target, double speed) {
        if (dragon.isFlying()) {
            dragon.getNavigation().stop();
            dragon.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
        } else {
            dragon.getNavigation().moveTo(target.x, target.y, target.z, speed);
        }
    }

    private void rerollOrbitParameters() {
        if (dragon.getRandom().nextFloat() < 0.30f) {
            orbitDirectionSign *= -1;
        }

        orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);
        orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.04, 0.13);

        orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(18);

        verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 8.0);
        verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.016, 0.05);

        if (dragon.getRandom().nextFloat() < 0.35f) {
            orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
        }

        paramsTimeToLiveTicks = 70 + dragon.getRandom().nextInt(160);
        yJitterDesired = Mth.nextDouble(dragon.getRandom(), -10.0, 10.0);
    }

    private double computeDesiredY(double x, double z, double leaderY) {
        BlockPos columnPos = BlockPos.containing(x, 0.0, z);
        int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, columnPos).getY();

        double y = Math.max(leaderY + orbitBaseHeightCurrent, groundY + 10.0);

        y += Math.sin(verticalWavePhase) * verticalWaveAmplitude;
        y += yJitterCurrent;

        return Mth.clamp(y, minY(), maxY());
    }

    private double minY() {
        return level.getMinBuildHeight() + 6.0;
    }

    private double maxY() {
        return level.getMaxBuildHeight() - 6.0;
    }

    private boolean canMoveTo(Vec3 pos) {
        Vec3 delta = pos.subtract(dragon.position());
        AABB moved = dragon.getBoundingBox().move(delta);
        return level.noCollision(dragon, moved) && !level.containsAnyLiquid(moved);
    }

    private static double wrapAngle(double angle) {
        angle %= TWO_PI;
        return angle < 0.0 ? angle + TWO_PI : angle;
    }
}