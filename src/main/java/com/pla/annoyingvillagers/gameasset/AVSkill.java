package com.pla.annoyingvillagers.gameasset;

import java.util.Set;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.dual_greatsword.skill.DualGreatswordSkill;
import com.pla.annoyingvillagers.compat.dual_greatsword.skill.EarthquakeSkill;
import com.pla.annoyingvillagers.skill.DualDancingEdge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.skill.Clash;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AVSkill {

    public static Skill DUAL_DANCING_EDGE;
    public static Skill CLASH;
    public static Skill EARTHQUAKE;
    public static Skill DUALGREATSWORD;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);

        WeaponInnateSkill dualDancingEdgeSkill = (WeaponInnateSkill) modRegistry.build(
                "dual_dancing_edge",
                DualDancingEdge::new,
                WeaponInnateSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));

        dualDancingEdgeSkill.newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0])))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));

        AVSkill.DUAL_DANCING_EDGE = dualDancingEdgeSkill;
        AVSkill.CLASH = modRegistry.build("clash", Clash::new, PassiveSkill.createPassiveBuilder());
        AVSkill.EARTHQUAKE = modRegistry.build("earthquake", EarthquakeSkill::new, EarthquakeSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
        AVSkill.DUALGREATSWORD = modRegistry.build("dualgreatsword", DualGreatswordSkill::new, DualGreatswordSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
    }
}
