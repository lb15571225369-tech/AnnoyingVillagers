package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.skill.Clash;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkill {
    public static Skill CLASH;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);
        AVSkill.CLASH = modRegistry.build("clash", Clash::new, PassiveSkill.createPassiveBuilder());
    }
}
