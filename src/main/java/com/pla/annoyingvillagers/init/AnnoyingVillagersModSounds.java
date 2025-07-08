package com.pla.annoyingvillagers.init;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD)
public class AnnoyingVillagersModSounds {

    public static Map<ResourceLocation, SoundEvent> REGISTRY = new HashMap();

    @SubscribeEvent
    public static void registerSounds(Register<SoundEvent> register) {
        Iterator iterator = AnnoyingVillagersModSounds.REGISTRY.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<ResourceLocation, SoundEvent> entry = (Entry) iterator.next();

            register.getRegistry().register((SoundEvent) ((SoundEvent) entry.getValue()).setRegistryName((ResourceLocation) entry.getKey()));
        }

    }

    static {
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "c4click"), new SoundEvent(new ResourceLocation("annoying_villagers", "c4click")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "ca_deep"), new SoundEvent(new ResourceLocation("annoying_villagers", "ca_deep")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "tridentfs_skill"), new SoundEvent(new ResourceLocation("annoying_villagers", "tridentfs_skill")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "electify"), new SoundEvent(new ResourceLocation("annoying_villagers", "electify")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "bluedemonsayyc"), new SoundEvent(new ResourceLocation("annoying_villagers", "bluedemonsayyc")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "bluedemon_say_player_interesting"), new SoundEvent(new ResourceLocation("annoying_villagers", "bluedemon_say_player_interesting")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "bluedemon_say_you_no_know"), new SoundEvent(new ResourceLocation("annoying_villagers", "bluedemon_say_you_no_know")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "bluedemonsayplayer"), new SoundEvent(new ResourceLocation("annoying_villagers", "bluedemonsayplayer")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "bluedemonsaydontbe"), new SoundEvent(new ResourceLocation("annoying_villagers", "bluedemonsaydontbe")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "smoke_used"), new SoundEvent(new ResourceLocation("annoying_villagers", "smoke_used")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "ob_place"), new SoundEvent(new ResourceLocation("annoying_villagers", "ob_place")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "metal_hit"), new SoundEvent(new ResourceLocation("annoying_villagers", "metal_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "gun_shot"), new SoundEvent(new ResourceLocation("annoying_villagers", "gun_shot")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "head_shot"), new SoundEvent(new ResourceLocation("annoying_villagers", "head_shot")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "target_block_hit"), new SoundEvent(new ResourceLocation("annoying_villagers", "target_block_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "dash_star"), new SoundEvent(new ResourceLocation("annoying_villagers", "dash_star")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "item_hit"), new SoundEvent(new ResourceLocation("annoying_villagers", "item_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "c4planted"), new SoundEvent(new ResourceLocation("annoying_villagers", "c4planted")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "c4defusing"), new SoundEvent(new ResourceLocation("annoying_villagers", "c4defusing")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "c4defused"), new SoundEvent(new ResourceLocation("annoying_villagers", "c4defused")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "pin"), new SoundEvent(new ResourceLocation("annoying_villagers", "pin")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "g_hit"), new SoundEvent(new ResourceLocation("annoying_villagers", "g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "dry_fire"), new SoundEvent(new ResourceLocation("annoying_villagers", "dry_fire")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "s_g"), new SoundEvent(new ResourceLocation("annoying_villagers", "s_g")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "s_g_hit"), new SoundEvent(new ResourceLocation("annoying_villagers", "s_g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoji_12"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoji_12")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoji_5"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoji_5")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoji_15"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoji_15")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoji_6"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoji_6")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoji_16"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoji_16")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "heavy_attack_legendary_sword"), new SoundEvent(new ResourceLocation("annoying_villagers", "heavy_attack_legendary_sword")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "heavy_attack_legendary_sword_2"), new SoundEvent(new ResourceLocation("annoying_villagers", "heavy_attack_legendary_sword_2")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "heavy_attack_start"), new SoundEvent(new ResourceLocation("annoying_villagers", "heavy_attack_start")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emote_2"), new SoundEvent(new ResourceLocation("annoying_villagers", "emote_2")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emoto_10"), new SoundEvent(new ResourceLocation("annoying_villagers", "emoto_10")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "music_box"), new SoundEvent(new ResourceLocation("annoying_villagers", "music_box")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "music_box_win"), new SoundEvent(new ResourceLocation("annoying_villagers", "music_box_win")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "hard_great_sword_skill"), new SoundEvent(new ResourceLocation("annoying_villagers", "hard_great_sword_skill")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "fight_music"), new SoundEvent(new ResourceLocation("annoying_villagers", "fight_music")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emote_23"), new SoundEvent(new ResourceLocation("annoying_villagers", "emote_23")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emote_24"), new SoundEvent(new ResourceLocation("annoying_villagers", "emote_24")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emote_21"), new SoundEvent(new ResourceLocation("annoying_villagers", "emote_21")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation("annoying_villagers", "emote_27"), new SoundEvent(new ResourceLocation("annoying_villagers", "emote_27")));
    }
}
