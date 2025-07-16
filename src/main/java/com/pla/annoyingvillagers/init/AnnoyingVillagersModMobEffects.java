package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//import com.pla.annoyingvillagers.potion.HerobrineEffectMobEffect;


public class AnnoyingVillagersModMobEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<MobEffect> ENCHANT_BED_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("enchant_bed_effect", () -> {
        return new EnchantBedEffectMobEffect();
    });
    public static final RegistryObject<MobEffect> BLUE_DEMON_SKILL_LIGHTING_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("blue_demon_skill_lighting_effect", () -> {
        return new BlueDemonSkillLightingEffectMobEffect();
    });
    public static final RegistryObject<MobEffect> EC = AnnoyingVillagersModMobEffects.REGISTRY.register("ec", () -> {
        return new EcMobEffect();
    });
    public static final RegistryObject<MobEffect> EC_PLAYER = AnnoyingVillagersModMobEffects.REGISTRY.register("ec_player", () -> {
        return new EcPlayerMobEffect();
    });
    public static final RegistryObject<MobEffect> GREAT_SWORD_EXECUTE_BLOCK = AnnoyingVillagersModMobEffects.REGISTRY.register("great_sword_execute_block", () -> {
        return new GreatSwordExecuteBlockMobEffect();
    });
    public static final RegistryObject<MobEffect> NPC_KICK_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("npc_kick_effect", () -> {
        return new NpcKickEffectMobEffect();
    });
    public static final RegistryObject<MobEffect> ELECTIFY = AnnoyingVillagersModMobEffects.REGISTRY.register("electify", () -> {
        return new ElectifyMobEffect();
    });
    public static final RegistryObject<MobEffect> BLOCK = AnnoyingVillagersModMobEffects.REGISTRY.register("block", () -> {
        return new BlockMobEffect();
    });
    public static final RegistryObject<MobEffect> CAPTIVE = AnnoyingVillagersModMobEffects.REGISTRY.register("captive", () -> {
        return new CaptiveMobEffect();
    });
    public static final RegistryObject<MobEffect> ENDURANCE = AnnoyingVillagersModMobEffects.REGISTRY.register("endurance", () -> {
        return new EnduranceMobEffect();
    });
    public static final RegistryObject<MobEffect> BLEED = AnnoyingVillagersModMobEffects.REGISTRY.register("bleed", () -> {
        return new BleedMobEffect();
    });
    public static final RegistryObject<MobEffect> WANTED = AnnoyingVillagersModMobEffects.REGISTRY.register("wanted", () -> {
        return new WantedMobEffect();
    });
}
