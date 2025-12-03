package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.shelmarow.combat_evolution.ai.BehaviorUtils;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.efcondition.HealthCheck;
import net.shelmarow.combat_evolution.iml.ILivingEntityData;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Random;

public class PlayerNpcCombatBehaviour {
    private static boolean canAttackWhileNotHealing(MobPatch<?> mobpatch) {
        PlayerNpcEntity playerNpcEntity = (PlayerNpcEntity) mobpatch.getOriginal();
        return !playerNpcEntity.isHealing();
    }

    private static boolean canPerformEating(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return false;
        }
        if (playerNpcEntity.getGapCooldown() > 0) {
            return false;
        }
        if (mobpatch instanceof ILivingEntityData entityData && entityData.combat_evolution$isGuard(playerNpcEntity)) {
            return false;
        }
        return !playerNpcEntity.isHealing();
    }

    private static boolean canThrowEnderPearl(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return false;
        }
        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (playerNpcEntity.isHealing()) {
            return false;
        }
        return playerNpcEntity.getEnderPearlCooldown() == 0;
    }

    private static void performEnderPearlToTarget(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }
        LivingEntity target = mobpatch.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        double dx = target.getX() - playerNpcEntity.getX();
        double dz = target.getZ() - playerNpcEntity.getZ();
        double dy = target.getEyeY() - playerNpcEntity.getEyeY();
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)(Mth.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
        float pitch = (float)(-(Mth.atan2(dy, horiz) * (180F / (float)Math.PI)));

        playerNpcEntity.setYRot(yaw);
        playerNpcEntity.setXRot(pitch);
        playerNpcEntity.setYBodyRot(yaw);
        playerNpcEntity.setYHeadRot(yaw);

        playerNpcEntity.yRotO = yaw;
        playerNpcEntity.xRotO = pitch;
        playerNpcEntity.yBodyRotO = yaw;
        playerNpcEntity.yHeadRotO = yaw;

        playerNpcEntity.setEnderPearlCooldown();
        CombatBehaviour.throwEnderPearl(playerNpcEntity, 0.0F);
    }

    private static void performEatingAnimation(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (!(entity instanceof PlayerNpcEntity playerNpcEntity)) {
            return;
        }
        BehaviorUtils.stopCurrentBehavior(playerNpcEntity);
        if (new Random().nextBoolean()) {
            CombatBehaviour.throwEnderPearl(entity, new Random().nextFloat(0.0F, 180.0F));
            playerNpcEntity.setEnderPearlCooldown();
        }
        playerNpcEntity.setGapCooldown();
        CombatBehaviour.eatingGoldenApple(
                playerNpcEntity,
                playerNpcEntity.level(),
                20.0D
        );
    }

    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .cooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(PlayerNpcCombatBehaviour::canPerformEating)
                                            .animationBehavior(Animations.BIPED_EAT, 0.0F)
                                            .addExBehavior(PlayerNpcCombatBehaviour::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(200.0D)
                            .cooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canThrowEnderPearl)
                                            .withinDistance(7.0D, 20.0D)
                                            .animationBehavior(Animations.BIPED_IDLE, 0.0F)
                                            .addExBehavior(PlayerNpcCombatBehaviour::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.FIST_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
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
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.FIST_LEFT, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.FIST_UP, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
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
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.WHIRLWIND_KICK_LEFT, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.WHIRLWIND_KICK, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_DASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.FIST_AIR_SLASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.RELENTLESS_COMBO, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_1, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_3, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_4, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_C, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .canInterruptParent(true)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_RUSH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
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
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canAttackWhileNotHealing)
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            );

    public static final Builder<MobPatch<?>> AXE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .cooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(PlayerNpcCombatBehaviour::canPerformEating)
                                            .animationBehavior(Animations.BIPED_EAT, 0.0F)
                                            .addExBehavior(PlayerNpcCombatBehaviour::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(200.0D)
                            .cooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(PlayerNpcCombatBehaviour::canThrowEnderPearl)
                                            .withinDistance(7.0D, 20.0D)
                                            .animationBehavior(Animations.BIPED_IDLE, 0.0F)
                                            .addExBehavior(PlayerNpcCombatBehaviour::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.AXE_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(Animations.AXE_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.AXE_HEAVY_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 2.0D)
                                                            .animationBehavior(AVAnimations.AXE_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 2.0D)
                                                                            .animationBehavior(AVAnimations.AXE_FUN_SKILL, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.AXE_DASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.AXE_AIRSLASH, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
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
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_1, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_3, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .cooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AVAnimations.KICK_4, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
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
                            .weight(2.0D)
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
                            .weight(2.0D)
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
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            );
}
