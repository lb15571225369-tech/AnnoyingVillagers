package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.ShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;

public class ShadowHerobrineCloneRenderer extends HumanoidMobRenderer<ShadowHerobrineCloneEntity, HumanoidModel<ShadowHerobrineCloneEntity>> {

    public ShadowHerobrineCloneRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(ShadowHerobrineCloneEntity shadowHerobrineCloneEntity) {
        return new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }

    protected boolean isShaking(ShadowHerobrineCloneEntity shadowHerobrineCloneEntity) {
        Level level = shadowHerobrineCloneEntity.level();
        double d0 = shadowHerobrineCloneEntity.getX();
        double d1 = shadowHerobrineCloneEntity.getY();
        double d2 = shadowHerobrineCloneEntity.getZ();

        return HerobrineLowProcedure.execute(shadowHerobrineCloneEntity);
    }
}
