package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class JevEntity extends PathfinderMobInventory {
    private UUID followTargetUUID;
    private AlexEntity followTarget;
    private boolean alexDeathMessageSent = false;

    public void setFollowTarget(AlexEntity followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public JevEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.JEV.get(), level);
    }

    public JevEntity(EntityType<JevEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 10;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.JEV_BOOK.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.JEV_PENCIL.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.JEV_GLASSES.get()));
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, AlexEntity.class, 12.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 5.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 5.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > (float)20.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    getNavigation().moveTo(followTarget, 2.0D);
                    getLookControl().setLookAt(followTarget, 30.0F, 30.0F);
                    if (distanceToSqr(followTarget) > 20.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(followTarget, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 50.0D;
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1.0D, 10.0F, 5.0F));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        TeamUtil.addOrJoinTeam(this, "alex");
        setMainWeaponItem(new ItemStack(AnnoyingVillagersModItems.JEV_PENCIL.get()));
        setOffWeaponItem(new ItemStack(AnnoyingVillagersModItems.JEV_BOOK.get()));
        return returnSpawnGroupData;
    }

    @Override
    public void die(@NotNull DamageSource pDamageSource) {
        super.die(pDamageSource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                JevDeadEntity jevDeadEntity = new JevDeadEntity(AnnoyingVillagersModEntities.JEV_DEAD.get(), serverLevel);
                jevDeadEntity.moveTo(this.getX(), this.getY(), this.getZ(), serverLevel.getRandom().nextFloat() * 360.0F, 0.0F);
                jevDeadEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(jevDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(jevDeadEntity);
                jevDeadEntity.kill();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (followTarget == null && followTargetUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(followTargetUUID);
                if (entity instanceof AlexEntity alex) {
                    followTarget = alex;
                } else {
                    followTargetUUID = null;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                if (!alexDeathMessageSent) {
                    alexDeathMessageSent = true;
                    if (level() instanceof ServerLevel serverLevel) {
                        String[] ALEX_DEATH_LINES = {
                                "Oh no... my Alex...",
                                "Why... Alex, why did you leave me...",
                                "I was supposed to protect you, Alex ...",
                                "Alex, come back, please...",
                                "Please, Alex, wake up...",
                                "Alex...? No..."
                        };

                        String message = ALEX_DEATH_LINES[level().getRandom().nextInt(ALEX_DEATH_LINES.length)];
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + message), false);
                    }
                }
                followTarget = null;
                followTargetUUID = null;
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            followTarget.getX(),
                            followTarget.getY(),
                            followTarget.getZ()
                    );
                }
            }
        }
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
        }
        tag.putBoolean("AlexDeathMessageSent", alexDeathMessageSent);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
        alexDeathMessageSent = tag.getBoolean("AlexDeathMessageSent");
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.villager.death"));
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        return super.hurt(damageSource, f);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}
