package com.pla.annoyingvillagers.init;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.client.particle.RedSparkParticle;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModParticles {

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent particlefactoryregisterevent) {
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.RED_SPARK.get(), RedSparkParticle::provider);
    }
}
