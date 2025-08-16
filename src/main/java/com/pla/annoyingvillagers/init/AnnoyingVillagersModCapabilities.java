package com.pla.annoyingvillagers.init;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AnnoyingVillagersModCapabilities {
    public static final Capability<SnakeBladeCapability.ISnakeBladeCapability> SNAKE_BLADE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SnakeBladeCapability.ISnakeBladeCapability.class);
    }

    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof LivingEntity living) {
            e.addCapability(SnakeBladeCapability.ID, new SnakeBladeCapability.SnakeBladeCapabilityImp.SnakeBladeProvider());
        }
    }

    @Nullable
    public static <T> T getCapability(Entity entity, Capability<T> capability) {
        if (entity == null) return null;
        if (!entity.isAlive()) return null;
        return entity.getCapability(capability).isPresent() ? entity.getCapability(capability).orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")) : null;
    }
}
