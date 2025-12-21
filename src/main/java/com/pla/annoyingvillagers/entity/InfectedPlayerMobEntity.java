package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.Objects;

public class InfectedPlayerMobEntity extends PlayerMobEntity {

    public InfectedPlayerMobEntity(EntityType<? extends InfectedPlayerMobEntity> type, Level level) {
        super(type, level);
        this.xpReward = 300;
        this.setMaxUpStep(0.6F);
        this.xpReward = 7;
        this.setNoAi(true);
        this.setCustomNameVisible(true);
    }

    public InfectedPlayerMobEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), level);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death")));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide() && this.tickCount % 20 == 0) {
            this.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.HEROBRINE.get(), 30, 0, false, false));
        }
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        String possessedBy = this.getPersistentData().getString("possessed_by");
        switch (possessedBy) {
            case "herobrine_clone" ->
                    ShadowHerobrineCloneDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            case "shadow_herobrine_clone" ->
                    HerobrineCloneDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            case "low_herobrine_clone", "low_shadow_herobrine_clone" ->
                    LowHerobrineCloneDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            case "herobrine_7" ->
                    Herobrine7DieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            case "shadow_herobrine" ->
                    DarkHerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
            case "null" -> NullDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack itemstack;
            ItemEntity itementity;

            itemstack = this.getItemBySlot(EquipmentSlot.FEET);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.FEET);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.CHEST);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            itementity = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
            itementity.setPickUpDelay(10);
            serverLevel.addFreshEntity(itementity);

            if (AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
                PlayerMobDeadEntity corpse = new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), serverLevel);
                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                corpse.setUsername(this.getUsername());
                corpse.setProfile(this.getProfile());
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.setInvisible(true);
                this.remove(Entity.RemovalReason.KILLED);
                serverLevel.addFreshEntity(corpse);
                corpse.kill();
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (!this.level().isClientSide()) {
            TeamUtil.addOrJoinTeam(this, "herobrine");
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity entity) {}

    protected void pushEntities() {}

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0D);
        return builder;
    }
}