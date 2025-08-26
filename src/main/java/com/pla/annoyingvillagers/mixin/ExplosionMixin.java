package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.annotation.Nullable;

@Mixin(value = {Explosion.class}, remap = true)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private Level level;

    @Shadow @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @ModifyArg(
            method = "finalizeExplosion(Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState av$replaceVanillaFire(BlockState originalState) {
        if (!(originalState.getBlock() instanceof BaseFireBlock)) {
            return originalState;
        }

        LivingEntity owner = this.getIndirectSourceEntity();
        if (owner != null && owner.isAlive()) {
            ItemStack stack = owner.getMainHandItem();
            if (stack.getItem() instanceof EnderGlaiveItem) {
                return AnnoyingVillagersModBlocks.END_FIRE.get().defaultBlockState();
            }
        }
        return originalState;
    }
}
