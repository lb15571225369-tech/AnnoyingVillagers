package com.pla.annoyingvillagers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
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
    @Unique private static final int AV_GLINT_NONE = 0;
    @Unique private static final int AV_GLINT_ORANGE = 1;
    @Unique private static final int AV_GLINT_CYAN = 2;

    @Unique
    private static final ThreadLocal<Integer> AV_GLINT_MODE = ThreadLocal.withInitial(() -> AV_GLINT_NONE);

    @Inject(method = "render", at = @At("HEAD"))
    private void markIfOurItem(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                               PoseStack pose, MultiBufferSource buf, int light, int overlay,
                               BakedModel model, CallbackInfo ci) {
        int mode = AV_GLINT_NONE;

        if (stack.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
            mode = AV_GLINT_ORANGE;
        } else if (
                (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) && BlueDemonTridentItem.isFullyCharged(stack))
                        || (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()) && BlueDemonChestplateItem.isFullyCharged(stack))
        ) {
            mode = AV_GLINT_CYAN;
        }

        AV_GLINT_MODE.set(mode);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void clearFlag(ItemStack stack, ItemDisplayContext ctx, boolean leftHand,
                           PoseStack pose, MultiBufferSource buf, int light, int overlay,
                           BakedModel model, CallbackInfo ci) {
        AV_GLINT_MODE.remove();
    }

    @Unique
    private static RenderType avPick(RenderType original, RenderType orange, RenderType cyan) {
        return switch (AV_GLINT_MODE.get()) {
            case AV_GLINT_ORANGE -> orange;
            case AV_GLINT_CYAN -> cyan;
            default -> original;
        };
    }

    @ModifyExpressionValue(
            method = "getFoilBufferDirect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;")
    )
    private static RenderType glintDirect(RenderType original) {
        return avPick(
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
        return avPick(
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
        return avPick(
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
        return avPick(
                original,
                ColoredGlintRenderTypes.ENTITY_GLINT_ORANGE,
                ColoredGlintRenderTypes.ENTITY_GLINT_CYAN
        );
    }
}