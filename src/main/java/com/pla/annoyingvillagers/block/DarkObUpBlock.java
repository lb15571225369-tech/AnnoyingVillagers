package com.pla.annoyingvillagers.block;

import java.util.Random;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.procedures.DarkObSsOnEntityInsideProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnAttackProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnTickProcedure;
import com.pla.annoyingvillagers.procedures.DarkObSsOnPlaceProcedure;

public class DarkObUpBlock extends Block {

    public DarkObUpBlock() {
        super(Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 50.0F).noOcclusion().hasPostProcess((blockstate, blockgetter, blockpos) -> {
            return true;
        }).emissiveRendering((blockstate, blockgetter, blockpos) -> {
            return true;
        }).isRedstoneConductor((blockstate, blockgetter, blockpos) -> {
            return false;
        }).dynamicShape().noDrops());
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

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    public void onPlace(BlockState blockstate, Level level, BlockPos blockpos, BlockState blockstate1, boolean flag) {
        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
        level.scheduleTick(blockpos, this, 20);
        DarkObSsOnPlaceProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
    }

    public void tick(BlockState blockstate, ServerLevel serverlevel, BlockPos blockpos, Random random) {
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
        DarkObSsOnEntityInsideProcedure.execute(level, entity);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.DARK_OB_UP.get(), (rendertype) -> {
            return rendertype == RenderType.cutout();
        });
    }
}
