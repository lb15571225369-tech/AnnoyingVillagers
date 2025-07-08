package com.pla.annoyingvillagers.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.Herobrine2Entity;
import com.pla.annoyingvillagers.procedures.HerobrinelowProcedure;

public class Herobrine2Renderer extends HumanoidMobRenderer<Herobrine2Entity, HumanoidModel<Herobrine2Entity>> {

    public Herobrine2Renderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public ResourceLocation getTextureLocation(Herobrine2Entity herobrine2entity) {
        return new ResourceLocation("annoying_villagers:textures/entities/herobrine_2.png");
    }

    protected boolean isShaking(Herobrine2Entity herobrine2entity) {
        Level level = herobrine2entity.level;
        double d0 = herobrine2entity.getX();
        double d1 = herobrine2entity.getY();
        double d2 = herobrine2entity.getZ();

        return HerobrinelowProcedure.execute(herobrine2entity);
    }
}
