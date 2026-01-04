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

import net.minecraft.util.Mth;

import java.util.Arrays;

/**
 * Very simple fixed size circular buffer implementation for animation purposes.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class CircularBuffer
{
    private final float[] buffer;
    private int index = 0;

    public CircularBuffer(int size)
    {
        buffer = new float[size];
    }

    public void fill(float value)
    {
        Arrays.fill(buffer, value);
    }

    public void update(float value)
    {
        // move forward
        index++;

        // restart pointer at the end to form a virtual ring
        index %= buffer.length;

        buffer[index] = value;
    }

    public float get(float x, int offset)
    {
        int i = index - offset;
        int len = buffer.length - 1;
        return Mth.clampedLerp(buffer[i - 1 & len], buffer[i & len], x);
    }

    public float get(float x, int offset1, int offset2)
    {
        return get(x, offset2) - get(x, offset1);
    }
}