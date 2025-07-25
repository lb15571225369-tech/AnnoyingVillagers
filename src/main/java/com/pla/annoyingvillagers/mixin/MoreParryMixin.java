package com.pla.annoyingvillagers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

@Mixin(value = {ParryingSkill.class}, remap = false)
public abstract class MoreParryMixin {
    @Inject(method = {"createActiveGuardBuilder"}, at = {@At("RETURN")})
    private static void InjectAdvancedGuardMotion(CallbackInfoReturnable<GuardSkill.Builder> callbackinforeturnable) {
        GuardSkill.Builder builder = (GuardSkill.Builder) callbackinforeturnable.getReturnValue();

        builder.addAdvancedGuardMotion(WeaponCategories.GREATSWORD, (capabilityitem, playerpatch) -> {
            return new StaticAnimation[]{Animations.GREATSWORD_GUARD_HIT};
        });
        builder.addGuardMotion(WeaponCategories.GREATSWORD, (capabilityitem, playerpatch) -> {
            return Animations.GREATSWORD_GUARD_HIT;
        });
        builder.addAdvancedGuardMotion(WeaponCategories.SPEAR, (capabilityitem, playerpatch) -> {
            return new StaticAnimation[]{WOMAnimations.AGONY_GUARD_HIT_1, WOMAnimations.AGONY_GUARD_HIT_2};
        });
        builder.addGuardMotion(WeaponCategories.SPEAR, (capabilityitem, playerpatch) -> {
            return AVAnimations.SPEAR_GUARD_HIT;
        });
        builder.addAdvancedGuardMotion(AVCategories.LEGENDARYSWORD, (capabilityitem, playerpatch) -> {
            return new StaticAnimation[]{AVAnimations.LEGENDARY_SWORD_GUARD_PARRY};
        });
        builder.addGuardMotion(AVCategories.LEGENDARYSWORD, (capabilityitem, playerpatch) -> {
            return AVAnimations.LEGENDARY_SWORD_GUARD_HIT;
        });
        builder.addAdvancedGuardMotion(WeaponCategories.AXE, (capabilityitem, playerpatch) -> {
            return capabilityitem.getStyle(playerpatch) == Styles.ONE_HAND ? new StaticAnimation[]{Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2} : new StaticAnimation[]{Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3};
        });
        builder.addGuardMotion(WeaponCategories.AXE, (capabilityitem, playerpatch) -> {
            return Animations.SWORD_GUARD_HIT;
        });
        builder.addAdvancedGuardMotion(WeaponCategories.TACHI, (capabilityitem, playerpatch) -> {
            return capabilityitem.getStyle(playerpatch) == Styles.TWO_HAND ? new StaticAnimation[]{Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT1} : new StaticAnimation[]{Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3};
        });
        builder.addGuardMotion(WeaponCategories.TACHI, (capabilityitem, playerpatch) -> {
            return capabilityitem.getStyle(playerpatch) == Styles.TWO_HAND ? Animations.LONGSWORD_GUARD_HIT : AVAnimations.DUAL_TACHI_GUARD_HIT;
        });
        builder.addAdvancedGuardMotion(AVCategories.HARDGREATSWORD, (capabilityitem, playerpatch) -> {
            return new StaticAnimation[]{AVAnimations.HARD_GREAT_SWORD_GUARD};
        });
        builder.addGuardMotion(AVCategories.HARDGREATSWORD, (capabilityitem, playerpatch) -> {
            return AVAnimations.HARD_GREAT_SWORD_GUARD;
        });
    }
}