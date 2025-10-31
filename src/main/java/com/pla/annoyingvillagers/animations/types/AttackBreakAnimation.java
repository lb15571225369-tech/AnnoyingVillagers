package com.pla.annoyingvillagers.animations.types;

import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;

public class AttackBreakAnimation extends ActionAnimation {

    public AttackBreakAnimation(float transitionTime,
                                float postDelay,
                                String path,
                                AssetAccessor<? extends Armature> armatureAccessor) {
        super(transitionTime, postDelay, path, armatureAccessor);
        this.addProperty(ActionAnimationProperty.STOP_MOVEMENT, true);
        this.stateSpectrumBlueprint
                .clear()
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.INACTION, true)
                .addState(EntityState.HURT_LEVEL, 2);
    }
}
