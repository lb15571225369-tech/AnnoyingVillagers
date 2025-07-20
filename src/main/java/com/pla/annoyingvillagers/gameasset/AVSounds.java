package com.pla.annoyingvillagers.gameasset;

import com.google.common.collect.Sets;
import java.util.HashSet;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "epicfight");
    public static final HashSet<SoundEvent> SOUND_EVENTS = Sets.newHashSet();
    public static final RegistryObject<SoundEvent> SWORD_WHOOSH = register("entity.weapon.sword_whoosh");
    public static final RegistryObject<SoundEvent> KICK = register("entity.weapon.kick");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () ->
                new SoundEvent(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, name))
        );
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
