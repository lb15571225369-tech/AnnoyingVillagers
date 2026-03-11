package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;

public class TridentFestivalSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("10cefa54-8fee-4627-a321-64a1a6388a25");

    public boolean isRangedMode(SkillContainer container) {
        return container.getDataManager().getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());
    }

    public boolean isMeleeMode(SkillContainer container) {
        return !this.isRangedMode(container);
    }

    public void toggleMode(SkillContainer container) {
        SkillDataManager data = container.getDataManager();
        boolean current = data.getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());
        data.setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get(), !current);
    }

    public TridentFestivalSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(Animations.WRATHFUL_LIGHTING, 0.0F);
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
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get(), false);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) {
                        return;
                    }

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) {
                        return;
                    }

                    if (this.isRangedMode(skillContainer)) {
                        event.setCanceled(true);

                        final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                        AssetAccessor<? extends StaticAnimation> dynamicAnimation =
                                Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

                        if (dynamicAnimation != null && dynamicAnimation == AVAnimations.TRIDENT_THROW_1) {
                            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_THROW_2, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AVAnimations.TRIDENT_THROW_2) {
                            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_THROW_3, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AVAnimations.TRIDENT_THROW_3) {
                            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_THROW_4, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AVAnimations.TRIDENT_THROW_4) {
                            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_THROW_5, 0.0F);
                        } else {
                            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.TRIDENT_THROW_1, 0.0F);
                        }
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return super.getSkillTexture();
    }
}
