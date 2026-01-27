package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ActionAnimation.class, remap = false)
public class ActionAnimationMixin {
    @Inject(
            method = "move",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stopMoveWhenInsideBlock(LivingEntityPatch<?> livingEntityPatch,
                                            AssetAccessor<? extends DynamicAnimation> animation,
                                            CallbackInfo ci) {
        if (livingEntityPatch == null || livingEntityPatch.getOriginal() == null) return;

        LivingEntity living = livingEntityPatch.getOriginal();
        AABB aabb = living.getBoundingBox().deflate(1.0E-7D);
        if (living.level().getBlockCollisions(living, aabb).iterator().hasNext()) {
            ci.cancel();
        }
    }
}