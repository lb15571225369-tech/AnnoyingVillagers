package com.pla.annoyingvillagers.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.BlueDemonEndEntity;
import com.pla.annoyingvillagers.procedures.BlueDemonRShiTiZhanDouTiaoJianProcedure;

public class BlueDemonEndRenderer extends HumanoidMobRenderer<BlueDemonEndEntity, HumanoidModel<BlueDemonEndEntity>> {

    public BlueDemonEndRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.0F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public ResourceLocation getTextureLocation(BlueDemonEndEntity bluedemonendentity) {
        return new ResourceLocation("annoying_villagers:textures/entities/b_bd.png");
    }

    protected boolean isShaking(BlueDemonEndEntity bluedemonendentity) {
        Level level = bluedemonendentity.level;
        double d0 = bluedemonendentity.getX();
        double d1 = bluedemonendentity.getY();
        double d2 = bluedemonendentity.getZ();

        return BlueDemonRShiTiZhanDouTiaoJianProcedure.execute(bluedemonendentity);
    }
}
