/*
 * SPDX-License-Identifier: GPL-3.0-or-later AND Apache-2.0
 *
 * This file is part of AnnoyingVillagers.
 * Contains code adapted from "Skyfall: Meteorites" by Yoshi01111
 * (https://www.curseforge.com/minecraft/mc-mods/skyfall-meteorites)
 *
 * Modifications and integration:
 * Copyright (C) 2026 pla_is_me
 *
 * Original work attribution:
 * "Skyfall: Meteorites" is licensed under the Apache License, Version 2.0.
 * You may obtain a copy of the License at:
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * This combined work is distributed under the GNU General Public License v3.0 or later
 * as part of AnnoyingVillagers, while retaining the required Apache-2.0 notices for
 * the portions derived from Skyfall: Meteorites.
 *
 * See:
 *   - LICENSE (GPL-3.0-or-later) in the project root
 *   - LICENSES/Apache-2.0.txt (Apache-2.0) in the project root
 *   - NOTICE (if you include upstream NOTICE content)
 */

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
import org.jetbrains.annotations.NotNull;

public class ModelDragonMeteorite<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modeldragonmeteorite"), "main");
    public final ModelPart bone;

    public ModelDragonMeteorite(ModelPart modelpart) {
        this.bone = modelpart.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.bone.render(poseStack, vertexConsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(@NotNull T t0, float f, float f1, float f2, float f3, float f4) {
        this.bone.yRot = f2 / 20.0F;
    }
}
