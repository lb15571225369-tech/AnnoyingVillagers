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

package com.pla.annoyingvillagers.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;

import com.pla.annoyingvillagers.client.renderer.HerobrineEnderEyeGlintRenderTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {

    @Inject(method = "put", at = @At("HEAD"))
    private static void addGlintTypes(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> mapBuildersIn, RenderType renderTypeIn, CallbackInfo callbackInfo) {
        HerobrineEnderEyeGlintRenderTypes.registerIntoFixed(mapBuildersIn);
    }
}