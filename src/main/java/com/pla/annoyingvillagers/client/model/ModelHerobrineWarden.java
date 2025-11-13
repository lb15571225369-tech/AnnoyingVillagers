package com.pla.annoyingvillagers.client.model;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.HerobrineWardenAnimations;
import com.pla.annoyingvillagers.entity.HerobrineWardenEntity;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ModelHerobrineWarden extends WardenModel<HerobrineWardenEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelherobrinewarden"), "main");

    private final ModelPart left_ribcage_extra;
    private final ModelPart right_ribcage_extra;

    public ModelHerobrineWarden(ModelPart root) {
        super(root);
        ModelPart body = root.getChild("bone").getChild("body");
        this.left_ribcage_extra  = body.getChild("left_ribcage").getChild("left_ribs_extra");
        this.right_ribcage_extra = body.getChild("right_ribcage").getChild("right_ribs_extra");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = bone.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -13.0F, -4.0F, 18.0F, 21.0F, 11.0F),
                PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition right_ribcage = body.addOrReplaceChild("right_ribcage",
                CubeListBuilder.create().texOffs(90, 11).addBox(-2.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F),
                PartPose.offset(-7.0F, -2.0F, -4.0F));

        PartDefinition left_ribcage = body.addOrReplaceChild("left_ribcage",
                CubeListBuilder.create().texOffs(90, 11).mirror().addBox(-7.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F).mirror(false),
                PartPose.offset(7.0F, -2.0F, -4.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F),
                PartPose.offset(0.0F, -13.0F, 0.0F));
        head.addOrReplaceChild("right_tendril",
                CubeListBuilder.create().texOffs(52, 32).addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(-8.0F, -12.0F, 0.0F));
        head.addOrReplaceChild("left_tendril",
                CubeListBuilder.create().texOffs(58, 0).addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(8.0F, -12.0F, 0.0F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F),
                PartPose.offset(-13.0F, -13.0F, 1.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F),
                PartPose.offset(13.0F, -13.0F, 1.0F));

        bone.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F),
                PartPose.offset(-5.9F, -13.0F, 0.0F));
        bone.addOrReplaceChild("left_leg",  CubeListBuilder.create().texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F),
                PartPose.offset(5.9F, -13.0F, 0.0F));

        left_ribcage.addOrReplaceChild("left_ribs_extra", CubeListBuilder.create().texOffs(11, 71).addBox(-7.0F, -15.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -19.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -23.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -27.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.0F, 3.0F, 0.0F, -0.3491F, 0.0F));

        right_ribcage.addOrReplaceChild("right_ribs_extra", CubeListBuilder.create().texOffs(11, 71).addBox(-7.0F, -15.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -19.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -23.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(11, 71).addBox(-7.0F, -27.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 21.0F, 1.0F, 0.0F, 0.3491F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(HerobrineWardenEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        animate(pEntity.idleAnimationState, HerobrineWardenAnimations.HEROBRINE_WARDEN_IDLE, pAgeInTicks);
        animate(pEntity.eatingAnimationState, HerobrineWardenAnimations.HEROBRINE_WARDEN_EATING, pAgeInTicks);
    }
}