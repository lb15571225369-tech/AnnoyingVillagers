package com.pla.annoyingvillagers.entity;

import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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

public class NullHoeEntity extends Monster {
    private UUID nullUUID;
    private NullEntity nullEntity;

    public void setNullUUID(UUID nullUUID) {
        this.nullUUID = nullUUID;
    }

    public void setNullEntity(NullEntity nullEntity) {
        this.nullEntity = nullEntity;
    }

    public NullHoeEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.NULL_HOE.get(), level);
    }

    public NullHoeEntity(EntityType<NullHoeEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(60.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_HOE.get()));
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getMainHandItem().getItem() != AnnoyingVillagersModItems.NULL_HOE.get()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_HOE.get()));
        }
        if (this.getItemBySlot(EquipmentSlot.OFFHAND).getItem() != Items.AIR) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        }
        if (this.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.AIR) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.AIR));
        }
        if (this.getItemBySlot(EquipmentSlot.CHEST).getItem() != Items.AIR) {
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.AIR));
        }
        if (this.getItemBySlot(EquipmentSlot.LEGS).getItem() != Items.AIR) {
            this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.AIR));
        }
        if (this.getItemBySlot(EquipmentSlot.FEET).getItem() != Items.AIR) {
            this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.AIR));
        }
        if (!level().isClientSide) {
            if (nullEntity == null && nullUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(nullUUID);
                if (entity instanceof NullEntity entityNull) {
                    this.nullEntity = entityNull;
                } else {
                    this.nullEntity = null;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                nullUUID = null;
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (nullUUID != null) {
            tag.putUUID("NullUUID", nullUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullUUID")) {
            nullUUID = tag.getUUID("NullUUID");
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && nullEntity.getTarget() == target));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && target.getLastHurtMob() == nullEntity));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4D, 20) {
            protected Vec3 getPosition() {
                Random random = new Random();
                double d0 = NullHoeEntity.this.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d1 = NullHoeEntity.this.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d2 = NullHoeEntity.this.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F));

                return new Vec3(d0, d1, d2);
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, NullEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(7, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullHoeEntity.this.getTarget() != null && !NullHoeEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullHoeEntity.this.getMoveControl().hasWanted() && NullHoeEntity.this.getTarget() != null && NullHoeEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullHoeEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullHoeEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullHoeEntity.this.getTarget();

                if (NullHoeEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullHoeEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullHoeEntity.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullHoeEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
                    }
                }

            }
        });
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        return false;
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (!this.level().isClientSide()) {
            this.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.BLOCK.get(), 60, 1, false, false));
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

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

        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "data merge entity @s {Invulnerable:1b}",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Blocks.AIR));
        return spawngroupdata1;
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

        builder = builder.add(Attributes.MOVEMENT_SPEED, 2.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 1000.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 2.0D);
        return builder;
    }
}
