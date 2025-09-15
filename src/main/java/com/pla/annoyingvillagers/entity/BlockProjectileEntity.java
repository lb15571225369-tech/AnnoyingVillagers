package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.block.DarkObUpBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.blockentity.DarkObUpBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.ObsidianWeaponUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class BlockProjectileEntity extends ThrowableProjectile {
    private static final EntityDataAccessor<BlockState> DATA_BLOCK =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Float> ROT_X =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Y =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Z =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);

    private boolean notReadyForShoot = false;
    private UUID playerUUID;

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setNotReadyForShoot(boolean notReadyForShoot) {
        this.notReadyForShoot = notReadyForShoot;
    }

    public BlockProjectileEntity(EntityType<? extends BlockProjectileEntity> type, Level level) {
        super(type, level);
        initRandomRotation();
    }

    public BlockProjectileEntity(Level level, LivingEntity shooter, BlockState block) {
        super(AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), shooter, level);
        setCarriedBlock(block);
        initRandomRotation();
    }

    public void setRotX(float v){ this.entityData.set(ROT_X, v); }
    public void setRotY(float v){ this.entityData.set(ROT_Y, v); }
    public void setRotZ(float v){ this.entityData.set(ROT_Z, v); }
    public float getRotX(){ return this.entityData.get(ROT_X); }
    public float getRotY(){ return this.entityData.get(ROT_Y); }
    public float getRotZ(){ return this.entityData.get(ROT_Z); }

    private void initRandomRotation() {
        if (!level().isClientSide) {
            var r = this.random;
            setRotX((r.nextFloat() - 0.5f) * 10f);
            setRotY((r.nextFloat() - 0.5f) * 10f);
            setRotZ((r.nextFloat() - 0.5f) * 10f);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_BLOCK, Blocks.STONE.defaultBlockState());
        this.entityData.define(ROT_X, 0f);
        this.entityData.define(ROT_Y, 0f);
        this.entityData.define(ROT_Z, 0f);
    }

    public void setCarriedBlock(BlockState state) {
        this.entityData.set(DATA_BLOCK, state);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.notReadyForShoot) return;
        Entity target = result.getEntity();
        final UUID ownerId = this.playerUUID;
        final boolean isHerobrine = ObsidianWeaponUtil.isHerobrineFaction(target);

        boolean blockDamage =
                (ownerId == null && isHerobrine)
                        || (ownerId != null && target instanceof Player p
                        && p.getUUID().equals(ownerId));

        if (blockDamage) return;

        if (!target.level().isClientSide() && target.getServer() != null) {
            try {
                target.getServer().getCommands().getDispatcher().execute(
                        "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                        target.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!target.level().isClientSide()) {
            target.level().playSound((Player) null, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5));
        } else {
            target.level().playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("annoyingvillagers", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5), false);
        }
        if (this.getOwner() == null) {
            target.hurt(target.level().damageSources().generic(), 2.0F);
        } else {
            target.hurt(target.level().damageSources().indirectMagic(this, this.getOwner()), 2.0F);
        }
        if (!target.level().isClientSide() && target.getServer() != null) {
            try {
                target.getServer().getCommands().getDispatcher().execute(
                        "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10",
                        target.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }

            if (target instanceof LivingEntity livingEntity) {
                float strength = 1.0F;
                double dx = this.getX() - target.getX();
                double dz = this.getZ() - target.getZ();
                livingEntity.knockback(strength, dx, dz);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (this.notReadyForShoot) return;
        BlockPos pos = result.getBlockPos();
        BlockState hitState = level().getBlockState(pos);

        if (!level().isClientSide) {
            if (!hitState.getFluidState().isEmpty()) {
                return;
            }

            if (hitState.canBeReplaced()) {
                return;
            }

            BlockPos placePos = pos.relative(result.getDirection());
            if (level().getBlockState(placePos).isAir() ||
                    !level().getFluidState(placePos).isEmpty() ||
                    level().getBlockState(placePos).canBeReplaced()) {
                UUID owner = this.playerUUID;

                BlockState placeState;
                if (getCarriedBlock().is(AnnoyingVillagersModBlocks.DARKOB.get())) {
                    placeState = AnnoyingVillagersModBlocks.DARK_OB_UP.get().defaultBlockState();
                     if (owner != null && placeState.hasProperty(DarkObUpBlock.FROM_PLAYER)) {
                         placeState = placeState.setValue(DarkObUpBlock.FROM_PLAYER, true);
                     }
                } else if (getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get())) {
                    placeState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
                    if (owner != null && placeState.hasProperty(ShadowObsidianBlock.FROM_PLAYER)) {
                        placeState = placeState.setValue(ShadowObsidianBlock.FROM_PLAYER, true);
                    }
                } else {
                    placeState = getCarriedBlock();
                }

                level().setBlockAndUpdate(placePos, placeState);
                BlockEntity blockEntity = level().getBlockEntity(placePos);
                if (owner != null) {
                    if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                        shadowObsidianBlockEntity.setOwner(owner);
                        blockEntity.setChanged();
                    } else if (blockEntity instanceof DarkObUpBlockEntity darkObUpBlockEntity) {
                        darkObUpBlockEntity.setOwner(owner);
                        blockEntity.setChanged();
                    }
                }
            }
            this.discard();
        }
    }

    public BlockState getCarriedBlock() {
        return this.entityData.get(DATA_BLOCK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("Block", NbtUtils.writeBlockState(getCarriedBlock()));
        tag.putFloat("RotX", getRotX());
        tag.putFloat("RotY", getRotY());
        tag.putFloat("RotZ", getRotZ());
        tag.putBoolean("NotReadyForShoot", notReadyForShoot);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Block")) {
            setCarriedBlock(NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("Block")));
        }
        setRotX(tag.contains("RotX") ? tag.getFloat("RotX") : 0f);
        setRotY(tag.contains("RotY") ? tag.getFloat("RotY") : 0f);
        setRotZ(tag.contains("RotZ") ? tag.getFloat("RotZ") : 0f);
        notReadyForShoot = tag.getBoolean("NotReadyForShoot");
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(0.9F, 0.9F);
    }

    @Override
    protected float getGravity() {
        return 0.005F;
    }
}
