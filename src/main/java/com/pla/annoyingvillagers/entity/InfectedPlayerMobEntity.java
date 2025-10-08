package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;

public class InfectedPlayerMobEntity extends PlayerMobEntity {

    public InfectedPlayerMobEntity(EntityType<? extends InfectedPlayerMobEntity> type, Level level) {
        super(type, level);
        this.xpReward = 300;
        this.setMaxUpStep(0.6F);
        this.xpReward = 7;
        this.setNoAi(false);
        this.setCustomNameVisible(true);
    }

    public InfectedPlayerMobEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), level);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
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

    public void baseTick() {
        super.baseTick();
        InfectedChrisOnTickProcedure.execute(this);
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        String possessedBy = this.getPersistentData().getString("possessed_by");
        if (possessedBy.equals("herobrine_2")) {
            Herobrine2DieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        } else if (possessedBy.equals("herobrine_1")) {
            Herobrine1DieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        } else if (possessedBy.equals("herobrine_5") || possessedBy.equals("herobrine_6")) {
            HerobrineCloneDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        } else if (possessedBy.equals("herobrine_7")) {
            Herobrine7OnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        } else if (possessedBy.equals("shadow_herobrine")) {
            DarkHerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        } else if (possessedBy.equals("null")) {
            NullDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            PlayerMobDeadEntity corpse =  new PlayerMobDeadEntity(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), levelaccessor);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            corpse.setUsername(this.getUsername());
            corpse.setProfile(this.getProfile());
            corpse.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            this.setInvisible(true);
            this.remove(Entity.RemovalReason.KILLED);
            levelaccessor.addFreshEntity(corpse);
            new DelayedTask(3) {
                @Override
                public void run() {
                    try {
                        corpse.getServer().getCommands().getDispatcher().execute(
                                "kill @s",
                                corpse.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                    } catch (CommandSyntaxException e) {
                    }
                }
            };
        }

        LevelAccessor levelaccessor1 = this.level();
        ItemEntity itementity;
        LivingEntity livingentity = (LivingEntity)this;
        ItemStack itemstack;

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                itementity = new ItemEntity(level,  this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                itementity = new ItemEntity(level,  this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @javax.annotation.Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        LivingEntity livingentity = (LivingEntity) this;

        if (!livingentity.level().isClientSide()) {
            livingentity.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.HEROBRINE.get(), 8000, 3));
        }
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add herobrine",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team modify herobrine friendlyFire false",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join herobrine @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }
        return spawngroupdata;
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) {}

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