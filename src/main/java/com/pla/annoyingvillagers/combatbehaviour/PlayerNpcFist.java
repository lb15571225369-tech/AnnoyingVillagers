package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.efcondition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcFist {
    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(PlayerNpcCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_EAT, 0.0F)
                                            .addExBehavior(PlayerNpcCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(200.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 20.0D)
                                            .animationBehavior(AVAnimations.THROWING_ENDER_PEARL_OFFHAND, 0.0F)
                                            .addExBehavior(PlayerNpcCommon::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.FIST_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(Animations.FIST_AUTO3, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.FIST_LEFT, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.FIST_UP, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(AVAnimations.FIST_DASH, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.WHIRLWIND_KICK_LEFT, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.WHIRLWIND_KICK, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_DASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_AIR_SLASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.RELENTLESS_COMBO, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_1, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_2, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_4, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_C, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_RUSH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_COMBO, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            );
}
