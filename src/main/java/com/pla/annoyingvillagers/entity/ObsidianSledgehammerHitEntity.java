    package com.pla.annoyingvillagers.entity;

    import java.util.UUID;

    import javax.annotation.Nullable;

    import com.mojang.brigadier.exceptions.CommandSyntaxException;
    import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
    import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
    import com.pla.annoyingvillagers.util.DelayedTask;
    import net.minecraft.core.particles.BlockParticleOption;
    import net.minecraft.core.particles.ParticleTypes;
    import net.minecraft.nbt.CompoundTag;
    import net.minecraft.network.protocol.Packet;
    import net.minecraft.network.protocol.game.ClientGamePacketListener;
    import net.minecraft.network.syncher.EntityDataAccessor;
    import net.minecraft.network.syncher.EntityDataSerializers;
    import net.minecraft.network.syncher.SynchedEntityData;
    import net.minecraft.server.level.ServerLevel;
    import net.minecraft.world.entity.Entity;
    import net.minecraft.world.entity.EntityType;
    import net.minecraft.world.entity.LivingEntity;
    import net.minecraft.world.level.Level;
    import net.minecraft.world.level.block.RenderShape;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraftforge.api.distmarker.Dist;
    import net.minecraftforge.api.distmarker.OnlyIn;
    import net.minecraftforge.network.NetworkHooks;

    public class ObsidianSledgehammerHitEntity extends Entity {
        private int warmupDelayTicks;
        private boolean sentSpikeEvent;
        private int lifeTicks = 34;
        private boolean clientSideAttackStarted;
        private LivingEntity caster;
        private UUID casterUuid;
        private static final EntityDataAccessor<Boolean> ACTIVATE = SynchedEntityData.defineId(ObsidianSledgehammerHitEntity.class, EntityDataSerializers.BOOLEAN);
        private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ObsidianSledgehammerHitEntity.class, EntityDataSerializers.FLOAT);
        public float activateProgress;
        public float prevactivateProgress;
        private static final float MAX_PROGRESS   = 10f;
        private static final float RISE_SPEED     = 3.0f;
        private static final float RETRACT_SPEED  = 3.0f;

        public ObsidianSledgehammerHitEntity(EntityType<? extends ObsidianSledgehammerHitEntity> type, Level world) {
            super(type, world);
        }

        public ObsidianSledgehammerHitEntity(Level worldIn, double x, double y, double z, float rotation, int delay, float damage, LivingEntity casterIn) {
            this(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_HIT.get(), worldIn);
            this.warmupDelayTicks = delay;
            this.setCaster(casterIn);
            this.setDamage(damage);
            this.setYRot(rotation * (180F / (float)Math.PI));
            this.setPos(x, y, z);
        }

        protected void defineSynchedData() {
            this.entityData.define(ACTIVATE, Boolean.valueOf(false));
            this.entityData.define(DAMAGE, 0F);
        }

        public float getDamage() {
            return entityData.get(DAMAGE);
        }

        public void setDamage(float damage) {
            entityData.set(DAMAGE, damage);
        }

        public void setCaster(@Nullable LivingEntity pLivingEntity) {
            this.caster = pLivingEntity;
            this.casterUuid = pLivingEntity == null ? null : pLivingEntity.getUUID();
        }

        @Nullable
        public LivingEntity getCaster() {
            if (this.caster == null && this.casterUuid != null && this.level() instanceof ServerLevel) {
                Entity entity = ((ServerLevel)this.level()).getEntity(this.casterUuid);
                if (entity instanceof LivingEntity) {
                    this.caster = (LivingEntity)entity;
                }
            }

            return this.caster;
        }

        protected void readAdditionalSaveData(CompoundTag compound) {
            this.warmupDelayTicks = compound.getInt("Warmup");
            if (compound.hasUUID("Owner")) {
                this.casterUuid = compound.getUUID("Owner");
            }
            this.setDamage(compound.getFloat("damage"));
        }

        protected void addAdditionalSaveData(CompoundTag compound) {
            compound.putInt("Warmup", this.warmupDelayTicks);
            if (this.casterUuid != null) {
                compound.putUUID("Owner", this.casterUuid);
            }
            compound.putFloat("damage", this.getDamage());
        }

        public void tick() {
            super.tick();
            prevactivateProgress = activateProgress;

            if (isActivate() && this.activateProgress > 0F) {
                this.activateProgress--;
            }

            if (this.level().isClientSide) {
                if (this.clientSideAttackStarted) {
                    --this.lifeTicks;
                    if (!isActivate() && this.activateProgress < MAX_PROGRESS) {
                        this.activateProgress = Math.min(MAX_PROGRESS, this.activateProgress + RISE_SPEED);
                    }
                    if (this.lifeTicks == 33) {
                        for(int i = 0; i < 80; ++i) {
                            BlockState block = level().getBlockState(blockPosition().below());
                            double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
                            double d1 = this.getY() + 0.03D;
                            double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
                            double d3 = (this.random.nextGaussian() * 0.07D);
                            double d4 = (this.random.nextGaussian() * 0.07D);
                            double d5 = (this.random.nextGaussian() * 0.07D);
                            if (block.getRenderShape() != RenderShape.INVISIBLE) {
                                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block), d0, d1, d2, d3, d4, d5);
                            }
                        }
                    }

                    if (this.lifeTicks == 14) {
                        this.setActivate(true);
                        for(int i = 0; i < 12; ++i) {
                            double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                            double d1 = this.getY() + 0.05D + this.random.nextDouble();
                            double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                            double d3 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                            double d4 = 0.3D + this.random.nextDouble() * 0.3D;
                            double d5 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                            this.level().addParticle(ParticleTypes.REVERSE_PORTAL, d0, d1, d2, d3, d4, d5);
                        }
                    }
                }
            } else if (--this.warmupDelayTicks < 0) {
                if (this.warmupDelayTicks == -10) {
                    if(isActivate()) {
                        this.setActivate(false);
                    }
                }
                if (this.warmupDelayTicks < -10 && this.warmupDelayTicks > -30) {
                    for(LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D))) {
                        this.damage(livingentity);
                    }
                }


                if (!this.sentSpikeEvent) {
                    this.level().broadcastEntityEvent(this, (byte)4);
                    this.sentSpikeEvent = true;
                }

                if (--this.lifeTicks < 0) {
                    this.discard();
                }
            }

        }

        public boolean isActivate() {
            return this.entityData.get(ACTIVATE);
        }

        public void setActivate(boolean Activate) {
            this.entityData.set(ACTIVATE, Activate);
        }

        private void damage(LivingEntity Hitentity) {
            LivingEntity livingentity = this.getCaster();
            if (Hitentity.isAlive() && !Hitentity.isInvulnerable() && Hitentity != livingentity) {
                if (this.tickCount % 5 == 0) {
                    try {
                        this.getServer().getCommands().getDispatcher().execute(
                                "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                                this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {

                    }
                    if (livingentity == null) {
                        Hitentity.hurt(this.level().damageSources().magic(), this.getDamage());
                    } else {
                        if (livingentity.isAlliedTo(Hitentity)) {
                            return;
                        }
                        Hitentity.hurt(this.level().damageSources().indirectMagic(this, livingentity), this.getDamage());
                    }
                    if (!Hitentity.level().isClientSide() && Hitentity.getServer() != null) {
                        try {
                            Hitentity.getServer().getCommands().getDispatcher().execute(
                                    "indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 10",
                                    Hitentity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {

                        }
                    }
                    float strength = 3.0F;
                    double dx = this.getX() - Hitentity.getX();
                    double dz = this.getZ() - Hitentity.getZ();
                    Hitentity.knockback(strength, dx, dz);
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        public void handleEntityEvent(byte id) {
            super.handleEntityEvent(id);
            if (id == 4) {
                this.clientSideAttackStarted = true;
                if (!this.isSilent()) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), AnnoyingVillagersModSounds.OB_PLACE.get(), this.getSoundSource(), 0.5F, this.random.nextFloat() * 0.2F + 0.85F, false);
                }
            }

        }

        public float getLightLevelDependentMagicValue() {
            return 1.0F;
        }



        @Override
        public Packet<ClientGamePacketListener> getAddEntityPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }
    }
