package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import com.pla.annoyingvillagers.entity.BlueDemonEndStagingEntity;
import org.jetbrains.annotations.NotNull;

public class BlueDemonEndStagingRenderer extends HumanoidMobRenderer<BlueDemonEndStagingEntity, HumanoidModel<BlueDemonEndStagingEntity>> {

    public BlueDemonEndStagingRenderer(Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull BlueDemonEndStagingEntity blueDemonEndStagingEntity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_demond_end_staging.png");
    }
}
