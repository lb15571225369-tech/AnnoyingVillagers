package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.ClashSkill;
import com.pla.annoyingvillagers.skill.EnderAegisSkill;
import com.pla.annoyingvillagers.skill.EnderGlaiveSkill;
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

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkills.CLASH = modRegistry.build("clash", ClashSkill::new, GuardSkill.createGuardBuilder());
        AVSkills.ENDER_AEGIS = modRegistry.build("ender_aegis", EnderAegisSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        AVSkills.ENDER_GLAIVE = modRegistry.build("ender_glaive", EnderGlaiveSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.WEAPON_CHARGE));
    }
}
