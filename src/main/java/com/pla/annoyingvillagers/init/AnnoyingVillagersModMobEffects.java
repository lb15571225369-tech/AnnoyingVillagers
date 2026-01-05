package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class AnnoyingVillagersModMobEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AnnoyingVillagers.MODID);
    public static final RegistryObject<MobEffect> ENCHANT_BED_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("enchant_bed_effect", EnchantBedEffectMobEffect::new);
    public static final RegistryObject<MobEffect> BLUE_DEMON_SKILL_LIGHTING_EFFECT = AnnoyingVillagersModMobEffects.REGISTRY.register("blue_demon_skill_lighting_effect", BlueDemonSkillLightingEffectMobEffect::new);
    public static final RegistryObject<MobEffect> ELECTIFY = AnnoyingVillagersModMobEffects.REGISTRY.register("electify", ElectifyMobEffect::new);
    public static final RegistryObject<MobEffect> CAPTIVE = AnnoyingVillagersModMobEffects.REGISTRY.register("captive", CaptiveMobEffect::new);
    public static final RegistryObject<MobEffect> HEROBRINE = AnnoyingVillagersModMobEffects.REGISTRY.register("herobrine", HerobrineMobEffect::new);
    public static final RegistryObject<MobEffect> SCREEN_SHAKE = AnnoyingVillagersModMobEffects.REGISTRY.register("screen_shake", () -> {
        return new ScreenShakeMobEffect();
    });
}
