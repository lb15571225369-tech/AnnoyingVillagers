package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.procedures.EliteHerobrineOnDeathProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Objects;

public class EliteHerobrineKnockedEntity extends PathfinderMob {
    private int wardenCallingCooldown;
    private int eatCount = 0;
    final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    public EliteHerobrineKnockedEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), level);
    }

    public EliteHerobrineKnockedEntity(EntityType<EliteHerobrineKnockedEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomNameVisible(false);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.ELITE_OBSIDIAN_LONG.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike) AnnoyingVillagersModItems.ELITE_OBSIDIAN_BIG.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.ELITE_OBSIDIAN.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        wardenCallingCooldown = pCompound.getInt("WardenCallingCooldown");
        eatCount = pCompound.getInt("EatCount");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("WardenCallingCooldown", wardenCallingCooldown);
        pCompound.putInt("EatCount", eatCount);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        return;
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

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof HerobrineWardenEntity) {
            eatCount = eatCount + 1;
            if (!this.level().isClientSide()) {
                this.level().playSound((Player) null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.eat"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
            if (eatCount == 10) {
                this.remove(RemovalReason.DISCARDED);
            }
            return super.hurt(pSource, 0.0F);
        }
        return super.hurt(pSource, 1.0F);
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            if (this.wardenCallingCooldown >= 0) {
                this.wardenCallingCooldown = this.wardenCallingCooldown - 1;
            }
            if (livingentitypatch != null) {
                if (eatCount == 1 || eatCount == 2) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.EATING_ELITE_1, 0.0F);
                } else if (eatCount == 3 || eatCount == 4) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.EATING_ELITE_2, 0.0F);
                } else if (eatCount == 5 || eatCount == 6) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.EATING_ELITE_3, 0.0F);
                } else if (eatCount > 6) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.EATING_ELITE_4, 0.0F);
                }
            }
            this.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
            if (this.wardenCallingCooldown == 0) {
                ServerLevel serverlevel = (ServerLevel) this.level();
                HerobrineWardenEntity herobrineWardenEntity = new HerobrineWardenEntity((EntityType) AnnoyingVillagersModEntities.HEROBRINE_WARDEN.get(), serverlevel);
                double dist = (this.getBbWidth() + herobrineWardenEntity.getBbWidth()) * 0.5 + 0.5;
                Vec3 backDir = Vec3.directionFromRotation(0.0F, this.getYRot()).normalize();
                Vec3 backPos = this.position().subtract(backDir.scale(dist));
                herobrineWardenEntity.moveTo(backPos.x, this.getY(), backPos.z, this.getYRot(), this.getXRot());
                herobrineWardenEntity.finalizeSpawn(serverlevel, serverlevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                herobrineWardenEntity.setEatingUUID(this.getUUID());
                herobrineWardenEntity.setEatingHerobrine(this);
                serverlevel.addFreshEntity(herobrineWardenEntity);
            }
        }
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.getPersistentData().contains("FromElite")) {
            String fromElite = this.getPersistentData().getString("FromElite");
            EliteHerobrineOnDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, fromElite);
        }
        if (this.level() instanceof ServerLevel levelaccessor && AnnoyingVillagersConfig.PHYSIC_MOD_COMPAT.get()) {
            ServerLevel serverlevel = (ServerLevel)levelaccessor;
            EliteHerobrineDeadEntity eliteHerobrineDeadEntity = new EliteHerobrineDeadEntity((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_DEAD.get(), serverlevel);

            eliteHerobrineDeadEntity.moveTo(this.getX(), this.getY(), this.getZ(), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            eliteHerobrineDeadEntity.finalizeSpawn(serverlevel, levelaccessor.getCurrentDifficultyAt(eliteHerobrineDeadEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            this.remove(RemovalReason.KILLED);
            levelaccessor.addFreshEntity(eliteHerobrineDeadEntity);
            try {
                eliteHerobrineDeadEntity.getServer().getCommands().getDispatcher().execute(
                        "kill @s",
                        eliteHerobrineDeadEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData spawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (!pLevel.isClientSide()) {
            int d0 = (int) this.getX();
            int d1 = (int) this.getY();
            int d2 = (int) this.getZ();
            RandomSource randomSource = RandomSource.create();
            pLevel.addParticle((SimpleParticleType) AnnoyingVillagersModParticleTypes.LIGHT.get(), d0, d1, d2, 0, 0, 0);
            pLevel.setBlock(new BlockPos(d0 + 1, d1, d2 + 1), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
            pLevel.setBlock(new BlockPos((int) d0 + Mth.nextInt(randomSource, -5, 5), d1 - Mth.nextInt(randomSource, 0, 1), d2 + Mth.nextInt(randomSource, -5, 5)), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
            pLevel.setBlock(new BlockPos(d0 + Mth.nextInt(randomSource, -5, 5), d1 + Mth.nextInt(randomSource, 0, 1), d2 + Mth.nextInt(randomSource, -5, 5)), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
            pLevel.setBlock(new BlockPos(d0 + Mth.nextInt(randomSource, -5, 5), d1 - Mth.nextInt(randomSource, 0, 1), d2 + Mth.nextInt(randomSource, -5, 5)), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
            pLevel.setBlock(new BlockPos(d0 + Mth.nextInt(randomSource, -5, 5), d1 - Mth.nextInt(randomSource, 0, 1), d2 + Mth.nextInt(randomSource, -5, 5)), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
            pLevel.setBlock(new BlockPos(d0 + Mth.nextInt(randomSource, -5, 5), d1, d2 + Mth.nextInt(randomSource, 3, 10)), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);

            if (this.getPersistentData().contains("FromElite") && this.getPersistentData().getString("FromElite").equals("DemoniacVoltageReaver")) {
                ItemEntity itemEntity = new ItemEntity(this.level(), d0 + Mth.nextDouble(randomSource, -5.0D, 5.0D), d1, d2 + Mth.nextDouble(randomSource, -5.0D, 5.0D), new ItemStack((ItemLike)AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_FRAGMENT.get()));
                itemEntity.setPickUpDelay(10);
                pLevel.addFreshEntity(itemEntity);

                ItemEntity itemEntity1 = new ItemEntity(this.level(), d0 + Mth.nextDouble(randomSource, -5.0D, 5.0D), d1, d2 + Mth.nextDouble(randomSource, -5.0D, 5.0D), new ItemStack((ItemLike)AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_FRAGMENT.get()));
                itemEntity1.setPickUpDelay(10);
                pLevel.addFreshEntity(itemEntity1);

                ItemEntity itemEntity2 = new ItemEntity(this.level(), d0 + Mth.nextDouble(randomSource, -5.0D, 5.0D), d1, d2 + Mth.nextDouble(randomSource, -5.0D, 5.0D), new ItemStack((ItemLike)AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_FRAGMENT.get()));
                itemEntity2.setPickUpDelay(10);
                pLevel.addFreshEntity(itemEntity2);

                ItemEntity itemEntity3 = new ItemEntity(this.level(), d0 + Mth.nextDouble(randomSource, -5.0D, 5.0D), d1, d2 + Mth.nextDouble(randomSource, -5.0D, 5.0D), new ItemStack((ItemLike)AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_BLADE.get()));
                itemEntity3.setPickUpDelay(10);
                pLevel.addFreshEntity(itemEntity3);
            }

            this.wardenCallingCooldown = 1200;
        }
        return spawnGroupData;
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity other) {
    }

    @Override
    public void push(Entity other) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.06D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return builder;
    }
}
