package com.pla.annoyingvillagers.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Entity.class}, remap = false)
public class EntityMixin {
    @Inject(method = "onAddedToWorld", at = @At("TAIL"))
    private void clearNBTtag(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        CompoundTag data = self.getPersistentData();
        if (data.contains("av_idle_animate_backup_main_hand")) {
            data.remove("av_idle_animate_backup_main_hand");
        }
    }
}
