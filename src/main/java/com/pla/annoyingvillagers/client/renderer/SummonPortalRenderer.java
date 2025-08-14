package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.SummonPortalEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SummonPortalRenderer extends EntityRenderer<SummonPortalEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/portal.png");

    public SummonPortalRenderer(EntityRendererProvider.Context mgr) {
        super(mgr);
    }

    @Override
    public ResourceLocation getTextureLocation(SummonPortalEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SummonPortalEntity portal, float yaw, float partialTicks,
                       PoseStack matrix, MultiBufferSource buffers, int packedLight) {
        int pPackedLight = LightTexture.pack(15, 15);

        float t = portal.getAnimationTick() + partialTicks;
        float angle = t * Mth.clamp(t / 30F, 1F, 10F);
        float scale = computeScale(t);
        scale = Math.max(0.001F, scale);
        int alpha = (int) (255 * Mth.clamp(scale * 1.1F, 0F, 1F));
        matrix.pushPose();
        matrix.translate(0.5, 0.015, 0.5);
        matrix.mulPose(Axis.YP.rotationDegrees(angle));
        matrix.scale(scale, 1.0F, scale); // <-- animate size
        float halfSize = 2.5F;

        PoseStack.Pose pose = matrix.last();
        VertexConsumer vc = buffers.getBuffer(RenderType.entityTranslucent(TEXTURE));

        int r = 255, g = 255, b = 255, a = Mth.clamp(alpha, 0, 255);
        vc.vertex(pose.pose(), -halfSize, 0, -halfSize)
                .color(r, g, b, a).uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
                .normal(pose.normal(), 0, 1, 0).endVertex();

        vc.vertex(pose.pose(),  halfSize, 0, -halfSize)
                .color(r, g, b, a).uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
                .normal(pose.normal(), 0, 1, 0).endVertex();

        vc.vertex(pose.pose(),  halfSize, 0,  halfSize)
                .color(r, g, b, a).uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
                .normal(pose.normal(), 0, 1, 0).endVertex();

        vc.vertex(pose.pose(), -halfSize, 0,  halfSize)
                .color(r, g, b, a).uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
                .normal(pose.normal(), 0, 1, 0).endVertex();

        matrix.popPose();
    }

    private static float computeScale(float t) {
        final float G = SummonPortalEntity.GROW_TICKS;
        final float H = SummonPortalEntity.HOLD_TICKS;
        final float S = SummonPortalEntity.SHRINK_TICKS;

        if (t <= G) {
            float p = t / G;
            return easeOutCubic(p);
        }
        if (t <= G + H) {
            return 1.0F;
        }
        float p = (t - G - H) / S;
        return 1.0F - easeInCubic(Mth.clamp(p, 0F, 1F));
    }

    private static float easeOutCubic(float x) { return 1.0F - (float)Math.pow(1.0F - x, 3); }
    private static float easeInCubic(float x)  { return x * x * x; }
}