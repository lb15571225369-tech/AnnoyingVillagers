package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class DarkHerobrineRenderer extends HumanoidMobRenderer<ShadowHerobrineEntity, HumanoidModel<ShadowHerobrineEntity>> {

    public DarkHerobrineRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(ShadowHerobrineEntity darkHerobrineEntity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }

    protected boolean isShaking(ShadowHerobrineEntity darkHerobrineEntity) {
        return HerobrineLowProcedure.execute(darkHerobrineEntity);
    }
}
