package com.pla.annoyingvillagers.block;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndFireBlock extends BaseFireBlock {
    public EndFireBlock(BlockBehaviour.Properties properties) { super(properties, 3.0F); }
    @Override public ItemStack getCloneItemStack(BlockGetter g, BlockPos p, BlockState s) {
        return ItemStack.EMPTY;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return this.canSurvive(pState, pLevel, pCurrentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    protected boolean canBurn(BlockState pState) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.END_FIRE.get(), (rendertype) -> {
            return rendertype == RenderType.cutout();
        });
    }
}
