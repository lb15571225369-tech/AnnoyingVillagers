package com.pla.annoyingvillagers.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
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
        if (data.contains("idle_message_broadcasted")) {
            data.remove("idle_message_broadcasted");
        }
        if (data.contains("av_idle_animation_playing") && self instanceof Mob mob) {
            data.remove("av_idle_animation_playing");
            mob.setNoAi(false);
        }
    }
}
