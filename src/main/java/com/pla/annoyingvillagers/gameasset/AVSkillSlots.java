package com.pla.annoyingvillagers.gameasset;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum AVSkillSlots implements SkillSlot {

    AV_KICK(AVSkillCategories.AV_KICK),
    AV_STUN_ESCAPE(AVSkillCategories.AV_STUN_ESCAPE);

    final AVSkillCategories category;
    final int id;

    AVSkillSlots(AVSkillCategories avSkillCategories) {
        this.category = avSkillCategories;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    public SkillCategory category() {
        return this.category;
    }

    public int universalOrdinal() {
        return this.id;
    }
}
