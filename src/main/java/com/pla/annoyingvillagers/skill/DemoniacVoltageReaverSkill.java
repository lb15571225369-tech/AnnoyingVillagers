package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.util.SnakeBladeHit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class DemoniacVoltageReaverSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("a86b0713-5f98-4e04-9930-fee81f157780");

    public DemoniacVoltageReaverSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        super.executeOnServer(skillContainer, friendlyByteBuf);

        Player player = skillContainer.getExecutor().getOriginal();
        ItemStack item = player.getMainHandItem();

        skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.SNAKE_BLADE, 0.0F);
        if (SnakeBladeHit.process(item, player)) {
            item.getOrCreateTag().putBoolean("SnakeAnimation", true);
        }
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack stack = player.getMainHandItem();

        boolean isCorrectItem = stack.getItem() instanceof DemoniacVoltageReaverItem;
        boolean isSnaking = stack.hasTag() && stack.getTag() != null && stack.getTag().getBoolean("SnakeAnimation");
        boolean isActivated = container.isActivated();

        return isCorrectItem && !isSnaking && !isActivated && super.canExecute(container);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, (event) -> {
            Player player = container.getExecutor().getOriginal();
            ItemStack item = player.getMainHandItem();
            Skill skill = event.getSkillContainer().getSkill();

            if (skill.getCategory() == SkillCategories.GUARD){
                if (container.getExecutor() instanceof ServerPlayerPatch serverPlayerPatch
                        && container.getStack() >= 1
                        && item.getItem() instanceof DemoniacVoltageReaverItem
                        && item.getTag() != null) {
                    event.setCanceled(true);
                    container.getExecutor().playAnimationSynchronized(AVAnimations.SNAKE_BLADE, 0.0F);
                    if (!item.getTag().getBoolean("SnakeAnimation")) {
                        this.getResourceType().consumer
                                .consume(container, serverPlayerPatch, this.getDefaultConsumptionAmount(serverPlayerPatch));
                        if (!container.getExecutor().isLogicalClient() && SnakeBladeHit.processGuard(item, player)) {
                            item.getOrCreateTag().putBoolean("SnakeAnimation", true);
                        }
                    }
                }
            } else if ((skill.getCategory() == SkillCategories.BASIC_ATTACK) &&
                    (item.getItem() instanceof DemoniacVoltageReaverItem
                            && item.getTag() != null
                            && item.getTag().getBoolean("SnakeAnimation"))) {
                event.setCanceled(true);
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID);
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
                itemStack.getItem() instanceof DemoniacVoltageReaverItem
                && !itemStack.getTag().getBoolean("SnakeAnimation")
                && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof DemoniacVoltageReaverItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }
    }
}
