package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.efcondition.HealthCheck;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcSword {
    public static final Builder<MobPatch<?>> SWORD = CECombatBehaviors.builder()
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
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_1, 0.0F).addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_2, 0.0F)
                                                                                            )
                                                                            )
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
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.SWORD_HEAVY_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.SWORD_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(AVAnimations.SWORD_HEAVY_AUTO_3, 0.0F)
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
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
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
                                            .animationBehavior(AVAnimations.KICK_H, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.5D, 2.0D)
                                            .custom(PlayerNpcCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            );

    public static final Builder<MobPatch<?>> DUAL_SWORD = CECombatBehaviors.builder()
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
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_DUAL_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.SWORD_DUAL_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(Animations.SWORD_DUAL_AUTO3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                            .animationBehavior(AVAnimations.DUAL_SWORD1, 0.0F).addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                                            .animationBehavior(AVAnimations.DUAL_SWORD2, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                                                            .animationBehavior(AVAnimations.DUAL_SWORD1, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                                                                            .animationBehavior(AVAnimations.DUAL_SWORD3, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
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
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.DAGGER_DUAL_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.LONGSWORD_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(AVAnimations.DUAL_DANCING_EDGE, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                            .animationBehavior(AVAnimations.DUAL_SWORD_DANCING_EDGE, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_DUAL_DASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.SWORD_DUAL_AIR_SLASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.DANCING_EDGE, 0.0F)
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
                                            .animationBehavior(AVAnimations.KICK_H, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.5D, 2.0D)
                                            .custom(PlayerNpcCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            );
}
