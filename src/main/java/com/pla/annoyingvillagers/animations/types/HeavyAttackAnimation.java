package com.pla.annoyingvillagers.animations.types;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

public class HeavyAttackAnimation extends AttackAnimation {
    void init() {
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float mfloat = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, mfloat);
        }
        Set<JointMask> jointMaskSet = new HashSet<>();
        jointMaskSet.add(JointMask.of("Root", JointMask.KEEP_CHILD_LOCROT));
        jointMaskSet.add(JointMask.of("Torso"));
        jointMaskSet.add(JointMask.of("Chest"));
        jointMaskSet.add(JointMask.of("Head"));
        jointMaskSet.add(JointMask.of("Shoulder_R"));
        jointMaskSet.add(JointMask.of("Arm_R"));
        jointMaskSet.add(JointMask.of("Hand_R"));
        jointMaskSet.add(JointMask.of("Elbow_R"));
        jointMaskSet.add(JointMask.of("Tool_R"));
        jointMaskSet.add(JointMask.of("Shoulder_L"));
        jointMaskSet.add(JointMask.of("Arm_L"));
        jointMaskSet.add(JointMask.of("Hand_L"));
        jointMaskSet.add(JointMask.of("Elbow_L"));
        jointMaskSet.add(JointMask.of("Tool_L"));
        jointMaskSet.add(JointMask.of("Thigh_R"));
        jointMaskSet.add(JointMask.of("Leg_R"));
        jointMaskSet.add(JointMask.of("Knee_R"));
        jointMaskSet.add(JointMask.of("Thigh_L"));
        jointMaskSet.add(JointMask.of("Leg_L"));
        jointMaskSet.add(JointMask.of("Knee_L"));
        JointMaskEntry jointMaskEntry = JointMaskEntry.builder()
                .defaultMask(JointMask.JointMaskSet.of(jointMaskSet))
                .create();

        this.addProperty(ClientAnimationProperties.JOINT_MASK, jointMaskEntry);
    }

    public HeavyAttackAnimation(float f, float f1, float f2, float f3, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        this(f, f1, f1, f2, f3, collider, joint, s, armature);
    }

    public HeavyAttackAnimation(float f, float f1, float f2, float f3, float f4, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        super(f, f1, f2, f3, f4, collider, joint, s, armature);
        init();
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public HeavyAttackAnimation(float f, float f1, float f2, float f3, InteractionHand interactionhand, @Nullable Collider collider, Joint joint, String s, Armature armature) {
        super(f, f1, f1, f2, f3, interactionhand, collider, joint, s, armature);
        init();
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public HeavyAttackAnimation(float f, String s, Armature armature, Phase... aphase) {
        super(f, s, armature, aphase);
        init();
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    protected void bindPhaseState(Phase phase) {
        float f = phase.preDelay;

        if (f == 0.0F) {
            f += 0.01F;
        }

        this.stateSpectrumBlueprint.newTimePair(phase.start, f).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.contact + 0.01F).addState(EntityState.CAN_SKILL_EXECUTION, false).newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).newTimePair(f, phase.contact + 0.01F).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact + 0.01F, phase.end).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true);
    }

    public void end(LivingEntityPatch<?> livingentitypatch, DynamicAnimation dynamicanimation, boolean flag) {
        super.end(livingentitypatch, dynamicanimation, flag);
        boolean flag1 = ((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get();

        if (!flag && !dynamicanimation.isMainFrameAnimation() && livingentitypatch.isLogicalClient() && !flag1) {
            float f = 0.05F * this.getPlaySpeed(livingentitypatch, dynamicanimation);

            livingentitypatch.getClientAnimator().baseLayer.copyLayerTo(livingentitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), f);
        }

    }

    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> livingentitypatch, float f) {
        TypeFlexibleHashMap<StateFactor<?>> typeflexiblehashmap = super.getStatesMap(livingentitypatch, f);

        if (!((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get()) {
            typeflexiblehashmap.put(EntityState.MOVEMENT_LOCKED, Optional.of(false));
            typeflexiblehashmap.put(EntityState.UPDATE_LIVING_MOTION, Optional.of(true));
        }

        return typeflexiblehashmap;
    }

    protected Vec3 getCoordVector(LivingEntityPatch<?> livingentitypatch, DynamicAnimation dynamicanimation) {
        Vec3 vec3 = super.getCoordVector(livingentitypatch, dynamicanimation);

        if (livingentitypatch.shouldBlockMoving() && (Boolean) this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0D);
        }

        return vec3;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public boolean shouldPlayerMove(LocalPlayerPatch localplayerpatch) {
        return !localplayerpatch.isLogicalClient() || ((BooleanValue) ((LocalPlayer) localplayerpatch.getOriginal()).level().getGameRules().getRule(EpicFightGamerules.STIFF_COMBO_ATTACKS)).get() || ((LocalPlayer) localplayerpatch.getOriginal()).input.forwardImpulse == 0.0F && ((LocalPlayer) localplayerpatch.getOriginal()).input.leftImpulse == 0.0F;
    }
}
