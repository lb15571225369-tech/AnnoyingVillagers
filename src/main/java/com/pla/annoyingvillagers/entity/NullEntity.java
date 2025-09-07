package com.pla.annoyingvillagers.entity;

import java.util.*;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.HerobrineTransfromProcedure;
import com.pla.annoyingvillagers.procedures.NullOnHurtProcedure;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;

public class NullEntity extends Monster {
    private NullSwordEntity nullSwordEntity;
    private UUID nullSwordUUID;

    private NullAxeEntity nullAxeEntity;
    private UUID nullAxeUUID;

    private NullPickaxeEntity nullPickaxeEntity;
    private UUID nullPickaxeUUID;

    private NullShovelEntity nullShovelEntity;
    private UUID nullShovelUUID;

    private NullHoeEntity nullHoeEntity;
    private UUID nullHoeUUID;

    private boolean initialSpawn = false;

    public NullSwordEntity getNullSwordEntity() {
        return nullSwordEntity;
    }

    public NullAxeEntity getNullAxeEntity() {
        return nullAxeEntity;
    }

    public NullPickaxeEntity getNullPickaxeEntity() {
        return nullPickaxeEntity;
    }

    public NullShovelEntity getNullShovelEntity() {
        return nullShovelEntity;
    }

    public NullHoeEntity getNullHoeEntity() {
        return nullHoeEntity;
    }

    public NullEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.NULL.get(), level);
    }

    public NullEntity(EntityType<NullEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (nullSwordUUID != null) {
            tag.putUUID("NullSwordUUID", nullSwordUUID);
        }
        if (nullAxeUUID != null) {
            tag.putUUID("NullAxeUUID", nullAxeUUID);
        }
        if (nullPickaxeUUID != null) {
            tag.putUUID("NullPickaxeUUID", nullPickaxeUUID);
        }
        if (nullShovelUUID != null) {
            tag.putUUID("NullShovelUUID", nullShovelUUID);
        }
        if (nullHoeUUID != null) {
            tag.putUUID("NullHoeUUID", nullHoeUUID);
        }
        tag.putBoolean("InitialSpawn", initialSpawn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullSwordUUID")) {
            nullSwordUUID = tag.getUUID("NullSwordUUID");
        }
        if (tag.hasUUID("NullAxeUUID")) {
            nullAxeUUID = tag.getUUID("NullAxeUUID");
        }
        if (tag.hasUUID("NullPickaxeUUID")) {
            nullPickaxeUUID = tag.getUUID("NullPickaxeUUID");
        }
        if (tag.hasUUID("NullShovelUUID")) {
            nullShovelUUID = tag.getUUID("NullShovelUUID");
        }
        if (tag.hasUUID("NullHoeUUID")) {
            nullHoeUUID = tag.getUUID("NullHoeUUID");
        }
        initialSpawn = tag.getBoolean("InitialSpawn");
    }

    private void initialSpawn() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            NullSwordEntity nullSwordEntity = new NullSwordEntity((EntityType) AnnoyingVillagersModEntities.NULL_SWORD.get(), serverlevel);
            nullSwordEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullSwordEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullSwordEntity);
            this.nullSwordUUID = nullSwordEntity.getUUID();
            this.nullSwordEntity = nullSwordEntity;
            nullSwordEntity.setNullEntity(this);
            nullSwordEntity.setNullUUID(this.getUUID());

            NullAxeEntity nullAxeEntity = new NullAxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_AXE.get(), serverlevel);
            nullAxeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullAxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullAxeEntity);
            this.nullAxeUUID = nullAxeEntity.getUUID();
            this.nullAxeEntity = nullAxeEntity;
            nullAxeEntity.setNullEntity(this);
            nullAxeEntity.setNullUUID(this.getUUID());

            NullPickaxeEntity nullPickaxeEntity = new NullPickaxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_PICKAXE.get(), serverlevel);
            nullPickaxeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullPickaxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullPickaxeEntity);
            this.nullPickaxeUUID = nullPickaxeEntity.getUUID();
            this.nullPickaxeEntity = nullPickaxeEntity;
            nullPickaxeEntity.setNullEntity(this);
            nullPickaxeEntity.setNullUUID(this.getUUID());

            NullShovelEntity nullShovelEntity = new NullShovelEntity((EntityType) AnnoyingVillagersModEntities.NULL_SHOVEL.get(), serverlevel);
            nullShovelEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullShovelEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullShovelEntity);
            this.nullShovelUUID = nullShovelEntity.getUUID();
            this.nullShovelEntity = nullShovelEntity;
            nullShovelEntity.setNullEntity(this);
            nullShovelEntity.setNullUUID(this.getUUID());

            NullHoeEntity nullHoeEntity = new NullHoeEntity((EntityType) AnnoyingVillagersModEntities.NULL_HOE.get(), serverlevel);
            nullHoeEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullHoeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullHoeEntity);
            this.nullHoeUUID = nullHoeEntity.getUUID();
            this.nullHoeEntity = nullHoeEntity;
            nullHoeEntity.setNullEntity(this);
            nullHoeEntity.setNullUUID(this.getUUID());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (!initialSpawn) {
                this.initialSpawn = true;
                initialSpawn();
            }
            if (nullSwordEntity == null && nullSwordUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullSwordUUID);
                if (entity instanceof NullSwordEntity nullSword) {
                    this.nullSwordEntity = nullSword;
                } else {
                    this.nullSwordUUID = null;
                }
            }
            if (nullAxeEntity == null && nullAxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullAxeUUID);
                if (entity instanceof NullAxeEntity nullAxe) {
                    this.nullAxeEntity = nullAxe;
                } else {
                    this.nullAxeUUID = null;
                }
            }
            if (nullPickaxeEntity == null && nullPickaxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullPickaxeUUID);
                if (entity instanceof NullPickaxeEntity nullPickaxe) {
                    this.nullPickaxeEntity = nullPickaxe;
                } else {
                    this.nullPickaxeUUID = null;
                }
            }
            if (nullShovelEntity == null && nullShovelUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullShovelUUID);
                if (entity instanceof NullShovelEntity nullShovel) {
                    this.nullShovelEntity = nullShovel;
                } else {
                    this.nullShovelUUID = null;
                }
            }
            if (nullHoeEntity == null && nullHoeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullHoeUUID);
                if (entity instanceof NullHoeEntity nullHoe) {
                    this.nullHoeEntity = nullHoe;
                } else {
                    nullHoeUUID = null;
                }
            }
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
        this.goalSelector.addGoal(24, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullEntity.this.getTarget() != null && !NullEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullEntity.this.getMoveControl().hasWanted() && NullEntity.this.getTarget() != null && NullEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullEntity.this.getTarget();

                if (NullEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullEntity.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 5.0D);
                    }
                }
            }
        });
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

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        return false;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        NullOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "null");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
            if (this.getNullSwordEntity() != null) {
                if (!this.level().isClientSide()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getNullSwordEntity().getX(), this.getNullSwordEntity().getY(), this.getNullSwordEntity().getZ(), new ItemStack(Items.DIAMOND_SWORD));
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.nullSwordEntity.discard();
            }
            if (this.getNullAxeEntity() != null) {
                if (!this.level().isClientSide()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getNullAxeEntity().getX(), this.getNullAxeEntity().getY(), this.getNullAxeEntity().getZ(), new ItemStack(Items.DIAMOND_AXE));
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.nullAxeEntity.discard();
            }
            if (this.getNullPickaxeEntity() != null) {
                if (!this.level().isClientSide()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getNullPickaxeEntity().getX(), this.getNullPickaxeEntity().getY(), this.getNullPickaxeEntity().getZ(), new ItemStack(Items.DIAMOND_PICKAXE));
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.nullPickaxeEntity.discard();
            }
            if (this.getNullShovelEntity() != null) {
                if (!this.level().isClientSide()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getNullShovelEntity().getX(), this.getNullShovelEntity().getY(), this.getNullShovelEntity().getZ(), new ItemStack(Items.DIAMOND_SHOVEL));
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.nullShovelEntity.discard();
            }
            if (this.getNullHoeEntity() != null) {
                if (!this.level().isClientSide()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getNullHoeEntity().getX(), this.getNullHoeEntity().getY(), this.getNullHoeEntity().getZ(), new ItemStack(Items.DIAMOND_HOE));
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.nullHoeEntity.discard();
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        if (!this.level().isClientSide() && this.level().getServer() != null) {
            this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("Null has arrived"), false);
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

        this.getPersistentData().putBoolean("a_player", true);
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Blocks.AIR));
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }

    public void baseTick() {
        super.baseTick();
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "execute as @s at @s run particle annoyingvillagers:null ~ ~0.8 ~ 0.2 0.2 0.2 0.07 50 force",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (Math.random() <= 0.1D) {
            RandomSource randomSource = RandomSource.create();
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.moveTo(this.getX() + (double) Mth.nextInt(randomSource, -4, 4), this.getY() + (double) Mth.nextInt(randomSource, -2, 2), this.getZ() + (double) Mth.nextInt(randomSource, -4, 4));
            }
        }

        if (this.getTarget() != null) {
            this.setDeltaMovement(new Vec3(this.getLookAngle().x * 0.2D, this.getLookAngle().y * 0.2D, this.getLookAngle().z * 0.2D));
        }
    }

    protected void checkFallDamage(double d0, boolean flag, BlockState blockstate, BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 3.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 150.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        return builder;
    }
}
