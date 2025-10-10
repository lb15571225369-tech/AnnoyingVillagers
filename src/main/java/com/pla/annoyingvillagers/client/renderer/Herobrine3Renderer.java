package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.Herobrine3Entity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Herobrine3Renderer extends HumanoidMobRenderer<Herobrine3Entity, HumanoidModel<Herobrine3Entity>> {

    public Herobrine3Renderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(Herobrine3Entity herobrineentity) {
        if (herobrineentity.getHealth() <= 15) {
            return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/infected_chris.png");
        } else {
            return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/herobrine.png");
        }
    }

    protected boolean isShaking(Herobrine3Entity herobrineentity) {
        return HerobrineLowProcedure.execute(herobrineentity);
    }
}
