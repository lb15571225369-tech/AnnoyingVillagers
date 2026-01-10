/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * This file is part of AnnoyingVillagers.
 * Contains code adapted from Dragon Mounts: Legacy.
 *
 * Copyright (C) 2026 pla_is_me
 * Original authors: Nico Bergemann (BarracudaATA), Kay9, and contributors.
 *
 * Licensed under the GNU General Public License v3.0 or later.
 * See the LICENSE file in the project root for details.
 */


package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.DragonAnimator;
import com.pla.annoyingvillagers.client.engine.MountCameraManager;
import com.pla.annoyingvillagers.client.engine.MountControlsMessenger;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModKeyMappings;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;

/**
 * Here be dragons.
 * <p>
 * Let the legacy live on.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @author Kay9
 */
@SuppressWarnings({"deprecation", "SameReturnValue"})
public class HerobrineDragonEntity extends TamableAnimal implements FlyingAnimal, PlayerRideable
{
    public static final double BASE_SPEED_GROUND = 0.3;
    public static final double BASE_SPEED_FLYING = 0.32;
    public static final double BASE_DAMAGE = 8;
    public static final double BASE_HEALTH = 150;
    public static final double BASE_FOLLOW_RANGE = 16;
    public static final int BASE_KB_RESISTANCE = 1;
    public static final float BASE_WIDTH = 2.75f;
    public static final float BASE_HEIGHT = 2.75f;

    public static final int GROUND_CLEARENCE_THRESHOLD = 3;

    private final DragonAnimator animator;
    private boolean flying;
    private boolean nearGround;

    private UUID summonerUUID;
    private LivingEntity summoner;

    private final GroundPathNavigation groundNavigation;
    private final FlyingPathNavigation flyingNavigation;

    private LivingEntity breathHoverTarget;
    private Vec3 breathHoverPos;
    private int breathHoverTimeToLiveTicks;

    private boolean recallActive = false;
    private boolean recallAutoMount = false;
    private Vec3 recallLandPos = null;

    @Nullable
    public EndCrystal nearestCrystal;

    private static final EntityDataAccessor<Boolean> DATA_CONTROL_LOCKED = SynchedEntityData.defineId(HerobrineDragonEntity.class, EntityDataSerializers.BOOLEAN);

    public boolean isRecallActive() {
        return recallActive;
    }

    public HerobrineDragonEntity(EntityType<? extends HerobrineDragonEntity> type, Level level)
    {
        super(type, level);

        noCulling = true;

        moveControl = new DragonMoveController(this);
        animator = level.isClientSide? new DragonAnimator(this) : null;

        flyingNavigation = new FlyingPathNavigation(this, level);
        groundNavigation = new GroundPathNavigation(this, level);

        flyingNavigation.setCanFloat(true);
        groundNavigation.setCanFloat(true);

        navigation = groundNavigation;
    }

    @Override
    @NotNull
    public BodyRotationControl createBodyControl()
    {
        return new DragonBodyController(this);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(1, new RecallLandGoal(this));
        goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(3, new DragonOrbitLeaderGoal(this, 1.15, 20f, 50f, 180f));
        goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.85f));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16f));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(DATA_CONTROL_LOCKED, false);
    }

    private boolean isControlLocked() {
        return this.entityData.get(DATA_CONTROL_LOCKED);
    }

    private void setControlLocked(boolean locked) {
        this.entityData.set(DATA_CONTROL_LOCKED, locked);
    }

    private boolean shouldApplyControlLocked() {
        return this.summoner instanceof Player;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data)
    {
        if (DATA_FLAGS_ID.equals(data)) refreshDimensions();
        else super.onSyncedDataUpdated(data);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        if (summonerUUID != null) {
            compound.putUUID("SummonerUUID", summonerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        setAge(0);
        if (compound.hasUUID("SummonerUUID")) {
            summonerUUID = compound.getUUID("SummonerUUID");
        }
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    public void setSummonerUUID(UUID summonerUUID) {
        this.summonerUUID = summonerUUID;
    }

    public UUID getSummonerUUID() {
        return summonerUUID;
    }

    public void setSummoner(LivingEntity summoner) {
        this.summoner = summoner;
    }

    public LivingEntity getSummoner() {
        return summoner;
    }

    public boolean canFly()
    {
        return true;
    }

    public boolean shouldFly()
    {
        if (isFlying()) return !onGround();
        return canFly() && !isInWater() && !isNearGround();
    }

    public boolean isFlying()
    {
        return flying;
    }

    public void setFlying(boolean flying)
    {
        this.flying = flying;
    }

    public boolean isNearGround()
    {
        return nearGround;
    }

    public void setNavigation(boolean flying)
    {
        navigation = flying ?
                flyingNavigation :
                groundNavigation;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @org.jetbrains.annotations.Nullable CompoundTag pDataTag) {
        setAge(0);

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox,
                        e -> e != sourceEntity
                                && !(e instanceof HerobrineDragonEntity)
                                && !(e instanceof EnderHand)
                                && !e.isAlliedTo(sourceEntity)
                                && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    private void aimBodyAndHeadAt(LivingEntity target) {
        Vec3 from = this.getEyePosition(1.0F);
        Vec3 to = target.getEyePosition(1.0F);

        double dx = to.x - from.x;
        double dz = to.z - from.z;
        double dy = to.y - from.y;
        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float wantYaw   = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float wantPitch = (float)(-(Mth.atan2(dy, distXZ) * (180F / Math.PI)));

        float yaw   = Mth.approachDegrees(this.getYRot(), wantYaw, (float) 10.0);
        float pitch = Mth.approachDegrees(this.getXRot(), wantPitch, (float) 6.0);

        this.setYRot(yaw);
        this.setXRot(pitch);

        this.setYHeadRot(yaw);
        this.setYBodyRot(yaw);
    }

    public Vec3 beamMouthPos(float partial) {
        Vec3 eye = new Vec3(
                Mth.lerp(partial, this.xOld, this.getX()),
                Mth.lerp(partial, this.yOld, this.getY()) + this.getEyeHeight(),
                Mth.lerp(partial, this.zOld, this.getZ())
        );

        float headYaw = Mth.rotLerp(partial, this.yHeadRotO, this.yHeadRot);
        float headPitch = Mth.lerp(partial, this.xRotO, this.getXRot());
        Vec3 look = Vec3.directionFromRotation(headPitch, headYaw);

        double baseForward = Math.max(1.0, this.getBbWidth() * 0.7);

        boolean hasPlayerRider = this.getFirstPassenger() instanceof Player;
        double extraUp = (hasPlayerRider ? 1.0 : 4.0) * this.getScale();
        double extraForward = 5.2 * this.getScale();

        double forward = baseForward + extraForward;

        return eye.add(look.scale(forward))
                .add(0.0, extraUp, 0.0);
    }

    public void shootThunderBreathAtTarget(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (target == null || !target.isAlive()) return;

        this.breathHoverTarget = target;

        Vec3 position = this.position();
        if (this.onGround()) position = position.add(0.0, 10.0, 0.0);
        this.breathHoverPos = position;

        this.breathHoverTimeToLiveTicks = 110;

        this.getNavigation().stop();

        if (!this.isFlying() && this.canFly()) this.liftOff();
        this.setFlying(true);
        this.setNavigation(true);

        Vec3 mouth = this.getEyePosition()
                .add(this.getLookAngle().scale(Math.max(1.0, this.getBbWidth() * 0.6)));

        DragonBeamEntity beam = new DragonBeamEntity(
                AnnoyingVillagersModEntities.DRAGON_BEAM.get(),
                serverLevel,
                this,
                target,
                mouth.x, mouth.y, mouth.z,
                100, 2
        );

        serverLevel.addFreshEntity(beam);
        this.playSound(AnnoyingVillagersModSounds.DRAGON_THUNDER_SHOOT_SOUND.get(), 2.0F, 1.0F);
    }

    public void shootMeteoriteAtTarget(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        if (target == null || !target.isAlive()) return;

        this.breathHoverTarget = target;

        Vec3 position = this.position();
        if (this.onGround()) position = position.add(0.0, 10.0, 0.0);
        this.breathHoverPos = position;

        this.breathHoverTimeToLiveTicks = 20;

        this.getNavigation().stop();

        if (!this.isFlying() && this.canFly()) this.liftOff();
        this.setFlying(true);
        this.setNavigation(true);

        Vec3 look = this.getLookAngle();
        double baseForward = Math.max(1.0, this.getBbWidth() * 0.6);
        double extraForward = 7.5 * this.getScale();
        double heightOffset = -1.0 * this.getScale();
        Vec3 spawnPos = this.getEyePosition()
                .add(look.scale(baseForward + extraForward))
                .add(0.0, heightOffset, 0.0);

        DragonMeteoriteEntity dragonMeteoriteEntity = new DragonMeteoriteEntity(AnnoyingVillagersModEntities.DRAGON_METEORITE.get(), serverLevel);
        dragonMeteoriteEntity.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, 0F, 0F);
        dragonMeteoriteEntity.setPosToAim(new Vec3(target.getX(), target.getY(0.5D), target.getZ()));
        dragonMeteoriteEntity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        dragonMeteoriteEntity.setOwner(this);
        serverLevel.addFreshEntity(dragonMeteoriteEntity);

        this.playSound(SoundEvents.ENDER_DRAGON_SHOOT, 2.0F, 1.0F);
    }

    public void recallAndLand(boolean autoMount) {
        if (this.level().isClientSide()) return;
        if (this.summoner == null || !this.summoner.isAlive()) return;

        this.recallActive = true;
        this.recallAutoMount = autoMount;
        this.recallLandPos = null;

        this.breathHoverTimeToLiveTicks = 0;
        this.breathHoverTarget = null;
        this.breathHoverPos = null;
    }

    private static boolean hasEnderSlayerScythe(Player p) {
        for (ItemStack s : p.getInventory().items) {
            if (s.getItem() instanceof EnderSlayerScytheItem) return true;
        }
        for (ItemStack s : p.getInventory().offhand) {
            if (s.getItem() instanceof EnderSlayerScytheItem) return true;
        }
        return false;
    }

    private static boolean isAllowedHeldCategory(Player p) {
        ItemStack main = p.getMainHandItem();

        if (main.getItem() instanceof EnderSlayerScytheItem) return true;

        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(main);
        if (!(cap instanceof WeaponCapability weaponCap)) return true;

        var cat = weaponCap.getWeaponCategory();
        return cat == CapabilityItem.WeaponCategories.FIST
                || cat == CapabilityItem.WeaponCategories.RANGED
                || cat == CapabilityItem.WeaponCategories.NOT_WEAPON;
    }

    private void checkCrystals() {
        if (this.getFirstPassenger() != null && this.getFirstPassenger() instanceof EndCrystal) {
            return;
        }
        if (this.nearestCrystal != null) {
            if (this.nearestCrystal.isRemoved()) {
                this.nearestCrystal = null;
            } else if (this.tickCount % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0F);
            }
        }

        if (this.random.nextInt(10) == 0) {
            List<EndCrystal> list = this.level().getEntitiesOfClass(EndCrystal.class, this.getBoundingBox().inflate((double)32.0F));
            EndCrystal endcrystalTemp = null;
            double d0 = Double.MAX_VALUE;

            for(EndCrystal endCrystal : list) {
                double d1 = endCrystal.distanceToSqr(this);
                if (d1 < d0) {
                    d0 = d1;
                    endcrystalTemp = endCrystal;
                }
            }

            if (endcrystalTemp == null && this.nearestCrystal != null) {
                this.nearestCrystal.setBeamTarget(null);
            }
            this.nearestCrystal = endcrystalTemp;
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel)
        {
            this.checkCrystals();
            if (this.nearestCrystal != null && !this.nearestCrystal.isRemoved()) {
                this.nearestCrystal.setBeamTarget(this.blockPosition());
            }

            if (breathHoverTimeToLiveTicks > 0) {
                if (shouldApplyControlLocked()) {
                    if (!isControlLocked()) setControlLocked(true);
                } else {
                    if (isControlLocked()) setControlLocked(false);
                }
                breathHoverTimeToLiveTicks--;

                if (breathHoverPos == null) breathHoverPos = this.position();

                if (!this.isFlying() && this.canFly()) this.liftOff();
                this.setFlying(true);
                this.setNavigation(true);

                this.getNavigation().stop();
                this.setNoGravity(true);

                if (breathHoverTarget != null) {
                    aimBodyAndHeadAt(breathHoverTarget);
                }

                double y = breathHoverPos.y + Math.sin((double) this.tickCount * 0.25) * 0.25;
                this.getMoveControl().setWantedPosition(breathHoverPos.x, y, breathHoverPos.z, 0.12D);

                Vec3 dv = this.getDeltaMovement();
                this.setDeltaMovement(dv.x * 0.2, dv.y * 0.6, dv.z * 0.2);

                if (breathHoverTimeToLiveTicks <= 0) {
                    this.setNoGravity(false);
                    breathHoverTarget = null;
                    breathHoverPos = null;
                }
            } else {
                if (this.isNoGravity()) this.setNoGravity(false);
                if (isControlLocked()) {
                    setControlLocked(false);
                }
            }

            if (summoner == null && summonerUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(summonerUUID);
                if (!(entity instanceof Player) && entity instanceof LivingEntity livingEntity) {
                    summoner = livingEntity;
                } else {
                    var server = serverLevel.getServer();
                    var serverPlayer = server.getPlayerList().getPlayer(summonerUUID);

                    if (serverPlayer != null) {
                        summoner = serverPlayer;
                    }
                }
            }

            if (summoner != null && !summoner.isAlive()) {
                summoner = null;
                summonerUUID = null;
                this.discard();
            }

            if (summoner != null && summoner.isAlive() && summoner instanceof Player player) {
                if (summoner.getPersistentData().contains("DragonUUID")
                        && !this.getUUID().equals(summoner.getPersistentData().getUUID("DragonUUID"))) {
                    this.discard();
                    return;
                } else if (!summoner.getPersistentData().contains("DragonUUID")) {
                    this.discard();
                    return;
                }

                if (!hasEnderSlayerScythe(player) || !isAllowedHeldCategory(player)) {
                    player.getPersistentData().remove("DragonUUID");
                    this.discard();
                    return;
                }
            }

            if (summoner != null && summoner.isAlive() && !isLeashed() && !isPassenger() && !hasControllingPassenger()) {
                double distSqr = this.distanceToSqr(summoner);
                double farDist = 320.0D;

                if (distSqr > farDist * farDist) {
                    if (!isFlying() && canFly()) liftOff();
                    getNavigation().stop();

                    double toY = Mth.clamp(
                            summoner.getY() + 18.0D,
                            level().getMinBuildHeight() + 6.0D,
                            level().getMaxBuildHeight() - 6.0D
                    );

                    getMoveControl().setWantedPosition(summoner.getX(), toY, summoner.getZ(), 1.8D);
                }
            }
        }
        else {
            animator.tick();
        }

        nearGround = onGround() || !level().noCollision(this, new AABB(getX(), getY(), getZ(), getX(), getY() - (GROUND_CLEARENCE_THRESHOLD * getScale()), getZ()));
        boolean flying = shouldFly();
        if (flying != isFlying())
        {
            setFlying(flying);
            if (!this.level().isClientSide()) setNavigation(flying);
        }
    }

    @Override
    public void travel(@NotNull Vec3 vec3)
    {
        if (isFlying())
        {
            if (isControlledByLocalInstance())
            {
                moveRelative(getSpeed(), vec3);
                move(MoverType.SELF, getDeltaMovement());
                if (getDeltaMovement().lengthSqr() < 0.1)
                    setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
                setDeltaMovement(getDeltaMovement().scale(0.9f));
            }

            calculateEntityAnimation(true);
        }
        else super.travel(vec3);
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player driver, @NotNull Vec3 move)
    {
        if (isControlLocked()) {
            return Vec3.ZERO;
        }
        double moveSideways = move.x;
        double moveY = move.y;
        double moveForward = Math.min(Math.abs(driver.zza) + Math.abs(driver.xxa), 1);

        if (isFlying() && hasLocalDriver())
        {
            moveForward = moveForward > 0? moveForward : 0;
            if (driver.jumping) moveY = 1;
            else if (AnnoyingVillagersModKeyMappings.DRAGON_FLIGHT_DESCENT_KEY.isDown()) moveY = -1;
            else if (moveForward > 0) moveY = -driver.getXRot() / 90;
        }

        var speed = getRiddenSpeed(driver);
        return new Vec3(moveSideways * speed, moveY * speed, moveForward * speed);
    }

    @Override
    protected void tickRidden(@NotNull Player driver, @NotNull Vec3 move)
    {
        if (isControlLocked()) {
            return;
        }

        float yaw = driver.yHeadRot;
        if (move.z > 0)
            yaw += (float) Mth.atan2(driver.zza, driver.xxa) * (180f / (float) Math.PI) - 90;
        yHeadRot = yaw;
        setXRot(driver.getXRot() * 0.68f);

        setYRot(Mth.rotateIfNecessary(yHeadRot, getYRot(), 4));

        if (isControlledByLocalInstance())
        {
            if (!isFlying() && canFly() && driver.jumping) liftOff();
        }
    }

    @Override
    public boolean isControlledByLocalInstance() {
        if (isControlLocked()) {
            return false;
        }
        return super.isControlledByLocalInstance();
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player driver)
    {
        return (float) getAttributeValue(isFlying()? FLYING_SPEED : MOVEMENT_SPEED);
    }

    public void liftOff()
    {
        if (!canFly() || isInWater()) return;

        Vec3 dv = this.getDeltaMovement();
        if (this.onGround() || dv.y < 0.15D) {
            this.setDeltaMovement(dv.x, 0.42D, dv.z);
        }

        this.setFlying(true);
        if (!this.level().isClientSide()) {
            this.setNavigation(true);
        }
    }

    @Override
    protected float getJumpPower()
    {
        return super.getJumpPower() * (canFly()? 3 : 1);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource)
    {
        return !canFly() && super.causeFallDamage(pFallDistance, pMultiplier, pSource);
    }

    @Override
    protected void tickDeath()
    {
        ejectPassengers();

        setDeltaMovement(Vec3.ZERO);
        setYRot(yRotO);
        setYHeadRot(yHeadRotO);

        if (deathTime >= getMaxDeathTime()) remove(RemovalReason.KILLED);

        deathTime++;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn)
    {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    public SoundEvent getStepSound()
    {
        return AnnoyingVillagersModSounds.DRAGON_STEP_SOUND.get();
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected SoundEvent getDeathSound()
    {
        return AnnoyingVillagersModSounds.DRAGON_DEATH_SOUND.get();
    }

    @Override
    public @NotNull SoundEvent getEatingSound(@NotNull ItemStack itemStackIn)
    {
        return SoundEvents.GENERIC_EAT;
    }

    public SoundEvent getWingsSound()
    {
        return SoundEvents.ENDER_DRAGON_FLAP;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(@NotNull BlockPos entityPos, @NotNull BlockState state)
    {
        if (isInWater()) return;

        var soundType = state.getSoundType();
        if (level().getBlockState(entityPos.above()).getBlock() == Blocks.SNOW)
            soundType = Blocks.SNOW.getSoundType(state, level(), entityPos, this);

        playSound(getStepSound(), soundType.getVolume(), soundType.getPitch() * getVoicePitch());
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 240;
    }

    @Override
    protected float getSoundVolume()
    {
        return getScale();
    }

    @Override
    public float getVoicePitch()
    {
        return 2 - getScale();
    }

    public boolean isTamedFor(Player player)
    {
        return isTame() && isOwnedBy(player);
    }

    /**
     * Returns the height of the eyes. Used for looking at other entities.
     */
    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, EntityDimensions sizeIn)
    {
        return sizeIn.height * 1.2f;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset()
    {
        return getBbHeight() - 0.175;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer)
    {
        return false;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    @Override
    public boolean onClimbable()
    {
        return false;
    }

    public void onWingsDown(float speed)
    {
        if (!isInWater())
        {
            float pitch = (1 - speed);
            float volume = 0.3f + (1 - speed) * 0.2f;
            float loudMul = 5.0f;
            pitch *= getVoicePitch();
            volume *= getSoundVolume();
            volume *= loudMul;
            level().playLocalSound(getX(), getY(), getZ(), getWingsSound(), SoundSource.HOSTILE, volume, pitch, true);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource src, float par2)
    {
        if (isInvulnerableTo(src)) return false;
        if (src.getEntity() == summoner) return false;
        if (src.getEntity() instanceof Projectile) return super.hurt(src, par2 * 0.1F);
        return super.hurt(src, par2);
    }

    @Override
    public boolean canMate(@NotNull Animal mate)
    {
        if (mate == this) return false;
        if (!(mate instanceof HerobrineDragonEntity)) return false;
        return isInLove() && mate.isInLove();
    }


    @Override
    @SuppressWarnings("ConstantConditions")
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob)
    {
        return AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get().create(level);
    }

    @Override
    public boolean wantsToAttack(@NotNull LivingEntity target, @NotNull LivingEntity owner)
    {
        return false;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target)
    {
        return false;
    }

    @Override
    public LivingEntity getControllingPassenger()
    {
        Entity rider = getFirstPassenger();
        return (rider instanceof Player player) ? player : null;
    }

    @Override
    protected void addPassenger(@NotNull Entity passenger)
    {
        super.addPassenger(passenger);

        if (passenger instanceof Player)
        {
            passenger.setYRot(getYRot());
            passenger.setXRot(getXRot());
        }

        if (hasLocalDriver())
        {
            MountControlsMessenger.sendControlsMessage();
            MountCameraManager.onDragonMount();
        }
    }

    @Override
    protected void removePassenger(@NotNull Entity passenger)
    {
        if (hasLocalDriver()) MountCameraManager.onDragonDismount();
        super.removePassenger(passenger);
    }

    @Override
    protected void positionRider(@NotNull Entity ridden, @NotNull MoveFunction pCallback)
    {
        if (hasPassenger(ridden))
        {
            var rePos = new Vec3(0, getPassengersRidingOffset() + ridden.getMyRidingOffset(), getScale())
                    .yRot((float) Math.toRadians(-yBodyRot))
                    .add(position());
            pCallback.accept(ridden, rePos.x, rePos.y, rePos.z);

            if (getFirstPassenger() instanceof LivingEntity)
            {
                ridden.xRotO = ridden.getXRot();
                ridden.yRotO = ridden.getYRot();
                ridden.setYBodyRot(yBodyRot);
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource src)
    {
        Entity srcEnt = src.getEntity();
        if (srcEnt != null && (srcEnt == this || hasPassenger(srcEnt))) return true;

        return super.isInvulnerableTo(src);
    }

    public float getHealthFraction()
    {
        return getHealth() / getMaxHealth();
    }

    public int getMaxDeathTime()
    {
        return 120;
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        if (this.getFirstPassenger() instanceof EndCrystal endCrystal
                && this.level() instanceof ServerLevel serverLevel) {
            endCrystal.hurt(serverLevel.damageSources().generic(), 1.0F);
        }
        if (this.getFirstPassenger() != null && this.getFirstPassenger() instanceof EndCrystal) {
            return;
        }
        if (this.nearestCrystal != null) {
            this.nearestCrystal.setBeamTarget(null);
        }

        super.remove(pReason);
    }

    @Override
    public void refreshDimensions()
    {
        double posXTmp = getX();
        double posYTmp = getY();
        double posZTmp = getZ();
        boolean onGroundTmp = onGround();

        super.refreshDimensions();
        setPos(posXTmp, posYTmp, posZTmp);
        setOnGround(onGroundTmp);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose poseIn)
    {
        var height = isInSittingPose()? 2.15f : BASE_HEIGHT;
        var scale = getScale();
        return new EntityDimensions(BASE_WIDTH * scale, height * scale, false);
    }

    @Override
    public boolean isBaby()
    {
        return false;
    }

    public DragonAnimator getAnimator()
    {
        return animator;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }

    @Override
    public boolean isInWall()
    {
        if (noPhysics) return false;
        else
        {
            var collider = getBoundingBox().deflate(getBbWidth() * 0.2f);
            return BlockPos.betweenClosedStream(collider).anyMatch((pos) ->
            {
                BlockState state = level().getBlockState(pos);
                return !state.isAir() && state.isSuffocating(level(), pos) && Shapes.joinIsNotEmpty(state.getCollisionShape(level(), pos).move(pos.getX(), pos.getY(), pos.getZ()), Shapes.create(collider), BooleanOp.AND);
            });
        }
    }

    @Override
    public @NotNull Vec3 getLightProbePosition(float p_20309_)
    {
        return new Vec3(getX(), getY() + getBbHeight(), getZ());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean hasLocalDriver()
    {
        return getFirstPassenger() instanceof Player p && p.isLocalPlayer();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(MOVEMENT_SPEED, BASE_SPEED_GROUND)
                .add(MAX_HEALTH, BASE_HEALTH)
                .add(FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(KNOCKBACK_RESISTANCE, BASE_KB_RESISTANCE)
                .add(ATTACK_DAMAGE, BASE_DAMAGE)
                .add(FLYING_SPEED, BASE_SPEED_FLYING);
    }

    public void aimBodyAndHeadAt(Vec3 to, float maxYawStep, float maxPitchStep) {
        Vec3 from = this.getEyePosition(1.0F);

        double dx = to.x - from.x;
        double dz = to.z - from.z;
        double dy = to.y - from.y;
        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float wantYaw   = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float wantPitch = (float)(-(Mth.atan2(dy, distXZ) * (180F / Math.PI)));

        float yaw   = Mth.approachDegrees(this.getYRot(), wantYaw, maxYawStep);
        float pitch = Mth.approachDegrees(this.getXRot(), wantPitch, maxPitchStep);

        this.setYRot(yaw);
        this.setXRot(pitch);
        this.setYHeadRot(yaw);
        this.setYBodyRot(yaw);
    }

    private static class RecallLandGoal extends Goal {
        private final HerobrineDragonEntity dragon;
        private int stage = 0;

        public RecallLandGoal(HerobrineDragonEntity dragon) {
            this.dragon = dragon;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return dragon.recallActive
                    && dragon.summoner != null
                    && dragon.summoner.isAlive()
                    && !dragon.isPassenger()
                    && !dragon.hasControllingPassenger();
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void start() {
            stage = 0;

            if (dragon.level() instanceof ServerLevel serverLevel) {
                dragon.recallLandPos = findLandingPosNearSummoner(serverLevel, dragon.summoner);
            }

            if (!dragon.isFlying() && dragon.canFly()) dragon.liftOff();
            dragon.setFlying(true);
            dragon.setNavigation(true);
            dragon.getNavigation().stop();
            dragon.setNoGravity(true);
        }

        @Override
        public void stop() {
            dragon.recallActive = false;
            dragon.recallLandPos = null;
            dragon.setNoGravity(false);
            stage = 0;
        }

        @Override
        public void tick() {
            if (!(dragon.level() instanceof ServerLevel serverLevel)) {
                stop();
                return;
            }

            LivingEntity owner = dragon.summoner;
            if (owner == null || !owner.isAlive()) {
                stop();
                return;
            }

            if (dragon.recallLandPos == null) {
                dragon.recallLandPos = findLandingPosNearSummoner(serverLevel, owner);
            }

            Vec3 land = dragon.recallLandPos;

            dragon.getNavigation().stop();
            dragon.setNoGravity(true);
            if (!dragon.isFlying() && dragon.canFly()) dragon.liftOff();
            dragon.setFlying(true);
            dragon.setNavigation(true);

            double aboveY = Math.max(owner.getY() + 10.0, land.y + 10.0);
            aboveY = clampYForWorld(serverLevel, land.x, land.z, aboveY);
            aboveY = findNearestFreeY(serverLevel, land.x, land.z, aboveY, serverLevel.dimensionType().hasCeiling());
            Vec3 above = new Vec3(land.x, aboveY, land.z);

            if (stage == 0) {
                dragon.getMoveControl().setWantedPosition(above.x, above.y, above.z, 1.8D);
                dragon.aimBodyAndHeadAt(above, 25.0F, 18.0F);

                if (dragon.distanceToSqr(above) < 16.0D) {
                    stage = 1;
                }
                return;
            }


            double landY = clampYForWorld(serverLevel, land.x, land.z, land.y);
            Vec3 landFixed = new Vec3(land.x, landY, land.z);

            dragon.getMoveControl().setWantedPosition(landFixed.x, landFixed.y, landFixed.z, 1.2D);
            dragon.aimBodyAndHeadAt(landFixed, 18.0F, 12.0F);

            if (dragon.distanceToSqr(landFixed) < 9.0D) {
                dragon.setNoGravity(false);
                dragon.setDeltaMovement(Vec3.ZERO);

                if (dragon.recallAutoMount) {
                    owner.startRiding(dragon, true);
                }

                stop();
            }
        }

        private Vec3 findLandingPosNearSummoner(ServerLevel level, LivingEntity owner) {
            BlockPos base = owner.blockPosition();
            boolean hasCeiling = level.dimensionType().hasCeiling();

            for (int r = 0; r <= 3; r++) {
                for (int dx = -r; dx <= r; dx++) {
                    for (int dz = -r; dz <= r; dz++) {
                        BlockPos col = base.offset(dx, 0, dz);
                        double x = col.getX() + 0.5;
                        double z = col.getZ() + 0.5;

                        if (!hasCeiling) {
                            int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
                            double y = groundY + 1.0;

                            if (canFitAt(level, x, y, z)) {
                                return new Vec3(x, y, z);
                            }
                        } else {
                            Vec3 found = findCeilingLandingAtColumn(level, owner, x, z);
                            if (found != null) return found;
                        }
                    }
                }
            }

            if (hasCeiling) {
                Vec3 found = findCeilingLandingAtColumn(level, owner, owner.getX(), owner.getZ());
                if (found != null) return found;

                double y = clampYForWorld(level, owner.getX(), owner.getZ(), owner.getY() + 1.0);
                y = findNearestFreeY(level, owner.getX(), owner.getZ(), y, true);
                return new Vec3(owner.getX(), y, owner.getZ());
            } else {
                BlockPos col = BlockPos.containing(owner.getX(), 0.0, owner.getZ());
                int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
                return new Vec3(owner.getX(), groundY + 1.0, owner.getZ());
            }
        }

        @Nullable
        private Vec3 findCeilingLandingAtColumn(ServerLevel level, LivingEntity owner, double x, double z) {
            double minY = level.getMinBuildHeight() + 6.0;
            int yStart = Mth.floor(owner.getY()) + 8;

            BlockPos col = BlockPos.containing(x, 0.0, z);
            int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();

            double maxY = Math.min(level.getMaxBuildHeight() - 2.0, roofAirY - dragon.getBbHeight() - 2.0);
            if (maxY < minY) maxY = minY;

            yStart = Math.min(yStart, Mth.floor(maxY));
            yStart = Math.max(yStart, Mth.floor(minY));

            int yMin = Mth.floor(minY);

            for (int y = yStart; y >= yMin && (yStart - y) <= 96; y--) {

                if (!canFitAt(level, x, y, z)) continue;
                if (!hasGroundBelow(level, x, y, z)) continue;

                return new Vec3(x, y, z);
            }

            return null;
        }

        private double clampYForWorld(ServerLevel level, double x, double z, double y) {
            double min = level.getMinBuildHeight() + 6.0;
            double max = level.getMaxBuildHeight() - 6.0;

            if (level.dimensionType().hasCeiling()) {
                BlockPos col = BlockPos.containing(x, 0.0, z);
                int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
                max = Math.min(max, roofAirY - dragon.getBbHeight() - 2.0);
            }

            if (max < min) max = min;
            return Mth.clamp(y, min, max);
        }

        private boolean canFitAt(ServerLevel level, double x, double y, double z) {
            AABB movedBox = dragon.getBoundingBox().move(
                    x - dragon.getX(),
                    y - dragon.getY(),
                    z - dragon.getZ()
            );
            return level.noCollision(dragon, movedBox) && !level.containsAnyLiquid(movedBox);
        }

        private boolean hasGroundBelow(ServerLevel level, double x, double y, double z) {
            AABB box = dragon.getBoundingBox().move(
                    x - dragon.getX(),
                    y - dragon.getY(),
                    z - dragon.getZ()
            );

            AABB below = box.move(0.0, -0.25, 0.0);
            return !level.noCollision(dragon, below);
        }

        private double findNearestFreeY(ServerLevel level, double x, double z, double desiredY, boolean preferDown) {
            double yClamped = clampYForWorld(level, x, z, desiredY);

            int base = Mth.floor(yClamped);
            int min = Mth.floor(level.getMinBuildHeight() + 6.0);
            int max = Mth.floor(level.getMaxBuildHeight() - 2.0);

            if (level.dimensionType().hasCeiling()) {
                BlockPos col = BlockPos.containing(x, 0.0, z);
                int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
                max = Math.min(max, Mth.floor(roofAirY - 2.0));
            }

            base = Mth.clamp(base, min, max);

            for (int step = 0; step <= 64; step++) {
                int y1 = preferDown ? (base - step) : (base + step);
                int y2 = preferDown ? (base + step) : (base - step);

                if (y1 >= min && y1 <= max && canFitAt(level, x, y1, z)) {
                    return y1;
                }
                if (step != 0 && y2 >= min && y2 <= max && canFitAt(level, x, y2, z)) {
                    return y2;
                }
            }

            return yClamped;
        }
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if (this.level() instanceof ServerLevel) {
            if (this.summoner != null && this.summoner instanceof Player player && player.isAlive()) {
                player.getCooldowns().addCooldown(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get(), 3600);
                if (player.getPersistentData().contains("DragonUUID")
                        && this.getUUID().equals(player.getPersistentData().getUUID("DragonUUID"))) {
                    player.getPersistentData().remove("DragonUUID");
                }
            }
        }
        super.die(source);
    }

    public static class DragonOrbitLeaderGoal extends Goal {
        private static final double TWO_PI = Math.PI * 2.0;

        private static final double ORBIT_RING_INNER_FACTOR = 0.80;
        private static final double ORBIT_RING_OUTER_FACTOR = 1.35;

        private final HerobrineDragonEntity dragon;
        private final Level level;

        private final double baseSpeed;
        private final float orbitRadiusMin;
        private final float orbitRadiusMax;
        private final float farCatchUpDistance;

        private LivingEntity leader;

        private int updateCooldownTicks;

        private double orbitAngleRadians;
        private int orbitDirectionSign;

        private double orbitRadiusCurrent;
        private double orbitRadiusDesired;

        private double orbitAngularSpeedCurrent;
        private double orbitAngularSpeedDesired;

        private double orbitBaseHeightCurrent;
        private double orbitBaseHeightDesired;

        private double verticalWaveAmplitude;
        private double verticalWaveSpeed;
        private double verticalWavePhase;

        private int paramsTimeToLiveTicks;

        private double yJitterCurrent;
        private double yJitterDesired;

        public DragonOrbitLeaderGoal(
                HerobrineDragonEntity dragon,
                double baseSpeed,
                float orbitRadiusMin,
                float orbitRadiusMax,
                float farCatchUpDistance
        ) {
            this.dragon = dragon;
            this.level = dragon.level();
            this.baseSpeed = baseSpeed;
            this.orbitRadiusMin = orbitRadiusMin;
            this.orbitRadiusMax = orbitRadiusMax;
            this.farCatchUpDistance = farCatchUpDistance;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Nullable
        private LivingEntity resolveOrbitCenter(HerobrineDragonEntity herobrineDragonEntity) {
            LivingEntity livingEntity = herobrineDragonEntity.getSummoner();
            if (livingEntity == null) return herobrineDragonEntity.getOwner();

            if (!(livingEntity instanceof Player)
                    && (herobrineDragonEntity.hasPassenger(livingEntity)
                    || (livingEntity.getVehicle() instanceof HerobrineDragonEntity herobrineDragon
                    && !herobrineDragon.getUUID().equals(herobrineDragonEntity.getUUID())))) {
                LivingEntity target = null;

                if (livingEntity instanceof Mob mob) {
                    target = mob.getTarget();
                }
                if (target == null || !target.isAlive()) target = livingEntity.getLastHurtMob();
                if (target == null || !target.isAlive()) target = livingEntity.getLastHurtByMob();

                if (target == null) {
                    livingEntity.stopRiding();
                }
                return target;
            }

            return livingEntity;
        }

        @Override
        public boolean canUse() {
            LivingEntity resolved = resolveOrbitCenter(dragon);
            if (resolved == null) return false;
            if (!resolved.isAlive()) return false;
            if (resolved.isSpectator()) return false;

            if (dragon.isLeashed()) return false;
            if (dragon.isPassenger()) return false;
            if (dragon.hasControllingPassenger()) return false;
            if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;
            if (dragon.isRecallActive()) return false;

            leader = resolved;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity resolved = resolveOrbitCenter(dragon);
            if (resolved == null) return false;
            if (!resolved.isAlive()) return false;
            if (resolved.isSpectator()) return false;

            if (dragon.isLeashed()) return false;
            if (dragon.isPassenger()) return false;
            if (dragon.hasControllingPassenger()) return false;
            if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;
            if (dragon.isRecallActive()) return false;

            leader = resolved;
            return true;
        }

        @Override
        public void start() {
            updateCooldownTicks = 0;

            orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
            orbitDirectionSign = dragon.getRandom().nextBoolean() ? 1 : -1;

            orbitRadiusCurrent = orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);

            orbitAngularSpeedCurrent = orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.045, 0.11);

            orbitBaseHeightCurrent = orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(14);

            verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 7.0);
            verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.018, 0.045);
            verticalWavePhase = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);

            paramsTimeToLiveTicks = 80 + dragon.getRandom().nextInt(140);

            dragon.getNavigation().stop();
            yJitterCurrent = yJitterDesired = Mth.nextDouble(dragon.getRandom(), -6.0, 6.0);
        }

        @Override
        public void stop() {
            leader = null;
            dragon.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (leader == null) return;

            dragon.getLookControl().setLookAt(leader, 10.0F, dragon.getMaxHeadXRot());

            orbitAngleRadians = wrapAngle(orbitAngleRadians + orbitDirectionSign * orbitAngularSpeedCurrent);
            verticalWavePhase = wrapAngle(verticalWavePhase + verticalWaveSpeed);

            if (--paramsTimeToLiveTicks <= 0 || dragon.getRandom().nextInt(220) == 0) {
                rerollOrbitParameters();
            }

            orbitRadiusCurrent = Mth.lerp(0.08, orbitRadiusCurrent, orbitRadiusDesired);
            orbitAngularSpeedCurrent = Mth.lerp(0.08, orbitAngularSpeedCurrent, orbitAngularSpeedDesired);
            orbitBaseHeightCurrent = Mth.lerp(0.08, orbitBaseHeightCurrent, orbitBaseHeightDesired);

            if (--updateCooldownTicks > 0) return;
            updateCooldownTicks = adjustedTickDelay(2);

            Vec3 leaderPosition = leader.position();
            Vec3 dragonOffsetFromLeader = dragon.position().subtract(leaderPosition);

            double distanceToLeader = dragonOffsetFromLeader.length();
            double distanceToLeaderSquared = distanceToLeader * distanceToLeader;
            double farCatchUpDistanceSquared = (double) farCatchUpDistance * (double) farCatchUpDistance;

            if (distanceToLeaderSquared >= farCatchUpDistanceSquared) {
                if (!dragon.isFlying() && dragon.canFly()) {
                    dragon.liftOff();
                }

                double catchUpY = computeDesiredY(leaderPosition.x, leaderPosition.z, leaderPosition.y) + 6.0;
                catchUpY = clampYForWorld(leaderPosition.x, leaderPosition.z, catchUpY);
                catchUpY = findNearestFreeY(leaderPosition.x, leaderPosition.z, catchUpY, hasCeiling(), 24);

                Vec3 catchUpTarget = new Vec3(leaderPosition.x, catchUpY, leaderPosition.z);
                setMoveTarget(catchUpTarget, baseSpeed * 1.65);
                return;
            }

            if (!dragon.isFlying() && dragon.canFly()) {
                if (dragon.onGround() || (leader.getY() - dragon.getY()) > 2.0 || distanceToLeader > orbitRadiusMin) {
                    dragon.liftOff();
                }
            }

            double orbitRingMinDistance = orbitRadiusMin * ORBIT_RING_INNER_FACTOR;
            double orbitRingMaxDistance = orbitRadiusMax * ORBIT_RING_OUTER_FACTOR;

            Vec3 targetPosition;

            if (distanceToLeader < orbitRingMinDistance || distanceToLeader > orbitRingMaxDistance) {
                Vec3 outwardDirection = distanceToLeader > 1.0E-4 ? dragonOffsetFromLeader.scale(1.0 / distanceToLeader) : new Vec3(1.0, 0.0, 0.0);
                Vec3 ringPoint = leaderPosition.add(outwardDirection.scale(orbitRadiusDesired));
                double ringY = computeDesiredY(ringPoint.x, ringPoint.z, leaderPosition.y);
                targetPosition = new Vec3(ringPoint.x, ringY, ringPoint.z);
            } else {
                double orbitX = leaderPosition.x + Math.cos(orbitAngleRadians) * orbitRadiusCurrent;
                double orbitZ = leaderPosition.z + Math.sin(orbitAngleRadians) * orbitRadiusCurrent;
                double orbitY = computeDesiredY(orbitX, orbitZ, leaderPosition.y);
                targetPosition = new Vec3(orbitX, orbitY, orbitZ);
            }
            if (!canMoveTo(targetPosition)) {
                boolean preferDown = hasCeiling();

                double yFixed = findNearestFreeY(targetPosition.x, targetPosition.z, targetPosition.y, preferDown, 32);
                Vec3 fixed = new Vec3(targetPosition.x, yFixed, targetPosition.z);

                if (canMoveTo(fixed)) {
                    targetPosition = fixed;
                } else {
                    // thử offset dọc (Nether ưu tiên xuống trước)
                    double[] offs = preferDown
                            ? new double[]{-6.0, -10.0, -14.0, 6.0, 10.0, 14.0}
                            : new double[]{6.0, 10.0, 14.0, -6.0, -10.0, -14.0};

                    boolean found = false;
                    for (double off : offs) {
                        double yy = clampYForWorld(targetPosition.x, targetPosition.z, targetPosition.y + off);
                        Vec3 t = new Vec3(targetPosition.x, yy, targetPosition.z);
                        if (canMoveTo(t)) {
                            targetPosition = t;
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
                        double fallbackY = clampYForWorld(leaderPosition.x, leaderPosition.z, leaderPosition.y + orbitBaseHeightCurrent + 18.0);
                        fallbackY = findNearestFreeY(leaderPosition.x, leaderPosition.z, fallbackY, preferDown, 32);
                        targetPosition = new Vec3(leaderPosition.x, fallbackY, leaderPosition.z);
                    }
                }
            }

            setMoveTarget(targetPosition, baseSpeed);
            yJitterCurrent = Mth.lerp(0.05, yJitterCurrent, yJitterDesired);
        }

        private void setMoveTarget(Vec3 target, double speed) {
            if (dragon.isFlying()) {
                dragon.getNavigation().stop();
                dragon.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
            } else {
                dragon.getNavigation().moveTo(target.x, target.y, target.z, speed);
            }
        }

        private void rerollOrbitParameters() {
            if (dragon.getRandom().nextFloat() < 0.30f) {
                orbitDirectionSign *= -1;
            }

            orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);
            orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.04, 0.13);

            orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(18);

            verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 8.0);
            verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.016, 0.05);

            if (dragon.getRandom().nextFloat() < 0.35f) {
                orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
            }

            paramsTimeToLiveTicks = 70 + dragon.getRandom().nextInt(160);
            yJitterDesired = Mth.nextDouble(dragon.getRandom(), -10.0, 10.0);
        }

        private double computeDesiredY(double x, double z, double leaderY) {
            double y = leaderY + orbitBaseHeightCurrent;

            y += Math.sin(verticalWavePhase) * verticalWaveAmplitude;
            y += yJitterCurrent;
            y = clampYForWorld(x, z, y);
            y = findNearestFreeY(x, z, y, hasCeiling(), 24);

            return y;
        }

        private boolean hasCeiling() {
            return level.dimensionType().hasCeiling();
        }

        private double minY() {
            return level.getMinBuildHeight() + 6.0;
        }

        private double maxY(double x, double z) {
            double max = level.getMaxBuildHeight() - 6.0;

            if (hasCeiling()) {
                BlockPos col = BlockPos.containing(x, 0.0, z);
                int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();

                max = Math.min(max, roofAirY - dragon.getBbHeight() - 2.0);
            }

            if (max < minY()) max = minY();
            return max;
        }

        private double clampYForWorld(double x, double z, double y) {
            return Mth.clamp(y, minY(), maxY(x, z));
        }

        private double findNearestFreeY(double x, double z, double desiredY, boolean preferDown, int maxSteps) {
            double yClamped = clampYForWorld(x, z, desiredY);

            int base = Mth.floor(yClamped);
            int min = Mth.floor(minY());
            int max = Mth.floor(maxY(x, z));

            base = Mth.clamp(base, min, max);

            for (int step = 0; step <= maxSteps; step++) {
                int y1 = preferDown ? (base - step) : (base + step);
                int y2 = preferDown ? (base + step) : (base - step);

                if (y1 >= min && y1 <= max && canMoveTo(new Vec3(x, (double) y1, z))) {
                    return (double) y1;
                }
                if (step != 0 && y2 >= min && y2 <= max && canMoveTo(new Vec3(x, (double) y2, z))) {
                    return (double) y2;
                }
            }

            return yClamped;
        }

        private boolean canMoveTo(Vec3 pos) {
            Vec3 delta = pos.subtract(dragon.position());
            AABB moved = dragon.getBoundingBox().move(delta);
            return level.noCollision(dragon, moved) && !level.containsAnyLiquid(moved);
        }

        private static double wrapAngle(double angle) {
            angle %= TWO_PI;
            return angle < 0.0 ? angle + TWO_PI : angle;
        }
    }

    public static class DragonMoveController extends MoveControl
    {
        private final HerobrineDragonEntity dragon;

        public DragonMoveController(HerobrineDragonEntity dragon)
        {
            super(dragon);
            this.dragon = dragon;
        }

        @Override
        public void tick()
        {
            if (!dragon.isFlying())
            {
                super.tick();
                return;
            }

            if (operation == Operation.MOVE_TO)
            {
                operation = Operation.WAIT;
                double xDif = wantedX - mob.getX();
                double yDif = wantedY - mob.getY();
                double zDif = wantedZ - mob.getZ();
                double sq = xDif * xDif + yDif * yDif + zDif * zDif;
                if (sq < (double) 2.5000003E-7F)
                {
                    mob.setYya(0.0F);
                    mob.setZza(0.0F);
                    return;
                }

                float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.FLYING_SPEED));
                double distSq = Math.sqrt(xDif * xDif + zDif * zDif);
                mob.setSpeed(speed);
                if (Math.abs(yDif) > (double) 1.0E-5F || Math.abs(distSq) > (double) 1.0E-5F)
                    mob.setYya((float) yDif * speed);

                float yaw = (float) (Mth.atan2(zDif, xDif) * (double) (180F / (float) Math.PI)) - 90.0F;
                mob.setYRot(rotlerp(mob.getYRot(), yaw, 6));
            }
            else
            {
                mob.setYya(0);
                mob.setZza(0);
            }
        }
    }

    public static class DragonBodyController extends BodyRotationControl
    {
        private final HerobrineDragonEntity dragon;

        public DragonBodyController(HerobrineDragonEntity dragon)
        {
            super(dragon);
            this.dragon = dragon;
        }

        @Override
        public void clientTick()
        {
            dragon.yBodyRot = dragon.getYRot();
            dragon.yHeadRot = Mth.rotateIfNecessary(dragon.yHeadRot, dragon.yBodyRot, dragon.getMaxHeadYRot());
        }
    }
}