package com.pla.annoyingvillagers.animations.types;

import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StateSpectrum;
import yesman.epicfight.api.animation.types.StateSpectrum.Blueprint;
import yesman.epicfight.api.model.Armature;

public class ExecuteAnimation extends AttackAnimation {

    protected final Blueprint stateUtilsBlueprint = new Blueprint();
    private final StateSpectrum stateUtils = new StateSpectrum();
    protected final float attackstart;
    protected final float attackwinopen;
    protected final float attackwinclose;
    protected final float skillwinopen;
    protected final float skillwinclose;
    protected final float attackend;
    protected final float lockon;
    protected final float lockoff;

    public ExecuteAnimation(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7, float f8, String s, Armature armature, Phase... aphase) {
        super(f, s, armature, aphase);
        this.attackstart = f1;
        this.attackwinopen = f3;
        this.attackwinclose = f4;
        this.skillwinopen = f5;
        this.skillwinclose = f6;
        this.lockon = f7;
        this.lockoff = f8;
        this.attackend = f2;
        this.stateUtilsBlueprint.clear();
        int i = aphase.length;

        this.addProperty(ActionAnimationProperty.STOP_MOVEMENT, true);
    }

    protected void bindPhaseState(Phase phase) {
        float f = phase.preDelay;

        this.stateSpectrumBlueprint.newTimePair(this.attackstart, this.attackwinopen).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.contact, phase.recovery).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, f).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.end + 1.0F).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, true).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).newTimePair(f, phase.contact + 0.01F).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact + 0.01F, phase.end).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true).newTimePair(this.attackwinclose, this.skillwinclose).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true).newTimePair(0.0F, Float.MAX_VALUE);
    }
}
