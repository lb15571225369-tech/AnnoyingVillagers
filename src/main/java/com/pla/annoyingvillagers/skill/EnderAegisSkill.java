package com.pla.annoyingvillagers.skill;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.StealthAttackEntity;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

import java.util.Objects;
import java.util.UUID;

public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");
    private static final int MAX_FORM_TICKS = 20 * 10;
    private static final String NBT_SECOND_FORM = "SecondForm";
    private static final int HIT_TRIGGER_COOLDOWN_TICKS = 10;
    private static final String NBT_HIT_CD = "ender_aegis_hit_cd";
    private static final String NBT_STACKS = "ender_aegis_stacks";
    private static final int READY_STACKS = 20;

    public EnderAegisSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    private static int getStacks(ServerPlayer p) {
        return Math.max(0, p.getPersistentData().getInt(NBT_STACKS));
    }

    private static void setStacks(ServerPlayer serverPlayer, int value) {
        serverPlayer.getPersistentData().putInt(NBT_STACKS, Math.max(0, Math.min(READY_STACKS, value)));
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkill.ENDER_AEGIS);
        if (skillContainer == null || skillContainer.isActivated()) return;

        ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
        int before = getStacks(serverPlayer);
        int after  = Math.min(READY_STACKS, before + 1);
        setStacks(serverPlayer, after);

        skillContainer.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), after);
        AnnoyingVillagers.LOGGER.info("[AV DEBUG] PARRY: +1 stack ({} -> {} / {})", before, after, READY_STACKS);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        ServerPlayer sp = ((ServerPlayerPatch) container.getExecutor()).getOriginal();

        if (container.isActivated()) {
            setSecondForm(container, false);
            setStacks(sp, 0);
            container.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), 0);
            this.cancelOnServer(container, args);
            container.deactivate();
            container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
            return;
        }

        int stacks = getStacks(sp);
        if (stacks < READY_STACKS) {
            return;
        }

        container.setMaxDuration(MAX_FORM_TICKS);
        this.setMaxDurationSynchronize(container, MAX_FORM_TICKS);
        container.setDuration(MAX_FORM_TICKS);
        this.setDurationSynchronize(container, MAX_FORM_TICKS);

        setSecondForm(container, true);
        setStacks(sp, 0);
        container.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), 0);

        this.setStackSynchronize(container, 1);
        container.activate();
        container.getServerExecutor().modifyLivingMotionByCurrentItem(false);

        SkillConsumeEvent consume = new SkillConsumeEvent(container.getExecutor(), this, this.resource, args);
        container.getExecutor().getEventListener().triggerEvents(EventType.SKILL_CONSUME_EVENT, consume);
        if (!consume.isCanceled()) {
            consume.getResourceType().consumer.consume(container, container.getServerExecutor(), consume.getAmount());
        }
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        setSecondForm(container, false);
        this.setStackSynchronize(container, Math.max(0, container.getStack() - 1));
        this.setDurationSynchronize(container, 0);
        container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
        super.cancelOnServer(container, args);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        if (!container.getExecutor().isLogicalClient()) {
            ServerPlayer sp = ((ServerPlayerPatch) container.getExecutor()).getOriginal();
            container.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), getStacks(sp));
        }

        container.getExecutor().getEventListener().addEventListener(
                EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) return;

                    ServerPlayer serverPlayer = event.getPlayerPatch().getOriginal();

                    if (!skillContainer.isActivated()) {
                        if (isThisInnateInMainHand(skillContainer) && getStacks(serverPlayer) >= READY_STACKS) {
                            try { event.setCanceled(true); } catch (Throwable ignored) {}
                            this.executeOnServer(skillContainer, null);
                        }
                        return;
                    }

                    try { event.setCanceled(true); } catch (Throwable ignored) {}
                    skillContainer.getExecutor().playAnimationSynchronized(WOMAnimations.BULL_CHARGE, 0.0F);
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) return;

                    ServerPlayer player = event.getPlayerPatch().getOriginal();

                    if (!skillContainer.isActivated()) {
                        if (!isThisInnateInMainHand(skillContainer)) {
                            return;
                        }
                        int before = getStacks(player);
                        int after  = Math.min(READY_STACKS, before + 1);
                        setStacks(player, after);
                        skillContainer.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), after);
                        return;
                    }

//                    if (EnderAegisSkill.getStacks(player) != 0) {
//                        EnderAegisSkill.setStacks(player, 0);
//                        skillContainer.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), 0);
//                    }

                    if (getStacks(player) != 0) {
                        setStacks(player, 0);
                        skillContainer.getDataManager().setDataSync(AVSkillDataKeys.ENDER_AEGIS_STACKS.get(), 0);
                    }

                    int coolDown = player.getPersistentData().getInt(NBT_HIT_CD);
                    if (coolDown > 0) {
                        return;
                    }

                    var target = event.getTarget();
                    if (target == null || !target.isAlive()) {
                        return;
                    }

                    shieldShootAtTarget(player, target.getEyePosition());
                    player.getPersistentData().putInt(NBT_HIT_CD, HIT_TRIGGER_COOLDOWN_TICKS);
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
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (!container.getExecutor().isLogicalClient()) {
            ServerPlayer serverPlayer = ((ServerPlayerPatch) container.getExecutor()).getOriginal();
            int coolDown = serverPlayer.getPersistentData().getInt(NBT_HIT_CD);
            if (coolDown > 0) serverPlayer.getPersistentData().putInt(NBT_HIT_CD, coolDown - 1);
        }


        if (!container.getExecutor().isLogicalClient() && container.isActivated()) {
            int remaining = container.getRemainDuration();
            if (remaining > 0) {
                this.setDurationSynchronize(container, remaining - 1);
            } else {
                setSecondForm(container, false);
                this.cancelOnServer(container, null);
                container.deactivate();
            }
        }
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerpatch, SkillCastEvent ev) {
        SkillConsumeEvent skillConsumeEvent = new SkillConsumeEvent(playerpatch, this, this.resource, null);
        playerpatch.getEventListener().triggerEvents(EventType.SKILL_CONSUME_EVENT, skillConsumeEvent);
        if (skillConsumeEvent.isCanceled()) return false;

        return playerpatch.getSkillContainerFor(this)
                .map(container -> skillConsumeEvent.getResourceType().predicate.canExecute(container, playerpatch, skillConsumeEvent.getAmount()))
                .orElse(false);
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        if (container.isActivated()) return true;
        if (container.getExecutor().isLogicalClient()) return super.canExecute(container);

        ServerPlayer serverPlayer = (ServerPlayer) container.getExecutor().getOriginal();
        ItemStack held = serverPlayer.getMainHandItem();
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(held);

        if (capabilityItem.getInnateSkill(container.getExecutor(), held) != this) {
            return false;
        }

        if (serverPlayer.getUseItem() != ItemStack.EMPTY) {
            return false;
        }

        int stacks = getStacks(serverPlayer);
        if (stacks < READY_STACKS) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isExecutableState(PlayerPatch<?> exec) {
        exec.updateEntityState();
        return !exec.getOriginal().isSleeping()
                && exec.getEntityState().canUseSkill()
                && exec.getEntityState().canBasicAttack();
    }

    private boolean isThisInnateInMainHand(SkillContainer skillContainer) {
        ServerPlayer serverPlayer = ((ServerPlayerPatch) skillContainer.getExecutor()).getOriginal();
        ItemStack held = serverPlayer.getMainHandItem();
        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(held);
        return cap.getInnateSkill(skillContainer.getExecutor(), held) == this;
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

    @OnlyIn(Dist.CLIENT)
    public void onScreen(LocalPlayerPatch playerPatch, float resolutionX, float resolutionY) {
        var container = playerPatch.getSkill(this);
        if (container == null) return;

        Integer stacks = container.getDataManager().getDataValue(
                AVSkillDataKeys.ENDER_AEGIS_STACKS.get()
        );
        if (stacks == null) stacks = 0;
        Minecraft minecraft = Minecraft.getInstance();

        String text;
        if (stacks >= READY_STACKS) text = "";
        else text = String.valueOf(stacks);
        int textWidth = minecraft.font.width(text);
        float x = resolutionX - textWidth - 8;
        float y = resolutionY - minecraft.font.lineHeight - 8;

        int color = (stacks >= READY_STACKS) ? 0x00FF00 : 0xFFFFFF;
        GuiGraphics guiGraphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
        guiGraphics.drawString(minecraft.font, text, (int)x, (int)y, color, true);
        guiGraphics.flush();
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
