package com.pla.annoyingvillagers.client.engine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.FORGE)
public class RenderIncinerator extends RenderItemBase {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, HumanoidArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if (entitypatch instanceof LivingEntityPatch) {
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.mainhandcorrectionMatrix);

            openmatrix4f.mulFront(poses[armature.toolR.getId()]);
            DynamicAnimation dynamicanimation = entitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            ItemStack itemstack1;

            if (dynamicanimation != AVAnimations.Legendary_Sword_Heavy_Attack && dynamicanimation != WOMAnimations.SOLAR_AUTO_2) {
                if (dynamicanimation == AVAnimations.Legendary_Sword_Wake_Up_Attack) {
                    itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.WAKE_UP_LEGENDARY_SWORD.get());
                    poseStack.pushPose();
                    this.mulPoseStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else {
                    itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                    poseStack.pushPose();
                    this.mulPoseStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                    poseStack.popPose();
                }
            } else {
                itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
                poseStack.pushPose();
                this.mulPoseStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                poseStack.popPose();
            }
        }
    }
}
