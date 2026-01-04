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
import com.pla.annoyingvillagers.init.AnnoyingVillagersModKeyMappings;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.util.DragonBodyController;
import com.pla.annoyingvillagers.util.DragonOrbitLeaderGoal;
import com.pla.annoyingvillagers.util.DragonMoveController;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
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
    // base attributes
    public static final double BASE_SPEED_GROUND = 0.3; // actual speed varies from ground friction
    public static final double BASE_SPEED_FLYING = 0.32;
    public static final double BASE_DAMAGE = 8;
    public static final double BASE_HEALTH = 60;
    public static final double BASE_FOLLOW_RANGE = 16;
    public static final int BASE_KB_RESISTANCE = 1;
    public static final float BASE_WIDTH = 2.75f; // adult sizes
    public static final float BASE_HEIGHT = 2.75f;

    public static final int GROUND_CLEARENCE_THRESHOLD = 3; // height in blocks (multiplied by scale of dragon)

    // server/client delegates
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

    private boolean flyThroughActive = false;
    private int flyThroughTicks = 0;
    private int flyThroughMaxTicks = 0;
    private int flyThroughCooldown = 0;
    private double flyThroughSpeed = 0.0D;
    private Vec3 flyThroughEnd = null;

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
    protected void registerGoals() // TODO: Much Smarter AI and features
    {
        goalSelector.addGoal(1, new FloatGoal(this));
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
        if (isFlying()) return !onGround(); // more natural landings
        return canFly() && !isInWater() && !isNearGround();
    }

    /**
     * Returns true if the entity is flying.
     */
    public boolean isFlying()
    {
        return flying;
    }

    /**
     * Set the flying flag of the entity.
     */
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

    public boolean isFlyThroughActive() {
        return flyThroughActive;
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

    private void aimBodyAndHeadAt(LivingEntity target, float maxYawStep, float maxPitchStep) {
        Vec3 from = this.getEyePosition(1.0F);
        Vec3 to   = target.getEyePosition(1.0F);

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

    public Vec3 beamMouthPos(float partial) {
        Vec3 eye = new Vec3(
                Mth.lerp(partial, this.xOld, this.getX()),
                Mth.lerp(partial, this.yOld, this.getY()) + this.getEyeHeight(),
                Mth.lerp(partial, this.zOld, this.getZ())
        );

        float headYaw   = Mth.lerp(partial, this.yHeadRotO, this.yHeadRot);
        float headPitch = Mth.lerp(partial, this.xRotO, this.getXRot());
        Vec3 look = Vec3.directionFromRotation(headPitch, headYaw);

        double baseForward = Math.max(1.0, this.getBbWidth() * 0.7);

        double extraForward = 5.2 * this.getScale();
        double extraUp = 4.0 * this.getScale();

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
    }

    private void aimBodyAndHeadAt(Vec3 to, float maxYawStep, float maxPitchStep) {
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

    public void startFlyThroughSummoner() {
        if (level().isClientSide()) return;
        if (flyThroughCooldown > 0) return;

        if (!summoner.isAlive()) return;

        this.breathHoverTimeToLiveTicks = 0;
        this.breathHoverTarget = null;
        this.breathHoverPos = null;

        // force flight mode
        if (!this.isFlying() && this.canFly()) this.liftOff();
        this.setFlying(true);
        this.setNavigation(true);
        this.getNavigation().stop();
        this.setNoGravity(true);

        Vec3 from = this.getEyePosition(1.0F);
        Vec3 target = summoner.getEyePosition(1.0F);

        Vec3 dir = target.subtract(from);
        if (dir.lengthSqr() < 1e-6) dir = this.getLookAngle();
        dir = dir.normalize();

        double passDistance = 12.0D * this.getScale(); // TUNE: how far past the player
        this.flyThroughEnd = target.add(dir.scale(passDistance));

        this.flyThroughActive = true;
        this.flyThroughTicks = 0;
        this.flyThroughMaxTicks = 45;
        this.flyThroughSpeed = 2.2D;
        this.flyThroughCooldown = 40;
    }

    private void stopFlyThrough() {
        this.flyThroughActive = false;
        this.flyThroughEnd = null;
        this.flyThroughSpeed = 0.0D;
        this.flyThroughTicks = 0;
        this.flyThroughMaxTicks = 0;

        this.setNoGravity(false);
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

    @Override
    public void tick()
    {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel)
        {
            if (flyThroughCooldown > 0) flyThroughCooldown--;

            if (flyThroughActive) {
                flyThroughTicks++;

                if (!this.summoner.isAlive() || flyThroughEnd == null) {
                    stopFlyThrough();
                } else {
                    if (!this.isFlying() && this.canFly()) this.liftOff();
                    this.setFlying(true);
                    this.setNavigation(true);
                    this.getNavigation().stop();
                    this.setNoGravity(true);

                    this.getMoveControl().setWantedPosition(
                            flyThroughEnd.x, flyThroughEnd.y, flyThroughEnd.z,
                            flyThroughSpeed
                    );

                    aimBodyAndHeadAt(flyThroughEnd, 25.0F, 18.0F);
                    if (this.distanceToSqr(flyThroughEnd) < 9.0D || flyThroughTicks >= flyThroughMaxTicks) {
                        stopFlyThrough();
                        summoner.startRiding(this);
                        navigation.stop();
                    }
                }
            }

            if (breathHoverTimeToLiveTicks > 0) {
                breathHoverTimeToLiveTicks--;

                if (breathHoverTarget != null && !breathHoverTarget.isAlive()) {
                    breathHoverTarget = null;
                }

                if (!hasControllingPassenger() && !isPassenger() && !isLeashed()) {
                    if (breathHoverPos == null) breathHoverPos = this.position();

                    if (!this.isFlying() && this.canFly()) this.liftOff();
                    this.setFlying(true);
                    this.setNavigation(true);

                    this.getNavigation().stop();
                    this.setNoGravity(true);

                    if (breathHoverTarget != null) {
                        aimBodyAndHeadAt(breathHoverTarget, 10.0F, 6.0F);
                    }

                    double y = breathHoverPos.y + Math.sin((double) this.tickCount * 0.25) * 0.25;
                    this.getMoveControl().setWantedPosition(breathHoverPos.x, y, breathHoverPos.z, 0.12D);

                    Vec3 dv = this.getDeltaMovement();
                    this.setDeltaMovement(dv.x * 0.2, dv.y * 0.6, dv.z * 0.2);
                }

                if (breathHoverTimeToLiveTicks <= 0) {
                    this.setNoGravity(false);
                    breathHoverTarget = null;
                    breathHoverPos = null;
                }
            } else {
                if (this.isNoGravity()) this.setNoGravity(false);
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
        else
        {
            // update animations on the client
            animator.tick();
        }

        // update nearGround state when moving for flight and animation logic
        nearGround = onGround() || !level().noCollision(this, new AABB(getX(), getY(), getZ(), getX(), getY() - (GROUND_CLEARENCE_THRESHOLD * getScale()), getZ()));

        // update flying state based on the distance to the ground
        boolean flying = shouldFly();
        if (flying != isFlying())
        {
            setFlying(flying);

            // update pathfinding method
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
                // Move relative to yaw - handled in the move controller or by driver
                moveRelative(getSpeed(), vec3);
                move(MoverType.SELF, getDeltaMovement());
                if (getDeltaMovement().lengthSqr() < 0.1) // we're not actually going anywhere, bob up and down.
                    setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
                setDeltaMovement(getDeltaMovement().scale(0.9f)); // smoothly slow down
            }

            calculateEntityAnimation(true);
        }
        else super.travel(vec3);
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player driver, Vec3 move)
    {
        double moveSideways = move.x;
        double moveY = move.y;
        double moveForward = Math.min(Math.abs(driver.zza) + Math.abs(driver.xxa), 1);

        if (isFlying() && hasLocalDriver())
        {
            moveForward = moveForward > 0? moveForward : 0;
            if (driver.jumping) moveY = 1;
            else if (AnnoyingVillagersModKeyMappings.DRAGON_FLIGHT_DESCENT_KEY.isDown()) moveY = -1;
            else if (moveForward > 0) moveY = -driver.getXRot() / 90; // normalize from -1 to 1
        }

        // mimic dogshit implementation of AI movement vectors
        // the way this works is that it will mimic how setSpeed in Mob works:
        // it sets the normal speed variable,
        // and then sets the walk forward variable to the same value.
        // so if speed is 0.3, walk forward will also be 0.3 instead of 1.0.
        // so when moveRelative calculates movespeed, (walkforward * speed) we get 0.15.
        // so I guess we should do it to.
        var speed = getRiddenSpeed(driver);
        return new Vec3(moveSideways * speed, moveY * speed, moveForward * speed);
    }

    @Override
    protected void tickRidden(Player driver, Vec3 move)
    {
        // rotate head to match driver.
        float yaw = driver.yHeadRot;
        if (move.z > 0) // rotate in the direction of the drivers controls
            yaw += (float) Mth.atan2(driver.zza, driver.xxa) * (180f / (float) Math.PI) - 90;
        yHeadRot = yaw;
        setXRot(driver.getXRot() * 0.68f);

        // rotate body towards the head
        setYRot(Mth.rotateIfNecessary(yHeadRot, getYRot(), 4));

        if (isControlledByLocalInstance())
        {
            if (!isFlying() && canFly() && driver.jumping) liftOff();
        }
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
        // stronger jumps for easier lift-offs
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
        // unmount any riding entities
        ejectPassengers();

        // freeze at place
        setDeltaMovement(Vec3.ZERO);
        setYRot(yRotO);
        setYHeadRot(yHeadRotO);

        if (deathTime >= getMaxDeathTime()) remove(RemovalReason.KILLED); // actually delete entity after the time is up

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

        // override sound type if the top block is snowy
        var soundType = state.getSoundType();
        if (level().getBlockState(entityPos.above()).getBlock() == Blocks.SNOW)
            soundType = Blocks.SNOW.getSoundType(state, level(), entityPos, this);

        // play stomping for bigger dragons
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
        // this better doesn't happen...
        return false;
    }

    public void onWingsDown(float speed)
    {
        if (!isInWater())
        {
            // play wing sounds
            float pitch = (1 - speed);
            float volume = 0.3f + (1 - speed) * 0.2f;
            pitch *= getVoicePitch();
            volume *= getSoundVolume();
            level().playLocalSound(getX(), getY(), getZ(), getWingsSound(), SoundSource.VOICE, volume, pitch, true);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(@NotNull DamageSource src, float par2)
    {
        if (isInvulnerableTo(src)) return false;
        if (src.getEntity() == summoner) return false;
        return super.hurt(src, par2);
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMate(@NotNull Animal mate)
    {
        if (mate == this) return false; // No. Just... no.
        if (!(mate instanceof HerobrineDragonEntity dragonMate)) return false;
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

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Override
    public LivingEntity getControllingPassenger()
    {
        Entity rider = getFirstPassenger();
        return rider instanceof LivingEntity livingEntity ? livingEntity : null;
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

            // fix rider rotation
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

    /**
     * Returns the entity's health relative to the maximum health.
     *
     * @return health normalized between 0 and 1
     */
    public float getHealthFraction()
    {
        return getHealth() / getMaxHealth();
    }

    public int getMaxDeathTime()
    {
        return 120;
    }

    /**
     * Public wrapper for protected final setScale(), used by DragonLifeStageHelper.
     */
    @Override
    public void refreshDimensions()
    {
        double posXTmp = getX();
        double posYTmp = getY();
        double posZTmp = getZ();
        boolean onGroundTmp = onGround();

        super.refreshDimensions();

        // workaround for a vanilla bug; the position is apparently not set correcty
        // after changing the entity size, causing asynchronous server/client positioning
        setPos(posXTmp, posYTmp, posZTmp);

        // otherwise, setScale stops the dragon from landing while it is growing
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
            // Reduce suffocation risks. They're fat and clusmy.
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
}