package com.pla.annoyingvillagers.skill;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Objects;
import java.util.UUID;

public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");
    private static final String NBT_SECOND_FORM = "SecondForm";
    private static final int HIT_TRIGGER_COOLDOWN_TICKS = 10;
    private static final String NBT_HIT_CD = "ender_aegis_hit_cd";

    public EnderAegisSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkill.ENDER_AEGIS);
        if (skillContainer == null) return;

        if (!skillContainer.isActivated()) {
            EnderAegisSkill skill = (EnderAegisSkill) skillContainer.getSkill();
            skill.setStackSynchronize(skillContainer, skillContainer.getStack() + 1);
        }
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        super.executeOnServer(skillContainer, friendlyByteBuf);
        setSecondForm(skillContainer, true);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) return;
                    if (skillContainer.isActivated()) {
                        try {
                            event.setCanceled(true);
                        } catch (Throwable ignored) {
                        }
                        skillContainer.getExecutor().playAnimationSynchronized(WOMAnimations.BULL_CHARGE, 0.0F);
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
                        int coolDown = player.getPersistentData().getInt(NBT_HIT_CD);
                        if (coolDown > 0) {
                            return;
                        }

                        LivingEntity target = event.getTarget();
                        if (target == null || !target.isAlive()) {
                            return;
                        }

                        shieldShootAtTarget(player, target.getEyePosition());
                        player.getPersistentData().putInt(NBT_HIT_CD, HIT_TRIGGER_COOLDOWN_TICKS);
                    }
                }
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        setSecondForm(container, false);
        container.getExecutor().getEventListener().removeListener(EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
    }

    @Override
    public void updateContainer(SkillContainer skillContainer) {
        super.updateContainer(skillContainer);

        if (!skillContainer.getExecutor().isLogicalClient()) {
            ServerPlayer serverPlayer = ((ServerPlayerPatch) skillContainer.getExecutor()).getOriginal();
            int coolDown = serverPlayer.getPersistentData().getInt(NBT_HIT_CD);
            if (coolDown > 0) serverPlayer.getPersistentData().putInt(NBT_HIT_CD, coolDown - 1);
        }

        if (!skillContainer.getExecutor().isLogicalClient() && skillContainer.isActivated()) {
            int remaining = skillContainer.getRemainDuration();
            if (remaining > 0) {
                this.setDurationSynchronize(skillContainer, remaining - 1);
            } else {
                setSecondForm(skillContainer, false);
                this.cancelOnServer(skillContainer, null);
                skillContainer.deactivate();
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        setSecondForm(skillContainer, false);
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    private void setSecondForm(SkillContainer container, boolean value) {
        if (container.getExecutor().isLogicalClient()) return;

        ServerPlayer serverPlayer = ((ServerPlayerPatch) container.getExecutor()).getOriginal();
        ItemStack itemStack = serverPlayer.getMainHandItem();
        if (itemStack.isEmpty()) return;

        ItemStack copy = itemStack.copy();
        copy.getOrCreateTag().putBoolean(NBT_SECOND_FORM, value);

        int slot = serverPlayer.getInventory().selected;
        serverPlayer.getInventory().setItem(slot, copy);
        serverPlayer.inventoryMenu.broadcastChanges();
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
                stealth.setBaseDamage(8.0D);
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
                        "execute as @s at @s anchored eyes run particle annoyingvillagers:spark ^ ^1 ^2 0 0 0 0.1 2000",
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
