package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class BowSkill extends WeaponInnateSkill {
    public BowSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return true;
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
        Player player = serverPlayerPatch.getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof BowItem) {
            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.BOW_AUTO_4, 0.0F);
            super.executeOnServer(skillContainer, friendlyByteBuf);
        }
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (container.getStack() < 1) {
            container.setStack(1);
        }
    }
}
