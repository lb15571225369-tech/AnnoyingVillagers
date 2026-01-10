package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerHitEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ModelObsidianSledgehammerHit<T extends ObsidianSledgehammerHitEntity> extends HierarchicalModel<ObsidianSledgehammerHitEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelobsidiansledgehammerhit"), "main");
    private final ModelPart root;
    private final ModelPart bone;

    public ModelObsidianSledgehammerHit(ModelPart root) {
        this.root = root;
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, 18.0F, 5.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, 4.0F, -8.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -9.0F, 4.0F, -0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 40, 32);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(@NotNull ObsidianSledgehammerHitEntity obsidianSledgehammerHitEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);

        float frameTime = Minecraft.getInstance().getFrameTime();
        float progress = obsidianSledgehammerHitEntity.prevActivateProgress + (obsidianSledgehammerHitEntity.activateProgress - obsidianSledgehammerHitEntity.prevActivateProgress) * frameTime;
        float yOff = 17.0F * (1.0F - Mth.clamp(progress / 10.0F, 0F, 1.0F));

        this.root.setPos(this.root.x, this.root.y + yOff, this.root.z);
    }
}