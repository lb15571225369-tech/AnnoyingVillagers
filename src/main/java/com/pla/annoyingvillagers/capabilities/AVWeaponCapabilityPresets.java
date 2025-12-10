package com.pla.annoyingvillagers.capabilities;

import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSounds;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.*;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class AVWeaponCapabilityPresets {

    public static final Function<Item, Builder> ENDER_AEGIS = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.ENDER_AEGIS)
                    .styleProvider((livingEntityPatch) -> Styles.ONE_HAND)
                    .canBePlacedOffhand(false)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            AnimsNapoleon.NAPOLEON_AUTO_1,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2,
                            AnimsSolar.SOLAR_QUEMADURA,
                            AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO,
                            AnimsSolar.SOLAR_HORNO)
                    .innateSkill(Styles.ONE_HAND, (stack) -> AVSkills.ENDER_AEGIS)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .weaponCombinationPredicator((patch) -> true);

    public static final Function<Item, CapabilityItem.Builder> ENDER_GLAIVE = (item) ->
            WeaponCapability.builder().category(AVCategories.ENDER_GLAIVE)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.AGONY)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsNapoleon.NAPOLEON_AUTO_1,
                            AnimsAgony.AGONY_AUTO_4,
                            WOMAnimations.STAFF_AUTO_2,
                            WOMAnimations.STAFF_AUTO_3,
                            AnimsNapoleon.NAPOLEON_AUSTERLITZ,
                            AnimsAgony.AGONY_RIPPING_FANGS)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.ENDER_GLAIVE)
                    .comboCancel((style) -> false)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsNapoleon.NAPOLEON_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsNapoleon.NAPOLEON_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsAgony.AGONY_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> DEMONIAC_VOLTAGE_REAVER = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.DEMONIAC_VOLTAGE_REAVER)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsRuine.RUINE_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_AUTO_4,
                            WOMAnimations.TORMENT_CHARGED_ATTACK_2,
                            AnimsRuine.RUINE_COMET
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.DEMONIAC_VOLTAGE_REAVER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.TORMENT_BERSERK_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> OBSIDIAN_SLEDGEHAMMER = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.OBSIDIAN_SLEDGEHAMMER)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_2,
                            AnimsSolar.SOLAR_OBSCURIDAD_AUTO_4,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_TISHNAW
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.OBSIDIAN_SLEDGEHAMMER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.TORMENT_BERSERK_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, CapabilityItem.Builder> ENDER_SLAYER_SCYTHE = (item) ->
            WeaponCapability.builder().category(AVCategories.ENDER_SLAYER_SCYTHE)
                    .styleProvider((entityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.ANTITHEUS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_2,
                            AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_3,
                            AVAnimations.ENDER_SLAYER_ANTITHEUS_AUTO_4,
                            AnimsNapoleon.NAPOLEON_WATERLOW,
                            AVAnimations.ENDER_SLAYER_ANTITHEUS_AGRESSION,
                            AnimsAgony.AGONY_CLAWSTRIKE,
                            AVAnimations.ENDER_SLAYER_ANTITHEUS_GUILLOTINE)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .comboCancel((style) -> false)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ANTITHEUS_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ANTITHEUS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> LEGENDARY_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.LEGENDARY_SWORD)
                    .styleProvider(
                            (livingentitypatch) -> Styles.TWO_HAND
                    ).collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            WOMAnimations.TORMENT_AUTO_1,
                            WOMAnimations.TORMENT_AUTO_2,
                            AVAnimations.YELLOW_NAPOLEON_AUTO_3,
                            AVAnimations.YELLOW_SOLAR_AUTO_2,
                            AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK,
                            AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT,
                            AVAnimations.YELLOW_NAPOLEON_AUTO_4
                    ).newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.LEGENDARY_SWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.LEGENDARY_SWORD_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> WOOPIE_THE_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.WOOPIE_THE_SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            AnimsSatsujin.SATSUJIN_AUTO_1,
                            AnimsSatsujin.SATSUJIN_AUTO_2,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            AnimsSatsujin.SATSUJIN_TSUKUYOMI)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_DUAL_AUTO1,
                            Animations.SWORD_DUAL_AUTO2,
                            Animations.SWORD_DUAL_AUTO3,
                            AnimsSatsujin.SATSUJIN_AUTO_1,
                            AnimsSatsujin.SATSUJIN_AUTO_2,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            AnimsSatsujin.SATSUJIN_TSUKUYOMI)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.WOOPIE_THE_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.WOOPIE_THE_SWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_AXE
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> HARD_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.HARD_GREATSWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsSatsujin.SATSUJIN_AUTO_3,
                            AnimsSatsujin.SATSUJIN_HARUSAKI,
                            AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_DUAL_AUTO1,
                            Animations.SWORD_DUAL_AUTO2,
                            Animations.SWORD_DUAL_AUTO3,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsSatsujin.SATSUJIN_AUTO_3,
                            AnimsSatsujin.SATSUJIN_HARUSAKI,
                            AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.HARD_GREAT_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.HARD_GREAT_SWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_AXE
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> AV_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(AVCategories.AV_SWORD)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .styleProvider(
                        (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_SWORD
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_AXE
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_TACHI
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                .collider(ColliderPreset.SWORD)
                .newStyleCombo(Styles.ONE_HAND,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3,
                        AnimsHerrscher.HERRSCHER_AUTO_1,
                        AnimsHerrscher.HERRSCHER_AUTO_2,
                        Animations.SWORD_DASH,
                        Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND,
                        Animations.SWORD_DUAL_AUTO1,
                        Animations.SWORD_DUAL_AUTO2,
                        Animations.SWORD_DUAL_AUTO3,
                        AVAnimations.DUAL_SWORD1,
                        AVAnimations.DUAL_SWORD2,
                        AVAnimations.DUAL_SWORD3,
                        Animations.SWORD_DUAL_DASH,
                        Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT,
                        Animations.SWORD_DUAL_AUTO1,
                        Animations.SWORD_DUAL_AUTO2,
                        Animations.SWORD_DUAL_AUTO3,
                        Animations.SWORD_MOUNT_ATTACK)
                .innateSkill(Styles.ONE_HAND,
                        (itemstack) -> EpicFightSkills.SWEEPING_EDGE)
                .innateSkill(Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.DANCING_EDGE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .weaponCombinationPredicator(
                        (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                                || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_AXE
                                || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                                || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

        if (item instanceof TieredItem tieredItem) {
            builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * (double) tieredItem.getTier().getLevel())));
            builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? EpicFightSounds.BLUNT_HIT.get() : EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? EpicFightParticles.HIT_BLUNT.get() : EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    public static final Function<Item, Builder> AV_AXE = (item) ->
            WeaponCapability.builder()
            .category(AVCategories.AV_AXE)
            .collider(ColliderPreset.TOOLS)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3,
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH
            ).innateSkill(Styles.ONE_HAND,
                    (itemstack) -> EpicFightSkills.GUILLOTINE_AXE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
            .collider(ColliderPreset.TOOLS);

    public static final Function<Item, Builder> AV_SPEAR = (item) -> WeaponCapability.builder()
            .category(AVCategories.AV_SPEAR)
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory()
                            == WeaponCategories.SHIELD ? Styles.ONE_HAND : Styles.TWO_HAND).collider(ColliderPreset.SPEAR)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.SPEAR_ONEHAND_AUTO,
                    AnimsAgony.AGONY_AUTO_4,
                    Animations.SPEAR_DASH,
                    WOMAnimations.STAFF_KINKONG)
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.SPEAR_TWOHAND_AUTO1,
                    Animations.SPEAR_TWOHAND_AUTO2,
                    AnimsAgony.AGONY_AUTO_4,
                    WOMAnimations.STAFF_AUTO_2,
                    WOMAnimations.STAFF_AUTO_3,
                    Animations.SPEAR_DASH,
                    Animations.SPEAR_TWOHAND_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SPEAR_MOUNT_ATTACK,
                    Animations.SPEAR_TWOHAND_AUTO1,
                    Animations.SPEAR_TWOHAND_AUTO2,
                    WOMAnimations.STAFF_AUTO_2)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> EpicFightSkills.HEARTPIERCER)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> AV_TACHI = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.AV_TACHI)
                    .collider(ColliderPreset.TACHI)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != AVCategories.AV_TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD ? Styles.TWO_HAND : Styles.OCHS)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.TACHI_AUTO1,
                            Animations.TACHI_AUTO2,
                            AnimsRuine.RUINE_AUTO_1,
                            Animations.TACHI_AUTO3,
                            AnimsRuine.RUINE_CHATIMENT,
                            AVAnimations.TACHI_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
                    .newStyleCombo(Styles.OCHS,
                            Animations.SWORD_DUAL_AUTO1,
                            Animations.SWORD_DUAL_AUTO2,
                            AVAnimations.DUAL_SWORD_AUTO3,
                            AVAnimations.DUAL_SWORD_AUTO4,
                            AVAnimations.DUAL_SWORD_AUTO5,
                            Animations.SWORD_DUAL_DASH,
                            Animations.SWORD_DUAL_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> EpicFightSkills.RUSHING_TEMPO)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD)
                    .innateSkill(Styles.OCHS, (itemstack) -> EpicFightSkills.DANCING_EDGE)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_SWORD
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_AXE
                                    || livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == AVCategories.AV_TACHI
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> AV_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.AV_LONGSWORD)
                    .styleProvider(
                        (livingentitypatch) -> {
                            if (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                                return Styles.ONE_HAND;
                            } else if (livingentitypatch instanceof PlayerPatch<?> playerPatch) {
                                return playerPatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? Styles.OCHS : Styles.TWO_HAND;
                            } else {
                                return Styles.TWO_HAND;
                            }
                        })
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.LONGSWORD_AUTO1,
                            Animations.LONGSWORD_AUTO2,
                            Animations.LONGSWORD_AUTO3,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.LONGSWORD_AUTO1,
                            Animations.LONGSWORD_AUTO1,
                            Animations.LONGSWORD_AUTO2,
                            Animations.LONGSWORD_AUTO3,
                            AVAnimations.DUAL_SWORD_AUTO1,
                            AVAnimations.DUAL_SWORD_AUTO2,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.OCHS,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_AUTO_3,
                            AnimsRuine.RUINE_CHATIMENT,
                            AVAnimations.RUSH_SWORD,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.SHARP_STAB)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, AnimsRuine.RUINE_IDLE)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, AnimsRuine.RUINE_WALK)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AnimsRuine.RUINE_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD);

    public static final Function<Item, Builder> AV_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.AV_GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            AnimsSolar.SOLAR_HORNO,
                            Animations.GREATSWORD_DASH,
                            Animations.GREATSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> WOODEN_DOOR = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.AV_GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            AnimsSolar.SOLAR_HORNO,
                            Animations.GREATSWORD_DASH,
                            Animations.GREATSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> CRAFTING_TABLE = (item) ->
            WeaponCapability.builder()
                    .category(AVCategories.AV_GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            AnimsSolar.SOLAR_HORNO,
                            Animations.GREATSWORD_DASH,
                            Animations.GREATSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, CapabilityItem.Builder> BOW = (item) ->
            WeaponCapability.builder()
                    .category(CapabilityItem.WeaponCategories.RANGED)
                    .styleProvider((patch) -> CapabilityItem.Styles.ONE_HAND)
                    .zoomInType(CapabilityItem.ZoomInType.USE_TICK)
                    .swingSound(SoundEvents.ARROW_SHOOT)
                    .hitSound(SoundEvents.ARROW_HIT)
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                            AVAnimations.BOW_AUTO_1,
                            AVAnimations.BOW_AUTO_3,
                            AVAnimations.BOW_AUTO_5)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> AVSkills.BOW)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN,  Animations.BIPED_RUN)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.AIM,  Animations.BIPED_BOW_AIM)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
                    .constructor(AVBowCapability::new);

    public static final Function<Item, Builder> SHADOW_OBSIDIAN_SWORD = (item) ->
            WeaponCapability.builder()
            .category(AVCategories.AV_SWORD)
            .styleProvider((patch) -> Styles.ONE_HAND)
            .canBePlacedOffhand(false)
            .collider(ColliderPreset.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3,
                    Animations.SWORD_DASH,
                    Animations.SWORD_AIR_SLASH)
            .innateSkill(Styles.ONE_HAND, (stack) -> EpicFightSkills.STEEL_WHIRLWIND)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .weaponCombinationPredicator((patch) -> true);

    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_aegis"), AVWeaponCapabilityPresets.ENDER_AEGIS);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive"), AVWeaponCapabilityPresets.ENDER_GLAIVE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "demoniac_voltage_reaver"), AVWeaponCapabilityPresets.DEMONIAC_VOLTAGE_REAVER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_sledgehammer"), AVWeaponCapabilityPresets.OBSIDIAN_SLEDGEHAMMER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_slayer_scythe"), AVWeaponCapabilityPresets.ENDER_SLAYER_SCYTHE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword"), AVWeaponCapabilityPresets.LEGENDARY_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_the_sword"), AVWeaponCapabilityPresets.WOOPIE_THE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hard_greatsword"), AVWeaponCapabilityPresets.HARD_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_sword"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_sword"), AVWeaponCapabilityPresets.AV_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_axe"), AVWeaponCapabilityPresets.AV_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_spear"), AVWeaponCapabilityPresets.AV_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_tachi"), AVWeaponCapabilityPresets.AV_TACHI);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_longsword"), AVWeaponCapabilityPresets.AV_LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_greatsword"), AVWeaponCapabilityPresets.AV_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_bow"), AVWeaponCapabilityPresets.BOW);
    }
}
