package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

public class EliteHerobrineKnockedEntity extends PathfinderMob {
    private boolean allowInternalDamage = false;

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

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
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
        }
        return spawnGroupData;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (allowInternalDamage) {
            return super.hurt(source, amount);
        }
        return false;
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

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && this.isAlive() && Math.random() <= 0.05D) {
            allowInternalDamage = true;
            super.hurt(this.level().damageSources().generic(), 3.5F);
            allowInternalDamage = false;
        }
        if (!this.level().isClientSide && this.isAlive()) {
            LivingEntityPatch<?> self = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
            if (self != null) {
                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: applying stun");
                self.applyStun(StunType.HOLD, 20);
            }
        }
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.06D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
