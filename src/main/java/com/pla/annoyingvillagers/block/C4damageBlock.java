//package com.pla.annoyingvillagers.block;
//
//import java.util.Collections;
//import java.util.List;
//import net.minecraft.client.renderer.ItemBlockRenderTypes;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.HoeItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.DirectionalBlock;
//import net.minecraft.world.level.block.Mirror;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.StateDefinition.Builder;
//import net.minecraft.world.level.block.state.properties.DirectionProperty;
//import net.minecraft.world.level.block.state.properties.Property;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.level.material.MaterialColor;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.Shapes;
//import net.minecraft.world.phys.shapes.VoxelShape;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
//import com.pla.annoyingvillagers.procedures.C4damageFangZhiFangKuaiShiProcedure;
//
//public class C4damageBlock extends Block {
//
//    public static final DirectionProperty FACING = DirectionalBlock.FACING;
//
//    public C4damageBlock() {
//        super(Properties.of(Material.STONE, MaterialColor.FIRE).sound(SoundType.STONE).strength(-1.0F, 3600000.0F).requiresCorrectToolForDrops().noCollission().noOcclusion().isRedstoneConductor((blockstate, blockgetter, blockpos) -> {
//            return false;
//        }));
//        this.registerDefaultState((BlockState) ((BlockState) this.stateDefinition.any()).setValue(C4damageBlock.FACING, Direction.NORTH));
//    }
//
//    public boolean propagatesSkylightDown(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
//        return true;
//    }
//
//    public int getLightBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
//        return 0;
//    }
//
//    public VoxelShape getVisualShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
//        return Shapes.empty();
//    }
//
//    public VoxelShape getShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
//        VoxelShape voxelshape;
//
//        switch ((Direction) blockstate.getValue(C4damageBlock.FACING)) {
//            case NORTH:
//                voxelshape = box(0.0D, 0.99213D, 0.0D, 16.0D, 2.0D, 16.0D);
//                break;
//            case EAST:
//                voxelshape = box(0.0D, 0.99213D, 0.0D, 16.0D, 2.0D, 16.0D);
//                break;
//            case WEST:
//                voxelshape = box(0.0D, 0.99213D, 0.0D, 16.0D, 2.0D, 16.0D);
//                break;
//            case UP:
//                voxelshape = box(0.0D, 0.0D, 0.99213D, 16.0D, 16.0D, 2.0D);
//                break;
//            case DOWN:
//                voxelshape = box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 15.00787D);
//                break;
//            default:
//                voxelshape = box(0.0D, 0.99213D, 0.0D, 16.0D, 2.0D, 16.0D);
//        }
//
//        return voxelshape;
//    }
//
//    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
//        builder.add(new Property[]{C4damageBlock.FACING});
//    }
//
//    public BlockState getStateForPlacement(BlockPlaceContext blockplacecontext) {
//        return (BlockState) this.defaultBlockState().setValue(C4damageBlock.FACING, blockplacecontext.getNearestLookingDirection().getOpposite());
//    }
//
//    public BlockState rotate(BlockState blockstate, Rotation rotation) {
//        return (BlockState) blockstate.setValue(C4damageBlock.FACING, rotation.rotate((Direction) blockstate.getValue(C4damageBlock.FACING)));
//    }
//
//    public BlockState mirror(BlockState blockstate, Mirror mirror) {
//        return blockstate.rotate(mirror.getRotation((Direction) blockstate.getValue(C4damageBlock.FACING)));
//    }
//
//    public boolean canHarvestBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, Player player) {
//        Item item = player.getInventory().getSelected().getItem();
//
//        if (item instanceof HoeItem) {
//            HoeItem hoeitem = (HoeItem) item;
//
//            return hoeitem.getTier().getLevel() >= 1;
//        } else {
//            return false;
//        }
//    }
//
//    public List<ItemStack> getDrops(BlockState blockstate, net.minecraft.world.level.storage.loot.LootContext.Builder net_minecraft_world_level_storage_loot_lootcontext_builder) {
//        List<ItemStack> list = super.getDrops(blockstate, net_minecraft_world_level_storage_loot_lootcontext_builder);
//
//        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
//    }
//
//    public void onPlace(BlockState blockstate, Level level, BlockPos blockpos, BlockState blockstate1, boolean flag) {
//        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
//        C4damageFangZhiFangKuaiShiProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public static void registerRenderLayer() {
//        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.C_4DAMAGE.get(), (rendertype) -> {
//            return rendertype == RenderType.cutout();
//        });
//    }
//}
