package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.Herobrine7Entity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Herobrine7Renderer extends HumanoidMobRenderer<Herobrine7Entity, HumanoidModel<Herobrine7Entity>> {

    public Herobrine7Renderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(Herobrine7Entity herobrineentity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }

    protected boolean isShaking(Herobrine7Entity herobrineentity) {
        return HerobrineLowProcedure.execute(herobrineentity);
    }
}
