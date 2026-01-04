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

package com.pla.annoyingvillagers.accessors;

public interface ModelPartAccess
{
    float getXScale();

    float getYScale();

    float getZScale();

    void setXScale(float x);

    void setYScale(float y);

    void setZScale(float z);

    default void setRenderScale(float x, float y, float z)
    {
        setXScale(x);
        setYScale(y);
        setZScale(z);
    }
}