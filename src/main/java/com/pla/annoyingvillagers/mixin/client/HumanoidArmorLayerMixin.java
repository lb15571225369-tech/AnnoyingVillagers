package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @Inject(method = "renderArmorPiece", at = @At("HEAD"))
    private void setArmorTarget(PoseStack poseStack, MultiBufferSource bufferSource, T entity, EquipmentSlot slot, int packedLight, A model, CallbackInfo ci) {
        ColoredGlintState.setTargetStack(entity.getItemBySlot(slot));
    }

    @Inject(method = "renderArmorPiece", at = @At("RETURN"))
    private void clearArmorTarget(PoseStack poseStack, MultiBufferSource bufferSource, T entity, EquipmentSlot slot, int packedLight, A model, CallbackInfo ci) {
        ColoredGlintState.clear();
    }

    @Group(name = "av_armor_glint", min = 1, max = 1)
    @ModifyExpressionValue(
            method = "renderGlint(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private RenderType armorGlintModel(RenderType original) {
        return ColoredGlintState.getMode() == ColoredGlintState.CYAN
                ? ColoredGlintRenderTypes.ARMOR_ENTITY_GLINT_CYAN
                : original;
    }

    @Group(name = "av_armor_glint")
    @ModifyExpressionValue(
            method = "renderGlint(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/HumanoidModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;")
    )
    private RenderType armorGlintHumanoid(RenderType original) {
        return ColoredGlintState.getMode() == ColoredGlintState.CYAN
                ? ColoredGlintRenderTypes.ARMOR_ENTITY_GLINT_CYAN
                : original;
    }
}