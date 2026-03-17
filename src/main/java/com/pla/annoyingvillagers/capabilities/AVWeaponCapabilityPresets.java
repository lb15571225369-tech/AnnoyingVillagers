package com.pla.annoyingvillagers.capabilities;

import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
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
                    .category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .canBePlacedOffhand(false)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsHerrscher.HERRSCHER_AUTO_1,
                            AnimsHerrscher.HERRSCHER_AUTO_2,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2,
                            AnimsSolar.SOLAR_QUEMADURA,
                            AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO,
                            AnimsSolar.SOLAR_HORNO)
                    .innateSkill(Styles.TWO_HAND, (stack) -> AVSkills.ENDER_AEGIS)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .weaponCombinationPredicator((patch) -> true);

    public static final Function<Item, CapabilityItem.Builder> ENDER_GLAIVE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.AGONY)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_1,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_2,
                            AnimsAgony.AGONY_AUTO_4,
                            AnimsAgony.AGONY_AUTO_2,
                            AnimsAgony.AGONY_AUTO_3,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUSTERLITZ,
                            AnimsAgony.AGONY_AIR_ATTACK_4)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.ENDER_GLAIVE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> DEMONIAC_VOLTAGE_REAVER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DEMONIAC_RUINE_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            AVAnimations.DEMONIAC_RUINE_AUTO_2,
                            AVAnimations.DEMONIAC_RUINE_AUTO_4,
                            AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2,
                            AVAnimations.DEMONIAC_RUINE_COMET
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.DEMONIAC_VOLTAGE_REAVER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> OBSIDIAN_SLEDGEHAMMER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DEMONIAC_RUINE_AUTO_1,
                            AVAnimations.DEMONIAC_RUINE_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_2,
                            AnimsSolar.SOLAR_OBSCURIDAD_AUTO_4,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_TISHNAW
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.OBSIDIAN_SLEDGEHAMMER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, CapabilityItem.Builder> ENDER_SLAYER_SCYTHE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((entityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.ANTITHEUS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_3,
                            WOMAnimations.KICK_AUTO_3,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_2,
                            WOMAnimations.KICK_AUTO_1,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_4,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_1,
                            AVAnimations.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .innateSkill(Styles.MOUNT, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.IDLE, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, CapabilityItem.Builder> NULL_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsAgony.AGONY_AIR_ATTACK_1,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_1,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3,
                            AVAnimations.CLONE_ANTITHEUS_ASCENDED_BLACKHOLE,
                            AnimsAgony.AGONY_AIR_ATTACK_4,
                            AnimsAgony.AGONY_AIR_ATTACK_3,
                            AVAnimations.CLONE_ANTITHEUS_ASCENDED_DEATHFALL)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.NULL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);

    public static final Function<Item, CapabilityItem.Builder> OBSIDIAN_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_BIPED_LANDING,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.OBSIDIAN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);

    public static final Function<Item, CapabilityItem.Builder> BEDROCK_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.ONE_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.FIST_AUTO1,
                            Animations.FIST_AUTO2,
                            Animations.FIST_AUTO3,
                            Animations.FIST_AIR_SLASH,
                            WOMAnimations.STRONG_PUNCH,
                            Animations.FIST_DASH,
                            WOMAnimations.STRONG_KICK)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.BEDROCK_WEAPON)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD);

    public static final Function<Item, CapabilityItem.Builder> SHADOW_OBSIDIAN_PILLAR = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) ? Styles.OCHS : Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_ZOMBIE_ATTACK3,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .newStyleCombo(Styles.OCHS,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_INFERNAL_AUTO_1,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR_SWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));;

    public static final Function<Item, Builder> SHADOW_OBSIDIAN_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(AnnoyingVillagersModSounds.OB_PLACE.get())
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .collider(ColliderPreset.SWORD)
                    .newStyleCombo(Styles.ONE_HAND,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.SWORD_HEAVY_AUTO_1,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5,
                            AVAnimations.GREATSWORD_DUAL_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3,
                            WOMAnimations.TORMENT_DASH,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD_DUAL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));

    public static final Function<Item, Builder> LEGENDARY_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingentitypatch) -> Styles.TWO_HAND
                    ).collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            WOMAnimations.TORMENT_AUTO_1,
                            WOMAnimations.TORMENT_AUTO_2,
                            AnimsSolar.SOLAR_AUTO_1,
                            AVAnimations.LEGENDARY_SWORD_AUTO_4,
                            AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK,
                            AVAnimations.YELLOW_SOLAR_AUTO_2,
                            AVAnimations.YELLOW_NAPOLEON_AUTO_3,
                            AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2,
                            AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT,
                            WOMAnimations.TORMENT_BERSERK_DASH
                    ).newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.LEGENDARY_SWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.LEGENDARY_SWORD_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> BLUE_DEMON_TRIDENT = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SPEAR)
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.SPEAR)
                    .swingSound(SoundEvents.TRIDENT_THROW)
                    .hitSound(SoundEvents.TRIDENT_RETURN)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .collider(ColliderPreset.SPEAR)
                    .newStyleCombo(Styles.ONE_HAND,
                            AVAnimations.ADVANCED_LANCER_AUTO1,
                            AVAnimations.NERF_TSUNAMI_REINFORCED,
                            AVAnimations.ADVANCED_DUELIST_SHOOTING_STAR)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.ADVANCED_LANCER_AUTO1,
                            AVAnimations.DUAL_SWORD_AUTO2,
                            AVAnimations.ADVANCED_DUELIST_SHOOTING_STAR,
                            AVAnimations.CUT_DP_AIR_ATTACK,
                            AVAnimations.ADVANCED_LANCER_AUTO3,
                            AVAnimations.ADVANCED_DUELIST_WHIRLEDGE,
                            AVAnimations.NERF_TSUNAMI_REINFORCED,
                            AVAnimations.CUT_HOOK_SPIN_SLASH_AIR)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.WRATHFUL_LIGHTING)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.TRIDENT_FESTIVAL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.TRIDENT_TWO_HAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.TRIDENT_TWO_HAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()));

    public static final Function<Item, Builder> WOOPIE_THE_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
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
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> GREAT_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
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
                            (itemstack) -> AVSkills.GREAT_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.GREAT_SWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> AV_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WeaponCategories.SWORD)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .styleProvider(
                        (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
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
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .weaponCombinationPredicator(
                        (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
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
            .category(WeaponCategories.AXE)
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
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .collider(ColliderPreset.TOOLS);

    public static final Function<Item, Builder> AV_SPEAR = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SPEAR)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> AV_TACHI = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.TACHI)
                    .collider(ColliderPreset.TACHI)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
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
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD)
                    .innateSkill(Styles.OCHS, (itemstack) -> EpicFightSkills.DANCING_EDGE)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> AV_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
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
                    .category(WeaponCategories.GREATSWORD)
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
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> WOODEN_DOOR = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(SoundEvents.WOODEN_DOOR_OPEN)
                    .hitSound(SoundEvents.WOODEN_DOOR_CLOSE)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            Animations.GREATSWORD_DASH,
                            WOMAnimations.TORMENT_AIRSLAM)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.WOODEN_DOOR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> TRAPDOOR = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(SoundEvents.WOODEN_TRAPDOOR_OPEN)
                    .hitSound(SoundEvents.WOODEN_TRAPDOOR_CLOSE)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO3,
                            Animations.SWORD_AUTO2,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            Animations.SWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.TRAPDOOR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.SHIELD_MAINHAND);

    public static final Function<Item, Builder> CRAFTING_TABLE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.TACHI_AUTO2,
                            Animations.TACHI_AUTO3,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_CHATIMENT,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.CRAFTING_TABLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.CARRY);

    public static final Function<Item, Builder> LADDER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(SoundEvents.LADDER_STEP)
                    .hitSound(SoundEvents.LADDER_HIT)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO3,
                            AVAnimations.SWORD_HEAVY_AUTO_1,
                            Animations.TACHI_AUTO3,
                            Animations.SWORD_DASH,
                            Animations.SWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.LADDER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD);

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
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN,  Animations.BIPED_RUN)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.AIM,  Animations.BIPED_BOW_AIM)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
                    .constructor(AVBowCapability::new);

    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_aegis"), AVWeaponCapabilityPresets.ENDER_AEGIS);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive"), AVWeaponCapabilityPresets.ENDER_GLAIVE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "demoniac_voltage_reaver"), AVWeaponCapabilityPresets.DEMONIAC_VOLTAGE_REAVER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_sledgehammer"), AVWeaponCapabilityPresets.OBSIDIAN_SLEDGEHAMMER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_slayer_scythe"), AVWeaponCapabilityPresets.ENDER_SLAYER_SCYTHE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "null_weapon"), AVWeaponCapabilityPresets.NULL_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_weapon"), AVWeaponCapabilityPresets.OBSIDIAN_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bedrock_weapon"), AVWeaponCapabilityPresets.BEDROCK_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_pillar"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_PILLAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_sword"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword"), AVWeaponCapabilityPresets.LEGENDARY_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blue_demon_trident"), AVWeaponCapabilityPresets.BLUE_DEMON_TRIDENT);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_the_sword"), AVWeaponCapabilityPresets.WOOPIE_THE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "great_sword"), AVWeaponCapabilityPresets.GREAT_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_sword"), AVWeaponCapabilityPresets.AV_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_axe"), AVWeaponCapabilityPresets.AV_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_spear"), AVWeaponCapabilityPresets.AV_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_tachi"), AVWeaponCapabilityPresets.AV_TACHI);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_longsword"), AVWeaponCapabilityPresets.AV_LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_greatsword"), AVWeaponCapabilityPresets.AV_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_bow"), AVWeaponCapabilityPresets.BOW);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wooden_door"), AVWeaponCapabilityPresets.WOODEN_DOOR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "crafting_table"), AVWeaponCapabilityPresets.CRAFTING_TABLE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "trapdoor"), AVWeaponCapabilityPresets.TRAPDOOR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ladder"), AVWeaponCapabilityPresets.LADDER);
    }
}
