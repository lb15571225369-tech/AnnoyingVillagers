/*
 * This file includes code derived from the "Sprite Arrows" mod.
 *
 * Original work Copyright (c) 2023 iliandev
 * Modified work Copyright (c) 2025 pla_is_me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.entity.TippableArrowRenderer.NORMAL_ARROW_LOCATION;

public class SpriteArrowRenderer extends EntityRenderer<AbstractArrow> {

    private final ItemRenderer renderer;

    public SpriteArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
        renderer = context.getItemRenderer();
    }

    @Override
    public void render(AbstractArrow abstractArrow, float pEntityYaw, float pPartialTicks, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.yRotO, abstractArrow.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.xRotO, abstractArrow.getXRot())));
        poseStack.translate(-0.2, 0, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-45));
        poseStack.scale(1.5f,1.5f,1.5f);
        ItemStack pickupItem = abstractArrow.getPickupItem();
        if (pickupItem.is(Items.ARROW) && abstractArrow instanceof Arrow arrow) {
            int color = arrow.getColor();
            if (color != -1) {
                pickupItem = Items.TIPPED_ARROW.getDefaultInstance();
                pickupItem.getOrCreateTag().putInt("CustomPotionColor", color);
            }
        }
        renderer.renderStatic(pickupItem, ItemDisplayContext.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, abstractArrow.level(), abstractArrow.getId());
        poseStack.popPose();
        super.render(abstractArrow, pEntityYaw, pPartialTicks, poseStack, buffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractArrow entity) {
        return NORMAL_ARROW_LOCATION;
    }
}