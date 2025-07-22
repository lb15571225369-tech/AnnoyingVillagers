package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.Herobrine2DieProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnEntityTickUpdateProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnHurtProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWhenEntityFallsProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnAwardKillScoreProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnInitialSpawnProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineNaturalSpawnProcedure;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@EventBusSubscriber
public class Herobrine2Entity extends Monster {
    public Herobrine2Entity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), level);
    }

    public Herobrine2Entity(EntityType<Herobrine2Entity> entitytype, Level level) {
        super(entitytype, level);
        this.maxUpStep = 0.7F;
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Herobrine§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerMobEntity.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, BlueDemonEntity.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, BlueDemon2Entity.class, true, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
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

    protected void dropCustomDeathLoot(DamageSource damagesource, int i, boolean flag) {
        super.dropCustomDeathLoot(damagesource, i, flag);
        this.spawnAtLocation(new ItemStack(Blocks.OBSIDIAN));
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death"));
    }

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        HerobrineWhenEntityFallsProcedure.execute();
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        HerobrineOnHurtProcedure.execute(this);
        return damagesource == DamageSource.FALL ? false : (damagesource == DamageSource.DROWN ? false : (damagesource == DamageSource.WITHER ? false : (damagesource.getMsgId().equals("witherSkull") ? false : super.hurt(damagesource, f))));
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        Herobrine2DieProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineOnAwardKillScoreProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), entity);
    }

    public void baseTick() {
        super.baseTick();
        HerobrineOnEntityTickUpdateProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            int i = blockpos.getX();
            int j = blockpos.getY();
            int k = blockpos.getZ();

            return HerobrineNaturalSpawnProcedure.execute(serverlevelaccessor, (double) i, (double) j, (double) k);
        });
        DungeonHooks.addDungeonMob((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), 180);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 12.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.0D);
        return builder;
    }
}
