/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * This file is part of AnnoyingVillagers.
 * Contains code adapted from Dragon Mounts: Legacy.
 *
 * Copyright (C) 2026 pla_is_me
 * Original authors: Nico Bergemann (BarracudaATA), Kay9, and contributors.
 *
 * Licensed under the GNU General Public License v3.0 or later.
 * See the LICENSE file in the project root for details.
 */


package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class DragonBodyController extends BodyRotationControl
{
    private final HerobrineDragonEntity dragon;

    public DragonBodyController(HerobrineDragonEntity dragon)
    {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void clientTick()
    {
        // sync the body to the yRot; no reason to have any other random rotations.
        dragon.yBodyRot = dragon.getYRot();

        // clamp head rotations so necks don't fucking turn inside out
        dragon.yHeadRot = Mth.rotateIfNecessary(dragon.yHeadRot, dragon.yBodyRot, dragon.getMaxHeadYRot());
    }
}