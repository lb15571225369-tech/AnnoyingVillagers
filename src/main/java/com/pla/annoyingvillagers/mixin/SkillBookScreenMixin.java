package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.capabilities.AVCategories;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.gui.screen.SkillBookScreen;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Map;

@Mixin(value = {SkillBookScreen.class}, remap = false)
public abstract class SkillBookScreenMixin {
    @Shadow
    @Final
    @Mutable
    private static Map<WeaponCategory, ItemStack> WEAPON_CATEGORY_ICONS;

    @Inject(method = {"registerIconItems"}, at = {@At("HEAD")})
    private static void addCustomGuardScreen(CallbackInfo ci) {
        WEAPON_CATEGORY_ICONS.put(AVCategories.LEGENDARY_SWORD, new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()));
        WEAPON_CATEGORY_ICONS.put(AVCategories.HARD_GREAT_SWORD, new ItemStack(AnnoyingVillagersModItems.HARD_GREAT_SWORD.get()));
    }
}