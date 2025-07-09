//package com.pla.annoyingvillagers.block;
//
//import java.util.Collections;
//import java.util.List;
//import net.minecraft.client.renderer.ItemBlockRenderTypes;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.level.storage.loot.LootContext.Builder;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
//import com.pla.annoyingvillagers.procedures.C4spawnDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure;
//
//public class C4spawnBlock extends Block {
//
//    public C4spawnBlock() {
//        super(Properties.of(Material.STONE).sound(SoundType.GRAVEL).strength(10.0F, 5.0F).noCollission());
//    }
//
//    public int getLightBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
//        return 15;
//    }
//
//    public boolean canBeReplaced(BlockState blockstate, BlockPlaceContext blockplacecontext) {
//        return blockplacecontext.getItemInHand().getItem() != this.asItem();
//    }
//
//    public List<ItemStack> getDrops(BlockState blockstate, Builder builder) {
//        List<ItemStack> list = super.getDrops(blockstate, builder);
//
//        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
//    }
//
//    public void entityInside(BlockState blockstate, Level level, BlockPos blockpos, Entity entity) {
//        super.entityInside(blockstate, level, blockpos, entity);
//        C4spawnDangShiTiZaiFangKuaiZhongPengZhuangShiProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), entity);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public static void registerRenderLayer() {
//        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.C_4SPAWN.get(), (rendertype) -> {
//            return rendertype == RenderType.cutout();
//        });
//    }
//}
