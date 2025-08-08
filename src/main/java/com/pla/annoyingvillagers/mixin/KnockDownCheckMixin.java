package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import java.util.Optional;

@Mixin(value = {EntityState.class}, remap = false)
public abstract class KnockDownCheckMixin {
    @Inject(method = {"attackResult"}, at = {@At("HEAD")}, cancellable = true)
    public void Inject(DamageSource damagesource, CallbackInfoReturnable<ResultType> callbackinforeturnable) {
        if (damagesource instanceof EpicFightDamageSource epicfightdamagesource) {
            epicfightdamagesource.getHurtItem().getCapability(EpicFightCapabilities.CAPABILITY_ITEM).ifPresent((capabilityitem) -> {
                Object state = ((EntityState)(Object)this).getState(EntityState.KNOCKDOWN);

                if (state instanceof Boolean bool && bool) {
                    callbackinforeturnable.setReturnValue(ResultType.SUCCESS);
                } else if (state instanceof Optional<?> optional) {
                    optional.filter(val -> val instanceof Boolean && (Boolean) val)
                            .ifPresent(val -> callbackinforeturnable.setReturnValue(ResultType.SUCCESS));
                }
            });
        }

    }
}
