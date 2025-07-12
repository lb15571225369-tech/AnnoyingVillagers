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
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "pin"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "pin")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "g_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "dry_fire"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "dry_fire")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "s_g"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "s_g")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "s_g_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword_2"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_legendary_sword_2")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_start"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_attack_start")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "music_box"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "music_box")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "music_box_win"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "music_box_win")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "hard_great_sword_skill"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "hard_great_sword_skill")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "fight_music"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "fight_music")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "shiqu"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "shiqu")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "wusheng"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "wusheng")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "fangzhiheiyaoshi"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "fangzhiheiyaoshi")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "pop"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "pop")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heiyaoshi_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heiyaoshi_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_hit"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "heavy_hit")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "woosh_hard"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "woosh_hard")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "humchuanqi"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "humchuanqi")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "wing"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "wing")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "l_g_wake_up"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "l_g_wake_up")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "zhanshenzhirenjuexing"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "zhanshenzhirenjuexing")));
        AnnoyingVillagersModSounds.REGISTRY.put(new ResourceLocation(AnnoyingVillagers.MODID, "whoosh"), new SoundEvent(new ResourceLocation(AnnoyingVillagers.MODID, "whoosh")));
    }
}
