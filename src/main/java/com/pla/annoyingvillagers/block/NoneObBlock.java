package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.procedures.NoneObWhenEntityCollidesWithBlockProcedure;
import com.pla.annoyingvillagers.procedures.NoneObPlaceBlockProcedure;

public class NoneObBlock extends Block {

    public NoneObBlock() {
        super(Properties.of(Material.STONE).sound(SoundType.GRAVEL).strength(1.0F, 10.0F).noCollission());
    }

    public int getLightBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
        return 15;
    }

    public List<ItemStack> getDrops(BlockState blockstate, Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    public void onPlace(BlockState blockstate, Level level, BlockPos blockpos, BlockState blockstate1, boolean flag) {
        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
        NoneObPlaceBlockProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
    }

    public void entityInside(BlockState blockstate, Level level, BlockPos blockpos, Entity entity) {
        super.entityInside(blockstate, level, blockpos, entity);
        NoneObWhenEntityCollidesWithBlockProcedure.execute(level, entity);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.NONEOB.get(), (rendertype) -> {
            return rendertype == RenderType.cutout();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void blockColorLoad(net.minecraftforge.client.event.ColorHandlerEvent.Block net_minecraftforge_client_event_colorhandlerevent_block) {
        net_minecraftforge_client_event_colorhandlerevent_block.getBlockColors().register((blockstate, blockandtintgetter, blockpos, i) -> {
            return blockandtintgetter != null && blockpos != null ? BiomeColors.getAverageGrassColor(blockandtintgetter, blockpos) : GrassColor.get(0.5D, 1.0D);
        }, new Block[]{(Block) AnnoyingVillagersModBlocks.NONEOB.get()});
    }
}
