package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeUtil {
    public static void applyScreenShake(ServerLevel level, Vec3 center, double range,
                                        int durationTicks, int amplifier) {
        double rangeSq = range * range;
        AABB box = new AABB(center, center).inflate(range);

        for (Player player : level.getEntitiesOfClass(Player.class, box,
                player -> player.isAlive() && !player.isSpectator())) {

            double distSq = player.distanceToSqr(center.x, center.y, center.z);
            if (distSq > rangeSq) continue;

            player.addEffect(new MobEffectInstance(
                    AnnoyingVillagersModMobEffects.SCREEN_SHAKE.get(),
                    durationTicks,
                    amplifier,
                    false,
                    false,
                    true
            ));
        }
    }
}
