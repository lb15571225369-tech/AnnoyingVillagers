package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AnnoyingVillagers.MODID);
    public static final RegistryObject<ParticleType<?>> RED_SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("red_spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> DIANHU = AnnoyingVillagersModParticleTypes.REGISTRY.register("dianhu", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> DIANHU_2 = AnnoyingVillagersModParticleTypes.REGISTRY.register("dianhu_2", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("spark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> PE = AnnoyingVillagersModParticleTypes.REGISTRY.register("pe", () -> {
        return new SimpleParticleType(false);
    });
    public static final RegistryObject<ParticleType<?>> GLOWINGEYES = AnnoyingVillagersModParticleTypes.REGISTRY.register("glowingeyes", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> LIGHT = AnnoyingVillagersModParticleTypes.REGISTRY.register("light", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> BLUESPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("bluespark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> GREENSPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("greenspark", () -> {
        return new SimpleParticleType(true);
    });
    public static final RegistryObject<ParticleType<?>> ENDER = AnnoyingVillagersModParticleTypes.REGISTRY.register("ender", () -> {
        return new SimpleParticleType(true);
    });
}
