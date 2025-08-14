package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.GroundRiseSpawner;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.PlayMessages;

public class SummonPortalEntity extends Entity {
    private int animationTick;
    private boolean hasSpawnedMob = false;
    public static final int GROW_TICKS   = 40;
    public static final int HOLD_TICKS   = 0;
    public static final int SHRINK_TICKS = 40;
    public static final int ANIMATION_DURATION = GROW_TICKS + HOLD_TICKS + SHRINK_TICKS;

    public SummonPortalEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.SUMMON_PORTAL.get(), level);
    }

    public SummonPortalEntity(EntityType<SummonPortalEntity> entitytype, Level level) {
        super(entitytype, level);
        this.animationTick = 0;
    }

    @Override
    protected void defineSynchedData() {
    }

    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (!hasSpawnedMob) {
                hasSpawnedMob = true;
                DarkHerobrineEntity darkHerobrineEntity = new DarkHerobrineEntity((EntityType) AnnoyingVillagersModEntities.DARK_HEROBRINE.get(), this.level());
                GroundRiseSpawner.spawnRising((ServerLevel) this.level(), darkHerobrineEntity, this.getX(), this.getZ(), 0.03);
            }

            animationTick++;
            if(this.getAnimationTick() > ANIMATION_DURATION) {
                this.discard();
            }
        } else {
            animationTick++;
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        animationTick = tag.getInt("AnimationTick");
        hasSpawnedMob = tag.getBoolean("HasSpawnedMob");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("AnimationTick", animationTick);
        tag.putBoolean("HasSpawnedMob", hasSpawnedMob);
    }

    public static void init() {
        SpawnPlacements.register((EntityType) AnnoyingVillagersModEntities.SUMMON_PORTAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entitytype, serverlevelaccessor, mobspawntype, blockpos, random) -> {
            return serverlevelaccessor.getRawBrightness(blockpos, 0) > 8;
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 50.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5.0D);
        return builder;
    }
}