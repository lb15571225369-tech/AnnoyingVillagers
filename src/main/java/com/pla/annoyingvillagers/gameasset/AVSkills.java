package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkills {
    public static Skill CLASH;
    public static Skill ENDER_AEGIS;
    public static Skill ENDER_GLAIVE;
    public static Skill DEMONIAC_VOLTAGE_REAVER;
    public static Skill OBSIDIAN_SLEDGEHAMMER;
    public static Skill ENDER_SLAYER_SCYTHE;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkills.CLASH = modRegistry.build("clash", ClashSkill::new, GuardSkill.createGuardBuilder());
        AVSkills.ENDER_AEGIS = modRegistry.build("ender_aegis", EnderAegisSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.ENDER_GLAIVE = modRegistry.build("ender_glaive", EnderGlaiveSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.WEAPON_CHARGE));
        AVSkills.DEMONIAC_VOLTAGE_REAVER = modRegistry.build("demoniac_voltage_reaver", DemoniacVoltageReaverSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.WEAPON_CHARGE));
        AVSkills.OBSIDIAN_SLEDGEHAMMER = modRegistry.build("obsidian_sledgehammer", ObsidianSledgeHammerSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.WEAPON_CHARGE));
        AVSkills.ENDER_SLAYER_SCYTHE = modRegistry.build("ender_slayer_scythe", EnderSlayerScytheSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.WEAPON_CHARGE));
    }
}
