/*
 * This file includes code derived from the "Sprite Arrows" mod.
 *
 * Original work Copyright (c) 2023 iliandev
 * Modified work Copyright (c) 2025 pla_is_me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.client.renderer.SpriteArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.Map;

public class SpriteArrowsCommonEntrypoint {
    public static void replace() {
        Map<EntityType<?>, EntityRendererProvider<?>> providers = EntityRenderers.PROVIDERS;
        replaceForArrow(providers, EntityType.ARROW);
        replaceForArrow(providers, EntityType.SPECTRAL_ARROW);
    }

    @SuppressWarnings({"rawtypes"})
    private static <T extends AbstractArrow> void replaceForArrow(
            Map<EntityType<?>, EntityRendererProvider<?>> providers,
            EntityType<T> type) {
        if (!providers.containsKey(type)) {
            return;
        }
        providers.put(type, (EntityRendererProvider) SpriteArrowRenderer::new);
    }
}
