package com.pla.annoyingvillagers.gameasset;

import java.util.Set;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.types.HeavyAttackAnimation;
import com.pla.annoyingvillagers.compat.efdg.skill.DualGreatswordSkill;
import com.pla.annoyingvillagers.compat.efdg.skill.EarthquakeSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.skill.Clash;
import com.pla.annoyingvillagers.skill.SpinningDeath;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.FORGE)
public class AVSkill {

    public static Skill DUAL_DANCING_EDGE;
    public static Skill SPINNING_DEATH;
    public static Skill CLASH;
    public static Skill EARTHQUAKE;
    public static Skill DUALGREATSWORD;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent skillbuildevent) {
        SkillBuildEvent.ModRegistryWorker modRegistry = skillbuildevent.createRegistryWorker(AnnoyingVillagers.MODID);

        WeaponInnateSkill weaponinnateskill = modRegistry.build(
                "dual_dancing_edge",
                SimpleWeaponInnateSkill::new,
                SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(() -> (AttackAnimation) AnimationManager.getInstance()
                                .byKeyOrThrow(new ResourceLocation(AnnoyingVillagers.MODID, "biped/combat/dancing_edge"))
                        )
        );
        weaponinnateskill.newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0])))
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
                .registerPropertiesToAnimation();

        AVSkill.DUAL_DANCING_EDGE = weaponinnateskill;
        AVSkill.SPINNING_DEATH = modRegistry.build("spinning_death", SpinningDeath::new, SpinningDeath.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
        AVSkill.CLASH = modRegistry.build("clash", Clash::new, PassiveSkill.createPassiveBuilder());
        AVSkill.EARTHQUAKE = modRegistry.build("earthquake", EarthquakeSkill::new, EarthquakeSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
        AVSkill.DUALGREATSWORD = modRegistry.build("dualgreatsword", DualGreatswordSkill::new, DualGreatswordSkill.createWeaponInnateBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
    }
}
