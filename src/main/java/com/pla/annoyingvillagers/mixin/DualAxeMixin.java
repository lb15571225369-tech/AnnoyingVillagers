package com.pla.annoyingvillagers.mixin;


import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import M6FGR.dualaxes.skill.weaponinnate.SpinningDeathSkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;

import java.util.Set;

@Mixin(value = {DualAxesSkills.class}, remap = false)
public abstract class DualAxeMixin {
    @Inject(method = {"buildSkillEvent"}, at = {@At("HEAD")}, cancellable = true)
    private static void buildSkillEvent(SkillBuildEvent build, CallbackInfo ci) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("dualaxes");
        WeaponInnateSkill spinning_death = (WeaponInnateSkill)modRegistry.build("spinning_death", SpinningDeathSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(() -> (AttackAnimation) DualAxesAnimations.AXE_SPINNING_DEATH));
        spinning_death.newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(5.0F)).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0]))).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
        DualAxesSkills.SPINNING_DEATH = spinning_death;
        ci.cancel();
    }
}
