package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineEnderAegis {
    public static final Builder<MobPatch<?>> ENDER_AEGIS = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(WOMAnimations.SHADOWSTEP_LEFT, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(WOMAnimations.SHADOWSTEP_RIGHT, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(WOMAnimations.SHADOWSTEP_BACKWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(WOMAnimations.SHADOWSTEP_FORWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(200)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(WOMAnimations.MOB_ENDERSTEP_OBSCURIS, 0.0F)
                                            .addExBehavior(HerobrineCommon::giveSlowFalling)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 6.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AVAnimations.SHIELD_MAINHAND, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AVAnimations.MOB_RAVANGER_CHARGE, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 3.0D)
                                                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 3.0D)
                                                                                            .animationBehavior(AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .withinDistance(0.0D, 3.0D)
                                                                                                            .animationBehavior(AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .withinDistance(0.0D, 3.0D)
                                                                                                                            .animationBehavior(AnimsSolar.SOLAR_QUEMADURA, 0.0F)
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
                            .weight(200)
                            .maxCooldown (40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AVAnimations.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AVAnimations.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AVAnimations.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsSolar.SOLAR_HORNO, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::canChangeToSecondForm)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AVAnimations.MOB_NAPOLEON_RELOAD_1, 0.0F)
                                            .addExBehavior(HerobrineCommon::changeToSecondForm)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_FORWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_LEFT, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
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
                                            .custom(HerobrineCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(HerobrineCommon::jump)
                            )
            );
}
