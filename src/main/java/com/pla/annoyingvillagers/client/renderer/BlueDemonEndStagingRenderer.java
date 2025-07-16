package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.BlueDemonEndStagingEntity;
import com.pla.annoyingvillagers.procedures.BlueDemonStagingCombatConditionProcedure;

public class BlueDemonEndStagingRenderer extends HumanoidMobRenderer<BlueDemonEndStagingEntity, HumanoidModel<BlueDemonEndStagingEntity>> {

    public BlueDemonEndStagingRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.0F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public ResourceLocation getTextureLocation(BlueDemonEndStagingEntity bluedemonendentity) {
        return new ResourceLocation(AnnoyingVillagers.MODID + ":textures/entities/blue_demond_end_staging.png");
    }

    protected boolean isShaking(BlueDemonEndStagingEntity bluedemonendentity) {
        Level level = bluedemonendentity.level;
        double d0 = bluedemonendentity.getX();
        double d1 = bluedemonendentity.getY();
        double d2 = bluedemonendentity.getZ();

        return BlueDemonStagingCombatConditionProcedure.execute(bluedemonendentity);
    }
}
