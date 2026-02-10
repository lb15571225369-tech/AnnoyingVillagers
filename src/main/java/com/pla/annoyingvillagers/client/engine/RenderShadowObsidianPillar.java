package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
public class RenderShadowObsidianPillar extends RenderItemBase {

    public RenderShadowObsidianPillar(JsonElement json) {
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
            if (livingEntityPatch.getOriginal().getMainHandItem().getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get())) {
                OpenMatrix4f openmatrix4fmainHand = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.MAIN_HAND, poses));
                OpenMatrix4f openmatrix4foffHand = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.OFF_HAND, poses));
                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
                AssetAccessor<? extends DynamicAnimation> dynamicAnimation = animationPlayer.getAnimation();
                float elapsedTimeFloat = animationPlayer.getElapsedTime();
                EntityState entityState = (dynamicAnimation.get()).getState(livingEntityPatch, elapsedTimeFloat);
                ItemStack itemstack;

                if (dynamicAnimation == AVAnimations.OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL
                        || dynamicAnimation == AVAnimations.OBSIDIAN_ZOMBIE_ATTACK3
                        || dynamicAnimation == AVAnimations.OBSIDIAN_KICK_AUTO_1
                        || dynamicAnimation == AVAnimations.OBSIDIAN_KICK_AUTO_3
                        || dynamicAnimation == AVAnimations.OBSIDIAN_FIST_AUTO3
                        || dynamicAnimation == AVAnimations.OBSIDIAN_STRONG_KICK
                        || dynamicAnimation == AVAnimations.OBSIDIAN_FIST_AUTO1
                        || dynamicAnimation == AVAnimations.OBSIDIAN_BIPED_LANDING
                        || dynamicAnimation == AVAnimations.OBSIDIAN_WHIRLWIND_KICK) {
                    itemstack = ItemStack.EMPTY;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else if (((dynamicAnimation == AVAnimations.OBSIDIAN_FIST_AIR_SLASH || dynamicAnimation == AVAnimations.OBSIDIAN_INFERNAL_AUTO_2) && entityState.getLevel() > 1)
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE_PILLAR) {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                    if (itemstack.getTag() != null) {
                        itemstack.getTag().putBoolean("foil", livingEntityPatch.getOriginal().getMainHandItem().isEnchanted());
                    }
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else if ((dynamicAnimation == AVAnimations.OBSIDIAN_INFERNAL_AUTO_1
                        || dynamicAnimation == AVAnimations.OBSIDIAN_STRONG_PUNCH
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO1
                        || dynamicAnimation == AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO3)
                        && entityState.getLevel() > 1) {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();

                    if (livingEntityPatch.getOriginal().getOffhandItem().getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                        itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_STRAIGHT.get());
                        if (itemstack.getTag() != null) {
                            itemstack.getTag().putBoolean("foil", livingEntityPatch.getOriginal().getOffhandItem().isEnchanted());
                        }
                        poseStack.pushPose();
                        MathUtils.mulStack(poseStack, openmatrix4foffHand);
                        Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                        poseStack.popPose();
                    }
                } else {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                    poseStack.popPose();

                    if (livingEntityPatch.getOriginal().getOffhandItem().getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                        itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get());
                        poseStack.pushPose();
                        MathUtils.mulStack(poseStack, openmatrix4foffHand);
                        poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
                        Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                        poseStack.popPose();
                    }
                }
            }
        }
    }
}
