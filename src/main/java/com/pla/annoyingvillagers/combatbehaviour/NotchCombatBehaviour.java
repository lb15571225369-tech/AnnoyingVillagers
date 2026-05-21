package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NotchCombatBehaviour {

    // Phase 0: Null-style fist combat - aggressive punching
    public static final Builder<MobPatch<?>> PHASE_NULL = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(8)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 4.0D)
                                            .animationBehavior(Animations.FIST_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 4.5D)
                                                            .animationBehavior(Animations.FIST_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(Animations.FIST_AUTO3, 0.0F)
                                                            )
                                            )
                            )
            );

    // Phase 1: Divine Sword - Notch holds sword but NEVER attacks. Just floats.
    // Only a guard behavior with impossible conditions so it never triggers.
    public static final Builder<MobPatch<?>> PHASE_DIVINE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(1.0D)
                            .maxCooldown(9999)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 0.01D)
                                            .guard(9999)
                            )
            );

    // Phase 2: Absolute - Obsidian-style combat, stronger than Shadow Obsidian
    public static final Builder<MobPatch<?>> PHASE_ABSOLUTE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(5.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canExecute)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::performExecute)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(10)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 4.0D)
                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 6.0D)
                                                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AIR_SLASH, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 6.0D)
                                                                                                            .animationBehavior(AVAnimations.OBSIDIAN_BIPED_LANDING, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO2, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 4.0D)
                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_DASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AVAnimations.OBSIDIAN_STRONG_PUNCH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 6.0D)
                                                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AIR_SLASH, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO3, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_AUTO1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AVAnimations.OBSIDIAN_FIST_DASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 6.0D)
                                                                                            .animationBehavior(AVAnimations.OBSIDIAN_BIPED_LANDING, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            );
}
