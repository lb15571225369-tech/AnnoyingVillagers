package com.pla.annoyingvillagers.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "annoying_villagers");
    public static final RegistryObject<ParticleType<?>> RED_SPARK = AnnoyingVillagersModParticleTypes.REGISTRY.register("red_spark", () -> {
        return new SimpleParticleType(true);
    });
}
