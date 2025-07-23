package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ForgeSoundType;
import com.pla.annoyingvillagers.procedures.ShadowObsidianWhenEntityInsideBlockOnCollisionProcedure;
import com.pla.annoyingvillagers.procedures.ShadowObsidianPreventBlockProcedure;

public class ShadowObsidianBlock extends Block {

    public ShadowObsidianBlock() {
        super(Properties.of()
                .sound(new ForgeSoundType(1.0F, 1.0F,
                        () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("annoyingvillagers", "lost")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("minecraft", "block.stone.step")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("minecraft", "block.stone.place")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("minecraft", "block.stone.hit")),
                        () -> BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("annoyingvillagers", "silent"))
                ))
                .strength(30.0F, 40.0F)
                .lightLevel((blockstate) -> 4)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
    }

    public void appendHoverText(ItemStack itemstack, BlockGetter blockgetter, List<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.literal("Shadow Obsidian Fired by Shadow Herobrine's Clone"));
    }

    public int getLightBlock(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos) {
        return 15;
    }

    public VoxelShape getVisualShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState blockstate, BlockGetter blockgetter, BlockPos blockpos, CollisionContext collisioncontext) {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 17.0D);
    }

    public List<ItemStack> getDrops(BlockState blockstate, LootParams.Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    public void onPlace(BlockState blockstate, Level level, BlockPos blockpos, BlockState blockstate1, boolean flag) {
        super.onPlace(blockstate, level, blockpos, blockstate1, flag);
        ShadowObsidianPreventBlockProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
    }

    public void entityInside(BlockState blockstate, Level level, BlockPos blockpos, Entity entity) {
        super.entityInside(blockstate, level, blockpos, entity);
        ShadowObsidianWhenEntityInsideBlockOnCollisionProcedure.execute(level, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), entity);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer((Block) AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get(), (rendertype) -> {
            return rendertype == RenderType.cutoutMipped();
        });
    }
}
