package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.NotchEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class NotchRenderer extends HumanoidMobRenderer<NotchEntity, PlayerModel<NotchEntity>> {

    public NotchRenderer(Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        ArrowLayer<NotchEntity, PlayerModel<NotchEntity>> arrowLayer = new ArrowLayer<>(context, this);
        this.addLayer(arrowLayer);
        this.addLayer(new NotchCapeLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NotchEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/notch.png");
    }

    @Override
    protected void setupRotations(@NotNull NotchEntity entity, @NotNull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);
        float bob = Mth.sin(ageInTicks * 0.067F) * 0.15F;
        poseStack.translate(0.0F, bob, 0.0F);
    }
}
