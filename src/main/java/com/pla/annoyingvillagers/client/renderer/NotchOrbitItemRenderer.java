package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.entity.NotchOrbitItemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NotchOrbitItemRenderer extends EntityRenderer<NotchOrbitItemEntity> {
    public NotchOrbitItemRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(NotchOrbitItemEntity entity, float yaw, float partialTicks,
                       PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        ItemStack stack = entity.getItem();
        if (stack.isEmpty()) return;

        poseStack.pushPose();
        poseStack.scale(1.2F, 1.2F, 1.2F);

        float age = entity.tickCount + partialTicks;
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRotX() * age));
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRotY() * age));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRotZ() * age));

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY,
                poseStack, buffer, entity.level(), entity.getId());

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NotchOrbitItemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
