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
            epicfightdamagesource.getUsedItem().getCapability(EpicFightCapabilities.CAPABILITY_ITEM).ifPresent((capabilityitem) -> {
                Object state = ((EntityState)(Object)this).getState(EntityState.KNOCKDOWN);
                if (Boolean.TRUE.equals(state)) {
                    callbackinforeturnable.setReturnValue(ResultType.SUCCESS);
                }
            });
        }

    }
}
