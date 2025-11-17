package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.UUID;

public class ObsidianSledgeHammerSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");
    public ObsidianSledgeHammerSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    private void triggerCircleWhenGroundHits(ServerPlayerPatch serverPlayerPatch) {
        if (serverPlayerPatch.getOriginal() == null) return;

        Player player = serverPlayerPatch.getOriginal();
        if (!player.level().isClientSide()) {
            HerobrineWeaponEffectProcedure.execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
        }
        AnimationPlayer animationPlayer = serverPlayerPatch.getAnimator().getPlayerFor(null);
        if (animationPlayer == null) {
            new DelayedTask(1) {
                public void run(){
                    triggerCircleWhenGroundHits(serverPlayerPatch);
                }
            };
            return;
        }

        float elapsedRaw = getElapsedRaw(animationPlayer);
        if (elapsedRaw >= 0.58F) {
            serverPlayerPatch.playAnimationSynchronized(AVAnimations.SLEDGE_HAMMER_INNATE_DASH, 0.0F);
            if (player.getMainHandItem().getItem() instanceof ObsidianSledgehammerItem) {
                Vec3 tip = ObsidianSledgehammerItem.jointWorldPoint(
                        serverPlayerPatch, new Vec3f(0, 0, -1.1f),
                        Armatures.BIPED.get().toolR);

                BlockHitResult blockHitResult = ObsidianSledgehammerItem.raycastDown(
                        player.level(), tip.add(0, 0.25, 0), serverPlayerPatch, 8.0);

                if (blockHitResult != null && !player.level().isClientSide()) {
                    ObsidianSledgehammerItem.circleHit(player, blockHitResult);
                }
            }
            return;
        }

        new DelayedTask(1){
            @Override public void run(){
                triggerCircleWhenGroundHits(serverPlayerPatch);
            }
        };
    }

    private static float getElapsedRaw(AnimationPlayer animationPlayer) {
        try {
            return (float) animationPlayer.getClass().getMethod("getElapsedTime").invoke(animationPlayer);
        } catch (Exception e) {
            return 0f;
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
            serverPlayerPatch.playAnimationSynchronized(WOMAnimations.TORMENT_DASH, 0.0F);
            triggerCircleWhenGroundHits(serverPlayerPatch);
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
                itemStack.getItem() instanceof ObsidianSledgehammerItem && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof ObsidianSledgehammerItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }
    }
}
