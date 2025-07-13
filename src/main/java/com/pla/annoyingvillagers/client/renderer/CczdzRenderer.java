package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.entity.CczdzEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class CczdzRenderer extends HumanoidMobRenderer<CczdzEntity, HumanoidModel<CczdzEntity>> {

    public CczdzRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public ResourceLocation getTextureLocation(CczdzEntity cczdzentity) {
        return new ResourceLocation("annoyingvillagers:textures/entities/cun_zhen_dui_chang_.png");
    }
}

