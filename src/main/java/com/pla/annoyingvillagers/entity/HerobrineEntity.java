package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.CommonGoals;
import com.pla.annoyingvillagers.util.DelayedTask;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.HerobrineOnEntityTickUpdateProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnHurtProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnDeathProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineWhenEntityFallsProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnAwardKillScoreProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineOnInitialSpawnProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineNaturalSpawnProcedure;

@EventBusSubscriber
public class HerobrineEntity extends Monster {
    private boolean wasOnGroundLastTick = false;
    public HerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE.get(), level);
    }

    public HerobrineEntity(EntityType<HerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.7F);
        this.xpReward = 300;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§5Herobrine§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
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
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death"));
    }

    public void thunderHit(ServerLevel serverlevel, LightningBolt lightningbolt) {
        super.thunderHit(serverlevel, lightningbolt);
//        HerobrineWhenStruckByLightningProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        HerobrineWhenEntityFallsProcedure.execute();
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        HerobrineOnHurtProcedure.execute(this);
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        HerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        double posX = this.getX();
        double posY = this.getY();
        double posZ = this.getZ();
        LevelAccessor levelAccessor = this.level();
        new DelayedTask(28) {
            @Override
            public void run() {
                if (levelAccessor instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                    ServerLevel serverlevel = levelaccessor;
                    HerobrineDeadEntity deadEntity = new HerobrineDeadEntity((EntityType) AnnoyingVillagersModEntities.HEROBRINE_DEAD.get(), serverlevel);
                    deadEntity.moveTo(posX, posY, posZ, levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
                    if (deadEntity instanceof Mob) {
                        Mob mob = (Mob) deadEntity;
                        mob.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(deadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                    }
                    levelaccessor.addFreshEntity(deadEntity);
                    deadEntity.hurt(deadEntity.damageSources().generic(), Float.MAX_VALUE);
                }
            }
        };
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        if (random.nextFloat() < AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get().floatValue()) {
            HerobrineOnAwardKillScoreProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity);
        }
    }

    public void baseTick() {
        super.baseTick();
        HerobrineOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.HEROBRINE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            int i = blockpos.getX();
            int j = blockpos.getY();
            int k = blockpos.getZ();

            return HerobrineNaturalSpawnProcedure.execute(serverlevelaccessor, (double) i, (double) j, (double) k);
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 9.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.0D);
        return builder;
    }
}
