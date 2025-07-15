package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModParticles {

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent particlefactoryregisterevent) {
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.RED_SPARK.get(), RedSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.DIANHU.get(), ElectricSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.DIANHU_2.get(), ElectricSpark2Particle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.SPARK.get(), SparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.PE.get(), PeParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.GLOWINGEYES.get(), GlowingEyesParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.LIGHT.get(), LightParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.BLUESPARK.get(), BlueSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.GREENSPARK.get(), GreenSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.ENDER.get(), EnderParticle::provider);
    }
}
