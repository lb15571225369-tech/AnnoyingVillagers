package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@EventBusSubscriber
public class BbqEntity extends PathfinderMob {
    private UUID followTargetUUID;
    private Monster followTarget;

    public void setFollowTarget(Monster followTarget) {
        if (followTarget instanceof BlueDemonEntity || followTarget instanceof BlueDemon2Entity) {
            this.followTarget = followTarget;
            this.followTargetUUID = followTarget.getUUID();
        }
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public BbqEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BBQ.get(), level);
    }

    public BbqEntity(EntityType<BbqEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(Component.literal("BBQ Sauce"));
        this.setCustomNameVisible(true);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, HerobrineEntity.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Herobrine2Entity.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, HerobrineEntity.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Herobrine3Entity.class, false, false));
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    getNavigation().moveTo(followTarget, 2.0D);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 2.0D;
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> followTarget != null
                && followTarget.isAlive()
                && target != null
                && target.getLastHurtMob() == followTarget));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, VillagerScoutEntity.class, false, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, RedVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, BlueVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, GreenVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, PurpleVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, VillagerScoutCaptainEntity.class, false, false));
        this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.targetSelector.addGoal(14, new NearestAttackableTargetGoal(this, SteveEntity.class, false, false));
        this.targetSelector.addGoal(19, new NearestAttackableTargetGoal(this, Monster.class, false, false));
        this.targetSelector.addGoal(20, new NearestAttackableTargetGoal(this, Steve2Entity.class, false, false));
        this.goalSelector.addGoal(24, new MeleeAttackGoal(this, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        this.targetSelector.addGoal(25, new NearestAttackableTargetGoal(this, Herobrine3Entity.class, false, false));
        this.targetSelector.addGoal(26, new NearestAttackableTargetGoal(this, Herobrine2Entity.class, false, false));
        this.goalSelector.addGoal(27, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(28, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(29, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(30, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(31, new OpenDoorGoal(this, false));
        this.goalSelector.addGoal(32, new FloatGoal(this));
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add blue_demon",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team modify blue_demon friendlyFire false",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join blue_demon @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (followTarget == null && followTargetUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(followTargetUUID);
                if (entity instanceof Monster monster) {
                    followTarget = monster;
                } else {
                    followTargetUUID = null;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
            }
        }
    }

    public boolean hurt(DamageSource damagesource, float f) {
        Level level = this.level();
        if (!level.isClientSide()) {
            Projectile projectile = new ThrownEgg(EntityType.EGG, level);
            projectile.setOwner(this);
            projectile.setPos(this.getX(), this.getEyeY() - 0.1D, this.getZ());
            projectile.shoot(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 2.0F, 0.0F);
            level.addFreshEntity(projectile);
        }
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) return false;
        return super.hurt(damagesource, f);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.BBQ.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) > 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27D);
        builder = builder.add(Attributes.MAX_HEALTH, 1007.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        return builder;
    }
}
