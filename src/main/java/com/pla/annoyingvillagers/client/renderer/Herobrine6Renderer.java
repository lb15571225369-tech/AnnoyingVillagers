package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.Herobrine6Entity;
import com.pla.annoyingvillagers.entity.Herobrine7Entity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Herobrine6Renderer extends HumanoidMobRenderer<Herobrine6Entity, HumanoidModel<Herobrine6Entity>> {

    public Herobrine6Renderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(Herobrine6Entity herobrineentity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/herobrine_2.png");
    }

    protected boolean isShaking(Herobrine6Entity herobrineentity) {
        return HerobrineLowProcedure.execute(herobrineentity);
    }
}
