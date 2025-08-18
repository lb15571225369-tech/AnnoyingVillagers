package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.entity.SwordsManHerobrineEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SwordsmanHerobrineRenderer extends HumanoidMobRenderer<SwordsManHerobrineEntity, HumanoidModel<SwordsManHerobrineEntity>> {

    public SwordsmanHerobrineRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new EyesLayer<SwordsManHerobrineEntity, HumanoidModel<SwordsManHerobrineEntity>>(this) {
            public RenderType renderType() {
                return RenderType.eyes(new ResourceLocation("annoyingvillagers:textures/entities/elite_herobrine_eye.png"));
            }
        });
    }

    public ResourceLocation getTextureLocation(SwordsManHerobrineEntity swordsManHerobrineEntity) {
        return new ResourceLocation("annoyingvillagers:textures/entities/elite_herobrine.png");
    }
}
