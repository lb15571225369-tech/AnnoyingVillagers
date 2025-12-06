/*
 * Colored enchantment rendering adapted from Quark.
 *
 * Original work: Quark by Vazkii and contributors
 * https://github.com/VazkiiMods/Quark
 *
 * Quark is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License (CC BY-NC-SA 3.0):
 * https://creativecommons.org/licenses/by-nc-sa/3.0/
 *
 * This file contains a modified version of Quark's colored enchantment code,
 * adapted for use in the <your-mod-id> mod. In accordance with the original
 * license, this derivative work is also licensed under CC BY-NC-SA 3.0.
 *
 * Please give proper credit to Quark / Vazkii if you reuse this code.
 */

package com.pla.annoyingvillagers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.HerobrineEnderEyeGlintRenderTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    private static final ThreadLocal<Boolean> AV_ORANGE_GLINT = ThreadLocal.withInitial(() -> false);

    @Inject(method = "render", at = @At("HEAD"))
    private void av$markIfOurItem(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                                  PoseStack pose, MultiBufferSource buf, int light, int overlay,
                                  BakedModel model, CallbackInfo ci) {
        AV_ORANGE_GLINT.set(stack.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void av$clearFlag(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                              PoseStack pose, MultiBufferSource buf, int light, int overlay,
                              BakedModel model, CallbackInfo ci) {
        AV_ORANGE_GLINT.remove();
    }

    @ModifyExpressionValue(
            method = "getFoilBufferDirect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType av$glintDirect(RenderType original) {
        return AV_ORANGE_GLINT.get()
                ? HerobrineEnderEyeGlintRenderTypes.GLINT_DIRECT_ORANGE
                : original;
    }

    @ModifyExpressionValue(
            method = "getFoilBufferDirect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlintDirect()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType av$entityGlintDirect(RenderType original) {
        return AV_ORANGE_GLINT.get()
                ? HerobrineEnderEyeGlintRenderTypes.ENTITY_GLINT_DIRECT_ORANGE
                : original;
    }

    @ModifyExpressionValue(
            method = "getFoilBuffer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType av$glint(RenderType original) {
        return AV_ORANGE_GLINT.get()
                ? HerobrineEnderEyeGlintRenderTypes.GLINT_ORANGE
                : original;
    }

    @ModifyExpressionValue(
            method = "getFoilBuffer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType av$entityGlint(RenderType original) {
        return AV_ORANGE_GLINT.get()
                ? HerobrineEnderEyeGlintRenderTypes.ENTITY_GLINT_ORANGE
                : original;
    }
}
