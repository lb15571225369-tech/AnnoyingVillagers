package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineDemoniacVoltageReaver {
    public static final Builder<MobPatch<?>> DEMONIAC_VOLTAGE_REAVER = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(WOMAnimations.MOB_ENDERSTEP_OBSCURIS, 0.0F)
                                            .addExBehavior(HerobrineCommon::giveSlowFalling)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AVAnimations.DEMONIAC_RUINE_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 3.0D)
                                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 3.0D)
                                                                                            .animationBehavior(AVAnimations.DEMONIAC_RUINE_AUTO_2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 3.0D)
                                                                                                            .animationBehavior(AVAnimations.DEMONIAC_RUINE_AUTO_4, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AVAnimations.DEMONIAC_RUINE_AUTO_1, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canChangeToSecondForm)
                                            .withinDistance(0.0D, 6.0D)
                                            .animationBehavior(AVAnimations.APPLY_IMBUEMENT, 0.0F)
                                            .addExBehavior(HerobrineCommon::changeToSecondForm)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(5)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 6.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AVAnimations.MOB_SNAKE_BLADE, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormGuardAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 6.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AVAnimations.MOB_SNAKE_BLADE, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(HerobrineCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_FORWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_LEFT, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_RIGHT, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(HerobrineCommon::jump)
                            )
            );
}
