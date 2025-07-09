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
}
