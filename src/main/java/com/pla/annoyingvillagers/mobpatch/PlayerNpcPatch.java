package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;

import java.util.Set;

import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.iml.CustomExecuteEntity;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

public class PlayerNpcPatch extends CEHumanoidPatch implements CustomExecuteEntity {
    public PlayerNpcPatch() {
        super(Factions.NEUTRAL);
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.BIPED_BLOCK);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    protected void setWeaponMotions() {
        this.weaponLivingMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(Styles.ONE_HAND, PlayerNpcFist.FIST));

        this.weaponLivingMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(Styles.ONE_HAND, PlayerNpcFist.FIST));

        this.weaponLivingMotions
                .put(WeaponCategories.AXE,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.BIPED_BLOCK),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.AXE,
                        ImmutableMap.of(Styles.ONE_HAND, PlayerNpcAxe.AXE));

        this.weaponLivingMotions
                .put(AVCategories.AV_AXE,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.BIPED_BLOCK),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(AVCategories.AV_AXE,
                        ImmutableMap.of(Styles.ONE_HAND, PlayerNpcAxe.AV_AXE));

        this.weaponLivingMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_DUAL),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcSword.SWORD,
                                Styles.TWO_HAND, PlayerNpcSword.DUAL_SWORD
                        ));

        this.weaponLivingMotions
                .put(AVCategories.AV_SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.RUN, AVAnimations.RUN_HOLD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(AVCategories.AV_SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcSword.AV_SWORD,
                                Styles.TWO_HAND, PlayerNpcSword.AV_DUAL_SWORD
                        ));

        this.weaponAttackMotions
                .put(WeaponCategories.DAGGER,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcDagger.DAGGER,
                                Styles.TWO_HAND, PlayerNpcDagger.DUAL_DAGGER
                        ));
        this.weaponLivingMotions
                .put(WeaponCategories.DAGGER,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_DUAL),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));

        this.weaponAttackMotions
                .put(WeaponCategories.RANGED,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcBow.BOW
                        ));
        this.weaponLivingMotions
                .put(WeaponCategories.RANGED,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH),
                                        Pair.of(LivingMotions.AIM, Animations.BIPED_BOW_AIM),
                                        Pair.of(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
                                )));

        this.weaponLivingMotions
                .put(WeaponCategories.UCHIGATANA,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.UCHIGATANA,
                        ImmutableMap.of(
                                Styles.TWO_HAND, PlayerNpcUchigatana.UCHIGATANA
                        ));

        this.weaponLivingMotions
                .put(WeaponCategories.GREATSWORD,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.GREATSWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.GREATSWORD,
                        ImmutableMap.of(Styles.TWO_HAND, PlayerNpcGreatsword.GREATSWORD));

        this.weaponLivingMotions
                .put(AVCategories.AV_GREATSWORD,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(AVCategories.AV_GREATSWORD,
                        ImmutableMap.of(Styles.TWO_HAND, PlayerNpcGreatsword.AV_GREATSWORD));

        this.weaponAttackMotions
                .put(WeaponCategories.SPEAR,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcSpear.SPEAR_SHIELD,
                                Styles.TWO_HAND, PlayerNpcSpear.SPEAR
                        ));
        this.weaponLivingMotions
                .put(WeaponCategories.SPEAR,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.SHIELD_OFFHAND),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_SPEAR),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SPEAR_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_SPEAR),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_SPEAR),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        this.weaponAttackMotions
                .put(AVCategories.AV_SPEAR,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcSpear.AV_SPEAR_SHIELD,
                                Styles.TWO_HAND, PlayerNpcSpear.AV_SPEAR
                        ));
        this.weaponLivingMotions
                .put(AVCategories.AV_SPEAR,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.SHIELD_OFFHAND),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_SPEAR),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_SPEAR),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_SPEAR),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        this.weaponAttackMotions
                .put(WeaponCategories.LONGSWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcLongsword.LONGSWORD_SHIELD,
                                Styles.TWO_HAND, PlayerNpcLongsword.LONGSWORD
                        ));
        this.weaponLivingMotions
                .put(WeaponCategories.LONGSWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.SHIELD_OFFHAND),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        this.weaponAttackMotions
                .put(AVCategories.AV_LONGSWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, PlayerNpcLongsword.AV_LONGSWORD_SHIELD,
                                Styles.TWO_HAND, PlayerNpcLongsword.AV_LONGSWORD
                        ));
        this.weaponLivingMotions
                .put(AVCategories.AV_LONGSWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.SHIELD_OFFHAND),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        this.weaponAttackMotions
                .put(WeaponCategories.TACHI,
                        ImmutableMap.of(
                                Styles.TWO_HAND, PlayerNpcTachi.TACHI
                        ));
        this.weaponLivingMotions
                .put(WeaponCategories.TACHI,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));

        this.weaponAttackMotions
                .put(AVCategories.AV_TACHI,
                        ImmutableMap.of(
                                Styles.TWO_HAND, PlayerNpcTachi.AV_TACHI,
                                Styles.OCHS, PlayerNpcTachi.AV_DUAL_TACHI
                        ));
        this.weaponLivingMotions
                .put(AVCategories.AV_TACHI,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ),
                                Styles.OCHS,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
    }

    public void playGuardBreakSound() {
        this.playSound(EpicFightSounds.NEUTRALIZE_BOSSES.get(), 0.0F, 0.0F);
    }

    public AttackResult attack(EpicFightDamageSource epicFightDamageSource, Entity entity, InteractionHand interactionhand) {
        AttackResult attackresult = super.attack(epicFightDamageSource, entity, interactionhand);

        if (attackresult.resultType == ResultType.SUCCESS && entity.isAlive()) {
            // More logic when mob attack success
        }

        return attackresult;
    }

    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
    }

    public void onDeath(LivingDeathEvent livingDeathEvent) {
        super.onDeath(livingDeathEvent);
    }

    public void onAttackBlocked(DamageSource damageSource, LivingEntityPatch<?> livingEntityPatch) {
        // More logic when player block success
    }

    public void onAttackParried(DamageSource damageSource, LivingEntityPatch<?> livingEntityPatch) {
        // More logic when player parry success
    }

    @Override
    public void onGuardHit(DamageSource damageSource) {
        super.onGuardHit(damageSource);
        // More logic when blocking damage success
    }

    public AnimationAccessor<? extends StaticAnimation> getHitAnimation(StunType stuntype) {
        return switch (stuntype) {
            case LONG -> Animations.BIPED_HIT_LONG;
            case SHORT, HOLD -> Animations.BIPED_HIT_SHORT;
            case KNOCKDOWN -> Animations.BIPED_KNOCKDOWN;
            case NEUTRALIZE -> (
                    this.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD ?
                            Animations.GREATSWORD_GUARD_BREAK : Animations.BIPED_COMMON_NEUTRALIZED);
            case FALL -> Animations.BIPED_LANDING;
            default -> null;
        };
    }

    @Override
    public boolean canBeExecuted(LivingEntityPatch<?> livingEntityPatch) {
        return false;
    }

    @Override
    public ExecutionTypeManager.Type getExecutionType() {
        return null;
    }
}
