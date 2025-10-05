package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class Herobrine4Entity extends Monster {
    private boolean whiteEye = false;
    private boolean summoning = false;
    private int summonTiming = -1;

    public void setWhiteEye(boolean whiteEye) {
        this.whiteEye = whiteEye;
    }

    public boolean isWhiteEye() {
        return whiteEye;
    }

    public boolean isSummoning() {
        return summoning;
    }

    public void setSummoning(boolean summoning) {
        this.summoning = summoning;
    }

    public Herobrine4Entity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_4.get(), level);
    }

    public Herobrine4Entity(EntityType<Herobrine4Entity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomName(Component.literal("Greg"));
        this.setCustomNameVisible(true);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VillagerScoutEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VillagerScoutCaptainEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, BlueVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, GreenVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RedVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PurpleVillagerGeneralEntity.class, 12.0F, 1.2D, 1.8D));

        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerMobEntity.class, 12.0F, 1.2D, 1.8D, target ->
                target instanceof PlayerMobEntity
                        && !(target instanceof PlayerMobDeadEntity)
                        && !(target instanceof InfectedPlayerMobEntity)
                        && !(target instanceof Herobrine5Entity)));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, SteveEntity.class, 24.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Steve2Entity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AngrySteveEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AlexEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, JevEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ChrisEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, BlueDemonEntity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, BlueDemon2Entity.class, 12.0F, 1.2D, 1.8D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, BbqEntity.class, 12.0F, 1.2D, 1.8D));

        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isDay(level())) {
            this.setWhiteEye(true);
        } else {
            if (!this.summoning && this.whiteEye) {
                this.whiteEye = false;
            }
        }
        if (this.getHealth() <= 10 && this.summonTiming == -1) {
            this.summonTiming = 30;
            this.setHealth(11);
        }
        if (this.summonTiming > 0) {
            this.summonTiming = this.summonTiming - 1;
        }
        if (this.summonTiming == 1) {
            summonHerobrines();
        }
    }

    private void summonHerobrines() {
        this.summoning = true;
        this.setNoAi(true);
        this.whiteEye = true;
        final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
        if (livingentitypatch != null && !this.level().isClientSide()) {
            livingentitypatch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
        }

        float yaw = this.getYRot();
        double radians = Math.toRadians(yaw);
        double offsetX = -Math.sin(radians);
        double offsetZ = Math.cos(radians);

        double spawnX = this.getX() + offsetX * 3;
        double spawnY = this.getY();
        double spawnZ = this.getZ() + offsetZ * 3;

        double summonLookX = this.getX() + offsetX * 10;
        double summonLookZ = this.getZ() + offsetZ * 10;

        this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(spawnX, spawnY, spawnZ));
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;
            ShadowHerobrineEntity shadowHerobrineEntity = new ShadowHerobrineEntity((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), serverlevel);
            shadowHerobrineEntity.moveTo(spawnX, spawnY, spawnZ, this.getYRot(), this.getXRot());
            shadowHerobrineEntity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(summonLookX, spawnY, summonLookZ));
            shadowHerobrineEntity.setGregUUID(this.getUUID());
            shadowHerobrineEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(shadowHerobrineEntity);
        }
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean isDay(Level level) {
        long timeOfDay = level.getDayTime() % 24000L;
        return timeOfDay >= 0 && timeOfDay < 13000;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (this.summoning || this.summonTiming > 0) {
            return false;
        }
        return super.hurt(damagesource, f);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<Greg> This world is peaceful, right?"), false);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public void baseTick() {
        super.baseTick();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        whiteEye = pCompound.getBoolean("WhiteEye");
        summoning = pCompound.getBoolean("Summoning");
        summonTiming = pCompound.getInt("SummonTiming");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("WhiteEye", whiteEye);
        pCompound.putBoolean("Summoning", summoning);
        pCompound.putInt("SummonTiming", summonTiming);
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5D);
        builder = builder.add(Attributes.MAX_HEALTH, 80.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return builder;
    }
}
