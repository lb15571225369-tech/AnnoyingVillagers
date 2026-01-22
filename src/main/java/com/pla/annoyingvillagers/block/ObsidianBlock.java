package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.procedures.DarkObSsOnTickProcedure;
import com.pla.annoyingvillagers.procedures.ObsidianBlockOnTickProcedure;
import com.pla.annoyingvillagers.procedures.ObsidianWhenEntityInsideBlockOnCollisionProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import com.pla.annoyingvillagers.procedures.ObsidianBlockPlaceBlockProcedure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObsidianBlock extends Block implements EntityBlock {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");
    public static final IntegerProperty REPLACE_BY_LIQUID = IntegerProperty.create("replace_by_liquid", 0, 2);

    public ObsidianBlock() {
        super(Properties.of()
                .sound(new ForgeSoundType(1.0F, 1.0F,
                        () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "lost")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.step")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.place")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "block.stone.hit")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "silent"))
                ))
                .strength(60.0F, 40.0F)
                .lightLevel((blockstate) -> 4)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
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

    public void appendHoverText(@NotNull ItemStack itemstack, BlockGetter blockgetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.obsidian"));
    }

    public int getLightBlock(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return 15;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 17.0D);
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockstate, LootParams.@NotNull Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void tick(@NotNull BlockState blockstate, @NotNull ServerLevel serverlevel, @NotNull BlockPos blockpos, @NotNull RandomSource random) {
        super.tick(blockstate, serverlevel, blockpos, random);
        ObsidianBlockOnTickProcedure.execute(serverlevel, blockpos.getX(), blockpos.getY(), blockpos.getZ());
    }

    public void onPlace(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockpos, @NotNull BlockState blockstate1, boolean flag) {
        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
        level.scheduleTick(blockpos, this, 25);
        ObsidianBlockPlaceBlockProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
    }

    public void entityInside(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockpos, @NotNull Entity entity) {
        super.entityInside(blockstate, level, blockpos, entity);
        if (entity.tickCount % 5 == 0) {
            ObsidianWhenEntityInsideBlockOnCollisionProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), entity);
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
                obsidianBlockEntity.setOwner(placer instanceof Player ? ((Player) placer).getUUID() : null);
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ObsidianBlockEntity(pPos, pState);
    }
}
