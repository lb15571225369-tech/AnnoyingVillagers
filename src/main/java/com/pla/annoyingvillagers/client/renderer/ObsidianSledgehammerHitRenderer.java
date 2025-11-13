package com.pla.annoyingvillagers.client.renderer;
import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelObsidianSledgehammerHit;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerHitEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ObsidianSledgehammerHitRenderer extends EntityRenderer<ObsidianSledgehammerHitEntity> {
    private static final ResourceLocation SHADOW_OBSIDIAN_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/obsidian_sledgehammer.png");
    private final ModelObsidianSledgehammerHit model;
    private final Random random = new Random();

    public ObsidianSledgehammerHitRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        ModelPart root = renderManagerIn.bakeLayer(ModelObsidianSledgehammerHit.LAYER_LOCATION);
        this.model = new ModelObsidianSledgehammerHit<>(root);
    }

    public void render(ObsidianSledgehammerHitEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F - entityIn.getYRot()));
        matrixStackIn.translate(0.0D, 3F, 0.0D);
        matrixStackIn.scale(-2.0F, -2.0F, 2.0F);
        VertexConsumer vertexConsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(SHADOW_OBSIDIAN_TEXTURE));
        model.setupAnim(entityIn, 0, 0, entityIn.tickCount + partialTicks, 0, 0);
        this.model.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public Vec3 getRenderOffset(ObsidianSledgehammerHitEntity entityIn, float partialTicks) {
        if (entityIn.activateProgress == 10) {
            return super.getRenderOffset(entityIn, partialTicks);
        } else {
            double d0 = 0.02D;
            return new Vec3(this.random.nextGaussian() * d0, 0.0D, this.random.nextGaussian() * d0);
        }
    }

    protected int getBlockLightLevel(ObsidianSledgehammerHitEntity entityIn, BlockPos pos) {
        return 15;
    }
    public ResourceLocation getTextureLocation(ObsidianSledgehammerHitEntity entity) {
        return SHADOW_OBSIDIAN_TEXTURE;
    }
}
