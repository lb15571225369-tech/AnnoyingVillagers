package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkills {
    public static Skill ENDER_AEGIS;
    public static Skill ENDER_GLAIVE;
    public static Skill DEMONIAC_VOLTAGE_REAVER;
    public static Skill OBSIDIAN_SLEDGEHAMMER;
    public static Skill ENDER_SLAYER_SCYTHE;
    public static Skill NULL_WEAPON;
    public static Skill OBSIDIAN_WEAPON;
    public static Skill BEDROCK_WEAPON;
    public static Skill SHADOW_OBSIDIAN_PILLAR;
    public static Skill SHADOW_OBSIDIAN_PILLAR_SWORD;
    public static Skill SHADOW_OBSIDIAN_SWORD;
    public static Skill SHADOW_OBSIDIAN_SWORD_DUAL;
    public static Skill LEGENDARY_SWORD;
    public static Skill WOOPIE_THE_SWORD;
    public static Skill HARD_GREAT_SWORD;
    public static Skill CRAFTING_TABLE;
    public static Skill WOODEN_DOOR;
    public static Skill TRAPDOOR;
    public static Skill LADDER;
    public static Skill KICK;
    public static Skill STUN_ESCAPE;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkills.ENDER_AEGIS = modRegistry.build("ender_aegis", EnderAegisSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.ENDER_GLAIVE = modRegistry.build("ender_glaive", EnderGlaiveSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.DEMONIAC_VOLTAGE_REAVER = modRegistry.build("demoniac_voltage_reaver", DemoniacVoltageReaverSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.OBSIDIAN_SLEDGEHAMMER = modRegistry.build("obsidian_sledgehammer", ObsidianSledgeHammerSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.ENDER_SLAYER_SCYTHE = modRegistry.build("ender_slayer_scythe", EnderSlayerScytheSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.NULL_WEAPON = modRegistry.build("null_weapon", NullWeaponSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.OBSIDIAN_WEAPON = modRegistry.build("obsidian_weapon", ObsidianWeaponSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.BEDROCK_WEAPON = modRegistry.build("bedrock_weapon", BedrockWeaponSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.SHADOW_OBSIDIAN_PILLAR = modRegistry.build("shadow_obsidian_pillar", ShadowObsidianPillarSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.SHADOW_OBSIDIAN_PILLAR_SWORD = modRegistry.build("shadow_obsidian_pillar_sword", ShadowObsidianPillarSwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.SHADOW_OBSIDIAN_SWORD = modRegistry.build("shadow_obsidian_sword", ShadowObsidianSwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.SHADOW_OBSIDIAN_SWORD_DUAL = modRegistry.build("shadow_obsidian_sword_dual", ShadowObsidianSwordDualSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.LEGENDARY_SWORD = modRegistry.build("legendary_sword", LegendarySwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.WOOPIE_THE_SWORD = modRegistry.build("woopie_the_sword", WoopieTheSwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.HARD_GREAT_SWORD = modRegistry.build("hard_greatsword", HardGreatSwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.CRAFTING_TABLE = modRegistry.build("crafting_table", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(AnimsSolar.SOLAR_AUTO_2).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)).newProperty();
        AVSkills.WOODEN_DOOR = modRegistry.build("wooden_door", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(WOMAnimations.TORMENT_BERSERK_DASH).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)).newProperty();
        AVSkills.TRAPDOOR = modRegistry.build("trapdoor", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(AVAnimations.GIANT_WHIRLWIND).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)).newProperty();
        AVSkills.LADDER = modRegistry.build("ladder", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(AVAnimations.SWORD_HEAVY_AUTO_3).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)).newProperty();
        AVSkills.KICK =  modRegistry.build("kick", KickSkill::new, PassiveSkill.createPassiveBuilder().setCategory(AVSkillCategories.AV_KICK));
        AVSkills.STUN_ESCAPE =  modRegistry.build("stun_escape", StunEscapeSkill::new, PassiveSkill.createPassiveBuilder().setCategory(AVSkillCategories.AV_STUN_ESCAPE));
    }
}