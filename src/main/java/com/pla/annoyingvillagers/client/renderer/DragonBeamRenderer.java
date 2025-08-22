package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class DragonBeamRenderer extends EntityRenderer<DragonBeamEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");

    public DragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public Vec3 getRenderOffset(DragonBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03);
    }

    public void render(DragonBeamEntity beam, float entityYaw, float delta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        return;
//        if (!beam.isRenderable() || beam.tickCount <= 1) {
//            return;
//        }
//
//        double collidePosX = beam.prevCollidePosX + (beam.collidePosX - beam.prevCollidePosX) * (double) delta;
//        double collidePosY = beam.prevCollidePosY + (beam.collidePosY - beam.prevCollidePosY) * (double) delta;
//        double collidePosZ = beam.prevCollidePosZ + (beam.collidePosZ - beam.prevCollidePosZ) * (double) delta;
//        double posX = beam.xo + (beam.getX() - beam.xo) * (double) delta;
//        double posY = beam.yo + (beam.getY() - beam.yo) * (double) delta;
//        double posZ = beam.zo + (beam.getZ() - beam.zo) * (double) delta;
//        float yaw = beam.prevYaw + (beam.renderYaw - beam.prevYaw) * delta;
//        float pitch = beam.prevPitch + (beam.renderPitch - beam.prevPitch) * delta;
//        float length = (float) Math.sqrt(Math.pow(collidePosX - posX, 2.0) + Math.pow(collidePosY - posY, 2.0) + Math.pow(collidePosZ - posZ, 2.0));
//        int frame = Mth.floor((2.0F + delta) * 2.0F);
//        if (frame < 0) {
//            frame = 6;
//        }
//
//        VertexConsumer ivertexbuilder = multiBufferSource.getBuffer(ISRenderType.getGlowingEffect(this.getTextureLocation(beam)));
//        this.renderStart(frame, poseStack, ivertexbuilder, light);
//        this.renderBeam(frame, length, 57.295776F * yaw, 57.295776F * pitch, poseStack, ivertexbuilder, light);
//        poseStack.pushPose();
//        poseStack.translate(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
//        this.renderEnd(frame, beam.blockSide, poseStack, ivertexbuilder, light);
//        poseStack.popPose();
    }

    public ResourceLocation getTextureLocation(DragonBeamEntity dragonBeam) {
        return TEXTURE;
    }

    private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0.625F;
        float minV = 0.0F;
        float maxU = minU + 0.0625F;
        float maxV = minV + 0.5F;
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        this.drawVertex(matrix4f, matrix3f, builder, -1.3F, -1.3F, 0.0F, minU, minV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -1.3F, 1.3F, 0.0F, minU, maxV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, 1.3F, 1.3F, 0.0F, maxU, maxV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, 1.3F, -1.3F, 0.0F, maxU, minV, 1.0F, packedLightIn);
    }

    private void renderStart(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        matrixStackIn.scale(0.5f, 0.5f, 0.5f);
        this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void renderEnd(int frame, Direction side, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        if (side != null) {
            matrixStackIn.pushPose();
            Quaternionf sideQuat = new Quaternionf(side.getRotation());
            sideQuat.mul(new Quaternionf().rotateXYZ((float) Math.toRadians(90), 0.0F, 0.0F));
            matrixStackIn.mulPose(sideQuat);
            matrixStackIn.translate(0.0, 0.0, -0.009999999776482582);
            this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
            matrixStackIn.popPose();
        }
    }

    private void drawBeam(int frame, float length, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0.0F;
        float minV = 0.5F + 0.03125F * (float) frame;
        float maxU = minU + 0.078125F;
        float maxV = minV + 0.03125F;
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        this.drawVertex(matrix4f, matrix3f, builder, -0.7F, 0.0F, 0.0F, minU, minV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, -0.7F, length, 0.0F, minU, maxV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, 0.7F, length, 0.0F, maxU, maxV, 1.0F, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, builder, 0.7F, 0.0F, 0.0F, maxU, minV, 1.0F, packedLightIn);
    }

    private void renderBeam(int frame, float length, float yaw, float pitch, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(new Quaternionf().rotateXYZ((float) Math.toRadians(90), 0.0F, 0.0F));
        matrixStackIn.mulPose(new Quaternionf().rotateXYZ(0.0F, 0.0F, (float) Math.toRadians(yaw - 90.0F)));
        matrixStackIn.mulPose(new Quaternionf().rotateXYZ((float) Math.toRadians(-pitch), 0.0F, 0.0F));
        matrixStackIn.pushPose();
        this.drawBeam(frame, length, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1.0F, 1.0F, 1.0F, alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
}