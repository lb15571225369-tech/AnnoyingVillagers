package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.compat.player_mobs.PlayerMobEpicFightOverlayLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@Mixin(value = PHumanoidRenderer.class, remap = false)
public abstract class MixinPHumanoidRenderer {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addHerobrineEye(MeshProvider<?> mesh,
                                 EntityRendererProvider.Context ctx,
                                 EntityType<?> type,
                                 CallbackInfo ci) {
        PatchedLivingEntityRenderer<?, ?, ?, ?, ?> self =
                (PatchedLivingEntityRenderer<?, ?, ?, ?, ?>)(Object)this;
        self.addCustomLayer(new PlayerMobEpicFightOverlayLayer<>((MeshProvider) mesh));
    }
}