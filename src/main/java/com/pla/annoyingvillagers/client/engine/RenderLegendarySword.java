package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import reascer.wom.gameasset.animations.weapons.AnimsNapoleon;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderLegendarySword extends RenderItemBase {

    public RenderLegendarySword(JsonElement json) {
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
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.MAIN_HAND, poses));
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            ItemStack itemstack;

            if (dynamicAnimation == AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK
                    || dynamicAnimation == AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK
                    || dynamicAnimation == AVAnimations.YELLOW_SOLAR_AUTO_2
                    || dynamicAnimation == AVAnimations.YELLOW_NAPOLEON_AUTO_3
                    || dynamicAnimation == AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT
                    || dynamicAnimation == AVAnimations.CLONE_NAPOLEON_WATERLOW_SHOOT
                    || dynamicAnimation == AVAnimations.YELLOW_TORMENT_CHARGED_ATTACK_3) {
                itemstack = new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            } else {
                itemstack = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            }
        }
    }
}
