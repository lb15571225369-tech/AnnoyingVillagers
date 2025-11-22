package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.BabyEnderDragonAnimations;
import com.pla.annoyingvillagers.entity.BabyEnderDragonEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ModelBabyEnderDragon<T extends BabyEnderDragonEntity> extends HierarchicalModel<BabyEnderDragonEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelbabyenderdragon"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart neck_1;
    private final ModelPart neck_2;
    private final ModelPart neck_3;
    private final ModelPart horn;
    private final ModelPart mouth_bottom;
    private final ModelPart mouth_top;
    private final ModelPart tail_1;
    private final ModelPart tail_2;
    private final ModelPart tail_3;
    private final ModelPart tail_4;
    private final ModelPart tail_5;
    private final ModelPart tail_6;
    private final ModelPart tail_7;
    private final ModelPart tail_8;
    private final ModelPart tail_9;
    private final ModelPart leg_bone_back;
    private final ModelPart leg_back;
    private final ModelPart foot_back;
    private final ModelPart leg_bone_front;
    private final ModelPart leg_front;
    private final ModelPart foot_front;
    private final ModelPart wing_in_left;
    private final ModelPart wing_out_left;
    private final ModelPart wing_in_right;
    private final ModelPart wing_out_right;

    public ModelBabyEnderDragon(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.neck_1 = root.getChild("neck_1");
        this.neck_2 = root.getChild("neck_2");
        this.neck_3 = root.getChild("neck_3");
        this.horn = root.getChild("horn");
        this.mouth_bottom = root.getChild("mouth_bottom");
        this.mouth_top = root.getChild("mouth_top");
        this.tail_1 = root.getChild("tail_1");
        this.tail_2 = root.getChild("tail_2");
        this.tail_3 = root.getChild("tail_3");
        this.tail_4 = root.getChild("tail_4");
        this.tail_5 = root.getChild("tail_5");
        this.tail_6 = root.getChild("tail_6");
        this.tail_7 = root.getChild("tail_7");
        this.tail_8 = root.getChild("tail_8");
        this.tail_9 = root.getChild("tail_9");
        this.leg_bone_back = root.getChild("leg_bone_back");
        this.leg_back = root.getChild("leg_back");
        this.foot_back = root.getChild("foot_back");
        this.leg_bone_front = root.getChild("leg_bone_front");
        this.leg_front = root.getChild("leg_front");
        this.foot_front = root.getChild("foot_front");
        this.wing_in_left = root.getChild("wing_in_left");
        this.wing_out_left = root.getChild("wing_out_left");
        this.wing_in_right = root.getChild("wing_in_right");
        this.wing_out_right = root.getChild("wing_out_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -4.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-1.0F, -3.0F, -4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 44).addBox(-1.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-1.0F, -3.0F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.5F, 15.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 18).addBox(-2.0F, -12.0F, -14.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
                .texOffs(18, 2).addBox(-1.0F, -12.0F, -14.26F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 22.0F, 1.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition neck_1 = partdefinition.addOrReplaceChild("neck_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -10.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.08F))
                .texOffs(0, 44).addBox(-0.5F, -11.0F, -10.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition neck_2 = partdefinition.addOrReplaceChild("neck_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.09F))
                .texOffs(0, 44).addBox(-0.5F, -11.0F, -8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition neck_3 = partdefinition.addOrReplaceChild("neck_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(0, 44).addBox(-0.5F, -11.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition horn = partdefinition.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(0, 45).addBox(1.0F, -12.0F, -12.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-2.0F, -12.0F, -12.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 1.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition mouth_bottom = partdefinition.addOrReplaceChild("mouth_bottom", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, -10.0F, -16.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 22.0F, 1.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition mouth_top = partdefinition.addOrReplaceChild("mouth_top", CubeListBuilder.create().texOffs(3, 5).addBox(1.0F, -10.25F, -16.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 5).addBox(-1.0F, -10.25F, -16.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-1.5F, -10.0F, -16.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 22.0F, 1.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition tail_1 = partdefinition.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(0, 44).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 16.0F, 5.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition tail_2 = partdefinition.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.08F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 16.0F, 5.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition tail_3 = partdefinition.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.06F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 17.0F, 9.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition tail_4 = partdefinition.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.04F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 16.0F, 7.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition tail_5 = partdefinition.addOrReplaceChild("tail_5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 18.0F, 12.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail_6 = partdefinition.addOrReplaceChild("tail_6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 17.0F, 10.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition tail_7 = partdefinition.addOrReplaceChild("tail_7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.02F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 19.0F, 13.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition tail_8 = partdefinition.addOrReplaceChild("tail_8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.04F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 21.0F, 16.0F, -0.7418F, 0.0F, 0.0F));

        PartDefinition tail_9 = partdefinition.addOrReplaceChild("tail_9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.06F))
                .texOffs(0, 44).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 22.0F, 17.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition leg_bone_back = partdefinition.addOrReplaceChild("leg_bone_back", CubeListBuilder.create().texOffs(7, 7).addBox(1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(7, 7).addBox(-2.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 16.0F, 2.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition leg_back = partdefinition.addOrReplaceChild("leg_back", CubeListBuilder.create().texOffs(7, 7).addBox(0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(7, 7).addBox(-2.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 18.0F, 4.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition foot_back = partdefinition.addOrReplaceChild("foot_back", CubeListBuilder.create().texOffs(6, 6).addBox(0.5F, 3.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
                .texOffs(6, 6).addBox(-2.5F, 3.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 18.0F, 4.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition leg_bone_front = partdefinition.addOrReplaceChild("leg_bone_front", CubeListBuilder.create().texOffs(7, 7).addBox(0.5F, -0.1749F, -1.5195F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(7, 7).addBox(-1.5F, -0.1749F, -1.5195F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.5F, 16.0F, -2.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg_front = partdefinition.addOrReplaceChild("leg_front", CubeListBuilder.create().texOffs(7, 7).addBox(0.0F, 0.0F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F))
                .texOffs(7, 7).addBox(-2.0F, 0.0F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 16.8251F, -1.5195F, -0.9163F, 0.0F, 0.0F));

        PartDefinition foot_front = partdefinition.addOrReplaceChild("foot_front", CubeListBuilder.create().texOffs(7, 7).addBox(-2.0F, 2.2F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(7, 7).addBox(0.0F, 2.2F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 16.8251F, -2.5195F, -0.9163F, 0.0F, 0.0F));

        PartDefinition wing_in_left = partdefinition.addOrReplaceChild("wing_in_left", CubeListBuilder.create().texOffs(-8, 48).addBox(0.0F, -0.5F, 0.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(0.0F, -1.0F, -1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.5F, 13.0F, -3.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition wing_out_left = partdefinition.addOrReplaceChild("wing_out_left", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, 0.0F, -1.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(-8, 56).addBox(0.0F, 0.5F, 0.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 12.0F, -3.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition wing_in_right = partdefinition.addOrReplaceChild("wing_in_right", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(-8, 48).mirror().addBox(-8.0F, -0.5F, 0.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 13.0F, -3.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition wing_out_right = partdefinition.addOrReplaceChild("wing_out_right", CubeListBuilder.create().texOffs(0, 46).addBox(-12.0F, 0.0F, -1.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(-8, 56).mirror().addBox(-12.0F, 0.5F, 0.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, 12.0F, -3.0F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck_3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        horn.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mouth_bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mouth_top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail_9.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_bone_back.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_back.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        foot_back.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_bone_front.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_front.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        foot_front.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_in_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_out_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_in_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wing_out_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(BabyEnderDragonEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);
        if (pEntity.shootAnimationState.isStarted()) {
            animate(pEntity.shootAnimationState, BabyEnderDragonAnimations.BABY_ENDER_DRAGON_SHOOT, pAgeInTicks);
        } else if (pEntity.summonAnimationState.isStarted()) {
            animate(pEntity.summonAnimationState, BabyEnderDragonAnimations.BABY_ENDER_DRAGON_SUMMON, pAgeInTicks);
        } else {
            animate(pEntity.idleAnimationState, BabyEnderDragonAnimations.BABY_ENDER_DRAGON_IDLE, pAgeInTicks);
        }
    }
}