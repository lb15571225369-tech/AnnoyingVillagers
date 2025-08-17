package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EnchantGlintLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public EnchantGlintLayerRenderer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffers, int packedLight,
                   T entity, float limbSwing, float limbSwingAmount,
                   float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ResourceLocation skin = this.getTextureLocation(entity);
        RenderType base = RenderType.entityCutoutNoCullZOffset(skin);

        VertexConsumer foil = ItemRenderer.getFoilBuffer(
                buffers,
                base,
                /* glintDirect */ false,
                /* foil */ true
        );

        this.getParentModel().renderToBuffer(pose, foil, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}

