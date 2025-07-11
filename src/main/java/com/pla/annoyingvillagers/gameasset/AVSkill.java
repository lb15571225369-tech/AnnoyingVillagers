package com.pla.annoyingvillagers.gameasset;

import java.util.Set;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.module.efdg.skill.DualGreatswordSkill;
import com.pla.annoyingvillagers.module.efdg.skill.EarthquakeSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.skill.Clash;
import com.pla.annoyingvillagers.skill.SpinningDeath;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.SourceTags;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.FORGE)
public class AVSkill {

    public static Skill DUAL_DANCING_EDGE;
    public static Skill SPINNING_DEATH;
    public static Skill CLASH;
    public static Skill EARTHQUAKE;
    public static Skill DUALGREATSWORD;

    public static void registerSkills() {
        SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(AnnoyingVillagers.MODID, "biped/combat/dancing_edge")), AnnoyingVillagers.MODID, "dual_dancing_edge");
        SkillManager.register(SpinningDeath::new, SpinningDeath.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), AnnoyingVillagers.MODID, "spinning_death");
        SkillManager.register(Clash::new, PassiveSkill.createPassiveBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), AnnoyingVillagers.MODID, "clash");
        SkillManager.register(EarthquakeSkill::new, EarthquakeSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), AnnoyingVillagers.MODID, "earthquake");
        SkillManager.register(DualGreatswordSkill::new, PassiveSkill.createPassiveBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS), AnnoyingVillagers.MODID, "dualgreatsword");
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        WeaponInnateSkill weaponinnateskill = (WeaponInnateSkill) skillbuildevent.build(AnnoyingVillagers.MODID, "dual_dancing_edge");

        weaponinnateskill.newProperty().addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0]))).addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE)).registerPropertiesToAnimation();
        AVSkill.DUAL_DANCING_EDGE = weaponinnateskill;
        AVSkill.SPINNING_DEATH = skillbuildevent.build(AnnoyingVillagers.MODID, "spinning_death");
        AVSkill.CLASH = skillbuildevent.build(AnnoyingVillagers.MODID, "clash");

        AVSkill.EARTHQUAKE = skillbuildevent.build(AnnoyingVillagers.MODID, "earthquake");
        AVSkill.DUALGREATSWORD = skillbuildevent.build(AnnoyingVillagers.MODID, "dualgreatsword");
    }
}
