package com.pla.annoyingvillagers.init;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.pla.annoyingvillagers.AnnoyingVillagers;
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
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "c4click"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "c4click")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "ca_deep"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "ca_deep")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "tridentfs_skill"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "tridentfs_skill")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "electify"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "electify")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayyc"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayyc")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_player_interesting"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_player_interesting")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_you_no_know"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemon_say_you_no_know")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayplayer"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsayplayer")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsaydontbe"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "bluedemonsaydontbe")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "smoke_used"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "smoke_used")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "ob_place")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "metal_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "metal_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "gun_shot"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "gun_shot")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "head_shot"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "head_shot")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "target_block_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "target_block_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "dash_star"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "dash_star")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "item_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "item_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "c4planted"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "c4planted")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "c4defusing"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "c4defusing")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "c4defused"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "c4defused")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "pin"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "pin")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "g_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "dry_fire"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "dry_fire")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "s_g"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "s_g")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_12"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_12")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_5"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_5")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_15"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_15")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_6"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_6")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_16"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoji_16")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword_2"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword_2")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_start"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_start")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emote_2"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emote_2")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emoto_10"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emoto_10")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "music_box"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "music_box")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "music_box_win"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "music_box_win")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "hard_great_sword_skill"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "hard_great_sword_skill")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "fight_music"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "fight_music")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emote_23"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emote_23")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emote_24"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emote_24")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emote_21"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emote_21")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "emote_27"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "emote_27")));
    }
}
