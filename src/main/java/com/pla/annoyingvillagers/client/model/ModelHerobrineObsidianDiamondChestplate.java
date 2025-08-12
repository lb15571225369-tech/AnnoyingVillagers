package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ModelHerobrineObsidianDiamondChestplate<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("annoyingvillagers", "modelherobrineobsidiandiamondchestplate"), "main");
    public final ModelPart Body;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;

    public ModelHerobrineObsidianDiamondChestplate(ModelPart modelpart) {
        this.Body = modelpart.getChild("Body");
        this.RightArm = modelpart.getChild("RightArm");
        this.LeftArm = modelpart.getChild("LeftArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)).texOffs(54, 7).addBox(-8.2385F, 1.609F, 1.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 4).addBox(-1.909F, -7.2615F, 16.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, -1.4835F, 0.0873F, 0.0F));
        partdefinition1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(51, 4).addBox(-3.909F, -8.2615F, -20.7F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 1.6552F, 0.3049F, -0.0119F));
        partdefinition1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(56, 2).addBox(-1.909F, -6.2615F, -18.7F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 1.4835F, 0.1745F, 0.0F));
        partdefinition1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(51, 4).addBox(5.4F, 19.2F, 4.1F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 0.0F, -1.9199F, 0.0F));
        partdefinition1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(51, 4).addBox(11.4F, 18.2F, 9.1F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 0.0F, -1.5708F, 0.0F));
        partdefinition1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(52, 1).addBox(-4.2385F, 19.609F, 3.8F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 0.0F, -0.0873F, 0.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).texOffs(54, 7).addBox(-3.2385F, -0.391F, 1.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        partdefinition2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(51, 4).addBox(-12.6F, 16.2F, -18.9F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -17.0F, -10.0F, 2.1639F, 1.4137F, 2.1562F));
        partdefinition2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(51, 4).addBox(-9.909F, -12.2615F, -16.7F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -17.0F, -2.0F, 1.7301F, 0.3982F, -0.0424F));
        partdefinition2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(49, 10).addBox(4.4F, 14.2F, 15.1F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -17.0F, -10.0F, -0.81F, -1.2543F, 0.9694F));
        partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void renderToBuffer(PoseStack posestack, VertexConsumer vertexconsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.Body.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.RightArm.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.LeftArm.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(T t0, float f, float f1, float f2, float f3, float f4) {
        this.RightArm.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * f1;
        this.LeftArm.xRot = Mth.cos(f * 0.6662F) * f1;
    }
}
