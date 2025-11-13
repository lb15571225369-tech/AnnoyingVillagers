package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelBabyEnderDragon;
import com.pla.annoyingvillagers.entity.BabyEnderDragonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BabyEnderDragonRenderer extends MobRenderer<BabyEnderDragonEntity, ModelBabyEnderDragon<BabyEnderDragonEntity>> {
    private final EntityRendererProvider.Context context;
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/baby_ender_dragon.png");

    public BabyEnderDragonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ModelBabyEnderDragon<>(pContext.bakeLayer(ModelBabyEnderDragon.LAYER_LOCATION)), 0.0f);
        this.context = pContext;
    }


    @Override
    public ResourceLocation getTextureLocation(BabyEnderDragonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BabyEnderDragonEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}