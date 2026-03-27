package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.BbqCombatMode;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BbqEntity extends Chicken {
    @Nullable
    private BlueDemonEntity leader;
    @Nullable
    private UUID leaderUUID;
    @Nullable
    private UUID combatTargetUUID;
    private BbqCombatMode combatMode = BbqCombatMode.IDLE;
    private int formationSide = 1;
    private int chainShotsRemaining;
    private int chainShotInterval;
    private int chainShotCooldown;
    private int combatModeTicks;
    private int meleeCooldown;
    private float orbitAngle;
    private float orbitRadius = 5.0F;

    public BbqEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.BBQ.get(), level);
    }

    public BbqEntity(EntityType<? extends BbqEntity> type, Level level) {
        super(type, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            if (!this.level().isClientSide && this.isAlive()) {
                this.heal(2.0F);
            }
            return false;
        }
        return super.canBeAffected(effect);
    }

    public void setLeader(@Nullable BlueDemonEntity leader) {
        this.leader = leader;
        this.leaderUUID = leader == null ? null : leader.getUUID();
    }

    @Nullable
    public BlueDemonEntity getLeader() {
        if (this.leader != null && this.leader.isAlive()) {
            return this.leader;
        }

        if (!this.level().isClientSide && this.leaderUUID != null) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.leaderUUID);
            if (entity instanceof BlueDemonEntity blueDemon && blueDemon.isAlive()) {
                this.leader = blueDemon;
                return blueDemon;
            }
        }

        return null;
    }

    public BbqCombatMode getCombatMode() {
        return combatMode;
    }

    public void setCombatTarget(@Nullable LivingEntity target) {
        this.combatTargetUUID = target == null ? null : target.getUUID();
    }

    @Nullable
    public LivingEntity getCombatTarget() {
        if (!this.level().isClientSide && this.combatTargetUUID != null) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.combatTargetUUID);
            if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                return livingEntity;
            }
            this.combatTargetUUID = null;
        }

        BlueDemonEntity leader = this.getLeader();
        if (leader != null) {
            LivingEntity target = leader.getTarget();
            if (target != null && target.isAlive()) {
                return target;
            }
        }

        return null;
    }

    public void startOrbit(@Nullable LivingEntity target, int ticks) {
        if (target == null) {
            this.clearCombat();
            return;
        }

        this.setCombatTarget(target);
        this.combatMode = BbqCombatMode.ORBIT;
        this.combatModeTicks = Math.max(this.combatModeTicks, ticks);

        if (this.random.nextInt(4) == 0) {
            this.orbitRadius = 4.0F + this.random.nextFloat() * 2.0F;
        }

        if (this.random.nextInt(6) == 0) {
            this.formationSide = -this.formationSide;
        }
    }

    public void startHeadAttack(@Nullable LivingEntity target, int ticks) {
        if (target == null) {
            this.clearCombat();
            return;
        }

        this.setCombatTarget(target);
        this.combatMode = BbqCombatMode.HEAD_ATTACK;
        this.combatModeTicks = ticks;
        this.chainShotsRemaining = 0;
        this.chainShotCooldown = 0;
        this.orbitAngle = this.random.nextFloat() * ((float)Math.PI * 2.0F);
        this.getNavigation().stop();
    }

    public boolean isHeadAttacking() {
        return this.combatMode == BbqCombatMode.HEAD_ATTACK && this.combatModeTicks > 0;
    }

    public void clearCombat() {
        this.combatMode = BbqCombatMode.IDLE;
        this.combatModeTicks = 0;
        this.combatTargetUUID = null;
        this.chainShotsRemaining = 0;
        this.chainShotCooldown = 0;
        this.setNoGravity(false);
    }

    public void shootChain(LivingEntity target, int shots, int intervalTicks) {
        if (target == null || this.chainShotsRemaining > 0 || this.chainShotCooldown > 0) {
            return;
        }

        this.setCombatTarget(target);
        this.chainShotsRemaining = Math.max(0, shots);
        this.chainShotInterval = Math.max(1, intervalTicks);
        this.chainShotCooldown = this.chainShotInterval;
    }

    public void shootCluster(LivingEntity target, int eggCount, float power, float inaccuracy) {
        if (target == null || this.chainShotsRemaining > 0 || this.chainShotCooldown > 0) {
            return;
        }

        this.setCombatTarget(target);

        for (int i = 0; i < eggCount; i++) {
            this.firePoisonEgg(target, power, inaccuracy);
        }

        this.chainShotCooldown = 45;
    }

    private void tickManualAttacks() {
        if (this.chainShotCooldown > 0) {
            this.chainShotCooldown--;
        }

        if (this.chainShotsRemaining <= 0) {
            return;
        }

        LivingEntity target = this.getCombatTarget();
        if (target == null) {
            this.chainShotsRemaining = 0;
            return;
        }

        if (this.chainShotCooldown > 0) {
            return;
        }

        this.firePoisonEgg(target, 1.45F, 6.0F);
        this.chainShotsRemaining--;

        if (this.chainShotsRemaining > 0) {
            this.chainShotCooldown = this.chainShotInterval;
        } else {
            this.chainShotCooldown = 35;
        }
    }

    private void firePoisonEgg(LivingEntity target, float power, float inaccuracy) {
        if (this.level().isClientSide) {
            return;
        }

        ThrownPoisonEggEntity projectile = new ThrownPoisonEggEntity(
                AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(),
                this,
                this.level()
        );

        double dX = target.getX() - this.getX();
        double dY = target.getEyeY() - projectile.getY();
        double dZ = target.getZ() - this.getZ();

        projectile.setOwner(this);
        projectile.setPos(this.getX(), this.getEyeY() - 0.1D, this.getZ());
        projectile.shoot(dX, dY, dZ, power, inaccuracy);
        this.level().addFreshEntity(projectile);
    }

    private void moveAerialTowards(double x, double y, double z, double accel, double drag) {
        Vec3 wanted = new Vec3(x - this.getX(), y - this.getY(), z - this.getZ());
        double len = wanted.length();

        if (len < 0.05D) {
            this.setDeltaMovement(this.getDeltaMovement().scale(drag));
            return;
        }

        Vec3 desired = wanted.normalize().scale(accel);
        Vec3 next = this.getDeltaMovement().scale(drag).add(desired);
        this.setDeltaMovement(next);
        this.hasImpulse = true;

        float yaw = (float)(Mth.atan2(next.z, next.x) * (180.0F / (float)Math.PI)) - 90.0F;
        this.setYRot(Mth.rotLerp(0.3F, this.getYRot(), yaw));
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    public void startGroundOrbit(@Nullable LivingEntity target, int ticks) {
        if (target == null) {
            this.clearCombat();
            return;
        }

        this.setCombatTarget(target);
        this.combatMode = BbqCombatMode.GROUND_ORBIT;
        this.combatModeTicks = Math.max(this.combatModeTicks, ticks);
        this.setNoGravity(false);

        if (this.random.nextInt(4) == 0) {
            this.orbitRadius = 3.5F + this.random.nextFloat() * 2.0F;
        }

        if (this.random.nextInt(6) == 0) {
            this.formationSide = -this.formationSide;
        }
    }

    private void tickGroundOrbit(LivingEntity target) {
        this.setNoGravity(false);
        this.fallDistance = 0.0F;

        if (this.random.nextInt(70) == 0) {
            this.formationSide = -this.formationSide;
        }

        if (this.random.nextInt(50) == 0) {
            this.orbitRadius = 3.5F + this.random.nextFloat() * 2.0F;
        }

        this.orbitAngle += 0.12F * this.formationSide;

        double x = target.getX() + Mth.cos(this.orbitAngle) * this.orbitRadius;
        double z = target.getZ() + Mth.sin(this.orbitAngle) * this.orbitRadius;

        if (this.distanceTo(target) < 2.5D) {
            Vec3 away = this.position().subtract(target.position());
            away = new Vec3(away.x, 0.0D, away.z);
            if (away.lengthSqr() > 1.0E-4D) {
                Vec3 desired = this.position().add(away.normalize().scale(1.75D));
                this.getNavigation().moveTo(desired.x, this.getY(), desired.z, 1.35D);
                return;
            }
        }

        this.getNavigation().moveTo(x, target.getY(), z, 1.25D);
    }

    private void tickOrbit(LivingEntity target) {
        this.setNoGravity(true);
        this.fallDistance = 0.0F;
        this.getNavigation().stop();

        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.32D, 0.0D));
        }

        if (this.random.nextInt(70) == 0) {
            this.formationSide = -this.formationSide;
        }

        if (this.random.nextInt(50) == 0) {
            this.orbitRadius = 4.0F + this.random.nextFloat() * 2.0F;
        }

        this.orbitAngle += 0.16F * this.formationSide;

        double x = target.getX() + Mth.cos(this.orbitAngle) * this.orbitRadius;
        double z = target.getZ() + Mth.sin(this.orbitAngle) * this.orbitRadius;
        double y = target.getEyeY() + 0.4D + Mth.sin((this.tickCount + this.getId()) * 0.25F) * 0.9D;

        this.moveAerialTowards(x, y, z, 0.18D, 0.86D);
    }

    private void tickHeadAttack(LivingEntity target) {
        if (this.combatModeTicks <= 0) {
            this.startOrbit(target, 20);
            return;
        }

        this.setNoGravity(true);
        this.fallDistance = 0.0F;
        this.getNavigation().stop();

        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.38D, 0.0D));
        }

        this.orbitAngle += 0.42F * this.formationSide;

        double x = target.getX() + Mth.cos(this.orbitAngle) * 0.85D;
        double z = target.getZ() + Mth.sin(this.orbitAngle) * 0.85D;
        double y = target.getEyeY() + 0.15D + Mth.sin((this.tickCount + this.getId()) * 0.55F) * 0.35D;

        this.moveAerialTowards(x, y, z, 0.26D, 0.82D);

        if (this.meleeCooldown <= 0 && this.distanceToSqr(target.getX(), target.getEyeY(), target.getZ()) < 2.25D) {
            target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            this.doEnchantDamageEffects(this, target);
            this.playSound(SoundEvents.CHICKEN_HURT, 1.0F, 1.1F + this.random.nextFloat() * 0.2F);
            this.playSound(SoundEvents.CHICKEN_AMBIENT, 0.75F, 1.2F + this.random.nextFloat() * 0.3F);
            this.meleeCooldown = 8;
        }
    }

    private void tickLeaderFollow() {
        BlueDemonEntity leader = this.getLeader();
        this.setNoGravity(false);

        if (leader == null || !leader.isAlive()) {
            this.getNavigation().stop();
            return;
        }

        if (this.distanceTo(leader) > 5.0D) {
            this.getNavigation().moveTo(leader, 1.35D);
        } else {
            this.getNavigation().stop();
        }
    }

    public void startParallelPursuit(@Nullable LivingEntity target, int ticks) {
        if (target == null) {
            this.clearCombat();
            return;
        }

        this.setCombatTarget(target);
        this.combatMode = BbqCombatMode.PARALLEL;
        this.combatModeTicks = Math.max(this.combatModeTicks, ticks);
        this.setNoGravity(false);

        if (this.random.nextInt(8) == 0) {
            this.formationSide = -this.formationSide;
        }
    }

    private void tickParallelPursuit(LivingEntity target) {
        BlueDemonEntity leader = this.getLeader();
        this.setNoGravity(false);

        if (leader == null || !leader.isAlive()) {
            this.tickLeaderFollow();
            return;
        }

        if (this.random.nextInt(80) == 0) {
            this.formationSide = -this.formationSide;
        }

        Vec3 forward = target.position().subtract(leader.position());
        forward = new Vec3(forward.x, 0.0D, forward.z);

        if (forward.lengthSqr() < 1.0E-4D) {
            forward = new Vec3(leader.getLookAngle().x, 0.0D, leader.getLookAngle().z);
        }

        if (forward.lengthSqr() < 1.0E-4D) {
            forward = new Vec3(1.0D, 0.0D, 0.0D);
        }

        forward = forward.normalize();

        Vec3 side = new Vec3(-forward.z, 0.0D, forward.x).scale(2.2D * this.formationSide);
        Vec3 back = forward.scale(-0.75D);
        Vec3 desired = leader.position().add(side).add(back);

        this.getLookControl().setLookAt(target, 45.0F, 45.0F);

        if (this.distanceToSqr(desired.x, leader.getY(), desired.z) > 2.25D) {
            this.getNavigation().moveTo(desired.x, leader.getY(), desired.z, 1.45D);
        } else {
            this.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            return;
        }

        BlueDemonEntity leader = this.getLeader();

        if (this.combatModeTicks > 0) {
            this.combatModeTicks--;
        }

        if (this.meleeCooldown > 0) {
            this.meleeCooldown--;
        }

        LivingEntity target = this.getCombatTarget();

        if (target != null && target.isAlive()) {
            if (leader != null && leader.isAlive() && leader.getTarget() == target && leader.distanceTo(target) > 10.0D && this.combatMode != BbqCombatMode.PARALLEL) {
                this.startParallelPursuit(target, 20);
            }

            this.getLookControl().setLookAt(target, 45.0F, 45.0F);
            this.tickManualAttacks();

            switch (this.combatMode) {
                case HEAD_ATTACK -> {
                    if (this.combatModeTicks > 0) {
                        this.tickHeadAttack(target);
                    } else {
                        this.startGroundOrbit(target, 20);
                        this.tickGroundOrbit(target);
                    }
                }
                case ORBIT -> this.tickOrbit(target);
                case GROUND_ORBIT -> this.tickGroundOrbit(target);
                case PARALLEL -> this.tickParallelPursuit(target);
                default -> {
                    if (leader != null && leader.isAlive() && leader.distanceTo(target) > 10.0D) {
                        this.startParallelPursuit(target, 20);
                        this.tickParallelPursuit(target);
                    } else {
                        this.startGroundOrbit(target, 20);
                        this.tickGroundOrbit(target);
                    }
                }
            }
            return;
        }

        this.clearCombat();
        this.tickLeaderFollow();
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        boolean result = super.hurt(damageSource, amount);

        if (result && !this.level().isClientSide && damageSource.getEntity() instanceof LivingEntity livingEntity) {
            BlueDemonEntity leader = this.getLeader();
            this.startOrbit(livingEntity, 40);
            if (leader != null) {
                leader.setTarget(livingEntity);
            }
        }

        return result;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level,
                                                 @NotNull DifficultyInstance difficulty,
                                                 @NotNull MobSpawnType reason,
                                                 @Nullable SpawnGroupData spawnData,
                                                 @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "blue_demon");
        }

        return data;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.leaderUUID != null) {
            tag.putUUID("LeaderUUID", this.leaderUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("LeaderUUID")) {
            this.leaderUUID = tag.getUUID("LeaderUUID");
        }
    }

    public static @NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ARMOR, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }
}