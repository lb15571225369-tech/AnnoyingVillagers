package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

import java.util.UUID;

public class EnderSlayerScytheSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");
    public EnderSlayerScytheSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
            Player player = serverPlayerPatch.getOriginal();
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel serverLevel) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : executeOnServer implement dragon shoot calling");
                if (player.getPersistentData().contains("DragonUUID")) {
                    Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                    LivingEntity target = player.getLastHurtMob();
                    if (target == null || !target.isAlive()) {
                        target = player.getLastHurtByMob();
                    }
                    if (target == null || !target.isAlive()) {
                        target = HerobrineDragonEntity.getNearestLivingEntity(player.level(), player, 40.0D);
                    }
                    if (entity instanceof HerobrineDragonEntity herobrineDragonEntity && target != null && target.isAlive()) {
                        serverPlayerPatch.playAnimationSynchronized(AVAnimations.CASTING_ONE_HAND_TOP, 0.0F);
                        herobrineDragonEntity.shootThunderBreathAtTarget(target);
                    }
                }
            }
        }
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
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (container.getExecutor().isLogicalClient()) return;
        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        SkillContainer skillContainer = serverPlayerPatch.getSkill(this);
        if ((skillContainer == null || skillContainer != container) && player.getPersistentData().contains("DragonUUID")
                && player.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
            if (entity instanceof HerobrineDragonEntity herobrineDragonEntity) {
                herobrineDragonEntity.discard();
            }
        }

        if (player.tickCount % 5 == 0 && player.level() instanceof ServerLevel serverLevel) {
            if (itemStack.getItem() instanceof EnderSlayerScytheItem && player.level() instanceof ServerLevel && player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer dragon already spawned, check if dragon is not null");
                Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                if (!(entity instanceof HerobrineDragonEntity)) {
                    HerobrineDragonEntity herobrineDragonEntity = spawnBabyEnderDragon(player, serverLevel);
                    if (herobrineDragonEntity != null) {
                        player.getPersistentData().putUUID("DragonUUID", herobrineDragonEntity.getUUID());
                    }
                }
            } else if (itemStack.getItem() instanceof EnderSlayerScytheItem && !player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer should spawn dragon here");
                HerobrineDragonEntity herobrineDragonEntity = spawnBabyEnderDragon(player, serverLevel);
                if (herobrineDragonEntity != null) {
                    player.getPersistentData().putUUID("DragonUUID", herobrineDragonEntity.getUUID());
                }
            } else if (!(itemStack.getItem() instanceof EnderSlayerScytheItem) && player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer remove dragon tag and despawn here ?");
                Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                if (entity instanceof HerobrineDragonEntity herobrineDragonEntity) {
                    herobrineDragonEntity.discard();
                }
                player.getPersistentData().remove("DragonUUID");
            }
        }
    }

    private static Vec3 posBehind3D(Player p, double back, double up, double right) {
        Vec3 look = p.getLookAngle().normalize();
        Vec3 forwardXZ = new Vec3(look.x, 0, look.z);
        if (forwardXZ.lengthSqr() < 1e-6) forwardXZ = new Vec3(-Mth.sin(p.getYRot() * ((float)Math.PI/180F)), 0, Mth.cos(p.getYRot() * ((float)Math.PI/180F)));
        forwardXZ = forwardXZ.normalize();
        Vec3 rightVec = new Vec3(-forwardXZ.z, 0, forwardXZ.x);
        return p.position()
                .subtract(look.scale(back))
                .add(0, up, 0)
                .add(rightVec.scale(right));
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
