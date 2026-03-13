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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlueDemonThrownTrident extends ThrownTrident {
    private TridentMode mode = TridentMode.DEFAULT;

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

    public TridentMode getMode() {
        return this.mode;
    }

    public void setMode(TridentMode mode) {
        this.mode = mode == null ? TridentMode.DEFAULT : mode;
    }

    public boolean hasTriggeredSpecialImpact() {
        return this.specialImpactTriggered;
    }

    public void setTriggeredSpecialImpact(boolean triggered) {
        this.specialImpactTriggered = triggered;
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

        if (this.specialImpactTriggered) {
            return;
        }

        this.specialImpactTriggered = true;
        this.handleModeImpact(result.getBlockPos(), null);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BlueDemonMode", this.mode.name());
        tag.putBoolean("SpecialImpactTriggered", this.specialImpactTriggered);
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

        if (tag.contains("StuckFace")) {
            this.setStuckFace(Direction.from3DDataValue(tag.getByte("StuckFace")));
        } else {
            this.setStuckFace(null);
        }
    }
}
