package com.pla.annoyingvillagers.Mixin;

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

@Mixin(value = {EntityState.class}, remap = false)
public abstract class KnockDownCheckMixin {

    @Shadow
    public abstract boolean knockDown();

    @Inject(method = {"attackResult"}, at = {@At("HEAD")}, cancellable = true)
    public void Inject(DamageSource damagesource, CallbackInfoReturnable<ResultType> callbackinforeturnable) {
        if (damagesource instanceof EpicFightDamageSource) {
            EpicFightDamageSource epicfightdamagesource = (EpicFightDamageSource) damagesource;

            epicfightdamagesource.getHurtItem().getCapability(EpicFightCapabilities.CAPABILITY_ITEM).ifPresent((capabilityitem) -> {
                if (this.knockDown()) {
                    callbackinforeturnable.setReturnValue(ResultType.SUCCESS);
                }

            });
        }

    }
}
