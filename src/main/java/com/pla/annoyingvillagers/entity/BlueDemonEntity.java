package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityUpdateProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityDamageProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityDeathProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityKillOtherEntityProcedure;
import com.pla.annoyingvillagers.procedures.BlueDemonOnEntityInitialSpawnProcedure;

import java.util.UUID;

@EventBusSubscriber
public class BlueDemonEntity extends Monster {
    private BbqEntity bbqEntityToProtect;
    private UUID bbqUUID;
    private boolean spawnBbq = false;

    public void setProtectingBbq(BbqEntity bbqEntity) {
        this.bbqEntityToProtect = bbqEntity;
    }

    public void setBbqUUID(UUID bbqUUID) {
        this.bbqUUID = bbqUUID;
    }

    public BlueDemonEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), level);
    }

    public UUID getBbqUUID() {
        return bbqUUID;
    }

    public BlueDemonEntity(EntityType<BlueDemonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§bBlue Demon§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()));
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> bbqEntityToProtect != null
                && bbqEntityToProtect.isAlive()
                && target != null
                && target.getLastHurtMob() == bbqEntityToProtect));
        CommonGoals.registerGoalForBlueDemonNpc(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (!spawnBbq) {
                this.spawnBbq = true;
                spawnBbq();
            }

            if (bbqEntityToProtect == null && bbqUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(bbqUUID);
                if (entity instanceof BbqEntity bbqEntity) {
                    bbqEntityToProtect = bbqEntity;
                } else {
                    bbqUUID = null;
                }
            }
            if (bbqEntityToProtect != null && !bbqEntityToProtect.isAlive()) {
                bbqEntityToProtect = null;
                bbqUUID = null;
            }
        }
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
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        BlueDemonOnEntityDamageProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.TRIDENT)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        BlueDemonOnEntityDeathProcedure.execute(this.level(), this, damagesource.getEntity());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (bbqUUID != null) {
            tag.putUUID("BbqUUID", bbqUUID);
        }
        tag.putBoolean("SpawnBbq", spawnBbq);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("BbqUUID")) {
            bbqUUID = tag.getUUID("BbqUUID");
        }
        spawnBbq = tag.getBoolean("SpawnBbq");
    }

    private void spawnBbq() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            BbqEntity bbqEntity = new BbqEntity((EntityType) AnnoyingVillagersModEntities.BBQ.get(), serverlevel);
            bbqEntity.moveTo(this.getX() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), this.getY() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), this.getZ() + Mth.nextDouble(AnnoyingVillagers.randomSource, 1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            bbqEntity.setFollowTarget(this);
            bbqEntity.setFollowTargetUUID(this.getUUID());

            bbqEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(bbqEntity);

            this.setProtectingBbq(bbqEntity);
            this.setBbqUUID(bbqEntity.getUUID());
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        BlueDemonOnEntityInitialSpawnProcedure.execute(this.level(), this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        BlueDemonOnEntityKillOtherEntityProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity);
    }

    public void baseTick() {
        super.baseTick();
        BlueDemonOnEntityUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) > 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 5.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 126.0D);
        return builder;
    }
}
