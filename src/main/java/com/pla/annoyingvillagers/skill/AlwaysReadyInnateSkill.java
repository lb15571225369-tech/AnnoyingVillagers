package com.pla.annoyingvillagers.skill;

import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

public class AlwaysReadyInnateSkill extends SimpleWeaponInnateSkill {
    public AlwaysReadyInnateSkill(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return true;
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerpatch, SkillCastEvent event) {
        return true;
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (!container.getExecutor().isLogicalClient() && container.getStack() < 1) {
            container.setStack(1);
        }
    }
}
