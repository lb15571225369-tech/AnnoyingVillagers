package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderShadowObsidianSword extends RenderItemBase {

    public RenderShadowObsidianSword(JsonElement json) {
        super(json);
    }

    @Override
    public void renderItemInHand(ItemStack stack,
                                 LivingEntityPatch<?> livingEntityPatch,
                                 InteractionHand hand,
                                 OpenMatrix4f[] poses,
                                 MultiBufferSource buffer,
                                 PoseStack poseStack,
                                 int packedLight,
                                 float partialTicks) {
        if (livingEntityPatch != null) {
            if (livingEntityPatch.getOriginal().getMainHandItem().getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.MAIN_HAND, poses));
                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = animationPlayer.getAnimation();
                float elapsedTimeFloat = animationPlayer.getElapsedTime();
                EntityState entityState = (dynamicAnimation.get()).getState(livingEntityPatch, elapsedTimeFloat);
                ItemStack itemstack;

                if (dynamicAnimation == AVAnimations.OBSIDIAN_FIST_DASH && entityState.getLevel() > 1) {
                    itemstack = ItemStack.EMPTY;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else if (((dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_FIST_AIR_SLASH) && entityState.getLevel() > 1)
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH) {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                }
            }
            if (livingEntityPatch.getOriginal().getOffhandItem().getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.OFF_HAND, poses));
                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = animationPlayer.getAnimation();
                float elapsedTimeFloat = animationPlayer.getElapsedTime();
                EntityState entityState = (dynamicAnimation.get()).getState(livingEntityPatch, elapsedTimeFloat);
                ItemStack itemstack;

                if (dynamicAnimation == AVAnimations.OBSIDIAN_FIST_DASH && entityState.getLevel() > 1) {
                    itemstack = ItemStack.EMPTY;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else if (((dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_FIST_AIR_SLASH) && entityState.getLevel() > 1)
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH) {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                }
            }
        }
    }
}
