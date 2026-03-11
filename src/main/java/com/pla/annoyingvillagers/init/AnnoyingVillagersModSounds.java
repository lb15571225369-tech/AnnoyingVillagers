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
    public static final RegistryObject<SoundEvent> ELECTRIFY = register("electrify");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_YC = register("bluedemonsayyc");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_PLAYER_INTERESTING = register("bluedemon_say_player_interesting");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_YOU_NO_KNOW = register("bluedemon_say_you_no_know");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_PLAYER = register("bluedemonsayplayer");
    public static final RegistryObject<SoundEvent> BLUEDEMON_SAY_DONT_BE = register("bluedemonsaydontbe");
    public static final RegistryObject<SoundEvent> OB_PLACE = register("ob_place");
    public static final RegistryObject<SoundEvent> METAL_HIT = register("metal_hit");
    public static final RegistryObject<SoundEvent> TARGET_BLOCK_HIT = register("target_block_hit");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD = register("heavy_attack_legendary_sword");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_LEGENDARY_SWORD_2 = register("heavy_attack_legendary_sword_2");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK_START = register("heavy_attack_start");
    public static final RegistryObject<SoundEvent> HARD_GREATSWORD_SKILL = register("hard_greatsword_skill");
    public static final RegistryObject<SoundEvent> LOST = register("lost");
    public static final RegistryObject<SoundEvent> SILENT = register("silent");
    public static final RegistryObject<SoundEvent> OBSIDIAN_PLACE = register("obsidian_place");
    public static final RegistryObject<SoundEvent> POP = register("pop");
    public static final RegistryObject<SoundEvent> OBSIDIAN_HIT = register("obsidian_hit");
    public static final RegistryObject<SoundEvent> HEAVY_HIT = register("heavy_hit");
    public static final RegistryObject<SoundEvent> WOOSH_HARD = register("woosh_hard");
    public static final RegistryObject<SoundEvent> WING = register("wing");
    public static final RegistryObject<SoundEvent> WHOOSH = register("whoosh");
    public static final RegistryObject<SoundEvent> HEROBRINE_ENOUGH = register("herobrine_enough");
    public static final RegistryObject<SoundEvent> HEROBRINE_ATTACK_1 = register("herobrine_attack_1");
    public static final RegistryObject<SoundEvent> HEROBRINE_ATTACK_2 = register("herobrine_attack_2");
    public static final RegistryObject<SoundEvent> HEROBRINE_ATTACK_3 = register("herobrine_attack_3");
    public static final RegistryObject<SoundEvent> HEROBRINE_YOU_ARE_WEAK = register("herobrine_youareweak");
    public static final RegistryObject<SoundEvent> HEROBRINE_HOWFOOLISH = register("herobrine_howfoolish");
    public static final RegistryObject<SoundEvent> HEROBRINE_AREYOUTALKINGABOUTME = register("herobrine_areyoutalkingaboutme");
    public static final RegistryObject<SoundEvent> HEROBRINE_WHOEVERGETINMYWAY = register("herobrine_whoevergetinmyway");
    public static final RegistryObject<SoundEvent> STEVE_BREATH = register("steave_breath");
    public static final RegistryObject<SoundEvent> STEVE_ATTACK = register("steve_attack");
    public static final RegistryObject<SoundEvent> STEVE_NO = register("steve_no");
    public static final RegistryObject<SoundEvent> STEVE_LEGENDARYSWORD = register("steve_legendarysword");
    public static final RegistryObject<SoundEvent> STEVE_WHY = register("steve_why");
    public static final RegistryObject<SoundEvent> STEVE_WHY_KEEP_FIGHTING = register("steve_whykeepfighting");
    public static final RegistryObject<SoundEvent> STEVE_SPAWN = register("steve_spawn");
    public static final RegistryObject<SoundEvent> STEVE_WIN = register("steve_win");
    public static final RegistryObject<SoundEvent> STEVE_ANGRY = register("steve_angry");
    public static final RegistryObject<SoundEvent> COOL_DOWN = register("cooldown");
    public static final RegistryObject<SoundEvent> GET_OUT = register("get_out");
    public static final RegistryObject<SoundEvent> THROW = register("throw");
    public static final RegistryObject<SoundEvent> ENDER_SHOT = register("ender_shot");
    public static final RegistryObject<SoundEvent> BLOOM = register("bloom");
    public static final RegistryObject<SoundEvent> DRAGON_BREATH = register("dragon_breath");
    public static final RegistryObject<SoundEvent> BIG_BLOOM = register("big_boom");
    public static final RegistryObject<SoundEvent> DRAGON_BREATH_FINALE = register("dragon_breath_finale");
    public static final RegistryObject<SoundEvent> SECOND_FORM_RELEASE = register("second_form_release");
    public static final RegistryObject<SoundEvent> PORTAL_SUMMON = register("portal_summon");
    public static final RegistryObject<SoundEvent> PORTAL_NATURAL = register("portal_natural");
    public static final RegistryObject<SoundEvent> SELF_REQUESTING_ASSISTANCE = register("self_requesting_assistance");
    public static final RegistryObject<SoundEvent> GREG_REQUESTING_ASSISTANCE = register("greg_requesting_assistance");
    public static final RegistryObject<SoundEvent> HEROBRINE_UNDERSTOOD = register("herobrine_understood");
    public static final RegistryObject<SoundEvent> WOOPIE_WIND = register("woopie_wind");
    public static final RegistryObject<SoundEvent> KICK_GUARD_BREAK = register("kick_guard_break");
    public static final RegistryObject<SoundEvent> DRAGON_AMBIENT_SOUND = register("entity.dragon.ambient");
    public static final RegistryObject<SoundEvent> DRAGON_STEP_SOUND = register("entity.dragon.step");
    public static final RegistryObject<SoundEvent> DRAGON_DEATH_SOUND = register("entity.dragon.death");
    public static final RegistryObject<SoundEvent> DRAGON_THUNDER_SHOOT_SOUND = register("entity.dragon.thunder_shoot");
    public static final RegistryObject<SoundEvent> MUFFLED_BOOM = register("muffled_boom");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, name)));
    }

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
