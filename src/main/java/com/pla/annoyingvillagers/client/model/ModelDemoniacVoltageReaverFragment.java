package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelDemoniacVoltageReaverFragment<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("annoyingvillagers", "modeldemoniacvoltagereaverfragment"), "main");
    private final ModelPart bb_main;

    public ModelDemoniacVoltageReaverFragment(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(6, 14).addBox(-0.5F, -4.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-0.6F, -8.0F, 0.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 4).addBox(-0.7F, -9.0F, 0.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-0.7F, -10.0F, 0.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-0.8F, -11.0F, 0.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 12).addBox(-0.7F, -3.0F, -2.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-0.6F, -5.0F, -2.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.6F, -6.0F, -1.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-0.6F, -6.0F, -2.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-0.7F, -6.0F, -3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-0.8F, -6.0F, -4.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-0.8F, -6.0F, -5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(-0.7F, -7.0F, -1.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.6F, -7.0F, -2.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-0.8F, -7.0F, -3.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-0.8F, -7.0F, -4.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-0.8F, -7.0F, -5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 10).addBox(-0.7F, -7.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-0.7F, -8.0F, -1.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-0.8F, -8.0F, -2.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-0.8F, -8.0F, -3.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 4).addBox(-0.6F, -8.0F, -4.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -8.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -8.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-0.8F, -9.0F, -1.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.8F, -9.0F, -2.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-0.7F, -9.0F, -3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-0.5F, -9.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -10.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -10.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -10.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -11.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 12).addBox(-0.7F, -3.0F, 1.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -3.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -3.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 8).addBox(-0.7F, -4.0F, 1.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.8F, -4.0F, 2.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -4.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-0.5F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.8F, -5.0F, 2.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -5.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -6.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.8F, -6.0F, 3.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -6.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -6.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-0.5F, -7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -7.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -7.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 6).addBox(-0.6F, -7.0F, 5.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -7.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-0.6F, -8.0F, 1.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -8.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.5F, -8.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-0.7F, -9.0F, 1.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-0.5F, -9.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-0.6F, -9.0F, 3.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-0.6F, -10.0F, 1.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 2).addBox(-0.6F, -10.0F, 2.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-0.8F, -11.0F, 1.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 26).addBox(-0.7F, -3.0F, -3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-0.7F, -3.0F, -5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 25).addBox(-0.7F, -2.0F, -4.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 23).addBox(-0.7F, -3.0F, -4.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 24).addBox(-0.7F, -3.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 27).addBox(-0.7F, -2.0F, -3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 24).addBox(-0.7F, -4.0F, -4.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 27).addBox(-0.7F, -4.0F, -5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 25).addBox(-0.7F, -2.0F, -5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 19).addBox(-0.7F, -1.0F, -4.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
