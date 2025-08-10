package com.pla.annoyingvillagers.compat.dual_greatsword.skill;

import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

public class DualGreatswordSkill extends WeaponInnateSkill {

    public DualGreatswordSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return null;
    }
}
