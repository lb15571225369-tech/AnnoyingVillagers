package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
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
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@EventBusSubscriber
public class VillagerScoutCaptainEntity extends PathfinderMob {
    public VillagerScoutCaptainEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), level);
    }

    public VillagerScoutCaptainEntity(EntityType<VillagerScoutCaptainEntity> entitytype, Level level) {
        super(entitytype, level);
        this.maxUpStep = 2.5F;
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(Component.literal("Villager Scout Captain"));
        this.setCustomNameVisible(true);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike) AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
    }

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Monster.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, BlueDemonEntity.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, BlueDemon2Entity.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, HerobrineEntity.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, HerobrineEntity.class, true, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, PlayerMobEntity.class, true, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, true, false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowMobGoal(this, 1.3D, 20.0F, 15.0F));
        this.goalSelector.addGoal(8, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(9, new OpenDoorGoal(this, false));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new FloatGoal(this));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, HbGaoJiFenShenEntity.class, true, false));
//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, LingZhiEntity.class, true, false));
//        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, DiJiherobrineEntity.class, false, false));
//        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Herobrine7Entity.class, true, false));
//        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, GraveEntity.class, true, false));
//        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, MrcolderEntity.class, true, false));
//        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, SteveEntity.class, true, false));
//        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, ShiTi303Entity.class, true, false));
//        this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, KeLiSiEntity.class, true, false));
//        this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, GeLeiGeEntity.class, true, false));
//        this.targetSelector.addGoal(13, new NearestAttackableTargetGoal(this, JianbingguoziEntity.class, true, false));
//        this.targetSelector.addGoal(14, new NearestAttackableTargetGoal(this, WanJia1Entity.class, true, false));
//        this.targetSelector.addGoal(15, new NearestAttackableTargetGoal(this, ZaiEZhiWangEntity.class, true, false));
//        this.targetSelector.addGoal(23, new NearestAttackableTargetGoal(this, CunZhenFuLuEntity.class, true, true));
//        this.targetSelector.addGoal(24, new NearestAttackableTargetGoal(this, LanCunQiFuLuEntity.class, true, true));
//        this.targetSelector.addGoal(25, new NearestAttackableTargetGoal(this, LvcunqifuluEntity.class, true, true));
//        this.targetSelector.addGoal(26, new NearestAttackableTargetGoal(this, HongCunQiFuLuEntity.class, true, true));
//        this.targetSelector.addGoal(27, new NearestAttackableTargetGoal(this, ZiCunQiFuLuEntity.class, true, true));
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

    public SoundEvent getAmbientSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.villager.death"));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        VillagerScoutCaptainOnHurtProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
        return damagesource == DamageSource.FALL ? false : (damagesource == DamageSource.CACTUS ? false : super.hurt(damagesource, f));
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        try {
            VillagerScoutCaptainOnEntityDeathProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
        } catch (CommandSyntaxException e) {
            
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        try {
            VillagerScoutCaptainOnEntityInitialSpawnProcedure.execute(serverlevelaccessor, this.getX(), this.getY(), this.getZ(), this);
        } catch (CommandSyntaxException e) {
            
        }
        return spawngroupdata1;
    }

    public InteractionResult mobInteract(Player player, InteractionHand interactionhand) {
        player.getItemInHand(interactionhand);
        InteractionResult interactionresult = InteractionResult.sidedSuccess(this.level.isClientSide());

        super.mobInteract(player, interactionhand);
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        Level level = this.level;

        try {
            VillagerScoutOnInteractProcedure.execute(level, d0, d1, d2, this);
        } catch (CommandSyntaxException e) {
            
        }
        return interactionresult;
    }

    public void baseTick() {
        super.baseTick();
        VillagerScoutSmartSkillProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
        VillagerScoutCaptainOnPlayerTouchProcedure.execute(this.level, this);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getBlockState(blockpos.below()).getMaterial() == Material.GRASS && serverlevelaccessor.getRawBrightness(blockpos, 0) > 8;
        });
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder = builder.add(Attributes.ARMOR, 6.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}

