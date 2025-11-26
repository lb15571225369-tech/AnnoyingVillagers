package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class LegendarySwordRender extends RenderItemBase implements Function<JsonElement, RenderItemBase> {
    public LegendarySwordRender(JsonElement json) {
        super(json);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        if (entitypatch instanceof LivingEntityPatch) {
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(entitypatch, hand, poses));

            openmatrix4f.mulFront(poses[Armatures.BIPED.get().toolR.getId()]);
            AssetAccessor<? extends DynamicAnimation> dynamicanimation = entitypatch.getAnimator().getPlayerFor(null).getAnimation();
            ItemStack itemstack1;

            if (dynamicanimation != AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK && dynamicanimation != AnimsSolar.SOLAR_AUTO_2) {
                if (dynamicanimation == AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK) {
                    itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.WAKE_UP_LEGENDARY_SWORD.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                    poseStack.popPose();
                } else {
                    itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4f);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                    poseStack.popPose();
                }
            } else {
                itemstack1 = new ItemStack((ItemLike) AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entitypatch.getOriginal().level(), 0);
                poseStack.popPose();
            }
        }
    }

    @Override public RenderItemBase apply(JsonElement json) { return new LegendarySwordRender(json); }
}
