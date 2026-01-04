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


package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;

public class MountCameraManager
{
    private static CameraType previousPerspective = CameraType.FIRST_PERSON;

    public static void onDragonMount()
    {
        previousPerspective = Minecraft.getInstance().options.getCameraType();
        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
    }

    public static void onDragonDismount()
    {
        Minecraft.getInstance().options.setCameraType(previousPerspective);
    }

    @SuppressWarnings("ConstantConditions") // player should never be null at time of calling
    public static void setMountCameraAngles(Camera camera)
    {
        if (Minecraft.getInstance().player.getVehicle() instanceof HerobrineDragonEntity && !Minecraft.getInstance().options.getCameraType().isFirstPerson())
        {
            camera.move(0, 4, 0);
            camera.move(-camera.getMaxZoom(6), 0, 0); // do distance calcs AFTER our new position is set
        }
    }
}
