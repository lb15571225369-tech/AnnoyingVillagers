package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DragonMeteoriteEntity extends PathfinderMob {

    public DragonMeteoriteEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.DRAGON_METEORITE.get(), level);
    }

    public DragonMeteoriteEntity(EntityType<DragonMeteoriteEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        return !damagesource.is(DamageTypes.IN_FIRE)
                && (!(damagesource.getDirectEntity() instanceof AbstractArrow)
                && (!(damagesource.getDirectEntity() instanceof Player)
                && (!(damagesource.getDirectEntity() instanceof ThrownPotion)
                && !(damagesource.getDirectEntity() instanceof AreaEffectCloud)
                && (!damagesource.is(DamageTypes.FALL)
                && (!damagesource.is(DamageTypes.CACTUS)
                && (!damagesource.is(DamageTypes.DROWN)
                && (!damagesource.is(DamageTypes.LIGHTNING_BOLT)
                && (!damagesource.is(DamageTypes.EXPLOSION)
                && !damagesource.is(DamageTypes.PLAYER_EXPLOSION)
                && (!damagesource.is(DamageTypes.TRIDENT)
                && (!damagesource.is(DamageTypes.FALLING_ANVIL)
                && (!damagesource.is(DamageTypes.DRAGON_BREATH)
                && (!damagesource.is(DamageTypes.WITHER)
                && !damagesource.is(DamageTypes.WITHER_SKULL)
                && super.hurt(damagesource, f)))))))))))));
    }

    public boolean ignoreExplosion() {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setInvulnerable(true);
        this.playSound(AnnoyingVillagersModSounds.MUFFLED_BOOM.get(), new Random().nextFloat(34.0F, 42.0F), new Random().nextFloat(0.0F, 0.2F));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void baseTick() {
        super.baseTick();
        if (this.level() instanceof ServerLevel serverLevel) {
            double d0 = this.getX();
            double d1 = this.getY();
            double d2 = this.getZ();

            double d3;
            double d4;
            double d5;

            this.setInvulnerable(true);
            this.getPersistentData().putDouble("xd", Mth.nextDouble(RandomSource.create(), -0.7D, 0.7D));
            this.getPersistentData().putDouble("zd", Mth.nextDouble(RandomSource.create(), -0.7D, 0.7D));

            if (this.onGround() || this.isInWall()) {
                serverLevel.explode(null, d0, d1, d2, new Random().nextFloat(4.0F, 8.0F), Level.ExplosionInteraction.MOB);

                RandomSource randomSource = serverLevel.getRandom();
                BlockState endfireState = AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState();

                for (int i = 0; i < 100; i++) {
                    BlockPos pos = BlockPos.containing(d0, d1, d2);
                    serverLevel.setBlock(pos, endfireState, 3);
                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, pos, endfireState);
                    fallingBlockEntity.time = 1;
                    fallingBlockEntity.dropItem = false;

                    double vx = Mth.nextDouble(randomSource, -0.5D, 0.5D);
                    double vy = Mth.nextDouble(randomSource,  0.1D, 0.9D);
                    double vz = Mth.nextDouble(randomSource, -0.5D, 0.5D);

                    fallingBlockEntity.setDeltaMovement(vx, vy, vz);
                    fallingBlockEntity.hasImpulse = true;
                }

                FallingBlockEntity.fall(serverLevel, BlockPos.containing(d0, d1, d2), Blocks.CRYING_OBSIDIAN.defaultBlockState());
                serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, d0, d1, d2, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                Vec3 center = new Vec3(d0, d1, d2);
                AABB box = new AABB(center, center).inflate(10.0D);

                var damageTypeReg = serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
                DamageSource damageSource = new DamageSource(damageTypeReg.getHolderOrThrow(DamageTypes.EXPLOSION), this);

                for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, box,
                        livingEntity -> livingEntity.isAlive() && !(livingEntity instanceof DragonMeteoriteEntity))) {

                    Vec3 dir = entity.position().subtract(center);
                    double dist = Math.max(0.001D, dir.length());
                    double falloff = 1.0D - Math.min(dist / 10.0D, 1.0D);

                    Vec3 push = dir.scale(1.0D / dist)
                            .scale(1.2D * falloff)
                            .add(0.0D, 0.35D * falloff, 0.0D);

                    entity.setDeltaMovement(entity.getDeltaMovement().add(push));
                    entity.hurt(damageSource, 24.0F);
                    entity.hasImpulse = true;

                    entity.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.SCREEN_SHAKE.get(), 2, 4, false, false));
                }

                this.playSound(SoundEvents.GENERIC_EXPLODE, 5.0F, 0.0F);
                this.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE_FAR, 6.0F, 0.0F);
                this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 10.0F, 0.0F);
                this.discard();
            }

            this.setDeltaMovement(new Vec3(this.getPersistentData().getDouble("xd"), -1.8D, this.getPersistentData().getDouble("zd")));
            d3 = -5.0D;
            for (int i = 0; i < 10; ++i) {
                d4 = -5.0D;

                for (int j = 0; j < 10; ++j) {
                    d5 = -5.0D;

                    for (int k = 0; k < 10; ++k) {
                        if ((double) serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getDestroySpeed(serverLevel, BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)) < 0.4D && serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getDestroySpeed(serverLevel, BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)) >= 0.0F) {
                            serverLevel.destroyBlock(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), false);
                            serverLevel.updateNeighborsAt(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock());
                            serverLevel.scheduleTick(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock(), 1);
                        }

                        if (serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).is(BlockTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "logs")))) {
                            serverLevel.destroyBlock(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), false);
                            serverLevel.updateNeighborsAt(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock());
                            serverLevel.scheduleTick(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5), serverLevel.getBlockState(BlockPos.containing(d0 + d3, d1 + d4, d2 + d5)).getBlock(), 1);
                        }
                        ++d5;
                    }
                    ++d4;
                }
                ++d3;
            }

            if (this.isInWaterOrBubble()) {
                for (int i = 0; i < 10; ++i) {
                    serverLevel.addParticle(AnnoyingVillagersModParticleTypes.BIG_SPLASH.get(), d0 + Mth.nextDouble(RandomSource.create(), -1.0D, 1.0D), d1 + 2.0D, d2 + Mth.nextDouble(RandomSource.create(), -1.0D, 1.0D), 0.0D, 1.0D, 0.0D);
                }
            }

            if (!this.isInWaterOrBubble()) {
                Entity entity = this;
                new DelayedTask(2) {
                    @Override
                    public void run() {
                        if (entity.isInWaterOrBubble()) {
                            serverLevel.playSound(null, BlockPos.containing(d0, d1, d2), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.NEUTRAL, 6.0F, 0.0F);
                        }
                    }
                };
            }

            serverLevel.sendParticles(ParticleTypes.EXPLOSION, d0, d1 + 0.5D, d2, 0, 0.0D, 1.0D, 0.0D, 0.0D);
            serverLevel.sendParticles(ParticleTypes.FLASH, d0, d1 + 0.5D, d2, 0, 0.0D, 1.0D, 0.0D, 0.0D);
            serverLevel.sendParticles(AnnoyingVillagersModParticleTypes.METEORITE_TRAIL.get(), d0, d1 + 0.5D, d2,  0, 0.0D, 0.01D, 0.0D, 0.0D);

            for (int i = 0; i < 20; ++i) {
                serverLevel.sendParticles(
                        ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        d0 + Mth.nextDouble(RandomSource.create(), -2.0D, 2.0D),
                        d1 + Mth.nextDouble(RandomSource.create(), -1.5D, 2.5D),
                        d2 + Mth.nextDouble(RandomSource.create(), -2.0D, 2.0D),
                        0, 0.0D, 0.01D, 0.0D, 0.0D);
            }
        }
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 200.0D);
        return builder;
    }
}
