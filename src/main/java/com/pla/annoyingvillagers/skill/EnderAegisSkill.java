package com.pla.annoyingvillagers.skill;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Objects;
import java.util.UUID;

public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");

    public EnderAegisSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
        if (skillContainer == null) return;
        EnderAegisSkill enderAegisSkill = (EnderAegisSkill) skillContainer.getSkill();

        if (!skillContainer.isActivated() && skillContainer.getStack() < 1) {
            float currentResource = skillContainer.getResource();
            float neededResource = skillContainer.getNeededResource();
            float addResource = Math.min(20f, neededResource);
            enderAegisSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
        } else if (skillContainer.isActivated()) {
            enderAegisSkill.setDurationSynchronize(skillContainer, skillContainer.getRemainDuration() + 40);
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.getExecutor().playAnimationSynchronized(AnimsNapoleon.NAPOLEON_RELOAD_1, 0.0F);
        skillContainer.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
        if (skillContainer.isActivated()) {
            this.cancelOnServer(skillContainer, friendlyByteBuf);
            skillContainer.deactivate();
        } else {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        if (container.isActivated()) return true;
        return super.canExecute(container);
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
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) return;
                    if (skillContainer.isActivated()) {
                        event.setCanceled(true);
                        skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.ENDER_AEGIS_BULL_CHARGE, 0.0F);
                    }
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) return;

                    ServerPlayer player = event.getPlayerPatch().getOriginal();

                    if (skillContainer.isActivated()) {
                        LivingEntity target = event.getTarget();
                        if (target == null || !target.isAlive()) {
                            return;
                        }
                        shieldShootAtTarget(player, target.getEyePosition());
                    }
                }
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
    }

    private void shieldShootAtTarget(ServerPlayer player, Vec3 targetPos) {
        Level level = player.level();
        Vec3 eye = player.getEyePosition();
        Vec3 dir = targetPos.subtract(eye);
        double len2 = dir.lengthSqr();
        if (len2 < 1.0e-6) return;
        dir = dir.normalize();

        if (!level.isClientSide()) {
            for (float speed = 1.0F; speed <= 5.0F; speed += 1.0F) {
                StealthAttackEntity stealth = new StealthAttackEntity(AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), level);
                stealth.setOwner(player);
                stealth.setBaseDamage(4.0D);
                stealth.setKnockback(5);
                stealth.setSilent(true);
                stealth.setPierceLevel((byte) 5);
                stealth.fromAegis = true;
                stealth.setPos(eye.x, eye.y - 0.1D, eye.z);
                stealth.shoot(dir.x, dir.y, dir.z, speed, 0.0F);
                level.addFreshEntity(stealth);
            }

            try {
                player.getServer().getCommands().getDispatcher().execute(
                        "execute as @s at @s anchored eyes run particle annoyingvillagers:spark ^ ^1 ^2 0 0 0 0.1 200",
                        player.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException ignored) {
            }

            BlockPos pos = player.blockPosition();
            level.playSound(null, pos, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cooldown"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
            level.playSound(null, pos, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
            level.playSound(null, pos, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bloom"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }
}
