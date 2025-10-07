package com.pla.annoyingvillagers.entity;

import java.util.*;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.NullOnHurtProcedure;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;

public class NullEntity extends HerobrineMob {
    private NullSwordEntity nullSwordEntity;
    private UUID nullSwordUUID;

    private NullAxeEntity nullAxeEntity;
    private UUID nullAxeUUID;

    private NullPickaxeEntity nullPickaxeEntity;
    private UUID nullPickaxeUUID;

    private NullShovelEntity nullShovelEntity;
    private UUID nullShovelUUID;

    private NullHoeEntity nullHoeEntity;
    private UUID nullHoeUUID;

    private boolean initialSpawn = false;

    public NullSwordEntity getNullSwordEntity() {
        return nullSwordEntity;
    }

    public NullAxeEntity getNullAxeEntity() {
        return nullAxeEntity;
    }

    public NullPickaxeEntity getNullPickaxeEntity() {
        return nullPickaxeEntity;
    }

    public NullShovelEntity getNullShovelEntity() {
        return nullShovelEntity;
    }

    public NullHoeEntity getNullHoeEntity() {
        return nullHoeEntity;
    }

    public NullEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.NULL.get(), level);
    }

    public NullEntity(EntityType<NullEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setChatName("§5Null§r");
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (nullSwordUUID != null) {
            tag.putUUID("NullSwordUUID", nullSwordUUID);
        }
        if (nullAxeUUID != null) {
            tag.putUUID("NullAxeUUID", nullAxeUUID);
        }
        if (nullPickaxeUUID != null) {
            tag.putUUID("NullPickaxeUUID", nullPickaxeUUID);
        }
        if (nullShovelUUID != null) {
            tag.putUUID("NullShovelUUID", nullShovelUUID);
        }
        if (nullHoeUUID != null) {
            tag.putUUID("NullHoeUUID", nullHoeUUID);
        }
        tag.putBoolean("InitialSpawn", initialSpawn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullSwordUUID")) {
            nullSwordUUID = tag.getUUID("NullSwordUUID");
        }
        if (tag.hasUUID("NullAxeUUID")) {
            nullAxeUUID = tag.getUUID("NullAxeUUID");
        }
        if (tag.hasUUID("NullPickaxeUUID")) {
            nullPickaxeUUID = tag.getUUID("NullPickaxeUUID");
        }
        if (tag.hasUUID("NullShovelUUID")) {
            nullShovelUUID = tag.getUUID("NullShovelUUID");
        }
        if (tag.hasUUID("NullHoeUUID")) {
            nullHoeUUID = tag.getUUID("NullHoeUUID");
        }
        initialSpawn = tag.getBoolean("InitialSpawn");
    }

    private void initialSpawn() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            NullSwordEntity nullSwordEntity = new NullSwordEntity((EntityType) AnnoyingVillagersModEntities.NULL_SWORD.get(), serverlevel);
            nullSwordEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullSwordEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullSwordEntity);
            this.nullSwordUUID = nullSwordEntity.getUUID();
            this.nullSwordEntity = nullSwordEntity;
            nullSwordEntity.setNullEntity(this);
            nullSwordEntity.setNullUUID(this.getUUID());

            NullAxeEntity nullAxeEntity = new NullAxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_AXE.get(), serverlevel);
            nullAxeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullAxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullAxeEntity);
            this.nullAxeUUID = nullAxeEntity.getUUID();
            this.nullAxeEntity = nullAxeEntity;
            nullAxeEntity.setNullEntity(this);
            nullAxeEntity.setNullUUID(this.getUUID());

            NullPickaxeEntity nullPickaxeEntity = new NullPickaxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_PICKAXE.get(), serverlevel);
            nullPickaxeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullPickaxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullPickaxeEntity);
            this.nullPickaxeUUID = nullPickaxeEntity.getUUID();
            this.nullPickaxeEntity = nullPickaxeEntity;
            nullPickaxeEntity.setNullEntity(this);
            nullPickaxeEntity.setNullUUID(this.getUUID());

            NullShovelEntity nullShovelEntity = new NullShovelEntity((EntityType) AnnoyingVillagersModEntities.NULL_SHOVEL.get(), serverlevel);
            nullShovelEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullShovelEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullShovelEntity);
            this.nullShovelUUID = nullShovelEntity.getUUID();
            this.nullShovelEntity = nullShovelEntity;
            nullShovelEntity.setNullEntity(this);
            nullShovelEntity.setNullUUID(this.getUUID());

            NullHoeEntity nullHoeEntity = new NullHoeEntity((EntityType) AnnoyingVillagersModEntities.NULL_HOE.get(), serverlevel);
            nullHoeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullHoeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullHoeEntity);
            this.nullHoeUUID = nullHoeEntity.getUUID();
            this.nullHoeEntity = nullHoeEntity;
            nullHoeEntity.setNullEntity(this);
            nullHoeEntity.setNullUUID(this.getUUID());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (!initialSpawn) {
                this.initialSpawn = true;
                initialSpawn();
            }
            if (nullSwordEntity == null && nullSwordUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullSwordUUID);
                if (entity instanceof NullSwordEntity nullSword) {
                    this.nullSwordEntity = nullSword;
                } else {
                    this.nullSwordUUID = null;
                }
            }
            if (nullAxeEntity == null && nullAxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullAxeUUID);
                if (entity instanceof NullAxeEntity nullAxe) {
                    this.nullAxeEntity = nullAxe;
                } else {
                    this.nullAxeUUID = null;
                }
            }
            if (nullPickaxeEntity == null && nullPickaxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullPickaxeUUID);
                if (entity instanceof NullPickaxeEntity nullPickaxe) {
                    this.nullPickaxeEntity = nullPickaxe;
                } else {
                    this.nullPickaxeUUID = null;
                }
            }
            if (nullShovelEntity == null && nullShovelUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullShovelUUID);
                if (entity instanceof NullShovelEntity nullShovel) {
                    this.nullShovelEntity = nullShovel;
                } else {
                    this.nullShovelUUID = null;
                }
            }
            if (nullHoeEntity == null && nullHoeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullHoeUUID);
                if (entity instanceof NullHoeEntity nullHoe) {
                    this.nullHoeEntity = nullHoe;
                } else {
                    nullHoeUUID = null;
                }
            }
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(24, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullEntity.this.getTarget() != null && !NullEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullEntity.this.getMoveControl().hasWanted() && NullEntity.this.getTarget() != null && NullEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullEntity.this.getTarget();

                if (NullEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullEntity.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 5.0D);
                    }
                }
            }
        });
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        return false;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        NullOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.is(DamageTypes.ON_FIRE)) return false;
        if (damagesource.is(DamageTypes.IN_FIRE)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.remove(RemovalReason.KILLED);
            }

            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "null");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    public void baseTick() {
        super.baseTick();
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "execute as @s at @s run particle annoyingvillagers:null ~ ~0.8 ~ 0.2 0.2 0.2 0.07 50 force",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (Math.random() <= 0.1D) {
            RandomSource randomSource = RandomSource.create();
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
        }

        if (this.getTarget() != null) {
            this.setDeltaMovement(new Vec3(this.getLookAngle().x * 0.2D, this.getLookAngle().y * 0.2D, this.getLookAngle().z * 0.2D));
        }
    }

    protected void checkFallDamage(double d0, boolean flag, BlockState blockstate, BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (this.level() instanceof ServerLevel serverLevel && pReason.equals(RemovalReason.DISCARDED)) {
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.remove(RemovalReason.DISCARDED);
            }
        }
        super.remove(pReason);
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 3.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        return builder;
    }
}
