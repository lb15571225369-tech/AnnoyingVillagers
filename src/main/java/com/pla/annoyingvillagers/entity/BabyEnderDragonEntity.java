package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.emitterinfo.GroundPillarEmitterInfo;
import com.pla.annoyingvillagers.client.emitterinfo.TopFollowEmitterInfo;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.procedures.HerobrineWeaponEffectProcedure;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import reascer.wom.world.entity.mob.EnderHand;

import java.util.List;
import java.util.UUID;

public class BabyEnderDragonEntity extends FlyingMob {
    private UUID followTargetUUID;
    private Player followTarget;
    private int lookAtTimer = 0;
    private LivingEntity lookAtTarget = null;

    public final AnimationState idleAnimationState = new AnimationState();

    private boolean shootingState = false;
    private int breathCooldown = 0;

    public final AnimationState shootAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> SHOOT_TICKS =
            SynchedEntityData.defineId(BabyEnderDragonEntity.class, EntityDataSerializers.INT);

    public void setFollowTarget(Player followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public UUID getFollowTargetUUID() {
        return followTargetUUID;
    }

    public Player getFollowTarget() {
        return followTarget;
    }

    public boolean isShootingState() {
        return shootingState;
    }

    public void setShootingState(boolean shootingState) {
        this.shootingState = shootingState;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT_TICKS, 0);
    }

    public int getShootTicks() {
        return this.entityData.get(SHOOT_TICKS);
    }
    public void startShoot(int ticks) {
        if (!this.level().isClientSide) {
            this.entityData.set(SHOOT_TICKS, ticks);
        }
    }

    public BabyEnderDragonEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), level);
    }

    public BabyEnderDragonEntity(EntityType<BabyEnderDragonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setNoGravity(true);
        this.setPersistenceRequired();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanPassDoors(true);
        nav.setCanFloat(true);
        return nav;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void lookAtEntity(LivingEntity target, float yawStep, float pitchStep) {
        if (target == null) return;

        Vec3 myEye = this.getEyePosition(1.0F);
        Vec3 tgEye = target.getEyePosition(1.0F);
        Vec3 to    = tgEye.subtract(myEye);

        float desiredYaw   = (float)(Mth.atan2(to.z, to.x) * (180F / Math.PI)) - 90.0F;
        float desiredPitch = (float)(-(Mth.atan2(to.y, Math.sqrt(to.x * to.x + to.z * to.z)) * (180F / Math.PI)));

        this.setYRot(Mth.approachDegrees(this.getYRot(), desiredYaw,   yawStep));
        this.setXRot(Mth.approachDegrees(this.getXRot(), desiredPitch, pitchStep));

        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    copyOwnerLook(followTarget);
                    Vec3 target = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    Vec3 to = target.subtract(position());
                    Vec3 vel = getDeltaMovement().scale(0.85).add(to.scale(0.15));
                    setDeltaMovement(vel);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 1.0D;
            }
        });
    }

    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        if (!this.level().isClientSide()
                && !damagesource.is(DamageTypes.IN_WALL)
                && damagesource.getEntity() != followTarget
                && !damagesource.is(DamageTypes.IN_FIRE)) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "playsound epicfight:entity.hit.clash neutral @p",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                this.getServer().getCommands().getDispatcher().execute(
                        "execute at @s run particle epicfight:hit_blade ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypes.FELL_OUT_OF_WORLD);
    }

    private static Vec3 posBehind3D(Player p, double back, double upFromGround, double right) {
        Vec3 look = p.getLookAngle().normalize();

        Vec3 forwardXZ = new Vec3(look.x, 0, look.z);
        if (forwardXZ.lengthSqr() < 1e-6) {
            float yaw = p.getYRot() * ((float)Math.PI/180F);
            forwardXZ = new Vec3(-Mth.sin(yaw), 0, Mth.cos(yaw));
        }
        forwardXZ = forwardXZ.normalize();

        Vec3 rightVec = new Vec3(-forwardXZ.z, 0, forwardXZ.x);

        double tx = p.getX() - forwardXZ.x * back + rightVec.x * right;
        double tz = p.getZ() - forwardXZ.z * back + rightVec.z * right;

        int blockY = Mth.floor(p.getY());
        double ty = blockY + upFromGround;
        if ( (blockY & 1) != 0 ) {
//            ty += 1.0D;
        }

        return new Vec3(tx, ty, tz);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyRotationControl(this) {
            @Override
            public void clientTick() {
                BabyEnderDragonEntity self = BabyEnderDragonEntity.this;
                self.yBodyRot = self.getYRot();
            }
        };
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.followTarget != null && this.followTarget.isAlive()) {
            copyOwnerLook(this.followTarget);
        }
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox,
                        e -> e != sourceEntity
                                && !(e instanceof BabyEnderDragonEntity)
                                && !(e instanceof EnderHand)
                                && !e.isAlliedTo(sourceEntity)
                                && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    public void breath() {
        LivingEntity target = getNearestLivingEntity(followTarget.level(), followTarget, 6.0D);
        if (target != null) {
            this.startShoot(55);

            Vec3 eye = this.getEyePosition(1.0F);
            Vec3 fwd = this.getViewVector(1.0F).normalize();
            double muzzle = 0.25;
            double sx = eye.x + fwd.x * muzzle;
            double sy = eye.y + fwd.y * muzzle;
            double sz = eye.z + fwd.z * muzzle;

            BabyDragonBeamEntity beam = new BabyDragonBeamEntity(
                    AnnoyingVillagersModEntities.BABY_DRAGON_BEAM.get(),
                    level(),
                    this,
                    target,
                    sx, sy, sz,
                    10, 6
            );
            level().addFreshEntity(beam);
            this.lookAtTarget = target;
            this.lookAtTimer  = 40;
        }
    }

    public void summonBeam() {
        if (!this.level().isClientSide()) {
            new TopFollowEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "magic_portal"))
                    .follow(this, 80, 0.8f)
                    .spawnInWorld(this.level(), null);
        }

        BabyEnderDragonEntity babyEnderDragonEntity = this;
        new DelayedTask(15) {
            @Override
            public void run() {
                final int radius = 20;

                AABB box = new AABB(
                        babyEnderDragonEntity.getX() - radius, babyEnderDragonEntity.getY() - radius, babyEnderDragonEntity.getZ() - radius,
                        babyEnderDragonEntity.getX() + radius, babyEnderDragonEntity.getY() + radius, babyEnderDragonEntity.getZ() + radius
                );

                List<LivingEntity> livingEntities = babyEnderDragonEntity.level().getEntitiesOfClass(
                        LivingEntity.class, box,
                        e -> e.isAlive() && e != babyEnderDragonEntity && (babyEnderDragonEntity.followTarget != null && e != babyEnderDragonEntity.followTarget) &&
                                !(e instanceof EnderHand) && !e.isAlliedTo(babyEnderDragonEntity.followTarget)
                );

                if (babyEnderDragonEntity.level() instanceof ServerLevel serverLevel) {
                    for (LivingEntity entity : livingEntities) {
                        BabyDragonBigBeamEntity beam = new BabyDragonBigBeamEntity(
                                AnnoyingVillagersModEntities.BABY_DRAGON_BIG_BEAM.get(),
                                serverLevel,
                                babyEnderDragonEntity,
                                entity
                        );
                        serverLevel.addFreshEntity(beam);
                    }
                }
            }
        };
    }

    private void copyOwnerLook(Player owner) {
        float targetYaw   = owner.getYRot();
        float targetPitch = owner.getXRot() * 0.35F;

        this.setYRot(Mth.approachDegrees(this.getYRot(), targetYaw, 10.0F));
        this.setXRot(Mth.approachDegrees(this.getXRot(), targetPitch, 8.0F));

        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            boolean shooting = getShootTicks() > 10;

            if (shooting) {
                if (!this.shootAnimationState.isStarted()) {
                    this.shootAnimationState.start(this.tickCount);
                }
                this.idleAnimationState.stop();
            } else {
                this.shootAnimationState.stop();
                if (!this.idleAnimationState.isStarted()) {
                    this.idleAnimationState.start(this.tickCount);
                }
            }
        }
        if (!level().isClientSide) {
            if (shootingState) {
                if (breathCooldown > 0) {
                    breathCooldown--;
                } else if (this.followTarget != null) {
                    LivingEntity near = getNearestLivingEntity(followTarget.level(), followTarget, 6.0D);
                    if (near != null) {
                        breath();
                        breathCooldown = 60;
                    }
                }
            }

            if (getShootTicks() > 0) {
                this.entityData.set(SHOOT_TICKS, getShootTicks() - 1);
                HerobrineWeaponEffectProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            }

            if (this.followTarget != null && this.followTarget.isAlive()) {
                if (this.lookAtTimer > 0 && this.lookAtTarget != null && this.lookAtTarget.isAlive()) {
                    lookAtEntity(this.lookAtTarget, 999f, 999f);
                    this.lookAtTimer--;
                    if (this.lookAtTimer == 0) this.lookAtTarget = null;
                } else {
                    copyOwnerLook(this.followTarget);
                }
            }

            if (followTarget == null && followTargetUUID != null) {
                if (!(this.level() instanceof ServerLevel serverLevel)) return;
                var server = serverLevel.getServer();
                var serverPlayer = server.getPlayerList().getPlayer(this.followTargetUUID);

                if (serverPlayer != null) {
                    this.followTarget = serverPlayer;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
                this.discard();
            }
            if (followTarget != null && followTarget.isAlive()) {
                if (followTarget.getPersistentData().contains("DragonUUID")
                        && !this.getUUID().equals(followTarget.getPersistentData().getUUID("DragonUUID"))) {
                    this.discard();
                } else if (!followTarget.getPersistentData().contains("DragonUUID")) {
                    this.discard();
                }

                if (!(followTarget.getMainHandItem().getItem() instanceof EnderSlayerScytheItem)) {
                    followTarget.getPersistentData().remove("DragonUUID");
                    this.discard();
                }

                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 4.0D) {
                    Vec3 posBehind3D = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    this.teleportTo(
                            posBehind3D.x,
                            posBehind3D.y,
                            posBehind3D.z
                    );
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
        }
        tag.putBoolean("ShootingState", shootingState);
        tag.putInt("BreathCooldown", breathCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
        shootingState = tag.getBoolean("ShootingState");
        breathCooldown = tag.getInt("BreathCooldown");
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MAX_HEALTH, 150.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        return builder;
    }
}
