//package com.pla.annoyingvillagers.module.efdg.gameasset;
//
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.module.efdg.skill.EarthquakeSkill;
//import com.pla.annoyingvillagers.module.efdg.skill.DualGreatswordSkill;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//import yesman.epicfight.api.data.reloader.SkillManager;
//import yesman.epicfight.api.forgeevent.SkillBuildEvent;
//import yesman.epicfight.skill.Skill;
//import yesman.epicfight.skill.passive.PassiveSkill;
//import yesman.epicfight.world.item.EpicFightCreativeTabs;
//
//@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.FORGE)
//public class EFSkills {
//
//    public static Skill EARTHQUAKE;
//    public static Skill DUALGREATSWORD;
//
//    public static void registerSkills() {
//        SkillManager.register(EarthquakeSkill::new, EarthquakeSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), "efdg", "earthquake");
//        SkillManager.register(DualGreatswordSkill::new, PassiveSkill.createPassiveBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), "efdg", "dualgreatsword");
//    }
//
//    @SubscribeEvent
//    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
//        EFSkills.EARTHQUAKE = skillbuildevent.build(AnnoyingVillagers.MODID, "earthquake");
//        EFSkills.DUALGREATSWORD = skillbuildevent.build(AnnoyingVillagers.MODID, "dualgreatsword");
//    }
//}
