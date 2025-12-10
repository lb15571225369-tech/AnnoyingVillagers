package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.DealDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;

public class WoopieTheSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("5a6ceb12-eacb-49c6-8030-37942b192e1d");
    public WoopieTheSwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.RUSH_SWORD, 0.0F);
        super.executeOnServer(skillContainer, friendlyByteBuf);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID,
                (DealDamageEvent.Damage event) -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                    AssetAccessor<? extends DynamicAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getAnimation();
                    if (dynamicAnimation == null || dynamicAnimation != AVAnimations.RUSH_SWORD) return;

                    if (container.getStack() < 1) {
                        WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) container.getSkill();
                        float currentResource = container.getResource();
                        float neededResource = container.getNeededResource();
                        woopieTheSwordSkill.setConsumptionSynchronize(container, currentResource + neededResource);
                    }
                },
                10
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
        super.onRemoved(container);
    }
}
