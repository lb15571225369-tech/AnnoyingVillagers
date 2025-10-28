package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.procedures.EnchantBedRightClickOnBlockProcedure;

public class EnchantBedBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public EnchantBedBlock() {
        super(Properties.of()
                .sound(SoundType.WOOD)
                .strength(1.25F, 10.0F)
                .lightLevel((blockstate) -> 2)
                .jumpFactor(5.0F)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
        this.registerDefaultState((BlockState) ((BlockState) this.stateDefinition.any()).setValue(EnchantBedBlock.FACING, Direction.NORTH));
    }

    public void appendHoverText(ItemStack itemstack, BlockGetter blockgetter, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.enchanted_bed"));
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
        VoxelShape voxelshape;

        switch ((Direction) blockstate.getValue(EnchantBedBlock.FACING)) {
            case NORTH:
                voxelshape = Shapes.or(box(0.0D, 0.0D, 29.0D, 3.0D, 3.0D, 32.0D), new VoxelShape[]{box(13.0D, 0.0D, 29.0D, 16.0D, 3.0D, 32.0D), box(0.0D, 3.0D, 16.0D, 16.0D, 9.0D, 32.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D), box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D)});
                break;
            case EAST:
                voxelshape = Shapes.or(box(-16.0D, 0.0D, 0.0D, -13.0D, 3.0D, 3.0D), new VoxelShape[]{box(-16.0D, 0.0D, 13.0D, -13.0D, 3.0D, 16.0D), box(-16.0D, 3.0D, 0.0D, 0.0D, 9.0D, 16.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D), box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D)});
                break;
            case WEST:
                voxelshape = Shapes.or(box(29.0D, 0.0D, 13.0D, 32.0D, 3.0D, 16.0D), new VoxelShape[]{box(29.0D, 0.0D, 0.0D, 32.0D, 3.0D, 3.0D), box(16.0D, 3.0D, 0.0D, 32.0D, 9.0D, 16.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D), box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D)});
                break;
            default:
                voxelshape = Shapes.or(box(13.0D, 0.0D, -16.0D, 16.0D, 3.0D, -13.0D), new VoxelShape[]{box(0.0D, 0.0D, -16.0D, 3.0D, 3.0D, -13.0D), box(0.0D, 3.0D, -16.0D, 16.0D, 9.0D, 0.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D), box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D)});
        }

        return voxelshape;
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[]{EnchantBedBlock.FACING});
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockplacecontext) {
        return (BlockState) this.defaultBlockState().setValue(EnchantBedBlock.FACING, blockplacecontext.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState blockstate, Rotation rotation) {
        return (BlockState) blockstate.setValue(EnchantBedBlock.FACING, rotation.rotate((Direction) blockstate.getValue(EnchantBedBlock.FACING)));
    }

    public BlockState mirror(BlockState blockstate, Mirror mirror) {
        return blockstate.rotate(mirror.getRotation((Direction) blockstate.getValue(EnchantBedBlock.FACING)));
    }

    public List<ItemStack> getDrops(BlockState blockstate, LootParams.Builder pParams) {
        List<ItemStack> list = super.getDrops(blockstate, pParams);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack((ItemLike) AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()));
    }

    public InteractionResult use(BlockState blockstate, Level level, BlockPos blockpos, Player player, InteractionHand interactionhand, BlockHitResult blockhitresult) {
        super.use(blockstate, level, blockpos, player, interactionhand, blockhitresult);
        int i = blockpos.getX();
        int j = blockpos.getY();
        int k = blockpos.getZ();
        double d0 = blockhitresult.getLocation().x;
        double d1 = blockhitresult.getLocation().y;
        double d2 = blockhitresult.getLocation().z;
        Direction direction = blockhitresult.getDirection();

        EnchantBedRightClickOnBlockProcedure.execute(level, (double) i, (double) j, (double) k, player);
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.ENCHANT_BED.get(), (rendertype) -> {
            return rendertype == RenderType.cutout();
        });
    }
}
