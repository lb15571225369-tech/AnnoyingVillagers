package com.pla.annoyingvillagers.block;

import com.pla.annoyingvillagers.blockentity.CryingObsidianSpikeBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.procedures.*;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CryingObsidianSpikeBlock extends Block implements EntityBlock {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");

    public CryingObsidianSpikeBlock() {
        super(Properties.of()
                .offsetType(OffsetType.XYZ)
                .sound(SoundType.STONE)
                .strength(3.0F, 50.0F)
                .noOcclusion()
                .hasPostProcess((state, getter, pos) -> true)
                .emissiveRendering((state, getter, pos) -> true)
                .isRedstoneConductor((state, getter, pos) -> false)
                .dynamicShape()
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(FROM_PLAYER, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROM_PLAYER);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState base = super.getStateForPlacement(blockPlaceContext);
        if (base == null) base = this.defaultBlockState();
        return base.setValue(FROM_PLAYER, blockPlaceContext.getPlayer() != null);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel serverlevel, BlockPos blockpos, RandomSource random) {
        super.tick(blockstate, serverlevel, blockpos, random);
        int i = blockpos.getX();
        int j = blockpos.getY();
        int k = blockpos.getZ();

        DarkObSsOnTickProcedure.execute(serverlevel, (double) i, (double) j, (double) k);
        serverlevel.scheduleTick(blockpos, this, 20);
    }

    public boolean propagatesSkylightDown(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
        return true;
    }

    public int getLightBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
        return box(5.0D, 6.0D, 0.0D, 9.0D, 10.0D, 16.0D);
    }

    public void entityInside(BlockState blockstate, Level level, BlockPos blockpos, Entity entity) {
        super.entityInside(blockstate, level, blockpos, entity);
        CryingObsidianSpikeWhenEntityInsideBlockOnCollisionProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), entity);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, 25);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CryingObsidianSpikeBlockEntity cryingObsidianSpikeBlockEntity) {
                cryingObsidianSpikeBlockEntity.setOwner(placer instanceof Player ? ((Player) placer).getUUID() : null);
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CryingObsidianSpikeBlockEntity(pPos, pState);
    }
}
