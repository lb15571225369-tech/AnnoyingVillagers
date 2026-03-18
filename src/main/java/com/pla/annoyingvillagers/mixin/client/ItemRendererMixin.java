package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void setTargetStack(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                                   PoseStack pose, MultiBufferSource buf, int light, int overlay,
                                   BakedModel model, CallbackInfo ci) {
        ColoredGlintState.setTargetStack(stack);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void clearTargetStack(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                                     PoseStack pose, MultiBufferSource buf, int light, int overlay,
                                     BakedModel model, CallbackInfo ci) {
        ColoredGlintState.clear();
    }

    @Unique
    private static RenderType pick(RenderType original, RenderType orange, RenderType cyan) {
        return switch (ColoredGlintState.getMode()) {
            case ColoredGlintState.ORANGE -> orange;
            case ColoredGlintState.CYAN -> cyan;
            default -> original;
        };
    }

    @ModifyExpressionValue(
            method = "getFoilBufferDirect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType glintDirect(RenderType original) {
        return pick(
                original,
                ColoredGlintRenderTypes.GLINT_DIRECT_ORANGE,
                ColoredGlintRenderTypes.GLINT_DIRECT_CYAN
        );
    }

    @ModifyExpressionValue(
            method = "getFoilBufferDirect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlintDirect()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType entityGlintDirect(RenderType original) {
        return pick(
                original,
                ColoredGlintRenderTypes.ENTITY_GLINT_DIRECT_ORANGE,
                ColoredGlintRenderTypes.ENTITY_GLINT_DIRECT_CYAN
        );
    }

    @ModifyExpressionValue(
            method = "getFoilBuffer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType glint(RenderType original) {
        return pick(
                original,
                ColoredGlintRenderTypes.GLINT_ORANGE,
                ColoredGlintRenderTypes.GLINT_CYAN
        );
    }

    @ModifyExpressionValue(
            method = "getFoilBuffer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType entityGlint(RenderType original) {
        return pick(
                original,
                ColoredGlintRenderTypes.ENTITY_GLINT_ORANGE,
                ColoredGlintRenderTypes.ENTITY_GLINT_CYAN
        );
    }
}