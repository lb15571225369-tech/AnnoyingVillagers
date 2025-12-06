/*
 * Colored enchantment rendering adapted from Quark.
 *
 * Original work: Quark by Vazkii and contributors
 * https://github.com/VazkiiMods/Quark
 *
 * Quark is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License (CC BY-NC-SA 3.0):
 * https://creativecommons.org/licenses/by-nc-sa/3.0/
 *
 * This file contains a modified version of Quark's colored enchantment code,
 * adapted for use in the <your-mod-id> mod. In accordance with the original
 * license, this derivative work is also licensed under CC BY-NC-SA 3.0.
 *
 * Please give proper credit to Quark / Vazkii if you reuse this code.
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class HerobrineEnderEyeGlintRenderTypes extends RenderType {
    private HerobrineEnderEyeGlintRenderTypes(String name, VertexFormat vf, VertexFormat.Mode mode,
                                              int bufSize, boolean affectsCrumbling, boolean sortOnUpload,
                                              Runnable setup, Runnable clean) {
        super(name, vf, mode, bufSize, affectsCrumbling, sortOnUpload, setup, clean);
        throw new UnsupportedOperationException("Don't instantiate");
    }

    private static final ResourceLocation ORANGE_TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/glint/enchanted_item_glint_orange.png");

    public static final RenderType GLINT_DIRECT_ORANGE = RenderType.create(
            "glint_direct_orange",
            DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_GLINT_DIRECT_SHADER)
                    .setTextureState(new TextureStateShard(ORANGE_TEX, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static final RenderType ENTITY_GLINT_DIRECT_ORANGE = RenderType.create(
            "entity_glint_direct_orange",
            DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                    .setTextureState(new TextureStateShard(ORANGE_TEX, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static final RenderType GLINT_ORANGE = RenderType.create(
            "glint_orange",
            DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER)
                    .setTextureState(new TextureStateShard(ORANGE_TEX, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static final RenderType ENTITY_GLINT_ORANGE = RenderType.create(
            "entity_glint_orange",
            DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_SHADER)
                    .setTextureState(new TextureStateShard(ORANGE_TEX, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static void registerIntoFixed(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> map) {
        put(map, GLINT_DIRECT_ORANGE);
        put(map, ENTITY_GLINT_DIRECT_ORANGE);
        put(map, GLINT_ORANGE);
        put(map, ENTITY_GLINT_ORANGE);
    }

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> map, RenderType rt) {
        if (!map.containsKey(rt)) map.put(rt, new BufferBuilder(rt.bufferSize()));
    }
}
