package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.pla.annoyingvillagers.potion.BlueDemonSkillLightingEffectMobEffect;
import com.pla.annoyingvillagers.potion.ECMobEffect;
import com.pla.annoyingvillagers.potion.EcPlayerMobEffect;
import com.pla.annoyingvillagers.potion.EnchantBedEffectMobEffect;
import com.pla.annoyingvillagers.potion.GreatSwordExecuteBlockMobEffect;
//import com.pla.annoyingvillagers.potion.HerobrineEffectMobEffect;
import com.pla.annoyingvillagers.potion.NpcKickEffectMobEffect;

public class AnnoyingVillagersModMobEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AnnoyingVillagers.MODID);
//    public static final RegistryObject<MobEffect> HEROBRINE_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("herobrine_effect", () -> {
//        return new HerobrineEffectMobEffect();
//    });
    public static final RegistryObject<MobEffect> ENCHANT_BED_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("enchant_bed_effect", () -> {
        return new EnchantBedEffectMobEffect();
    });
    public static final RegistryObject<MobEffect> BLUE_DEMON_SKILL_LIGHTING_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("blue_demon_skill_lighting_effect", () -> {
        return new BlueDemonSkillLightingEffectMobEffect();
    });
    public static final RegistryObject<MobEffect> EC = AnnoyingVillagersModMobEffects.REGISTRY.register("ec", () -> {
        return new ECMobEffect();
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
}
