package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class BowSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("691f9c77-fba4-43b4-a893-03b9a990347b");

    public BowSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        return !player.isPassenger();
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
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    SkillContainer skillContainer = container.getExecutor().getSkill(AVSkills.BOW);
                    if (skillContainer == null) return;
                    Player player = skillContainer.getExecutor().getOriginal();

                    if (player.isPassenger() && player.getVehicle() != null && player.getVehicle() instanceof HerobrineDragonEntity) {
                        event.setCanceled(true);
                        skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.BOW_AUTO_1, 0.0F);
                    }
                }
        );
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (container.getStack() < 1) {
            container.setStack(1);
        }
    }
}
