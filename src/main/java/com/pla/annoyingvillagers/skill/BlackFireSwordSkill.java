package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import java.util.UUID;

public class BlackFireSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("efc63717-f05c-4f0a-afa5-8c17769766a4");
    public BlackFireSwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.BLACK_FIRE_SWORD_SKILL, 0.0F);
        super.executeOnServer(skillContainer, friendlyByteBuf);
    }
}
