package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.efclash_blade.skill.ClashBladeSkill;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

@Mixin(value = ClashBladeSkill.class, remap = false)
public class ClashBladeMixin {
    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                                EntityState entityState,
                                                                ServerPlayer serverPlayer,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (dynamicAnimation.get() instanceof BowAttackAnimation) {
            cir.setReturnValue(false);
        }
    }
}