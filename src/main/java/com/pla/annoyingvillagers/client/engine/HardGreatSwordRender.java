package com.pla.annoyingvillagers.client.engine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.FORGE)
public class HardGreatSwordRender extends RenderItemBase {

    @OnlyIn(Dist.CLIENT)
    public void renderItemInHand(ItemStack itemstack, LivingEntityPatch<?> livingentitypatch, InteractionHand interactionhand, HumanoidArmature humanoidarmature, OpenMatrix4f[] aopenmatrix4f, MultiBufferSource multibuffersource, PoseStack posestack, int i) {
        if (livingentitypatch instanceof LivingEntityPatch) {
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.mainhandcorrectionMatrix);

            openmatrix4f.mulFront(aopenmatrix4f[humanoidarmature.toolR.getId()]);
            DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            ItemStack itemstack1;

            if (dynamicanimation != AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL && !dynamicanimation.equals(AVAnimations.HARD_GREAT_SWORD_GUARD)) {
                itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.HARD_GREAT_SWORD.get());
                posestack.pushPose();
                this.mulPoseStack(posestack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, TransformType.THIRD_PERSON_RIGHT_HAND, i, OverlayTexture.NO_OVERLAY, posestack, multibuffersource, 0);
                posestack.popPose();
            } else {
                itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.HARD_GREAT_SWORD_SKILL.get());
                posestack.pushPose();
                this.mulPoseStack(posestack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, TransformType.THIRD_PERSON_RIGHT_HAND, i, OverlayTexture.NO_OVERLAY, posestack, multibuffersource, 0);
                posestack.popPose();
            }
        }

    }
}
