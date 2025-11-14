package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class DemoniacVoltageReaverSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("a86b0713-5f98-4e04-9930-fee81f157780");

    public DemoniacVoltageReaverSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsAgony.AGONY_AUTO_1, 0.0F);

            Player player = skillContainer.getExecutor().getOriginal();
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() instanceof DemoniacVoltageReaverItem) {
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (container.getStack() == 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof DemoniacVoltageReaverItem && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof DemoniacVoltageReaverItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
    }
}
