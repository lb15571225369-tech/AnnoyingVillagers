package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin(AttributeInstance.class)
public abstract class AttributeInstanceMixin {

    @Shadow
    @Final
    private Map<UUID, AttributeModifier> modifierById;
    @Shadow protected abstract void setDirty();
    @Shadow protected abstract Set<AttributeModifier> getModifiers(AttributeModifier.Operation operation);

    @Inject(method = "addModifier", at = @At("HEAD"), cancellable = true)
    private void safeAddModifier(AttributeModifier modifier, CallbackInfo ci) {
        UUID id = modifier.getId();
        if (modifierById.containsKey(id)) {
            ci.cancel();
        }
    }
}
