package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine4Entity extends Monster {

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

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && !this.isShiftKeyDown()) {
            this.setShiftKeyDown(true);
        }
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public void baseTick() {
        super.baseTick();
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add((Attribute) ForgeMod.SWIM_SPEED.get(), 0.26D);
        return builder;
    }
}
