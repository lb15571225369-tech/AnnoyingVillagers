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
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class HardGreatSwordRender extends RenderItemBase implements Function<JsonElement, RenderItemBase> {
    public HardGreatSwordRender(JsonElement json) {
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
        if (livingEntityPatch == null) return;
        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG HardGreatSwordRender] renderItemInHand is called");

        OpenMatrix4f mat = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));
        mat.mulFront(poses[Armatures.BIPED.get().toolR.getId()]);

        AssetAccessor<? extends DynamicAnimation> currentAnim =
                Objects.requireNonNull(livingEntityPatch.getClientAnimator().getPlayerFor(null)).getAnimation();

        ItemStack itemstack;
        boolean isGuardSkill = currentAnim != null && currentAnim.equals(AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL);
        boolean isGuard = currentAnim != null && currentAnim.equals(AVAnimations.HARD_GREAT_SWORD_GUARD);

        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG HardGreatSwordRender]" +
                "\n  currentAnim = " + (currentAnim == null ? "null" : currentAnim +
                "\n  equals(HARD_GREAT_SWORD_GUARD_SKILL) = " + isGuardSkill +
                "\n  equals(HARD_GREAT_SWORD_GUARD)       = " + isGuard));

        if (isGuardSkill || isGuard) {
            itemstack = new ItemStack(AnnoyingVillagersModItems.HARD_GREAT_SWORD_SKILL.get());
        } else {
            itemstack = new ItemStack(AnnoyingVillagersModItems.HARD_GREAT_SWORD.get());
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

    @Override public RenderItemBase apply(JsonElement json) { return new HardGreatSwordRender(json); }
}
