package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.entity.GlaiveHerobrineEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class GlaiveHerobrineRenderer extends HumanoidMobRenderer<GlaiveHerobrineEntity, HumanoidModel<GlaiveHerobrineEntity>> {

    public GlaiveHerobrineRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(GlaiveHerobrineEntity glaiveHerobrineEntity) {
        return new ResourceLocation("annoyingvillagers:textures/entities/elite_herobrine.png");
    }
}
