package com.pla.annoyingvillagers.compat.efdg.world.capabilities.item;

import java.util.function.Function;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVCollider;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

@EventBusSubscriber(modid = "efdg", bus = Bus.MOD)
public class EFWeaponCapabilityPresets {

    public static final Function<Item, Builder> GREATSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD && ((PlayerPatch) livingentitypatch).getSkill(AVSkill.DUALGREATSWORD) != null && ((PlayerPatch) livingentitypatch).getSkill(AVSkill.DUALGREATSWORD).getSkill().getRegistryName().getPath() == "dualgreatsword" ? Styles.OCHS : Styles.TWO_HAND;
        }).collider(AVCollider.GREATSWORD).swingSound(EpicFightSounds.WHOOSH_BIG).hitSound(EpicFightSounds.BLADE_HIT).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD).newStyleCombo(Styles.OCHS, new StaticAnimation[]{AVAnimations.GREATSWORD_DUAL_AUTO_1, AVAnimations.GREATSWORD_DUAL_AUTO_2, AVAnimations.GREATSWORD_DUAL_AUTO_3, AVAnimations.GREATSWORD_DUAL_AUTO_4, AVAnimations.GREATSWORD_DUAL_DASH, AVAnimations.GREATSWORD_DUAL_AIRSLASH}).innateSkill(Styles.OCHS, (itemstack) -> {
            return AVSkill.EARTHQUAKE;
        }).livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, AVAnimations.GREATSWORD_DUAL_IDLE).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, AVAnimations.GREATSWORD_DUAL_WALK).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.GREATSWORD_DUAL_IDLE).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.GREATSWORD_DUAL_RUN).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, AVAnimations.GREATSWORD_DUAL_RUN).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD && ((PlayerPatch) livingentitypatch).getSkill(AVSkill.DUALGREATSWORD) != null && ((PlayerPatch) livingentitypatch).getSkill(AVSkill.DUALGREATSWORD).getSkill().getRegistryName().getPath() == "dualgreatsword" ? true : false;
        });

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put("greatsword", EFWeaponCapabilityPresets.GREATSWORD);
    }
}
