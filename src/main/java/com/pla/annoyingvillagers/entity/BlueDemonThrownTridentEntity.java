package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BlueDemonThrownTridentEntity extends ThrownTrident {
    private TridentMode mode = TridentMode.DEFAULT;

    private static final int MAX_GROUNDED_TRIDENTS_PER_OWNER = 20;
    private static final double OWNER_BOX_HALF_SIZE = 25.0D;
    private static final String TAG_SPAWN_SEQUENCE = "BlueDemonSpawnSequence";
    private static final String TAG_OWNER_SHOT_COUNTER = "BlueDemonOwnerShotCounter";

    private static final int RELAUNCH_ANIMATION_DURATION = 20;

    private boolean relaunchAnimationActive = false;
    private int relaunchAnimationTick = 0;
    private Vec3 relaunchAnimationStart = Vec3.ZERO;
    private Vec3 relaunchAnimationEnd = Vec3.ZERO;
    private boolean loadedFromDisk = false;

    @Nullable
    private UUID queuedTargetUUID = null;
    @Nullable
    private Vec3 queuedFallbackDirection = null;
    private float queuedLaunchSpeed = 0.0F;

    private long spawnSequence;

    private boolean specialImpactTriggered = false;
    private static final EntityDataAccessor<Byte> DATA_STUCK_FACE =
            SynchedEntityData.defineId(BlueDemonThrownTridentEntity.class, EntityDataSerializers.BYTE);

    @Nullable
    public Direction getStuckFace() {
        byte value = this.entityData.get(DATA_STUCK_FACE);
        return value == (byte)255 ? null : Direction.from3DDataValue(value);
    }

    public void setStuckFace(@Nullable Direction direction) {
        this.entityData.set(DATA_STUCK_FACE, direction == null ? (byte)255 : (byte) direction.get3DDataValue());
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    public BlueDemonThrownTridentEntity(EntityType<? extends ThrownTrident> type, Level level) {
        super(type, level);
    }

    public BlueDemonThrownTridentEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(AnnoyingVillagersModEntities.BLUE_DEMON_THROWN_TRIDENT.get(), level);
    }

    public BlueDemonThrownTridentEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(level, shooter, stack);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STUCK_FACE, (byte)255);
    }

    public void setMode(TridentMode mode) {
        this.mode = mode == null ? TridentMode.DEFAULT : mode;
    }

    @Nullable
    public LivingEntity getOwnerLiving() {
        return this.getOwner() instanceof LivingEntity living ? living : null;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();

        float damage = 8.0F;
        DamageSource damageSource = this.damageSources().trident(this, owner == null ? this : owner);

        this.dealtDamage = true;
        SoundEvent sound = SoundEvents.TRIDENT_HIT;

        boolean hurtSuccess = target.hurt(damageSource, damage);

        if (hurtSuccess) {
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.addEffect(new MobEffectInstance(
                        AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                        20,
                        1
                ));
            }

            if (!this.level().isClientSide && !this.specialImpactTriggered) {
                this.specialImpactTriggered = true;
                this.handleModeImpact(target.blockPosition(), target);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(sound, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.loadedFromDisk) {
            this.discard();
            return;
        }

        if (!this.level().isClientSide && this.relaunchAnimationActive) {
            this.baseTick();
            this.tickAnimatedRelaunch();
            this.tickElectricEffects();
            return;
        }

        super.tick();

        if (!this.level().isClientSide && this.inGround && this.tickCount % 10 == 0) {
            this.discardIfGroundedAndFarFromOwner();
            if (!this.isAlive()) {
                return;
            }
        }

        this.tickElectricEffects();
    }

    protected void handleModeImpact(BlockPos pos, @Nullable Entity hitTarget) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        switch (this.mode) {
            case DEFAULT -> {
            }
            case LIGHTNING -> {
                this.spawnTridentLightning(serverLevel, pos, hitTarget);
            }
            case EXPLOSION -> {
                this.spawnTridentExplosion(serverLevel, pos, hitTarget);
            }
        }
    }

    protected void spawnTridentLightning(ServerLevel serverLevel, BlockPos pos, Entity hitTarget) {
        TridentLightningBolt lightning = new TridentLightningBolt(
                AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(),
                serverLevel
        );

        lightning.moveTo(Vec3.atBottomCenterOf(pos));
        lightning.setDamage(5.0F);

        LivingEntity owner = this.getOwnerLiving();
        if (owner != null) {
            lightning.setOwner(owner);
        }

        serverLevel.addFreshEntity(lightning);
    }

    protected void spawnSuperTridentLightning(ServerLevel serverLevel, BlockPos pos, Entity hitTarget) {
        TridentLightningBolt lightning = new TridentLightningBolt(
                AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(),
                serverLevel
        );

        lightning.moveTo(Vec3.atBottomCenterOf(pos));
        lightning.setSuperLightning(true);
        lightning.setDamage(15.0F);

        LivingEntity owner = this.getOwnerLiving();
        if (owner != null) {
            lightning.setOwner(owner);
        }

        serverLevel.addFreshEntity(lightning);
    }

    protected void spawnTridentExplosion(ServerLevel serverLevel, BlockPos pos, Entity hitTarget) {
        Entity owner = this.getOwner();

        serverLevel.explode(
                owner,
                this.getX(),
                this.getY(),
                this.getZ(),
                2.5F,
                Level.ExplosionInteraction.TNT
        );
    }

    public void assignSpawnSequence(@NotNull LivingEntity owner) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        CompoundTag ownerData = owner.getPersistentData();
        int shotCounter = (ownerData.getInt(TAG_OWNER_SHOT_COUNTER) + 1) & 0xFFFF;
        ownerData.putInt(TAG_OWNER_SHOT_COUNTER, shotCounter);

        this.spawnSequence = (serverLevel.getGameTime() << 16) | (shotCounter & 0xFFFFL);
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return source.is(DamageTypeTags.IS_EXPLOSION) || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public long getSpawnSequence() {
        return this.spawnSequence;
    }

    private static AABB makeOwnerGroundBox(Entity owner) {
        Level level = owner.level();
        return new AABB(
                owner.getX() - OWNER_BOX_HALF_SIZE,
                level.getMinBuildHeight(),
                owner.getZ() - OWNER_BOX_HALF_SIZE,
                owner.getX() + OWNER_BOX_HALF_SIZE,
                level.getMaxBuildHeight(),
                owner.getZ() + OWNER_BOX_HALF_SIZE
        );
    }

    private boolean isGroundedForLimit() {
        return this.inGround;
    }

    private boolean hasSameOwner(UUID ownerUuid) {
        Entity owner = this.getOwner();
        return owner != null && owner.getUUID().equals(ownerUuid);
    }

    private boolean isOutsideOwnerGroundBox(Entity owner) {
        return Math.abs(this.getX() - owner.getX()) > OWNER_BOX_HALF_SIZE
                || Math.abs(this.getZ() - owner.getZ()) > OWNER_BOX_HALF_SIZE;
    }

    public void trimOldGroundedTridentsAroundOwnerOnSpawn() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        LivingEntity owner = this.getOwnerLiving();
        if (owner == null) {
            return;
        }

        UUID ownerUuid = owner.getUUID();

        List<BlueDemonThrownTridentEntity> grounded = serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                makeOwnerGroundBox(owner),
                trident -> trident != this
                        && trident.isAlive()
                        && trident.isGroundedForLimit()
                        && trident.hasSameOwner(ownerUuid)
        );

        int removeCount = grounded.size() - MAX_GROUNDED_TRIDENTS_PER_OWNER + 1;
        if (removeCount <= 0) {
            return;
        }

        grounded.sort(
                Comparator.comparingLong(BlueDemonThrownTridentEntity::getSpawnSequence)
                        .thenComparing(BlueDemonThrownTridentEntity::getUUID)
        );

        for (int i = 0; i < removeCount; i++) {
            grounded.get(i).discard();
        }
    }

    private void discardIfGroundedAndFarFromOwner() {
        if (!this.inGround) {
            return;
        }

        LivingEntity owner = this.getOwnerLiving();
        if (owner != null && this.isOutsideOwnerGroundBox(owner)) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);

        this.setGlowingTag(false);

        if (this.level().isClientSide) {
            return;
        }

        this.setStuckFace(result.getDirection());

        if (result.getDirection() == Direction.UP) {
            float[] pitchChoices = new float[]{-90.0F, -60.0F, -45.0F, -30.0F};
            float[] yawChoices = new float[]{-90.0F, -60.0F, -45.0F, -30.0F, 0.0F, 30.0F, 45.0F, 60.0F, 90.0F};

            float pitch = pitchChoices[this.random.nextInt(pitchChoices.length)];
            float yawOffset = yawChoices[this.random.nextInt(yawChoices.length)];

            this.setXRot(pitch);
            this.xRotO = this.getXRot();

            this.setYRot(this.getYRot() + yawOffset);
            this.yRotO = this.getYRot();
        }

        if (!this.specialImpactTriggered) {
            this.specialImpactTriggered = true;
            this.handleModeImpact(result.getBlockPos(), null);
        }

        this.discardIfGroundedAndFarFromOwner();
    }

    public boolean isGroundedTrident() {
        return this.inGround;
    }

    public boolean belongsToOwner(@NotNull LivingEntity owner) {
        Entity projectileOwner = this.getOwner();
        return projectileOwner != null && projectileOwner.getUUID().equals(owner.getUUID());
    }

    public void relaunchTowards(@NotNull Vec3 direction, float speed, float inaccuracy) {
        if (direction.lengthSqr() < 1.0E-7D) {
            return;
        }

        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        Vec3 normalized = direction.normalize();
        Vec3 newPos = this.position().add(normalized.scale(0.35D));

        this.setStuckFace(null);
        this.inGround = false;
        this.inGroundTime = 0;
        this.shakeTime = 0;
        this.dealtDamage = false;
        this.specialImpactTriggered = false;

        this.setNoPhysics(false);
        this.setNoGravity(false);
        this.hasImpulse = true;

        this.setGlowingTag(true);

        this.setPos(newPos.x, newPos.y + 0.05D, newPos.z);
        this.setDeltaMovement(Vec3.ZERO);

        this.setYRot((float) (Mth.atan2(normalized.x, normalized.z) * (180F / Math.PI)));
        this.setXRot((float) (Mth.atan2(normalized.y, Math.sqrt(normalized.x * normalized.x + normalized.z * normalized.z)) * (180F / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        this.shoot(normalized.x, normalized.y, normalized.z, speed, inaccuracy);
        this.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 1.0F);
    }

    public void summonSuperLightningAtSelf() {
        if (this.level() instanceof ServerLevel serverLevel) {
            this.spawnSuperTridentLightning(serverLevel, BlockPos.containing(this.position()), null);
        }
    }

    public void summonLightningAtSelf() {
        if (this.level() instanceof ServerLevel serverLevel) {
            this.spawnTridentLightning(serverLevel, BlockPos.containing(this.position()), null);
        }
    }

    public void beginAnimatedRelaunch(@Nullable LivingEntity target, @Nullable Vec3 fallbackDirection, float speed, float inaccuracy) {
        if (this.relaunchAnimationActive) {
            return;
        }

        int offsetX = this.random.nextInt(3) - 1;
        int offsetZ = this.random.nextInt(3) - 1;

        if (offsetX == 0 && offsetZ == 0) {
            if (this.random.nextBoolean()) {
                offsetX = this.random.nextBoolean() ? 1 : -1;
            } else {
                offsetZ = this.random.nextBoolean() ? 1 : -1;
            }
        }

        double riseY = 1.0D + this.random.nextDouble() * 2.0D;

        this.relaunchAnimationStart = this.position();
        this.relaunchAnimationEnd = this.relaunchAnimationStart.add(offsetX, riseY, offsetZ);

        this.queuedTargetUUID = target != null ? target.getUUID() : null;
        this.queuedFallbackDirection = fallbackDirection != null && fallbackDirection.lengthSqr() > 1.0E-7D
                ? fallbackDirection.normalize()
                : null;

        this.queuedLaunchSpeed = speed;

        this.relaunchAnimationTick = 0;
        this.relaunchAnimationActive = true;

        this.setStuckFace(null);
        this.inGround = false;
        this.inGroundTime = 0;
        this.shakeTime = 0;
        this.dealtDamage = false;
        this.specialImpactTriggered = false;

        this.setNoPhysics(true);
        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);
        this.hasImpulse = false;
        this.setGlowingTag(true);
    }

    @Nullable
    private LivingEntity getQueuedTargetLiving() {
        if (!(this.level() instanceof ServerLevel serverLevel) || this.queuedTargetUUID == null) {
            return null;
        }

        Entity entity = serverLevel.getEntity(this.queuedTargetUUID);
        return entity instanceof LivingEntity living && living.isAlive() ? living : null;
    }

    @Nullable
    private Vec3 resolveQueuedLaunchDirection() {
        LivingEntity target = this.getQueuedTargetLiving();
        if (target != null) {
            Vec3 computed = this.computeLaunchDirectionTo(target, this.queuedLaunchSpeed);
            if (computed != null) {
                return computed;
            }
        }

        if (this.queuedFallbackDirection != null && this.queuedFallbackDirection.lengthSqr() > 1.0E-7D) {
            return this.queuedFallbackDirection.normalize();
        }

        LivingEntity owner = this.getOwnerLiving();
        return owner != null ? com.pla.annoyingvillagers.item.BlueDemonTridentItem.getTridentThrowDirection(owner, this.position()) : null;
    }

    @Nullable
    private Vec3 computeLaunchDirectionTo(@NotNull LivingEntity target, float speed) {
        Vec3 start = this.position();
        Vec3 targetPos = target.position().add(0.0D, target.getBbHeight() * 0.72D, 0.0D);

        double dx = targetPos.x - start.x;
        double dz = targetPos.z - start.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double dy = targetPos.y - start.y + horizontal * 0.065D;

        Vec3 direction = new Vec3(dx, dy, dz);
        return direction.lengthSqr() > 1.0E-7D ? direction.normalize() : null;
    }

    private void tickAnimatedRelaunch() {
        this.relaunchAnimationTick++;

        float t = Math.min(1.0F, (float) this.relaunchAnimationTick / (float) RELAUNCH_ANIMATION_DURATION);
        float eased = t * t * (3.0F - 2.0F * t);

        Vec3 nextPos = new Vec3(
                Mth.lerp(eased, this.relaunchAnimationStart.x, this.relaunchAnimationEnd.x),
                Mth.lerp(eased, this.relaunchAnimationStart.y, this.relaunchAnimationEnd.y),
                Mth.lerp(eased, this.relaunchAnimationStart.z, this.relaunchAnimationEnd.z)
        );

        Vec3 moveDelta = nextPos.subtract(this.position());

        this.setPos(nextPos.x, nextPos.y, nextPos.z);
        this.setDeltaMovement(Vec3.ZERO);
        this.updateRotationFromMovement(moveDelta);

        if (this.relaunchAnimationTick >= RELAUNCH_ANIMATION_DURATION) {
            float speed = this.queuedLaunchSpeed;

            this.relaunchAnimationActive = false;
            this.relaunchAnimationTick = 0;

            Vec3 direction = this.resolveQueuedLaunchDirection();

            this.queuedTargetUUID = null;
            this.queuedFallbackDirection = null;

            this.setNoPhysics(false);
            this.setNoGravity(false);
            this.hasImpulse = true;
            this.specialImpactTriggered = false;
            this.inGround = false;
            this.inGroundTime = 0;
            this.shakeTime = 0;
            this.dealtDamage = false;

            if (direction != null && direction.lengthSqr() > 1.0E-7D) {
                this.relaunchTowards(direction, speed, 0.0F);
            }
        }
    }

    private void updateRotationFromMovement(Vec3 delta) {
        if (delta.lengthSqr() < 1.0E-7D) {
            return;
        }

        double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

        this.setYRot((float) (Mth.atan2(delta.x, delta.z) * (180F / Math.PI)));
        this.setXRot((float) (Mth.atan2(delta.y, horizontal) * (180F / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        if (target == this) {
            return false;
        }

        if (target instanceof BlueDemonThrownTridentEntity) {
            return false;
        }

        if (target instanceof TridentLightningBolt) {
            return false;
        }

        if (target == getOwner()) {
            return false;
        }

        return super.canHitEntity(target);
    }

    private void tickElectricEffects() {
        if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 5 == 0) {
            if (Math.random() <= 0.1D) {
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                        this.getX(), this.getY(), this.getZ(),
                        1,
                        0.3D, 1.2D, 0.3D,
                        0.0D
                );

                if (serverLevel.random.nextDouble() <= 0.8D) {
                    float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                    float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                    serverLevel.playSound(
                            null,
                            BlockPos.containing(this.getX(), this.getY(), this.getZ()),
                            AnnoyingVillagersModSounds.ELECTRIFY.get(),
                            SoundSource.NEUTRAL,
                            volume,
                            pitch
                    );
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BlueDemonMode", this.mode.name());
        tag.putBoolean("SpecialImpactTriggered", this.specialImpactTriggered);
        tag.putLong(TAG_SPAWN_SEQUENCE, this.spawnSequence);
        Direction face = this.getStuckFace();
        if (face != null) {
            tag.putByte("StuckFace", (byte) face.get3DDataValue());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadedFromDisk = true;

        if (tag.contains("BlueDemonMode")) {
            try {
                this.mode = TridentMode.valueOf(tag.getString("BlueDemonMode"));
            } catch (IllegalArgumentException ignored) {
                this.mode = TridentMode.DEFAULT;
            }
        }

        this.specialImpactTriggered = tag.getBoolean("SpecialImpactTriggered");
        this.spawnSequence = tag.getLong(TAG_SPAWN_SEQUENCE);

        if (tag.contains("StuckFace")) {
            this.setStuckFace(Direction.from3DDataValue(tag.getByte("StuckFace")));
        } else {
            this.setStuckFace(null);
        }
    }
}
