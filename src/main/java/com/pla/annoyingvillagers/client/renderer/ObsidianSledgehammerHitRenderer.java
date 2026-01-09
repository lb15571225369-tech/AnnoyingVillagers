package com.pla.annoyingvillagers.client.renderer;

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
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ObsidianSledgehammerHitRenderer extends EntityRenderer<ObsidianSledgehammerHitEntity> {
    private static final ResourceLocation OBSIDIAN_SLEDGEHAMMER_SPIKE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obsidian_sledgehammer_spike.png");

    private final ModelObsidianSledgehammerHit<ObsidianSledgehammerHitEntity> model;
    private final Random random = new Random();

    public ObsidianSledgehammerHitRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart root = context.bakeLayer(ModelObsidianSledgehammerHit.LAYER_LOCATION);
        this.model = new ModelObsidianSledgehammerHit<>(root);
    }

    @Override
    public void render(@NotNull ObsidianSledgehammerHitEntity entity, float entityYaw, float partialTicks,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
        poseStack.translate(0.0D, 1.5F, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(OBSIDIAN_SLEDGEHAMMER_SPIKE_TEXTURE));
        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(@NotNull ObsidianSledgehammerHitEntity entity, float partialTicks) {
        if (entity.activateProgress == 10.0F) {
            return super.getRenderOffset(entity, partialTicks);
        }
        double d0 = 0.02D;
        return new Vec3(this.random.nextGaussian() * d0, 0.0D, this.random.nextGaussian() * d0);
    }

    @Override
    protected int getBlockLightLevel(@NotNull ObsidianSledgehammerHitEntity entity, @NotNull BlockPos pos) {
        return 15;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ObsidianSledgehammerHitEntity entity) {
        return OBSIDIAN_SLEDGEHAMMER_SPIKE_TEXTURE;
    }
}