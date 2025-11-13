package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelHerobrineObsidianDiamondHelmet<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelherobrineobsidiandiamondhelmet"), "main");
    public final ModelPart Head;

    public ModelHerobrineObsidianDiamondHelmet(ModelPart modelpart) {
        this.Head = modelpart.getChild("Head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)).texOffs(32, 0).addBox(4.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(51, 4).addBox(-3.2385F, -3.391F, 3.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 4).addBox(1.091F, 1.7385F, 0.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 0.0F, -0.6545F, 0.3927F));
        partdefinition1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(51, 4).addBox(-3.6F, 13.2F, -9.9F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 1.0472F, -0.6545F, 0.7854F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void renderToBuffer(PoseStack posestack, VertexConsumer vertexconsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.Head.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(T t0, float f, float f1, float f2, float f3, float f4) {
        this.Head.yRot = f3 / 57.295776F;
        this.Head.xRot = f4 / 57.295776F;
    }
}
