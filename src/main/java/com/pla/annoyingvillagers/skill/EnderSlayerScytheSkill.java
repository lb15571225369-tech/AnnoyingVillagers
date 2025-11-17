package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BabyEnderDragonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderSlayerScythe;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.UUID;

public class EnderSlayerScytheSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");
    public EnderSlayerScytheSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();

            ServerPlayerPatch serverPlayerPatch = skillContainer.getServerExecutor();
            Player player = serverPlayerPatch.getOriginal();
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() instanceof EnderSlayerScythe && player.level() instanceof ServerLevel serverLevel) {
                serverPlayerPatch.playAnimationSynchronized(AVAnimations.ENDER_SLAYER_ANTITHEUS_AIMING, 0.0F);
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : executeOnServer implement dragon shoot calling");
                if (player.getPersistentData().contains("DragonUUID")) {
                    Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                    if (entity instanceof BabyEnderDragonEntity babyEnderDragonEntity) {
                        babyEnderDragonEntity.breath();
                    }
                }
            }
        }
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
        if (container.getStack() == 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof EnderSlayerScythe && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof ObsidianSledgehammerItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }

        if (container.getExecutor().isLogicalClient()) return;
        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        SkillContainer skillContainer = serverPlayerPatch.getSkill(this);
        if ((skillContainer == null || skillContainer != container) && player.getPersistentData().contains("DragonUUID")
                && player.level() instanceof ServerLevel serverLevel) {
//            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer skill container swapped, removing dragon");
            Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
            if (entity instanceof BabyEnderDragonEntity babyEnderDragonEntity) {
                babyEnderDragonEntity.discard();
            }
        }

        if (player.tickCount % 5 == 0 && player.level() instanceof ServerLevel serverLevel) {
            if (itemStack.getItem() instanceof EnderSlayerScythe && player.level() instanceof ServerLevel && player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer dragon already spawned, check if dragon is not null");
                Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                if (!(entity instanceof BabyEnderDragonEntity)) {
                    BabyEnderDragonEntity babyEnderDragonEntity = spawnBabyEnderDragon(player, serverLevel);
                    if (babyEnderDragonEntity != null) {
                        player.getPersistentData().putUUID("DragonUUID", babyEnderDragonEntity.getUUID());
                    }
                }
            } else if (itemStack.getItem() instanceof EnderSlayerScythe && !player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer should spawn dragon here");
                BabyEnderDragonEntity babyEnderDragonEntity = spawnBabyEnderDragon(player, serverLevel);
                if (babyEnderDragonEntity != null) {
                    player.getPersistentData().putUUID("DragonUUID", babyEnderDragonEntity.getUUID());
                }
            } else if (!(itemStack.getItem() instanceof EnderSlayerScythe) && player.getPersistentData().contains("DragonUUID")) {
//                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] : updateContainer remove dragon tag and despawn here ?");
                Entity entity = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                if (entity instanceof BabyEnderDragonEntity babyEnderDragonEntity) {
                    babyEnderDragonEntity.discard();
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

    private BabyEnderDragonEntity spawnBabyEnderDragon(Player player, ServerLevel serverLevel) {
        if (!player.isAlive()) return null;
        BabyEnderDragonEntity babyEnderDragonEntity = new BabyEnderDragonEntity(AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), serverLevel);
        Vec3 posBehind3D = posBehind3D(player, 1.0D, 2.0D, 1.0D);
        babyEnderDragonEntity.moveTo(
                posBehind3D.x,
                posBehind3D.y,
                posBehind3D.z
        );
        babyEnderDragonEntity.setFollowTarget(player);
        babyEnderDragonEntity.setFollowTargetUUID(player.getUUID());
        serverLevel.addFreshEntity(babyEnderDragonEntity);
        return babyEnderDragonEntity;
    }
}
