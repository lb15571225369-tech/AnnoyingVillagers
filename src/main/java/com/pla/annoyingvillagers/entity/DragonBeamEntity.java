package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.aaa_particles.DragonBeamParticleEmitterInfo;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DragonBeamEntity extends Entity {
    public EnderDragon caster;
    public LivingEntity target;
    public double endPosX;
    public double endPosY;
    public double endPosZ;
    public double collidePosX;
    public double collidePosY;
    public double collidePosZ;
    public double prevCollidePosX;
    public double prevCollidePosY;
    public double prevCollidePosZ;
    public float renderYaw;
    public float renderPitch;
    public boolean on;
    public Direction blockSide;
    private int power;
    private static final EntityDataAccessor<Float> YAW;
    private static final EntityDataAccessor<Float> PITCH;
    private static final EntityDataAccessor<Integer> DURATION;
    private static final EntityDataAccessor<Integer> CASTER;
    private static final EntityDataAccessor<Integer> TARGET;
    public float prevYaw;
    public float prevPitch;
    private Vec3 targetPos;
    @OnlyIn(Dist.CLIENT)
    private Vec3[] attractorPos;
    private boolean renderBeam = false;

    public DragonBeamEntity(EntityType<? extends DragonBeamEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.on = true;
        this.blockSide = null;
        this.noCulling = true;
        if (pLevel.isClientSide) {
            this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
        }

    }

    public DragonBeamEntity(EntityType<? extends DragonBeamEntity> type, Level world, EnderDragon caster, LivingEntity target, double x, double y, double z, int duration, int pow) {
        this(type, world);
        this.caster = caster;
        this.target = target;
        this.setDuration(duration);
        this.setPower(pow);
        this.setPos(x, y, z);

        Vec3 from = new Vec3(x, y, z);
        Vec3 to = target.getEyePosition(1.0F);

        float yawRad   = yawTowards(from, to);
        float pitchRad = pitchTowards(from, to);

        this.setYaw(yawRad);
        this.setPitch(pitchRad);

        if (world.isClientSide) {
            this.renderYaw = yawRad;
            this.renderPitch = pitchRad;
        }
        if (!world.isClientSide) {
            this.setCasterID(caster.getId());
            this.setTargetID(target.getId());
        }

        this.calculateEndPos();
    }

    protected void defineSynchedData() {
        this.entityData.define(YAW, 0.0F);
        this.entityData.define(PITCH, 0.0F);
        this.entityData.define(DURATION, 0);
        this.entityData.define(CASTER, -1);
        this.entityData.define(TARGET, -1);
    }

    public void setTargetID(int id) {
        this.entityData.set(TARGET, id);
    }

    public int getTargetID() {
        return this.entityData.get(TARGET);
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public float getYaw() {
        return this.getEntityData().get(YAW);
    }

    public void setYaw(float yaw) {
        this.getEntityData().set(YAW, yaw);
    }

    public float getPitch() {
        return this.getEntityData().get(PITCH);
    }

    public void setPitch(float pitch) {
        this.getEntityData().set(PITCH, pitch);
    }

    public int getDuration() {
        return this.getEntityData().get(DURATION);
    }

    public void setDuration(int duration) {
        this.getEntityData().set(DURATION, duration);
    }

    public int getCasterID() {
        return this.getEntityData().get(CASTER);
    }

    public void setCasterID(int id) {
        this.getEntityData().set(CASTER, id);
    }

    public void setPower(int power) {
        this.power = power;
    }

    private static float yawTowards(Vec3 from, Vec3 to) {
        Vec3 d = to.subtract(from);
        return (float) Math.atan2(d.z, d.x);
    }

    private static float pitchTowards(Vec3 from, Vec3 to) {
        Vec3 d = to.subtract(from);
        double len = d.length();
        return len == 0 ? 0f : (float) Math.asin(d.y / len);
    }

    private void calculateEndPos() {
        double radius = 128.0;
        double yaw = this.getYaw();
        double pitch = this.getPitch();

        this.endPosX = this.getX() + radius * Math.cos(yaw) * Math.cos(pitch);
        this.endPosZ = this.getZ() + radius * Math.sin(yaw) * Math.cos(pitch);
        this.endPosY = this.getY() + radius * Math.sin(pitch);

    }

    public DragonBeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        DragonBeamHitResult result = new DragonBeamHitResult();
        result.setBlockHit(world.clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this)));
        result.setBlockHit(world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));
        if (result.blockHit != null) {
            Vec3 hitVec = result.blockHit.getLocation();
            BlockPos hitBlock = result.blockHit.getBlockPos();

            this.collidePosX = hitVec.x;
            this.collidePosY = hitVec.y;
            this.collidePosZ = hitVec.z;
            this.blockSide = result.blockHit.getDirection();

            if (world.isClientSide) {
                AAALevel.addParticle(world, false,
                        new ParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "DragonBeamHit"))
                                .clone()
                                .position(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ()));
            }

            if (!world.isClientSide) {
                boolean shouldBreak = true;

                if (this.target != null && this.target.isAlive()) {
                    double hitDist2    = from.distanceToSqr(hitVec);
                    double targetDist2 = from.distanceToSqr(this.target.getEyePosition(1.0F));
                    shouldBreak = hitDist2 + 1e-6 < targetDist2;

                    BlockPos targetFeet = this.target.blockPosition();
                    BlockPos targetEyes = BlockPos.containing(this.target.getEyePosition(1.0F));
                    if (hitBlock.equals(targetFeet) || hitBlock.equals(targetEyes)) {
                        shouldBreak = false;
                    }
                }

                if (shouldBreak) {
                    var hitState = world.getBlockState(hitBlock);
                    if (!hitState.isAir()) {
                        if (ForgeHooks.canEntityDestroy(world, hitBlock, this.caster)) {
                            world.destroyBlock(hitBlock, true, this.caster);
                        } else {
                            world.setBlock(hitBlock, Blocks.AIR.defaultBlockState(),
                                    net.minecraft.world.level.block.Block.UPDATE_ALL);
                        }

                        BlockPos above = hitBlock.above();
                        if (world.getBlockState(above).isAir()) {
                            world.setBlockAndUpdate(above, AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState());
                        }
                    }
                }

                if (world.getBlockState(hitBlock.above()).isAir() && world.getBlockState(hitBlock).isSolidRender(world, hitBlock)) {
                    world.setBlockAndUpdate(hitBlock.above(), AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState());
                }
            }
        } else {
            this.collidePosX = this.endPosX;
            this.collidePosY = this.endPosY;
            this.collidePosZ = this.endPosZ;
            this.blockSide = null;
        }

        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, (new AABB(Math.min(this.getX(), this.collidePosX), Math.min(this.getY(), this.collidePosY), Math.min(this.getZ(), this.collidePosZ), Math.max(this.getX(), this.collidePosX), Math.max(this.getY(), this.collidePosY), Math.max(this.getZ(), this.collidePosZ))).inflate(1.0, 1.0, 1.0));

        for (LivingEntity entity : entities) {
            if (entity != this.caster) {
                float pad = entity.getPickRadius() + 0.5F;
                AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
                Optional<Vec3> hit = aabb.clip(from, to);
                if (aabb.contains(from)) {
                    result.addEntityHit(entity);
                } else if (hit.isPresent()) {
                    result.addEntityHit(entity);
                }
            }
        }

        return result;
    }

    public boolean isRenderable() {
        return (this.target != null && this.target.isAlive()) || this.targetPos != null;
    }

    public void push(Entity entityIn) {
    }

    public boolean isPickable() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 1024.0;
    }

    public void tick() {
        super.tick();

        this.prevCollidePosX = this.collidePosX;
        this.prevCollidePosY = this.collidePosY;
        this.prevCollidePosZ = this.collidePosZ;
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        this.prevYaw = this.renderYaw;
        this.prevPitch = this.renderPitch;
        if (this.level().isClientSide) {
            this.renderYaw = this.getYaw();
            this.renderPitch = this.getPitch();
        }

        if (this.tickCount == 1 && this.level().isClientSide) {
            this.caster = (EnderDragon) this.level().getEntity(this.getCasterID());
            this.target = (LivingEntity) this.level().getEntity(this.getTargetID());
        }

        if (this.level().isClientSide && this.target == null && this.getTargetID() != -1) {
            Entity e = this.level().getEntity(this.getTargetID());
            if (e instanceof LivingEntity living) {
                this.target = living;
            }
        }

        if (this.caster != null) {
            this.moveTo(caster.getX(), caster.getY() + caster.getBbHeight() * 0.6, caster.getZ());
        }

        if (this.target != null && this.target.isAlive()) {
            Vec3 from = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 to = target.getEyePosition(1.0F);

            this.targetPos = to;

            float targetYaw = yawTowards(from, to);
            float targetPitch = pitchTowards(from, to);

            float interpolatedYaw = Mth.lerp(0.85f, this.getYaw(), targetYaw);
            float interpolatedPitch = Mth.lerp(0.85f, this.getPitch(), targetPitch);

            this.setYaw(interpolatedYaw);
            this.setPitch(interpolatedPitch);

            if (this.level().isClientSide) {
                this.renderYaw = interpolatedYaw;
                this.renderPitch = interpolatedPitch;
            }
        } else if (this.targetPos != null) {
            Vec3 from = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 to = this.targetPos;

            float targetYaw = yawTowards(from, to);
            float targetPitch = pitchTowards(from, to);

            float interpolatedYaw = Mth.lerp(0.5f, this.getYaw(), targetYaw);
            float interpolatedPitch = Mth.lerp(0.5f, this.getPitch(), targetPitch);

            this.setYaw(interpolatedYaw);
            this.setPitch(interpolatedPitch);

            if (this.level().isClientSide) {
                this.renderYaw = interpolatedYaw;
                this.renderPitch = interpolatedPitch;
            }
        } else {
            return;
        }

        if (!this.on || (this.caster != null && !this.caster.isAlive())) {
            this.discard();
            return;
        }

        if (this.level().isClientSide && this.tickCount <= 10 && this.caster != null) {
            int particleCount = 8;

            while (true) {
                --particleCount;
                if (particleCount == 0) {
                    break;
                }

                double radius = (2.0F * this.caster.getBbWidth());
                double yaw = (double) (this.random.nextFloat() * 2.0F) * Math.PI;
                double pitch = (double) (this.random.nextFloat() * 2.0F) * Math.PI;
                double ox = radius * Math.sin(yaw) * Math.sin(pitch);
                double var10000 = radius * Math.cos(pitch);
                double oz = radius * Math.cos(yaw) * Math.sin(pitch);
                double rootX = this.caster.getX();
                double rootY = this.caster.getY() + (double) (this.caster.getBbHeight() / 2.0F) + 0.30000001192092896;
                double rootZ = this.caster.getZ();
                this.attractorPos[0] = new Vec3(rootX, rootY, rootZ);
            }
        }

        this.calculateEndPos();

        if (this.isRenderable() && this.level().isClientSide() && !renderBeam && this.tickCount >= 3) {
            renderBeam = true;
            Vec3 from = new Vec3(caster.head.getX(), caster.head.getEyeY(), caster.head.getZ());
            Vec3 to = new Vec3(target.getX(), target.getY(), target.getZ());

            new DragonBeamParticleEmitterInfo(new ResourceLocation(AnnoyingVillagers.MODID, "DragonBeam"))
                    .fromTo(from, to, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                    .follow(caster.head, target, 120,
                            DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z,
                            0f)
                    .spawnInWorld(caster.level(), null);
        }

        if (this.isRenderable() && this.tickCount >= 50) {
            List<LivingEntity> hit = this.raytraceEntities(this.level(), new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ), false, true, true).entities;
            if (!this.level().isClientSide) {

                for (LivingEntity target : hit) {
                    target.hurt(damageSources().indirectMagic(this, this.caster), (float) this.power);
                    target.hurtMarked = true;
                    target.setDeltaMovement(0.0, 0.0, 0.0);
                    target.lerpMotion(0.0, 0.0, 0.0);
                }
            }
        }

        if (this.tickCount > this.getDuration()) {
            this.on = false;
        }

    }

    static {
        YAW = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.FLOAT);
        PITCH = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.FLOAT);
        DURATION = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
        CASTER = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
        TARGET = SynchedEntityData.defineId(DragonBeamEntity.class, EntityDataSerializers.INT);
    }

    public static class DragonBeamHitResult {
        private BlockHitResult blockHit;
        private final List<LivingEntity> entities = new ArrayList<>();

        public DragonBeamHitResult() {
        }

        public BlockHitResult getBlockHit() {
            return this.blockHit;
        }

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult.getType() == Type.BLOCK) {
                this.blockHit = (BlockHitResult) rayTraceResult;
            }
        }

        public void addEntityHit(LivingEntity entity) {
            this.entities.add(entity);
        }
    }
}