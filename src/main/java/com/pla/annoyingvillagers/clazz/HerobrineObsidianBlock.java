package com.pla.annoyingvillagers.clazz;

import java.util.*;

import com.pla.annoyingvillagers.blockentity.*;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class HerobrineObsidianBlock extends Block {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");
    public static final IntegerProperty REPLACE_BY_LIQUID = IntegerProperty.create("replace_by_liquid", 0, 2);

    public HerobrineObsidianBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FROM_PLAYER, Boolean.FALSE)
                        .setValue(REPLACE_BY_LIQUID, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROM_PLAYER, REPLACE_BY_LIQUID);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState base = super.getStateForPlacement(blockPlaceContext);
        if (base == null) base = this.defaultBlockState();
        return base.setValue(FROM_PLAYER, blockPlaceContext.getPlayer() != null);
    }

    public int getLightBlock(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return 15;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockstate, LootParams.@NotNull Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);
        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    public void customTickSound(ServerLevel serverLevel, BlockPos blockPos) {
    }

    public void customPlaceSound(ServerLevel serverLevel, BlockPos blockPos) {
    }

    @Override
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        super.tick(blockState, serverLevel, blockPos, randomSource);
        customTickSound(serverLevel, blockPos);
        BlockState current = serverLevel.getBlockState(blockPos);
        int replace = 0;
        if (current.getBlock() instanceof HerobrineObsidianBlock && current.hasProperty(HerobrineObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID);
        }
        BlockState replacement = switch (replace) {
            case 1 -> Blocks.WATER.defaultBlockState();
            case 2 -> Blocks.LAVA.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
        serverLevel.setBlock(blockPos, replacement, 3);
    }

    public void onPlace(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, boolean flag) {
        super.onPlace(blockstate, level, blockPos, blockState, flag);
        level.scheduleTick(blockPos, this, 25);
        if (level instanceof ServerLevel serverLevel) {
            customPlaceSound(serverLevel, blockPos);
        }
    }

    public boolean conditionEveryTickEntityInside(Entity entity) {
        return true;
    }

    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
    }

    public void entityInside(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (conditionEveryTickEntityInside(entity) && level instanceof ServerLevel serverLevel) {
            boolean fromPlayer =
                    blockState.getBlock() instanceof HerobrineObsidianBlock
                            && blockState.hasProperty(HerobrineObsidianBlock.FROM_PLAYER)
                            && blockState.getValue(HerobrineObsidianBlock.FROM_PLAYER);
            if (!fromPlayer && HerobrineUtil.isHerobrineFaction(entity)) {
                return;
            }
            if (entity instanceof Player && fromPlayer && !serverLevel.getServer().isPvpAllowed()) {
                return;
            }
            Entity owner = null;
            if (level.getBlockEntity(blockPos) instanceof ObsidianBlockEntity obsidianBlockEntity) {
                UUID ownerUuid = obsidianBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            } else if (level.getBlockEntity(blockPos) instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                UUID ownerUuid = shadowObsidianBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            } else if (level.getBlockEntity(blockPos) instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
                UUID ownerUuid = cryingObsidianBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            } else if (level.getBlockEntity(blockPos) instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
                UUID ownerUuid = shadowObsidianShortPillarBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            } else if (level.getBlockEntity(blockPos) instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
                UUID ownerUuid = shadowObsidianMiddlePillarBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            } else if (level.getBlockEntity(blockPos) instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
                UUID ownerUuid = shadowObsidianLongPillarBlockEntity.getOwner();
                if (ownerUuid != null) {
                    // skip logic entity inside for the owner
                    if (ownerUuid.equals(entity.getUUID())) {
                        return;
                    }
                    if (fromPlayer) {
                        owner = serverLevel.getPlayerByUUID(ownerUuid);
                    } else {
                        owner = serverLevel.getEntity(ownerUuid);
                    }
                }
            }
            customHurtLogic(entity, owner, serverLevel, blockPos);
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
                obsidianBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                shadowObsidianBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            } else if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
                cryingObsidianBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            } else if (blockEntity instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
                shadowObsidianShortPillarBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            } else if (blockEntity instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
                shadowObsidianMiddlePillarBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
                shadowObsidianLongPillarBlockEntity.setOwner(placer.getUUID());
                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }
}
