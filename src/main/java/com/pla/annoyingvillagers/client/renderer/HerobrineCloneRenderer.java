package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.entity.HerobrineCloneEntity;
import com.pla.annoyingvillagers.procedures.HerobrineLowProcedure;

public class HerobrineCloneRenderer extends HumanoidMobRenderer<HerobrineCloneEntity, HumanoidModel<HerobrineCloneEntity>> {

    public HerobrineCloneRenderer(Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(HerobrineCloneEntity herobrineentity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine.png");
    }

    protected boolean isShaking(HerobrineCloneEntity herobrineentity) {
        Level level = herobrineentity.level();
        double d0 = herobrineentity.getX();
        double d1 = herobrineentity.getY();
        double d2 = herobrineentity.getZ();

        return HerobrineLowProcedure.execute(herobrineentity);
    }
}
