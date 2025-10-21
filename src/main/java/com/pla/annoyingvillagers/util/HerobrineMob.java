package com.pla.annoyingvillagers.util;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.procedures.*;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.UUID;

import static com.pla.annoyingvillagers.procedures.HerobrinePortalProcedure.*;

public class HerobrineMob extends Monster {
    private boolean renderPortal = false;
    private int recallTicks = 0;
    private String chatName;
    private boolean neverRecall = false;
    private UUID gregUUID = null;
    private boolean initialSpawn = true;
    private BlockPos lastFeetPos = null;
    private EliteHerobrineKnockedEntity protectEntity;
    private UUID protectUUID;

    public void setProtectUUID(UUID protectUUID) {
        this.protectUUID = protectUUID;
    }

    public void setProtectEntity(EliteHerobrineKnockedEntity protectEntity) {
        this.protectEntity = protectEntity;
    }

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

    public void setInitialSpawn(boolean initialSpawn) {
        this.initialSpawn = initialSpawn;
    }

    protected HerobrineMob(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    public static class AnyFluidPathNavigation extends GroundPathNavigation {
        public AnyFluidPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
        }

        @Override
        protected boolean hasValidPathType(@NotNull BlockPathTypes type) {
            if (type == BlockPathTypes.WATER
                    || type == BlockPathTypes.WATER_BORDER
                    || type == BlockPathTypes.LAVA
                    || type == BlockPathTypes.DANGER_FIRE
                    || type == BlockPathTypes.DAMAGE_FIRE) {
                return true;
            }
            return super.hasValidPathType(type);
        }

        @Override
        public boolean isStableDestination(@NotNull BlockPos blockPos) {
            return this.level.getFluidState(blockPos).getType() != Fluids.EMPTY || super.isStableDestination(blockPos);
        }
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > (float)10.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (protectEntity != null && protectEntity.isAlive()) {
                    getNavigation().moveTo(protectEntity, 2.0D);
                    getLookControl().setLookAt(protectEntity, 30.0F, 30.0F);
                    if (distanceToSqr(protectEntity) > 10.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(protectEntity, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return protectEntity != null && protectEntity.isAlive() && distanceTo(protectEntity) > 50.0D;
            }
        });
        CommonGoals.registerGoalForHostileNpc(this);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    protected void dropCustomDeathLoot(@NotNull DamageSource damagesource, int i, boolean flag) {
        super.dropCustomDeathLoot(damagesource, i, flag);
        this.spawnAtLocation(new ItemStack(Blocks.OBSIDIAN));
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.death")));
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return super.causeFallDamage(f, f1, damagesource);
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (this.getPersistentData().getBoolean(NBT_RISING) || this.getPersistentData().getBoolean(NBT_SINKING)) {
            return false;
        }
        return super.hurt(damagesource, f);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        recallTicks = pCompound.getInt("RecallTicks");
        renderPortal = pCompound.getBoolean("RenderPortal");
        neverRecall = pCompound.getBoolean("NeverRecall");
        if (pCompound.contains("GregUUID")) {
            gregUUID = pCompound.getUUID("GregUUID");
        }
        initialSpawn = pCompound.getBoolean("InitialSpawn");
        if (pCompound.hasUUID("ProtectUUID")) {
            protectUUID = pCompound.getUUID("ProtectUUID");
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("RecallTicks", recallTicks);
        pCompound.putBoolean("RenderPortal", renderPortal);
        pCompound.putBoolean("NeverRecall", neverRecall);
        if (gregUUID != null) {
            pCompound.putUUID("GregUUID", gregUUID);
        }
        pCompound.putBoolean("InitialSpawn", initialSpawn);
        if (protectUUID != null) {
            pCompound.putUUID("ProtectUUID", protectUUID);
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AnyFluidPathNavigation(this, level);
    }

    private void floatOnAnyFluid() {
        BlockPos pos = this.blockPosition();
        FluidState fluidState = this.level().getFluidState(pos);
        if (fluidState.isEmpty()) {
            return;
        }

        CollisionContext collisionContext = CollisionContext.of(this);
        Fluid typeHere = fluidState.getType();
        FluidState above = this.level().getFluidState(pos.above());

        if (collisionContext.isAbove(LiquidBlock.STABLE_SHAPE, pos, true) && above.getType() != typeHere) {
            this.setOnGround(true);

            double surfaceY = pos.getY() + fluidState.getHeight(this.level(), pos);
            double bottomY  = this.getBoundingBox().minY;
            double diff     = surfaceY - bottomY - 0.001D;

            if (diff > 0.0D) {
                Vec3 vel = this.getDeltaMovement();
                this.setDeltaMovement(vel.x, Math.max(vel.y, Math.min(0.2D, diff * 0.2D)), vel.z);
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean isInWater() {
        FluidState fs = this.level().getFluidState(this.blockPosition());
        if (!fs.isEmpty() && this.canStandOnFluid(fs)) return false;
        return super.isInWater();
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        return !state.isEmpty();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    private void placeObsidianBlockWhenInWater(Block block) {
        BlockPos feet = this.getOnPos();
        if (lastFeetPos == null) lastFeetPos = feet;
        if (!feet.equals(lastFeetPos)) {
            if (!this.level().getBlockState(lastFeetPos).is(block)) {
                FluidState fluidState = this.level().getFluidState(lastFeetPos);
                if (!fluidState.isEmpty()) {
                    int replace = fluidState.is(FluidTags.WATER) ? 1 : (fluidState.is(FluidTags.LAVA) ? 2 : 0);
                    this.level().setBlockAndUpdate(
                            lastFeetPos,
                            block
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.REPLACE_BY_LIQUID, replace)
                    );
                }
            }
            lastFeetPos = feet;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.floatOnAnyFluid();
        this.checkInsideBlocks();
        if (!this.level().isClientSide) {
            if (this instanceof HerobrineCloneEntity || this instanceof HerobrineChrisEntity) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get());
            } else if (this instanceof ShadowHerobrineCloneEntity || this instanceof Herobrine7Entity
                    || this instanceof ArmoredHerobrineEntity || this instanceof ShadowHerobrineEntity) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get());
            } else if (!(this instanceof NullEntity)) {
                placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get());
            }
            if (this.tickCount == 1) {
                if (this.renderPortal) {
                    AnnoyingVillagers.PACKET_HANDLER.send(
                            PacketDistributor.TRACKING_ENTITY.with(() -> this),
                            new ClientboundHerobrinePortalFx(HerobrinePortalProcedure.finalSurfacePos(this))
                    );
                    this.renderPortal = false;
                }
                if (this.initialSpawn) {
                    this.setNoAi(true);
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
                        try {
                            this.getServer().getCommands().getDispatcher().execute(
                                    "playsound annoyingvillagers:portal_natural neutral @a ~ ~ ~",
                                    this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                        } catch (CommandSyntaxException e) {
                        }
                        HerobrinePortalProcedure.sinkIntoGround(serverLevel, this, 0.06);
                    }
                }
                if (remaining <= 0) {
                    this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal(this.getChatName() + " was sent back to the §4Herobrine Vessel Realm§r"), false);
                    this.discard();
                }
            }

            if (protectEntity == null && protectUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(protectUUID);
                if (entity instanceof EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity) {
                    protectEntity = eliteHerobrineKnockedEntity;
                } else {
                    protectEntity = null;
                }
            }
            if (protectEntity != null && !protectEntity.isAlive()) {
                protectEntity = null;
                protectUUID = null;
                this.recallTicks = 41;
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            HerobrineMobData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            HerobrineMobData herobrineMobData = HerobrineMobData.get(serverLevel);

            if (!herobrineMobData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            } else {
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
        }

        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        HerobrineOnInitialSpawnProcedure.execute(serverlevelaccessor, this, recallTicks, mobspawntype);
        return spawngroupdata1;
    }

    public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
        HerobrineTransfromProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entity, this);
    }
}
