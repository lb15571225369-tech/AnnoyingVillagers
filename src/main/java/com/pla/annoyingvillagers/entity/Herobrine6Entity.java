package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;

public class Herobrine6Entity extends Monster {
    private boolean summoned = false;

    public boolean isSummoned() {
        return summoned;
    }

    public void setSummoned(boolean summoned) {
        this.summoned = summoned;
    }

    boolean renderPortal = false;

    public void setRenderPortal(boolean renderPortal) {
        this.renderPortal = renderPortal;
    }

    public Herobrine6Entity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.HEROBRINE_6.get(), level);
    }

    public Herobrine6Entity(EntityType<Herobrine6Entity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setCustomNameVisible(false);
        this.setPersistenceRequired();
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get()));
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForHostileNpc(this);
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

    public boolean hurt(DamageSource damagesource, float f) {
        Herobrine6OnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), damagesource.getEntity());
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "herobrine_6");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            corpse.setItemSlot(EquipmentSlot.HEAD, this.getItemBySlot(EquipmentSlot.HEAD).copy());
            corpse.setItemSlot(EquipmentSlot.CHEST, this.getItemBySlot(EquipmentSlot.CHEST).copy());
            corpse.setItemSlot(EquipmentSlot.LEGS, this.getItemBySlot(EquipmentSlot.LEGS).copy());
            corpse.setItemSlot(EquipmentSlot.FEET, this.getItemBySlot(EquipmentSlot.FEET).copy());
            serverLevel.addFreshEntity(corpse);
        }
        LevelAccessor levelaccessor1 = this.level();
        ItemEntity itementity;
        LivingEntity livingentity = (LivingEntity)this;
        ItemStack itemstack;

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getMainHandItem();
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getOffhandItem();
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, 0, mobspawntype);
        return spawngroupdata;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                if (this.renderPortal) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY.with(() -> this),
                            new ClientboundHerobrinePortalFx(HerobrinePortalProcedure.finalSurfacePos(this))
                    );
                    renderPortal = false;
                }
                final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                if (livingentitypatch != null && !this.level().isClientSide()) {
                    livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_ANIMATE, 0.0F);
                }
            }
        }
    }

    public void baseTick() {
        super.baseTick();
    }

    public void playerTouch(Player player) {
        super.playerTouch(player);
    }

    public static boolean canSpawn(EntityType<Herobrine6Entity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        boolean isNight = (level instanceof ServerLevel serverLevel) && (serverLevel.getDayTime() % 24000L >= 13000L && serverLevel.getDayTime() % 24000L <= 23000L);
        if (!isNight) return false;

        ServerLevel serverLevel = level.getLevel();
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        summoned = pCompound.getBoolean("Summoned");
        renderPortal = pCompound.getBoolean("RenderPortal");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Summoned", summoned);
        pCompound.putBoolean("RenderPortal", renderPortal);
    }

    public static Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 25.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        return builder;
    }
}
