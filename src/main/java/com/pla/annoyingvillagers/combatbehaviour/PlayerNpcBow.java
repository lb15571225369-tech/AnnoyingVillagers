package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.efcondition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcBow {
    public static final Builder<MobPatch<?>> BOW = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(250.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(PlayerNpcCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(PlayerNpcCommon::swapToMelee)
                            )
            )
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
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.BOW_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(7.0D, 14.0D)
                                                            .animationBehavior(AVAnimations.BOW_AUTO_1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(7.0D, 14.0D)
                                                                            .animationBehavior(AVAnimations.BOW_AUTO_1, 0.0F)
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
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.BOW_AUTO_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.BOW_AUTO_4, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.BOW_AUTO_5, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(60.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AVAnimations.THROWING_ENDER_PEARL_OFFHAND, 0.0F)
                                            .addExBehavior(PlayerNpcCommon::performEnderPearlToTarget)
                            )
            );
}
