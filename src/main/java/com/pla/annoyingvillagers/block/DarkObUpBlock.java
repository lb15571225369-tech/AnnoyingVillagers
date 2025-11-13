package com.pla.annoyingvillagers.block;

import com.pla.annoyingvillagers.blockentity.DarkObUpBlockEntity;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.pla.annoyingvillagers.procedures.DarkObSsOnEntityInsideProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnAttackProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnTickProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnPlaceProcedure;
import org.jetbrains.annotations.Nullable;

public class DarkObUpBlock extends Block implements EntityBlock {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");
    public DarkObUpBlock() {
        super(Properties.of()
                .sound(SoundType.STONE)
                .offsetType(OffsetType.XYZ)
                .strength(3.0F, 50.0F)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false)
                .dynamicShape());
        this.registerDefaultState(this.stateDefinition.any().setValue(FROM_PLAYER, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROM_PLAYER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState base = super.getStateForPlacement(ctx);
        if (base == null) base = this.defaultBlockState();
        return base.setValue(FROM_PLAYER, ctx.getPlayer() != null);
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
        return Shapes.or(box(6.0D, 0.0D, 12.0D, 10.0D, 16.0D, 16.0D), new VoxelShape[]{box(6.0D, 16.0D, 12.0D, 10.0D, 32.0D, 16.0D), box(6.0D, -16.0D, 12.0D, 10.0D, 0.0D, 16.0D)});
    }

    public void onPlace(BlockState blockstate, Level level, BlockPos blockpos, BlockState blockstate1, boolean flag) {
        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
        level.scheduleTick(blockpos, this, 20);
        DarkObSsOnPlaceProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
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

    public void attack(BlockState blockstate, Level level, BlockPos blockpos, Player player) {
        super.attack(blockstate, level, blockpos, player);
        DarkObSsOnAttackProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), player);
    }

    public void entityInside(BlockState blockstate, Level level, BlockPos blockpos, Entity entity) {
        super.entityInside(blockstate, level, blockpos, entity);
        DarkObSsOnEntityInsideProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), entity);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DarkObUpBlockEntity darkObUpBlockEntity) {
                darkObUpBlockEntity.setOwner(placer instanceof Player ? ((Player) placer).getUUID() : null);
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DarkObUpBlockEntity(pPos, pState);
    }
}
