package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AnnoyingVillagers.MODID);

    public static final RegistryObject<SoundEvent> TRIDENTFS_SKILL = register("tridentfs_skill");
    public static final RegistryObject<SoundEvent> ELECTIFY = register("electify");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_YC = register("bluedemonsayyc");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_PLAYER_INTERESTING = register("bluedemon_say_player_interesting");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_YOU_NO_KNOW = register("bluedemon_say_you_no_know");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_PLAYER = register("bluedemonsayplayer");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_DONT_BE = register("bluedemonsaydontbe");
    public static final RegistryObject<SoundEvent> OB_PLACE = register("ob_place");
    public static final RegistryObject<SoundEvent> METAL_HIT = register("metal_hit");
    public static final RegistryObject<SoundEvent> TARGET_BLOCK_HIT = register("target_block_hit");
    public static final RegistryObject<SoundEvent> DASH_STAR = register("dash_star");
    public static final RegistryObject<SoundEvent> ITEM_HIT = register("item_hit");
    public static final RegistryObject<SoundEvent> PIN = register("pin");
    public static final RegistryObject<SoundEvent> G_HIT = register("g_hit");
    public static final RegistryObject<SoundEvent> DRY_FIRE = register("dry_fire");
    public static final RegistryObject<SoundEvent> S_G = register("s_g");
    public static final RegistryObject<SoundEvent> S_G_HIT = register("s_g_hit");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD = register("heavy_attack_legendary_sword");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD_2 = register("heavy_attack_legendary_sword_2");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_START = register("heavy_attack_start");
    public static final RegistryObject<SoundEvent> MUSIC_BOX = register("music_box");
    public static final RegistryObject<SoundEvent> MUSIC_BOX_WIN = register("music_box_win");
    public static final RegistryObject<SoundEvent> HARD_GREAT_SWORD_SKILL = register("hard_great_sword_skill");
    public static final RegistryObject<SoundEvent> FIGHT_MUSIC = register("fight_music");
    public static final RegistryObject<SoundEvent> LOST = register("lost");
    public static final RegistryObject<SoundEvent> SILENT = register("silent");
    public static final RegistryObject<SoundEvent> OBSIDIAN_PLACE = register("obsidian_place");
    public static final RegistryObject<SoundEvent> POP = register("pop");
    public static final RegistryObject<SoundEvent> OBSIDIAN_HIT = register("obsidian_hit");
    public static final RegistryObject<SoundEvent> HEAVY_HIT = register("heavy_hit");
    public static final RegistryObject<SoundEvent> WOOSH_HARD = register("woosh_hard");
    public static final RegistryObject<SoundEvent> SOUL_LEGEND = register("soul_legend");
    public static final RegistryObject<SoundEvent> WING = register("wing");
    public static final RegistryObject<SoundEvent> L_G_WAKE_UP = register("l_g_wake_up");
    public static final RegistryObject<SoundEvent> LEGENDARY_SWORD_AWAKENING = register("legendary_sword_awakening");
    public static final RegistryObject<SoundEvent> WHOOSH = register("whoosh");
    public static final RegistryObject<SoundEvent> HIMSAYENOUGH = register("himsayenough");
    public static final RegistryObject<SoundEvent> HIMATTACK = register("himattack");
    public static final RegistryObject<SoundEvent> HIMATTACK2 = register("himattack2");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AnnoyingVillagers.MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
