package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.entity.InfectedTheMostMoistBurrit0Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class InfectedTheMostMoistBurrit0Renderer extends HumanoidMobRenderer<InfectedTheMostMoistBurrit0Entity, HumanoidModel<InfectedTheMostMoistBurrit0Entity>> {

    public InfectedTheMostMoistBurrit0Renderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(InfectedTheMostMoistBurrit0Entity infectedTheMostMoistBurrit0Entity) {
        return new ResourceLocation("annoyingvillagers:textures/entities/infected_themostmoistburrit0.png");
    }
}
