package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.NotchEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class NotchCapeLayer extends RenderLayer<NotchEntity, PlayerModel<NotchEntity>> {

    private static final ResourceLocation CAPE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/notch_cape.png");

    public NotchCapeLayer(RenderLayerParent<NotchEntity, PlayerModel<NotchEntity>> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight,
                       @NotNull NotchEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 0.125F);

        float bodyYaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        double yawRadians = Math.toRadians(bodyYaw);
        double motionX = Mth.lerp(partialTick, entity.xOld, entity.getX()) - entity.xOld;
        double motionZ = Mth.lerp(partialTick, entity.zOld, entity.getZ()) - entity.zOld;
        double forwardMotion = motionX * Math.sin(yawRadians) - motionZ * Math.cos(yawRadians);

        float capeSwing = (float) Math.min(forwardMotion * 10.0, 6.0);
        float baseAngle = Math.min(6.0F + capeSwing + limbSwingAmount * 10.0F, 32.0F);

        this.getParentModel().cloak.xRot = (float) Math.toRadians(baseAngle);
        this.getParentModel().cloak.visible = true;

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(CAPE_TEXTURE));
        this.getParentModel().cloak.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}
