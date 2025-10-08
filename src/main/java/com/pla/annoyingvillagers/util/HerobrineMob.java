package com.pla.annoyingvillagers.util;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.UUID;

import static com.pla.annoyingvillagers.procedures.HerobrinePortalProcedure.*;

public class HerobrineMob extends Monster {
    private boolean renderPortal = false;
    private int recallTicks = 0;
    private String chatName;
    private boolean neverRecall = false;
    private UUID gregUUID = null;

    public void setGregUUID(UUID gregUUID) {
        this.gregUUID = gregUUID;
    }

    public UUID getGregUUID() {
        return gregUUID;
    }

    public void setRecallTicks(int recallTicks) {
        this.recallTicks = recallTicks;
    }

    public int getRecallTicks() {
        return recallTicks;
    }

    public void setRenderPortal(boolean renderPortal) {
        this.renderPortal = renderPortal;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setNeverRecall(boolean neverRecall) {
        this.neverRecall = neverRecall;
    }

    protected HerobrineMob(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
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

    protected void dropCustomDeathLoot(DamageSource damagesource, int i, boolean flag) {
        super.dropCustomDeathLoot(damagesource, i, flag);
        this.spawnAtLocation(new ItemStack(Blocks.OBSIDIAN));
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death"));
    }

    public void thunderHit(ServerLevel serverlevel, LightningBolt lightningbolt) {
        super.thunderHit(serverlevel, lightningbolt);
    }

    public boolean causeFallDamage(float f, float f1, DamageSource damagesource) {
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(DamageSource damagesource, float f) {
        if (this.getPersistentData().getBoolean(NBT_RISING) || this.getPersistentData().getBoolean(NBT_SINKING)) {
            return false;
        }
        return super.hurt(damagesource, f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        recallTicks = pCompound.getInt("RecallTicks");
        renderPortal = pCompound.getBoolean("RenderPortal");
        neverRecall = pCompound.getBoolean("NeverRecall");
        if (pCompound.contains("GregUUID")) {
            gregUUID = pCompound.getUUID("GregUUID");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("RecallTicks", recallTicks);
        pCompound.putBoolean("RenderPortal", renderPortal);
        pCompound.putBoolean("NeverRecall", neverRecall);
        if (gregUUID != null) {
            pCompound.putUUID("GregUUID", gregUUID);
        }
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
                    this.renderPortal = false;
                }
                final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
                if (livingentitypatch != null && !this.level().isClientSide()) {
                    if (this instanceof ReaperHerobrineEntity || this instanceof GlaiveHerobrineEntity) {
                        livingentitypatch.playAnimationSynchronized(AVAnimations.GLOWING_AGONY_GUARD, 0.0F);
                    } else if (this instanceof AegisHerobrineEntity aegisHerobrineEntity) {
                        // For some reason the block animation can't be played inside finalize spawn
                        aegisHerobrineEntity.getPersistentData().putBoolean("init_animation", true);
                    } else if (!(this instanceof SledgehammerHerobrineEntity) && !(this instanceof SwordsmanHerobrineEntity)) {
                        livingentitypatch.playAnimationSynchronized(AVAnimations.HEROBRINE_ANIMATE, 0.0F);
                    }
                }
            }

            if (!neverRecall) {
                this.recallTicks = this.recallTicks - 1;
                int remaining = this.recallTicks;

                if (remaining == SHINK_TIME_START) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                            new ClientboundHerobrinePortalFx(new Vec3(this.getX(), this.getY(), this.getZ()))
                    );
                    if (this.level() instanceof ServerLevel serverLevel) {
                        HerobrinePortalProcedure.sinkIntoGround(serverLevel, this, 0.06);
                    }
                }
                if (remaining <= 0) {
                    this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal(this.getChatName() + " was sent back to the §4Herobrine Vessel Realm§r"), false);
                    this.discard();
                }
            }
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, recallTicks, mobspawntype);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }
}
