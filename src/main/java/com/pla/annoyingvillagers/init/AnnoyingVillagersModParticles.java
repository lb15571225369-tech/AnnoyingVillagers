package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.RED_SPARK.get(), RedSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(), ElectricSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.DRAGON_SPARK.get(), DragonSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK_2.get(), ElectricSpark2Particle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.SPARK.get(), SparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.PE.get(), PeParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.GLOWINGEYES.get(), GlowingEyesParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.LIGHT.get(), LightParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.BLUESPARK.get(), BlueSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.GREENSPARK.get(), GreenSparkParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.ENDER.get(), EnderParticle::provider);
        Minecraft.getInstance().particleEngine.register((SimpleParticleType) AnnoyingVillagersModParticleTypes.NULL.get(), SmokeParticle.Provider::new);
    }
}
