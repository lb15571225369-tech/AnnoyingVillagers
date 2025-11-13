package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BabyDragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class BabyDragonBeamRenderer extends EntityRenderer<BabyDragonBeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");

    public BabyDragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public Vec3 getRenderOffset(BabyDragonBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03);
    }

    public void render(BabyDragonBeamEntity beam, float entityYaw, float delta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
    }

    public ResourceLocation getTextureLocation(BabyDragonBeamEntity dragonBeam) {
        return TEXTURE;
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1.0F, 1.0F, 1.0F, alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
}