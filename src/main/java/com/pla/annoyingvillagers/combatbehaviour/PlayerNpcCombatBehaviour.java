package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Random;

public class PlayerNpcCombatBehaviour {
    public static final Builder<MobPatch<?>> AXE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.AXE_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.AXE_AUTO2, 0.0F)
                                            )
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            )
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                            )
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.AXE_DASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.AXE_AIRSLASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.AXE_HEAVY_AUTO_1, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.AXE_HEAVY_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.AXE_FUN_SKILL, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.THE_GUILLOTINE, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .canInterruptParent(true)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.KICK_2, 0.0F)
                                            )
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .canInterruptParent(true)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.KICK_3, 0.0F)
                                            )
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .canInterruptParent(true)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.KICK_4, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_RUSH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
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
                            .weight(10.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_C, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(50.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.5D, 2.0D)
                                            .guard(new Random().nextInt(60, 200))
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .guard(new Random().nextInt(60, 200))
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.5D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .guard(new Random().nextInt(60, 200))
                                            )
                            )
            );
}
