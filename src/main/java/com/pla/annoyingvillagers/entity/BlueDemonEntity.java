package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.Material;
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
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@EventBusSubscriber
public class BlueDemonEntity extends Monster {
    public BlueDemonEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), level);
    }

    public BlueDemonEntity(EntityType<BlueDemonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.maxUpStep = 3.0F;
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(Component.literal("§bBlue Demon§r"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike) AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()));
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerMobEntity.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, HerobrineEntity.class, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Herobrine2Entity.class, true, false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
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
        BlueDemonOnEntityDamageProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        return damagesource.getDirectEntity() instanceof AbstractArrow ? false : (damagesource == DamageSource.FALL ? false : (damagesource == DamageSource.CACTUS ? false : (damagesource == DamageSource.DROWN ? false : (damagesource == DamageSource.LIGHTNING_BOLT ? false : (damagesource.isExplosion() ? false : (damagesource.getMsgId().equals("trident") ? false : (damagesource == DamageSource.WITHER ? false : (damagesource.getMsgId().equals("witherSkull") ? false : super.hurt(damagesource, f)))))))));
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        BlueDemonOnEntityDeathProcedure.execute(this.level, this, damagesource.getEntity());
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        BlueDemonOnEntityInitialSpawnProcedure.execute(this);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        BlueDemonOnEntityKillOtherEntityProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), entity);
    }

    public void baseTick() {
        super.baseTick();
        BlueDemonOnEntityUpdateProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getBlockState(blockpos.below()).getMaterial() == Material.GRASS && serverlevelaccessor.getRawBrightness(blockpos, 0) > 8;
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
