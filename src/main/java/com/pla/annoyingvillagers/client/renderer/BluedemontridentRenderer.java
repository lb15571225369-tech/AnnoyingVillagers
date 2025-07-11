package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.pla.annoyingvillagers.client.model.Modelbluedemontrident;
import com.pla.annoyingvillagers.entity.BluedemontridentEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BluedemontridentRenderer extends EntityRenderer<BluedemontridentEntity> {

    private static final ResourceLocation texture = new ResourceLocation("annoyingvillagers:textures/entities/bluedemontrident.png");
    private final Modelbluedemontrident model;

    public BluedemontridentRenderer(Context context) {
        super(context);
        this.model = new Modelbluedemontrident<>(context.bakeLayer(Modelbluedemontrident.LAYER_LOCATION));
    }

    public void render(BluedemontridentEntity bluedemontridententity, float f, float f1, PoseStack posestack, MultiBufferSource multibuffersource, int i) {
        VertexConsumer vertexconsumer = multibuffersource.getBuffer(RenderType.entityCutout(this.getTextureLocation(bluedemontridententity)));

        posestack.pushPose();
        posestack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(f1, bluedemontridententity.yRotO, bluedemontridententity.getYRot()) - 90.0F));
        posestack.mulPose(Vector3f.ZP.rotationDegrees(90.0F + Mth.lerp(f1, bluedemontridententity.xRotO, bluedemontridententity.getXRot())));
        this.model.renderToBuffer(posestack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.0625F);
        posestack.popPose();
        super.render(bluedemontridententity, f, f1, posestack, multibuffersource, i);
    }

    public ResourceLocation getTextureLocation(BluedemontridentEntity bluedemontridententity) {
        return BluedemontridentRenderer.texture;
    }
}

