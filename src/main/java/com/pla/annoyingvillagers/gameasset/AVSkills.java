package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkills {
    public static Skill ENDER_AEGIS;
    public static Skill ENDER_GLAIVE;
    public static Skill DEMONIAC_VOLTAGE_REAVER;
    public static Skill OBSIDIAN_SLEDGEHAMMER;
    public static Skill ENDER_SLAYER_SCYTHE;
    public static Skill BOW;
    public static Skill LEGENDARY_SWORD;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkills.ENDER_AEGIS = modRegistry.build("ender_aegis", EnderAegisSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.ENDER_GLAIVE = modRegistry.build("ender_glaive", EnderGlaiveSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.DEMONIAC_VOLTAGE_REAVER = modRegistry.build("demoniac_voltage_reaver", DemoniacVoltageReaverSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.OBSIDIAN_SLEDGEHAMMER = modRegistry.build("obsidian_sledgehammer", ObsidianSledgeHammerSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.ENDER_SLAYER_SCYTHE = modRegistry.build("ender_slayer_scythe", EnderSlayerScytheSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.BOW = modRegistry.build("bow", BowSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        AVSkills.LEGENDARY_SWORD = modRegistry.build("legendary_sword", LegendarySwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
    }
}
