package com.pla.annoyingvillagers.mixin;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AimAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = {AimAnimation.class}, remap = false)
public abstract class AimAnimationMixin extends StaticAnimation {
    @Inject(method = "getPoseByTime", at = @At("HEAD"), cancellable = true)
    private void safeGetPose(LivingEntityPatch<?> entitypatch, float time, float partialTicks, CallbackInfoReturnable<Pose> cir) {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            cir.setReturnValue(new Pose());
        }
    }
}