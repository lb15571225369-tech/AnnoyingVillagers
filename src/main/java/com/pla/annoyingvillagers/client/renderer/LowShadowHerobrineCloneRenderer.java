package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class LowShadowHerobrineCloneRenderer extends HumanoidMobRenderer<LowShadowHerobrineCloneEntity, HumanoidModel<LowShadowHerobrineCloneEntity>> {

    public LowShadowHerobrineCloneRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(LowShadowHerobrineCloneEntity herobrineentity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }

    protected boolean isShaking(LowShadowHerobrineCloneEntity herobrineentity) {
        return HerobrineLowProcedure.execute(herobrineentity);
    }
}
