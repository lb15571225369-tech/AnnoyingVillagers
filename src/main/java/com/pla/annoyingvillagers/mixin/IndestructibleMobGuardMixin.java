package com.pla.annoyingvillagers.mixin;

import com.nameless.indestructible.world.capability.Utils.CapabilityState;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

@Mixin(value = CapabilityState.class, remap = false)
public abstract class IndestructibleMobGuardMixin {
    @Shadow(remap = false)
    public abstract MobPatch<?> getPatch();

    @Inject(method = "TryHurt", at = @At("RETURN"), remap = false)
    private void mobOnGuard(DamageSource damageSource, float amount,
                                       CallbackInfoReturnable<AttackResult> cir) {
        if (cir.getReturnValue() != null
                && cir.getReturnValue().resultType == AttackResult.ResultType.BLOCKED) {
            Mob victim = getPatch().getOriginal();
            if (!victim.level().isClientSide()) {
                AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG] guard success from {}",
                        victim.getName().getString());
            }
        }
    }
}
