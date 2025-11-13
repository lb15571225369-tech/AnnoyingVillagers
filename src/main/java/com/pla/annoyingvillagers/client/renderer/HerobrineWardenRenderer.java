package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelHerobrineWarden;
import com.pla.annoyingvillagers.entity.HerobrineWardenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HerobrineWardenRenderer extends MobRenderer<HerobrineWardenEntity, ModelHerobrineWarden> {
    private static final ResourceLocation BASE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_warden.png");
    private static final ResourceLocation BIOLUM =
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/warden/warden_bioluminescent_layer.png");
    private static final ResourceLocation HEART =
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/warden/warden_heart.png");
    private static final ResourceLocation SPOTS1 =
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/warden/warden_pulsating_spots_1.png");
    private static final ResourceLocation SPOTS2 =
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/entity/warden/warden_pulsating_spots_2.png");

    public HerobrineWardenRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelHerobrineWarden(ctx.bakeLayer(ModelHerobrineWarden.LAYER_LOCATION)), 0.9F);
        this.addLayer(new WardenEmissiveLayer<>(
                this, BIOLUM,
                (e, partial, age) -> 1.0F,
                ModelHerobrineWarden::getBioluminescentLayerModelParts
        ));
        this.addLayer(new WardenEmissiveLayer<>(
                this, SPOTS1,
                (e, partial, age) -> Math.max(0.0F, Mth.cos(age * 0.045F) * 0.25F),
                ModelHerobrineWarden::getPulsatingSpotsLayerModelParts
        ));
        this.addLayer(new WardenEmissiveLayer<>(
                this, SPOTS2,
                (e, partial, age) -> Math.max(0.0F, Mth.cos(age * 0.045F + (float)Math.PI) * 0.25F),
                ModelHerobrineWarden::getPulsatingSpotsLayerModelParts
        ));
        this.addLayer(new WardenEmissiveLayer<>(
                this, BASE,
                (e, partial, age) -> e.getTendrilAnimation(partial),
                ModelHerobrineWarden::getTendrilsLayerModelParts
        ));
        this.addLayer(new WardenEmissiveLayer<>(
                this, HEART,
                (e, partial, age) -> e.getHeartAnimation(partial),
                ModelHerobrineWarden::getHeartLayerModelParts
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(HerobrineWardenEntity e) {
        return BASE;
    }
}