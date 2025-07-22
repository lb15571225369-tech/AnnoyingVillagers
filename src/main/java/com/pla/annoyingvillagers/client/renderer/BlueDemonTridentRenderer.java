package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelBlueDemonTrident;
import com.pla.annoyingvillagers.entity.BlueDemonTridentEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BlueDemonTridentRenderer extends EntityRenderer<BlueDemonTridentEntity> {

    private static final ResourceLocation texture = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/bluedemontrident.png");
    private final ModelBlueDemonTrident model;

    public BlueDemonTridentRenderer(Context context) {
        super(context);
        this.model = new ModelBlueDemonTrident<>(context.bakeLayer(ModelBlueDemonTrident.LAYER_LOCATION));
    }

    public void render(BlueDemonTridentEntity bluedemontridententity, float f, float f1, PoseStack posestack, MultiBufferSource multibuffersource, int i) {
        VertexConsumer vertexconsumer = multibuffersource.getBuffer(RenderType.entityCutout(this.getTextureLocation(bluedemontridententity)));

        posestack.pushPose();
        posestack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(f1, bluedemontridententity.yRotO, bluedemontridententity.getYRot()) - 90.0F));
        posestack.mulPose(Vector3f.ZP.rotationDegrees(90.0F + Mth.lerp(f1, bluedemontridententity.xRotO, bluedemontridententity.getXRot())));
        this.model.renderToBuffer(posestack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.0625F);
        posestack.popPose();
        super.render(bluedemontridententity, f, f1, posestack, multibuffersource, i);
    }

    public ResourceLocation getTextureLocation(BlueDemonTridentEntity bluedemontridententity) {
        return BlueDemonTridentRenderer.texture;
    }
}

