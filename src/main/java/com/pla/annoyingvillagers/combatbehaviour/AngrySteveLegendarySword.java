package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.LegendarySwordItem;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class AngrySteveLegendarySword {
    static void legendarySwordHeavyAttack(MobPatch<?> mobpatch) {
        AngrySteveEntity steveEntity = (AngrySteveEntity) mobpatch.getOriginal();
        ItemStack itemStack = steveEntity.getMainHandItem();
        if (itemStack.getItem() instanceof LegendarySwordItem && steveEntity.level() instanceof ServerLevel serverLevel) {
            steveEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
            serverLevel.playSound(
                    null,
                    steveEntity.getX(), steveEntity.getY(), steveEntity.getZ(),
                    AnnoyingVillagersModSounds.STEVE_ATTACK.get(),
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F
            );
            new DelayedTask(10) {
                @Override
                public void run() {
                    serverLevel.playSound(
                            null,
                            steveEntity.getX(), steveEntity.getY(), steveEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_START.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.playSound(
                            null,
                            steveEntity.getX(), steveEntity.getY(), steveEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.playSound(
                            null,
                            steveEntity.getX(), steveEntity.getY(), steveEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            steveEntity.getX(), steveEntity.getY(), steveEntity.getZ(),
                            15,
                            0.0D, 0.0D, 0.0D,
                            0.2D);

                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            steveEntity.getX(), steveEntity.getEyeY(), steveEntity.getZ(),
                            100,
                            0.0D, 0.0D, 0.0D,
                            0.5D
                    );
                    mobpatch.playAnimationSynchronized(AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK, 0.0F);
                }
            };
        }
    }

    static void legendarySwordSpecialAttack(MobPatch<?> mobpatch) {
        AngrySteveEntity steveEntity = (AngrySteveEntity) mobpatch.getOriginal();
        ItemStack itemStack = steveEntity.getMainHandItem();
        if (itemStack.getItem() instanceof LegendarySwordItem && steveEntity.level() instanceof ServerLevel) {
            steveEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
            new DelayedTask(20) {
                @Override
                public void run() {
                    mobpatch.playAnimationSynchronized(AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
                }
            };
        }
    }

    static void blockProjectile(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof PathfinderMobInventory pathfinderMobInventory && entity.level() instanceof ServerLevel serverLevel) {
            Entity projectile = pathfinderMobInventory.getBlockDamage();

            int defenderGroundY = entity.blockPosition().below().getY();
            BlockPos projXZ = BlockPos.containing(projectile.getX(), 0.0, projectile.getZ());
            int projSurfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, projXZ).getY();

            if (Math.abs(projSurfaceY - defenderGroundY) <= 1) {
                pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.COBBLESTONE));
                int topY = Mth.floor(projectile.getY());
                int maxHeight = 3;
                for (int y = projSurfaceY + 1; y <= topY && (y - projSurfaceY) <= maxHeight; y++) {
                    BlockPos p = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                    if (serverLevel.getBlockState(p).canBeReplaced()) {
                        mobpatch.playAnimationSynchronized(AVAnimations.PLACE_BLOCK, 0.0F);
                        entity.playSound(SoundEvents.STONE_PLACE, 1.0F, 1.0F);
                        serverLevel.setBlockAndUpdate(p, Blocks.COBBLESTONE.defaultBlockState());
                    } else {
                        break;
                    }
                }
                pathfinderMobInventory.setItemInHand(InteractionHand.MAIN_HAND, pathfinderMobInventory.getMainWeaponItem());
            } else {
                mobpatch.playAnimationSynchronized(WOMAnimations.TORMENT_AUTO_1, 0.0F);
            }
            pathfinderMobInventory.setBlockDamage(null);
        }
    }

    public static final Builder<MobPatch<?>> LEGENDARY_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canBlockProjectile)
                                            .animationBehavior(AVAnimations.IDLE_BREAK, 0.0F)
                                            .addExBehavior(AngrySteveLegendarySword::blockProjectile)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
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
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AnimsSolar.SOLAR_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                            .animationBehavior(AnimsSolar.SOLAR_AUTO_4, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(AVAnimations.YELLOW_SOLAR_AUTO_2, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F).addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(AVAnimations.YELLOW_NAPOLEON_AUTO_3, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )

                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AnimsSolar.SOLAR_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                            .animationBehavior(AnimsSolar.SOLAR_AUTO_4, 0.0F)
                                                                            )
                                                            )

                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsSolar.SOLAR_AUTO_4, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AVAnimations.YELLOW_SOLAR_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F).addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                            .animationBehavior(AVAnimations.YELLOW_NAPOLEON_AUTO_3, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_DASH, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsAgony.AGONY_RISING_EAGLE, 0.0F)
                                            .addExBehavior(AngrySteveLegendarySword::legendarySwordHeavyAttack)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT, 0.0F)
                                            .addExBehavior(AngrySteveLegendarySword::legendarySwordSpecialAttack)
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
