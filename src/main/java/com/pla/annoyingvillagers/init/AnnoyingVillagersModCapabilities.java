package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;

public final class AnnoyingVillagersModCapabilities {
    public static final Capability<SnakeBladeCapability.ISnakeBladeCapability> SNAKE_BLADE_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private AnnoyingVillagersModCapabilities() {}

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SnakeBladeCapability.ISnakeBladeCapability.class);
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(SnakeBladeCapability.ID, new SnakeBladeCapability.SnakeBladeProvider());
        }
    }

    @Nullable
    public static <T> T getCapability(@Nullable Entity entity, Capability<T> capability) {
        if (entity == null || !entity.isAlive()) return null;
        return entity.getCapability(capability).orElse(null);
    }
}