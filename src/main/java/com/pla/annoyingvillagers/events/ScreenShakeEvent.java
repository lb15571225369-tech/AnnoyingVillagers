package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeFov;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ScreenShakeEvent {

    @SubscribeEvent
    public static void onComputeFov(ComputeFov event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        Entity cameraEntity = event.getCamera().getEntity();
        if (!(cameraEntity instanceof LivingEntity living)) return;

        MobEffectInstance shake = living.getEffect(AnnoyingVillagersModMobEffects.SCREEN_SHAKE.get());
        if (shake == null) return;

        int amp = shake.getAmplifier();
        RandomSource rng = level.getRandom();

        double range = amp * 0.02D;
        double newFov = event.getFOV() + Mth.nextDouble(rng, -range, range);

        event.setFOV(newFov);
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        Entity cameraEntity = event.getCamera().getEntity();
        if (!(cameraEntity instanceof LivingEntity living)) return;

        MobEffectInstance shake = living.getEffect(AnnoyingVillagersModMobEffects.SCREEN_SHAKE.get());
        if (shake == null) return;

        int amp = shake.getAmplifier();
        if (amp <= 0) return;

        RandomSource rng = level.getRandom();

        double yawRange   = amp * 0.03D;
        double pitchRange = amp * 0.03D;
        double rollRange  = amp * 0.10D;

        float newYaw   = (float) (event.getYaw()   + Mth.nextDouble(rng, -yawRange,   yawRange));
        float newPitch = (float) (event.getPitch() + Mth.nextDouble(rng, -pitchRange, pitchRange));
        float newRoll  = (float) (event.getRoll()  + Mth.nextDouble(rng, -rollRange,  rollRange));

        event.setYaw(newYaw);
        event.setPitch(newPitch);
        event.setRoll(newRoll);
    }
}
