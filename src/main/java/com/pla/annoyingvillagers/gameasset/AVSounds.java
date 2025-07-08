package com.pla.annoyingvillagers.gameasset;

import com.google.common.collect.Sets;
import java.util.HashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = "annoying_villagers", bus = Bus.MOD)
public class AVSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "epicfight");
    public static final HashSet<SoundEvent> SOUND_EVENTS = Sets.newHashSet();
    public static final SoundEvent SWORD_WHOOSH = RegSound("entity.weapon.sword_whoosh");
    public static final SoundEvent KICK = RegSound("entity.weapon.kick");

    private static SoundEvent RegSound(String s) {
        ResourceLocation resourcelocation = new ResourceLocation("annoying_villagers", s);
        SoundEvent soundevent = (SoundEvent) (new SoundEvent(resourcelocation)).setRegistryName(s);

        AVSounds.SOUND_EVENTS.add(soundevent);
        return soundevent;
    }

    public static void registerSoundRegistry(Register<SoundEvent> register) {
        register.getRegistry().registerAll(new SoundEvent[]{AVSounds.SWORD_WHOOSH, AVSounds.KICK});
    }

    @SubscribeEvent
    public static void onSoundRegistry(Register<SoundEvent> register) {
        AVSounds.SOUND_EVENTS.forEach((soundevent) -> {
            register.getRegistry().register(soundevent);
        });
    }
}
