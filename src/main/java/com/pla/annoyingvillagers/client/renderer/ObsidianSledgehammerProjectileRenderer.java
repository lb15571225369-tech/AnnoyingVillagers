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

package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelDragonMeteorite;
import com.pla.annoyingvillagers.entity.ObsidianSledgehammerProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ObsidianSledgehammerProjectileRenderer extends MobRenderer<ObsidianSledgehammerProjectileEntity, ModelDragonMeteorite<ObsidianSledgehammerProjectileEntity>> {

    public ObsidianSledgehammerProjectileRenderer(Context context) {
        super(context, new ModelDragonMeteorite<>(context.bakeLayer(ModelDragonMeteorite.LAYER_LOCATION)), 0.0F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_meteorite.png");
    }

    protected boolean isShaking(@NotNull ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity) {
        return true;
    }
}
