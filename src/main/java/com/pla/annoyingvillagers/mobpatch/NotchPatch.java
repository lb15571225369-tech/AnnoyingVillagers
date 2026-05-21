package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.NotchCombatBehaviour;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import reascer.wom.gameasset.WOMAnimations;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.EscapeUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.iml.CustomExecuteEntity;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public class NotchPatch extends CEHumanoidPatch implements CustomExecuteEntity {
    public NotchPatch() {
        super(Factions.NEUTRAL);
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, AVAnimations.FIST_GUARD);
        animator.addLivingAnimation(LivingMotions.IDLE, AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    protected void setWeaponMotions() {
        // Phase 0: Fist - Null style floating
        this.weaponLivingMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.FIST_GUARD),
                                        Pair.of(LivingMotions.IDLE, AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE),
                                        Pair.of(LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK),
                                        Pair.of(LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        // Phase 1: Sword - just idle holding, no attack animations
        this.weaponLivingMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.FIST_GUARD),
                                        Pair.of(LivingMotions.IDLE, AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE),
                                        Pair.of(LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK),
                                        Pair.of(LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        // Phase 0/2: Fist combat behavior
        this.weaponAttackMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(
                                Styles.TWO_HAND, NotchCombatBehaviour.PHASE_NULL
                        ));

        // Phase 1: Sword - no real attacks (PHASE_DIVINE has impossible conditions)
        this.weaponAttackMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NotchCombatBehaviour.PHASE_DIVINE
                        ));
    }

    public void playGuardBreakSound() {
        this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 0.0F, 0.0F);
    }

    public AttackResult attack(EpicFightDamageSource epicFightDamageSource, Entity entity, InteractionHand interactionhand) {
        return super.attack(epicFightDamageSource, entity, interactionhand);
    }

    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
    }

    public void onDeath(LivingDeathEvent livingDeathEvent) {
        super.onDeath(livingDeathEvent);
    }

    @Override
    public boolean isBlockableSource(DamageSource damageSource) {
        return true;
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        EscapeUtil.stepLeftRightOnHurtByDangerousAnimation(damageSource, this);
        return super.tryHurt(damageSource, amount);
    }

    public AnimationAccessor<? extends StaticAnimation> getHitAnimation(StunType stuntype) {
        return switch (stuntype) {
            case LONG -> Animations.BIPED_HIT_LONG;
            case SHORT, HOLD -> Animations.BIPED_HIT_SHORT;
            case KNOCKDOWN -> Animations.BIPED_KNOCKDOWN;
            case NEUTRALIZE -> Animations.BIPED_COMMON_NEUTRALIZED;
            case FALL -> Animations.BIPED_LANDING;
            default -> null;
        };
    }

    @Override
    public boolean canBeExecuted(LivingEntityPatch<?> livingEntityPatch) {
        return AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB.get();
    }

    @Override
    public boolean canUseCustomType(LivingEntityPatch<?> livingEntityPatch, ExecutionTypeManager.Type type) {
        return true;
    }

    @Override
    public ExecutionTypeManager.Type getExecutionType(LivingEntityPatch<?> livingEntityPatch, ExecutionTypeManager.Type type) {
        return type;
    }
}
