package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.entity.DragonMeteoriteEntity;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

import java.util.UUID;

public class EnderSlayerScytheSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");

    public EnderSlayerScytheSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    private static Vec3 findOrbitSpawnPos(ServerLevel level, Player player, HerobrineDragonEntity dragon,
                                          float rMin, float rMax) {
        RandomSource rng = level.getRandom();

        for (int i = 0; i < 24; i++) {
            double ang = rng.nextDouble() * (Math.PI * 2.0);
            double r = Mth.nextDouble(rng, rMin, rMax);

            double x = player.getX() + Math.cos(ang) * r;
            double z = player.getZ() + Math.sin(ang) * r;

            BlockPos col = BlockPos.containing(x, 0.0, z);
            int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();

            double y = Math.max(player.getY() + 12.0, groundY + 18.0);
            y += Mth.nextDouble(rng, -2.0, 6.0);

            y = Mth.clamp(y, level.getMinBuildHeight() + 6.0, level.getMaxBuildHeight() - 6.0);

            dragon.moveTo(x, y, z, rng.nextFloat() * 360.0F, 0.0F);
            AABB box = dragon.getBoundingBox();

            if (level.noCollision(dragon, box) && !level.containsAnyLiquid(box)) {
                return new Vec3(x, y, z);
            }
        }

        return player.position().add(0.0, 20.0, 0.0);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.AGONY_GUARD_HIT_1, 0.0F);
            skillContainer.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
        }
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
                    ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
                    Player player = serverPlayerPatch.getOriginal();

                    if (skillContainer.isActivated()) {
                        event.setCanceled(true);
                        if (event.getPlayerPatch().getOriginal().getCooldowns().getCooldownPercent(itemStack.getItem(), 0) == 0
                                && itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel serverLevel
                                && player.getPersistentData().contains("DragonUUID")) {
                            Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                            LivingEntity target = player.getLastHurtMob();
                            if (target == null || !target.isAlive()) {
                                target = player.getLastHurtByMob();
                            }
                            if (target == null || !target.isAlive()) {
                                target = HerobrineDragonEntity.getNearestLivingEntity(player.level(), player, 40.0D);
                            }
                            if (entity instanceof HerobrineDragonEntity herobrineDragonEntity && target != null && target.isAlive()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_TOP, 0.0F);
                                LivingEntity finalTarget = target;
                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        herobrineDragonEntity.shootThunderBreathAtTarget(finalTarget);
                                        ItemCooldowns cooldowns = event.getPlayerPatch().getOriginal().getCooldowns();
                                        cooldowns.addCooldown(itemStack.getItem(), 120);
                                    }
                                };
                            }
                        }
                    } else if (!skillContainer.isActivated() && player.isPassenger() && player.getVehicle() != null && player.getVehicle() instanceof HerobrineDragonEntity) {
                        event.setCanceled(true);
                        skillContainer.getExecutor().playAnimationSynchronized(AnimsAgony.AGONY_AUTO_1, 0.0F);
                    }
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID, (event) -> {
                    if (event.getPlayerPatch().isLogicalClient()) return;
                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    ItemStack itemStack = event.getPlayerPatch().getOriginal().getMainHandItem();
                    ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
                    Player player = serverPlayerPatch.getOriginal();
                    Skill skill = event.getSkillContainer().getSkill();

                    if (skillContainer.isActivated()
                            && itemStack.getTag() != null && skill.getCategory() == SkillCategories.GUARD) {
                        event.setCanceled(true);
                        if (event.getPlayerPatch().getOriginal().getCooldowns().getCooldownPercent(itemStack.getItem(), 0) == 0
                                && itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel serverLevel
                                && player.getPersistentData().contains("DragonUUID")) {
                            Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                            LivingEntity target = player.getLastHurtMob();
                            if (target == null || !target.isAlive()) {
                                target = player.getLastHurtByMob();
                            }
                            if (target == null || !target.isAlive()) {
                                target = HerobrineDragonEntity.getNearestLivingEntity(player.level(), player, 40.0D);
                            }
                            if (entity instanceof HerobrineDragonEntity herobrineDragonEntity && target != null && target.isAlive()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_BUFF, 0.0F);
                                LivingEntity finalTarget = target;
                                new DelayedTask(10) {
                                    @Override
                                    public void run() {
                                        herobrineDragonEntity.shootMeteoriteAtTarget(finalTarget);
                                        ItemCooldowns cooldowns = event.getPlayerPatch().getOriginal().getCooldowns();
                                        cooldowns.addCooldown(itemStack.getItem(), 40);
                                    }
                                };
                            }
                        }
                    }
                });
    }

    @Override
    public boolean resourcePredicate(PlayerPatch<?> playerpatch, SkillCastEvent event) {
        return true;
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnServer(container, args);
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
    public boolean canExecute(SkillContainer container) {
        ItemStack itemstack = container.getExecutor().getOriginal().getMainHandItem();

        return EpicFightCapabilities.getItemStackCapability(itemstack).getInnateSkill(container.getExecutor(), itemstack) == this
                && (container.getExecutor().getOriginal().getVehicle() == null
                || (container.getExecutor().getOriginal().getVehicle() != null && container.getExecutor().getOriginal().getVehicle() instanceof HerobrineDragonEntity))
                && (!this.isActivated(container) || this.activateType == ActivateType.TOGGLE);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_CAST_EVENT, EVENT_UUID);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().isLogicalClient()) return;

        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        Player player = serverPlayerPatch.getOriginal();
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        if (player.tickCount % 5 != 0) return;

        ItemStack main = player.getMainHandItem();
        if (!(main.getItem() instanceof EnderSlayerScytheItem)) {
            return;
        }

        if (!player.getPersistentData().contains("DragonUUID")) {
            HerobrineDragonEntity herobrineDragonEntity = spawnBabyEnderDragon(player, serverLevel);
            if (herobrineDragonEntity != null)
                player.getPersistentData().putUUID("DragonUUID", herobrineDragonEntity.getUUID());
        }
    }

    private HerobrineDragonEntity spawnBabyEnderDragon(Player player, ServerLevel serverLevel) {
        if (!player.isAlive()) return null;
        HerobrineDragonEntity herobrineDragonEntity = new HerobrineDragonEntity(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), serverLevel);
        Vec3 spawnPos = findOrbitSpawnPos(serverLevel, player, herobrineDragonEntity, 20.0F, 50.0F);
        herobrineDragonEntity.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
        herobrineDragonEntity.setSummoner(player);
        herobrineDragonEntity.setSummonerUUID(player.getUUID());
        serverLevel.addFreshEntity(herobrineDragonEntity);
        return herobrineDragonEntity;
    }
}
