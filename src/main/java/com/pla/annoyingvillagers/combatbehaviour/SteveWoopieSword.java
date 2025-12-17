package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.util.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.efcondition.HealthCheck;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsSatsujin;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class SteveWoopieSword {
    static void woopieWindup(MobPatch<?> mobpatch) {
        SteveEntity steveEntity = (SteveEntity) mobpatch.getOriginal();
        ItemStack itemStack = steveEntity.getMainHandItem();
        if (itemStack.getItem() instanceof WoopieTheSwordItem) {
            new DelayedTask(6) {
                @Override
                public void run() {
                    Vec3 windPos = EpicfightUtil.getJointWithTranslation(
                            steveEntity,
                            new Vec3f(0.0F, 0.0F, 0.0F),
                            Armatures.BIPED.get().toolR,
                            5.3F,
                            0.5F
                    );
                    if (windPos != null) {
                        BlockPos mutePos = BlockPos.containing(windPos);
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> steveEntity),
                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                        );
                        steveEntity.level().explode(steveEntity, windPos.x, windPos.y, windPos.z,
                                2.0F, false, Level.ExplosionInteraction.NONE);
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> steveEntity),
                                new ClientboundWoopieSwordWindFx(windPos)
                        );
                    }
                }
            };
        }
    }

    public static final Builder<MobPatch<?>> WOOPIE_THE_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(80.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AVAnimations.THROWING_ENDER_PEARL_OFFHAND, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
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
                                                                                            .animationBehavior(AnimsSatsujin.SATSUJIN_AUTO_1, 0.0F).addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .withinDistance(0.0D, 2.0D)
                                                                                                            .animationBehavior(AnimsSatsujin.SATSUJIN_AUTO_2, 0.0F)
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
                            .weight(40.0D)
                            .maxCooldown (40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(2.0D, 14.0D)
                                            .animationBehavior(AVAnimations.RUSH_SWORD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown (40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AnimsHerrscher.HERRSCHER_AUTO_2, 0.0F)
                                            .addExBehavior(SteveWoopieSword::woopieWindup)
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
                                            .animationBehavior(AnimsHerrscher.HERRSCHER_VERDAMMNIS, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(AnimsSatsujin.SATSUJIN_TSUKUYOMI, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .animationBehavior(AVAnimations.THROWING_ENDER_PEARL_OFFHAND, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlAway)
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
                                            .custom(CombatCommon::canPerformGuarding)
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
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 2.0D)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(CombatCommon::jump)
                            )
            );
}
