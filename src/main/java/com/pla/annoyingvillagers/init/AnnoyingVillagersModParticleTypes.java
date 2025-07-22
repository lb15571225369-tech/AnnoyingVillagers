package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AnnoyingVillagers.MODID);
    public static final RegistryObject<ParticleType<?>> RED_SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("red_spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> ELECTRIC_SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("electric_spark", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> ELECTRIC_SPARK_2 = AnnoyingVillagersModParticleTypes.REGISTRY.register("electric_spark_2", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> PE = AnnoyingVillagersModParticleTypes.REGISTRY.register("pe", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> GLOWINGEYES = AnnoyingVillagersModParticleTypes.REGISTRY.register("glowing_eyes", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> LIGHT = AnnoyingVillagersModParticleTypes.REGISTRY.register("light", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> BLUESPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("blue_spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> GREENSPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("green_spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> ENDER = AnnoyingVillagersModParticleTypes.REGISTRY.register("ender", () -> {
        return new SimpleParticleType(true);
    });
}
