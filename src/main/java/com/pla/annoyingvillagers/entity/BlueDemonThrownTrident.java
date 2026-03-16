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
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class BlueDemonThrownTrident extends ThrownTrident {
    private TridentMode mode = TridentMode.DEFAULT;

    private static final int MAX_GROUNDED_TRIDENTS_PER_OWNER = 20;
    private static final double OWNER_BOX_HALF_SIZE = 25.0D;
    private static final String TAG_SPAWN_SEQUENCE = "BlueDemonSpawnSequence";
    private static final String TAG_OWNER_SHOT_COUNTER = "BlueDemonOwnerShotCounter";

    private long spawnSequence;

    private boolean specialImpactTriggered = false;
    private static final EntityDataAccessor<Byte> DATA_STUCK_FACE =
            SynchedEntityData.defineId(BlueDemonThrownTrident.class, EntityDataSerializers.BYTE);

    @Nullable
    public Direction getStuckFace() {
        byte value = this.entityData.get(DATA_STUCK_FACE);
        return value == (byte)255 ? null : Direction.from3DDataValue(value);
    }

    public void setStuckFace(@Nullable Direction direction) {
        this.entityData.set(DATA_STUCK_FACE, direction == null ? (byte)255 : (byte) direction.get3DDataValue());
    }

    public BlueDemonThrownTrident(EntityType<? extends ThrownTrident> type, Level level) {
        super(type, level);
    }

    public BlueDemonThrownTrident(PlayMessages.SpawnEntity packet, Level level) {
        this(AnnoyingVillagersModEntities.BLUE_DEMON_THROWN_TRIDENT.get(), level);
    }

    public BlueDemonThrownTrident(Level level, LivingEntity shooter, ItemStack stack) {
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
        super.tick();

        if (!this.level().isClientSide && this.inGround && this.tickCount % 10 == 0) {
            this.discardIfGroundedAndFarFromOwner();
            if (!this.isAlive()) {
                return;
            }
        }

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
                    float pitch  = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

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

        List<BlueDemonThrownTrident> grounded = serverLevel.getEntitiesOfClass(
                BlueDemonThrownTrident.class,
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
                Comparator.comparingLong(BlueDemonThrownTrident::getSpawnSequence)
                        .thenComparing(BlueDemonThrownTrident::getUUID)
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
