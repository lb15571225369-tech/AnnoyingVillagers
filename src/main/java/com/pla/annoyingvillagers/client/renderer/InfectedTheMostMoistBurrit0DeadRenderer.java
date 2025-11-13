package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.InfectedTheMostMoistBurrit0DeadEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class InfectedTheMostMoistBurrit0DeadRenderer extends HumanoidMobRenderer<InfectedTheMostMoistBurrit0DeadEntity, HumanoidModel<InfectedTheMostMoistBurrit0DeadEntity>> {

    public InfectedTheMostMoistBurrit0DeadRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(InfectedTheMostMoistBurrit0DeadEntity infectedTheMostMoistBurrit0Entity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/infected_themostmoistburrit0.png");
    }
}
