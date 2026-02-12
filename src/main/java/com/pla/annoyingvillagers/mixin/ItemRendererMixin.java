/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: CC-BY-NC-SA-3.0
 *
 * Upstream: Quark - Vazkii and contributors
 * Source: https://github.com/VazkiiMods/Quark
 *
 * This file contains code adapted from the upstream project (colored enchantment rendering).
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/CC-BY-NC-SA-3.0.md
 *
 * Modifications and integration:
 *   Copyright (c) 2025 pla_is_me
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
