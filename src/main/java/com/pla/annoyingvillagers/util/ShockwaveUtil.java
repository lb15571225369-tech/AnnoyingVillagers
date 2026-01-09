package com.pla.annoyingvillagers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPFracture;
import yesman.epicfight.api.utils.LevelUtil;

import javax.annotation.Nullable;

public class ShockwaveUtil {
    private static void schedule(MinecraftServer server, int delayTicks, Runnable action) {
        if (delayTicks <= 0) {
            action.run();
            return;
        }
        server.tell(new TickTask(server.getTickCount() + delayTicks, action));
    }

    /**
     * Expanding fracture shockwave: 4 -> 5 -> 6 -> 7 (each step after stepTicks)
     * - First pulses: visual only (send SPFracture), no damage
     * - Last pulse: call circleSlamFracture to apply damage (optional)
     */
    public static void expandingShockwave(ServerLevel level, @Nullable LivingEntity caster, Vec3 center,
                                          int stepTicks, double... radii) {
        MinecraftServer server = level.getServer();
        if (server == null) return;

        // Important: fracture origin checks block at (floor x,y,z)
        // Put center at ground Y (usually caster.getY()).
        BlockPos bp = BlockPos.containing(center);

        for (int i = 0; i < radii.length; i++) {
            final int idx = i;
            final double r = radii[i];
            final boolean last = (i == radii.length - 1);

            schedule(server, idx * stepTicks, () -> {
                if (!level.hasChunkAt(bp)) return;

                boolean noSound = (idx != 0);    // only first pulse plays sound
                boolean noParticle = false;      // keep particles for each pulse

                if (!last) {
                    // VISUAL ONLY pulse (cheap): client will render fracture via packet
                    EpicFightNetworkManager.sendToAllPlayerTrackingThisChunkWithSelf(
                            new SPFracture(center, r, noSound, noParticle),
                            level.getChunkAt(bp)
                    );
                } else {
                    // FINAL pulse: apply damage + also sends SPFracture internally
                    // If you want NO damage at all -> set last arg to false
                    LevelUtil.circleSlamFracture(caster, level, center, r, noSound, noParticle, true);
                }
            });
        }
    }

    // Convenience preset: 4 -> 5 -> 6 -> 7
    public static void expandingShockwave_4to7(ServerLevel level, @Nullable LivingEntity caster, Vec3 center) {
        expandingShockwave(level, caster, center, 2, 4D, 5D, 6D, 7D); // 2 ticks between rings
    }
}
