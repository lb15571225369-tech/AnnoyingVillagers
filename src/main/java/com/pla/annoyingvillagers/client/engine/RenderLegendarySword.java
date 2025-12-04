package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
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
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderLegendarySword extends RenderItemBase {

    public RenderLegendarySword(JsonElement json) {
        super(json);
        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG LegendarySwordRender] constructor called. json = {}", json);
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
        if (livingEntityPatch == null) return;
        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG LegendarySwordRender] renderItemInHand is called");

        OpenMatrix4f mat = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));
        mat.mulFront(poses[Armatures.BIPED.get().toolR.getId()]);

        AssetAccessor<? extends DynamicAnimation> currentAnim =
                Objects.requireNonNull(livingEntityPatch.getClientAnimator().getPlayerFor(null)).getAnimation();

        ItemStack itemstack;

        boolean isHeavy =
                currentAnim != null && (
                        currentAnim.equals(AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK)
                                || currentAnim.equals(AnimsSolar.SOLAR_AUTO_2)
                );

        boolean isWakeUp =
                currentAnim != null && currentAnim.equals(AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK);

        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG LegendarySwordRender]" +
                "\n  currentAnim = " + (currentAnim == null ? "null" : currentAnim +
                "\n  equals(LEGENDARY_SWORD_HEAVY_ATTACK) = " +
                currentAnim.equals(AVAnimations.LEGENDARY_SWORD_HEAVY_ATTACK) +
                "\n  equals(SOLAR_AUTO_2)                  = " +
                currentAnim.equals(AnimsSolar.SOLAR_AUTO_2)) +
                "\n  equals(LEGENDARY_SWORD_WAKE_UP)       = " + isWakeUp);

        if (isHeavy) {
            itemstack = new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
        } else if (isWakeUp) {
            itemstack = new ItemStack(AnnoyingVillagersModItems.WAKE_UP_LEGENDARY_SWORD.get());
        } else {
            itemstack = new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
        }

        poseStack.pushPose();
        MathUtils.mulStack(poseStack, mat);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                itemstack,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                livingEntityPatch.getOriginal().level(),
                0
        );
        poseStack.popPose();
    }
}
