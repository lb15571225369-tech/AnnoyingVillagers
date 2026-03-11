package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.skill.TridentFestivalSkill;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@Mixin(value = WeaponInnateSkill.class, remap = false)
public class WeaponInnateSkillMixin {
    @Redirect(
            method = "drawOnGui",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/skill/Skill;getSkillTexture()Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    private ResourceLocation dynamicSkillTextureForTridentFestival(Skill skill, BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        if (skill instanceof TridentFestivalSkill) {
            boolean ranged = container.getDataManager().getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE.get());

            return ranged
                    ? ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/gui/skills/weapon_innate/blue_demon_trident_ranged.png")
                    : ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/gui/skills/weapon_innate/blue_demon_trident_melee.png");
        }

        return skill.getSkillTexture();
    }
}
