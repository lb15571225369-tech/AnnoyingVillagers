// com.pla.annoyingvillagers.entity.BabyDragonBigBeamEntity.java
package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.emitterinfo.GroundPillarEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Objects;

public class BabyDragonBigBeamEntity extends Entity {
    private static final EntityDataAccessor<Integer> CX =
            SynchedEntityData.defineId(BabyDragonBigBeamEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CY =
            SynchedEntityData.defineId(BabyDragonBigBeamEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CZ =
            SynchedEntityData.defineId(BabyDragonBigBeamEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> OWNER_ID =
            SynchedEntityData.defineId(BabyDragonBigBeamEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TARGET_ID =
            SynchedEntityData.defineId(BabyDragonBigBeamEntity.class, EntityDataSerializers.INT);

    private static final int DURATION_TICKS = 50;
    private static final float DAMAGE_PER_TICK = 8.0F;
    private static final int DAMAGE_START_TICK = 10;
    private static final int DAMAGE_PERIOD_TICKS = 4;
    private static final double RADIUS = 22.5;
    private static final double HEIGHT = 10.0;

    public BabyDragonBigBeamEntity(EntityType<? extends BabyDragonBigBeamEntity> type, Level level) {
        super(type, level);
        this.noCulling = true;
    }

    public BabyDragonBigBeamEntity(EntityType<? extends BabyDragonBigBeamEntity> type,
                                   Level level, LivingEntity owner, LivingEntity anchorEntity) {
        this(type, level);
        if (!level.isClientSide) {
            this.entityData.set(OWNER_ID, owner == null ? -1 : owner.getId());
            this.entityData.set(TARGET_ID, anchorEntity == null ? -1 : anchorEntity.getId());
            BlockPos c = (anchorEntity == null) ? this.blockPosition() : groundUnder(anchorEntity);
            setCenter(c);
            this.setPos(c.getX() + 0.5, c.getY(), c.getZ() + 0.5);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CX, 0);
        this.entityData.define(CY, 0);
        this.entityData.define(CZ, 0);
        this.entityData.define(OWNER_ID, -1);
        this.entityData.define(TARGET_ID, -1);
    }

    private LivingEntity getOwner() {
        Entity e = level().getEntity(this.entityData.get(OWNER_ID));
        return (e instanceof LivingEntity l) ? l : null;
    }
    private LivingEntity getAnchorTarget() {
        Entity e = level().getEntity(this.entityData.get(TARGET_ID));
        return (e instanceof LivingEntity l) ? l : null;
    }

    private BlockPos center() {
        return new BlockPos(this.entityData.get(CX), this.entityData.get(CY), this.entityData.get(CZ));
    }
    private void setCenter(BlockPos pos) {
        this.entityData.set(CX, pos.getX());
        this.entityData.set(CY, pos.getY());
        this.entityData.set(CZ, pos.getZ());
    }

    @Override public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount == 1) {
            if (!this.level().isClientSide()) {
                this.level().playSound(null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_breath"))), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.5D), (float) Mth.nextDouble(RandomSource.create(), 0.8D, 1.1D));
            } else {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_breath"))), SoundSource.NEUTRAL, (float) Mth.nextDouble(RandomSource.create(), 0.05D, 0.5D), (float) Mth.nextDouble(RandomSource.create(), 0.8D, 1.1D), false);
            }
        }

        if (!level().isClientSide && this.tickCount == 1) {
            LivingEntity anchor = getAnchorTarget();
            if (anchor != null) setCenter(groundUnder(anchor));
        }

        if (level().isClientSide && this.tickCount == 1) {
            new GroundPillarEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "baby_dragon_big_beam"))
                    .at(center(), 0.0F)
                    .duration(170)
                    .spawnInWorld(level(), null);
        }

        if (!level().isClientSide) {
            if (this.tickCount >= DAMAGE_START_TICK && (this.tickCount % DAMAGE_PERIOD_TICKS) == 0) {
                doDamageAndBlocks();
            }
            if (this.tickCount > DURATION_TICKS) {
                this.discard();
            }
        } else if (this.tickCount > DURATION_TICKS) {
            this.discard();
        }
    }

    private void doDamageAndBlocks() {
        final LivingEntity owner = getOwner();
        final LivingEntity ownersAlly = (owner instanceof BabyEnderDragonEntity babyEnderDragonEntity) ? babyEnderDragonEntity.getFollowTarget() : null;

        BlockPos c = center();
        double cx = c.getX() + 0.5, cy = c.getY(), cz = c.getZ() + 0.5;

        AABB box = new AABB(cx - RADIUS, cy, cz - RADIUS, cx + RADIUS, cy + HEIGHT, cz + RADIUS);
        List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, box,
                e -> e.isAlive()
                        && e != owner
                        && e != ownersAlly
                        && !(e instanceof EnderHand)
                        && (ownersAlly == null || !e.isAlliedTo(ownersAlly)));

        for (LivingEntity entity : list) {
            double dx = entity.getX() - cx;
            double dz = entity.getZ() - cz;
            if (dx * dx + dz * dz <= RADIUS * RADIUS) {
                if (ownersAlly != null) {
                    entity.hurt(damageSources().indirectMagic(this, ownersAlly), DAMAGE_PER_TICK);
                } else if (owner != null) {
                    entity.hurt(damageSources().indirectMagic(this, owner), DAMAGE_PER_TICK);
                } else {
                    entity.hurt(damageSources().generic(), DAMAGE_PER_TICK);
                }
                entity.hurtMarked = true;
                if (this.level() instanceof ServerLevel serverLevel) {
                    LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (livingEntityPatch != null && (this.tickCount % 20 == 0 || this.tickCount == 1)) {
                        livingEntityPatch.applyStun(StunType.LONG, 40.0F);
                    }

                    if (ownersAlly != null && (this.tickCount % 40 == 0 || this.tickCount == 1)) {
                        EnderHand enderHand = new EnderHand(serverLevel, new Vec3(entity.getX(), entity.getY(), entity.getZ()), ownersAlly, entity);
                        serverLevel.addFreshEntity(enderHand);

                    }
                }
                entity.setDeltaMovement(0, 0, 0);
                entity.lerpMotion(0, 0, 0);
            }
        }
    }

    private BlockPos groundUnder(LivingEntity e) {
        BlockPos pos = e.blockPosition();
        int minY = level().getMinBuildHeight();
        while (pos.getY() > minY) {
            BlockState st = level().getBlockState(pos);
            if (!st.isAir() && !st.getCollisionShape(level(), pos).isEmpty()) break;
            pos = pos.below();
        }
        return pos.above();
    }

    @Override public boolean isPickable() { return false; }
    @Override public boolean isPushable() { return false; }
    @Override protected void readAdditionalSaveData(CompoundTag compoundTag) {}
    @Override protected void addAdditionalSaveData(CompoundTag compoundTag) {}
}
