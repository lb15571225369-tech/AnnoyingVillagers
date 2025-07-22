package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.BlueDemonStagingEntity;
import com.pla.annoyingvillagers.procedures.BlueDemonStagingCombatConditionProcedure;

public class BlueDemonStagingRenderer extends HumanoidMobRenderer<BlueDemonStagingEntity, HumanoidModel<BlueDemonStagingEntity>> {

    public BlueDemonStagingRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.0F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public ResourceLocation getTextureLocation(BlueDemonStagingEntity bluedemonrentity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/blue_demon.png");
    }

    protected boolean isShaking(BlueDemonStagingEntity bluedemonrentity) {
        Level level = bluedemonrentity.level;
        double d0 = bluedemonrentity.getX();
        double d1 = bluedemonrentity.getY();
        double d2 = bluedemonrentity.getZ();

        return BlueDemonStagingCombatConditionProcedure.execute(bluedemonrentity);
    }
}
