package com.pla.annoyingvillagers.capabilities;

import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

import com.pla.annoyingvillagers.gameasset.AVCollider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSounds;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class AVWeaponCapability {

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
                            AnimsSolar.SOLAR_AUTO_1,
                            AnimsSolar.SOLAR_AUTO_2,
                            AnimsSolar.SOLAR_AUTO_2,
                            AnimsSolar.SOLAR_AUTO_3,
                            AnimsSolar.SOLAR_AUTO_4,
                            WOMAnimations.TORMENT_AUTO_4,
                            WOMAnimations.TORMENT_CHARGED_ATTACK_2,
                            WOMAnimations.TORMENT_CHARGED_ATTACK_1,
                            WOMAnimations.TORMENT_DASH,
                            WOMAnimations.TORMENT_AIRSLAM
                    ).newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsSolar.SOLAR_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, AnimsSolar.SOLAR_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.LEGENDARY_SWORD_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> AXE = (item) ->
            WeaponCapability.builder()
            .category(WeaponCategories.AXE)
            .collider(ColliderPreset.TOOLS)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.VINDICATOR_SWING_AXE3,
                    Animations.BIPED_MOB_TACHI,
                    Animations.AXE_AIRSLASH
            ).innateSkill(Styles.ONE_HAND,
                    (itemstack) -> EpicFightSkills.GUILLOTINE_AXE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
            .collider(ColliderPreset.TOOLS);

    public static final Function<Item, Builder> SWORD = (item) -> {
         WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WeaponCategories.SWORD)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .styleProvider(
                        (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                                .getWeaponCategory() != WeaponCategories.SWORD
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                .collider(ColliderPreset.SWORD)
                .newStyleCombo(Styles.ONE_HAND,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3,
                        Animations.SWORD_AUTO2,
                        AVAnimations.SWORD_DASH,
                        Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND,
                        Animations.SWORD_DUAL_AUTO1,
                        Animations.SWORD_DUAL_AUTO2,
                        Animations.SWORD_DUAL_AUTO3,
                        Animations.SWORD_AUTO2,
                        AVAnimations.DUAL_SWORD_AUTO3,
                        AVAnimations.DUAL_SWORD_AUTO4,
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
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
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

    public static final Function<Item, Builder> SPEAR = (item) -> WeaponCapability.builder()
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
                    WOMAnimations.STAFF_AUTO_2,
                    WOMAnimations.STAFF_AUTO_3,
                    Animations.SPEAR_DASH,
                    WOMAnimations.STAFF_KINKONG)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> IRON_FIST = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(WeaponCategories.UCHIGATANA)
                .collider(ColliderPreset.TOOLS)
                .newStyleCombo(Styles.ONE_HAND,
                        Animations.FIST_AUTO1,
                        Animations.FIST_AUTO2,
                        Animations.FIST_AUTO3,
                        Animations.FIST_DASH,
                        Animations.FIST_AIR_SLASH)
                .innateSkill(Styles.ONE_HAND,
                        (itemstack) -> EpicFightSkills.RELENTLESS_COMBO)
                .newStyleCombo(Styles.MOUNT,
                        Animations.FIST_AUTO1,
                        Animations.FIST_AUTO2,
                        Animations.FIST_AUTO3)
                .livingMotionModifier(Styles.ONE_HAND,
                        LivingMotions.BLOCK,
                        Animations.UCHIGATANA_GUARD);

        if (item instanceof TieredItem tieredItem) {
            builder.addStyleAttibutes(Styles.COMMON,
                    Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.8D + 0.4D * (double) tieredItem.getTier().getLevel())));
        }

        return builder;
    };

    public static final Function<Item, Builder> TACHI = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.TACHI)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD ? Styles.TWO_HAND : Styles.OCHS)
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
                            Animations.TACHI_AUTO3,
                            AVAnimations.TACHI_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
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
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.RUSHING_TEMPO)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD)
                    .innateSkill(Styles.OCHS, (itemstack) -> EpicFightSkills.DANCING_EDGE)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
                    .styleProvider(
                        (livingentitypatch) -> {
                            if (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                                return Styles.ONE_HAND;
                            } else if (livingentitypatch instanceof PlayerPatch playerPatch) {
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
                            Animations.LONGSWORD_AUTO1,
                            Animations.LONGSWORD_AUTO2,
                            Animations.LONGSWORD_AUTO3,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.OCHS,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO3,
                            AVAnimations.TACHI_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.SHARP_STAB)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

    public static final Function<Item, Builder> ESWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
                    .styleProvider((livingentitypatch) -> {
                        if (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                            return Styles.ONE_HAND;
                        } else if (livingentitypatch instanceof PlayerPatch playerPatch) {
                            return playerPatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? Styles.OCHS : Styles.TWO_HAND;
                        } else {
                            return Styles.TWO_HAND;
                        }
                    })
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.LONGSWORD_AUTO1,
                            Animations.LONGSWORD_AUTO2,
                            Animations.LONGSWORD_AUTO3,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DAGGER_AUTO1,
                            AVAnimations.DAGGER_AUTO2,
                            AVAnimations.DAGGER_AUTO3,
                            Animations.LONGSWORD_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.OCHS,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO3,
                            AVAnimations.TACHI_DASH,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.SHARP_STAB)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .innateSkill(Styles.OCHS, (itemstack) -> EpicFightSkills.LIECHTENAUER)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

    public static final Function<Item, Builder> ETRIDENT = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.TRIDENT)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TRIDENT ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.TRIDENT_AUTO1,
                            Animations.TRIDENT_AUTO2,
                            Animations.TRIDENT_AUTO3,
                            Animations.SWORD_DASH,
                            Animations.SPEAR_ONEHAND_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DAGGER_DUAL_AUTO1,
                            AVAnimations.DAGGER_DUAL_AUTO2,
                            AVAnimations.DAGGER_DUAL_AUTO3,
                            AVAnimations.DAGGER_DUAL_AUTO4,
                            AVAnimations.DAGGER_AUTO2,
                            Animations.BIPED_MOB_DAGGER_TWOHAND1,
                            AVAnimations.RUSH_SWORD,
                            Animations.SPEAR_ONEHAND_AIR_SLASH)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.AIM, Animations.BIPED_JAVELIN_AIM)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SHOT, Animations.BIPED_JAVELIN_THROW)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TRIDENT)));

    public static final Function<Item, Builder> KNIFE = (item) ->
            WeaponCapability.builder()
            .category(AVCategories.KNIFE)
                    .styleProvider(
                            (livingentitypatch) -> Styles.COMMON)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH_SMALL.get())
                    .collider(ColliderPreset.DAGGER)
                    .newStyleCombo(Styles.COMMON,
                            Animations.DAGGER_AUTO1,
                            Animations.DAGGER_AUTO2,
                            Animations.DAGGER_AUTO3,
                            Animations.DAGGER_DASH,
                            Animations.DAGGER_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.COMMON,
                            (itemstack) -> EpicFightSkills.EVISCERATE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.KNIFE_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.FLOAT, AVAnimations.KNIFE_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.FALL, AVAnimations.KNIFE_IDLE);

    public static final Function<Item, Builder> REDGREATSWORD = (item) ->
            WeaponCapability.builder()
            .category(WeaponCategories.GREATSWORD)
            .styleProvider(
                    (livingentitypatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.GREATSWORD)
            .swingSound(EpicFightSounds.WHOOSH_BIG.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.GREATSWORD_AUTO1,
                    Animations.GREATSWORD_AUTO2,
                    AnimsSolar.SOLAR_AUTO_3,
                    WOMAnimations.TORMENT_AUTO_3,
                    WOMAnimations.TORMENT_AUTO_4,
                    AnimsSolar.SOLAR_AUTO_4,
                    WOMAnimations.TORMENT_DASH,
                    WOMAnimations.TORMENT_AIRSLAM)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsSolar.SOLAR_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsSolar.SOLAR_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsSolar.SOLAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsSolar.SOLAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

    public static final Function<Item, Builder> HARD_GREAT_SWORD = (item) ->
            WeaponCapability.builder()
            .category(AVCategories.HARD_GREAT_SWORD)
            .styleProvider(
                    (livingentitypatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.TACHI)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.LONGSWORD_AUTO1,
                    Animations.LONGSWORD_AUTO2,
                    Animations.LONGSWORD_AUTO3,
                    AVAnimations.TACHI_DASH,
                    Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.HARD_GREAT_SWORD_GUARD);

    public static final Function<Item, Builder> UCHIGATANA = (item) -> {
        return WeaponCapability.builder()
                .category(WeaponCategories.UCHIGATANA)
                .styleProvider((livingentitypatch) -> {
                    if (livingentitypatch instanceof PlayerPatch playerPatch) {
                        if (playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(SkillDataKeys.SHEATH.get()) && (Boolean) playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SkillDataKeys.SHEATH.get())) {
                            return Styles.SHEATH;
                        }
                    }
                    return Styles.TWO_HAND;
                })
                .passiveSkill(EpicFightSkills.BATTOJUTSU_PASSIVE)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.UCHIGATANA)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .canBePlacedOffhand(false)
                .newStyleCombo(Styles.SHEATH,
                        Animations.UCHIGATANA_SHEATHING_AUTO,
                        Animations.UCHIGATANA_SHEATHING_DASH,
                        Animations.UCHIGATANA_SHEATH_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND,
                        Animations.UCHIGATANA_AUTO1,
                        Animations.UCHIGATANA_AUTO2,
                        Animations.UCHIGATANA_AUTO3,
                        Animations.UCHIGATANA_DASH,
                        Animations.UCHIGATANA_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT,
                        Animations.SWORD_MOUNT_ATTACK)
                .innateSkill(Styles.SHEATH,
                        (itemstack) -> EpicFightSkills.BATTOJUTSU)
                .innateSkill(Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.BATTOJUTSU)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_WALK_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.CHASE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);
    };

    public static final Function<Item, Builder> GREATSWORD = (item) ->
            WeaponCapability.builder()
            .category(WeaponCategories.GREATSWORD)
            .collider(AVCollider.GREATSWORD)
            .swingSound(EpicFightSounds.WHOOSH_BIG.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.GREATSWORD_AUTO1,
                    Animations.GREATSWORD_AUTO2,
                    Animations.GREATSWORD_DASH,
                    Animations.GREATSWORD_AIR_SLASH)
            .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

    public static final Function<Item, Builder> SWORD_SHIELD = (item) ->
            WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .styleProvider((patch) -> Styles.ONE_HAND)
            .canBePlacedOffhand(false)
            .collider(ColliderPreset.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.SWORD_HEAVY_AUTO_1,
                    AVAnimations.SWORD_HEAVY_AUTO_2,
                    AVAnimations.SWORD_HEAVY_AUTO_3,
                    AVAnimations.AXE_HEAVY_AUTO_1,
                    AVAnimations.AXE_HEAVY_AUTO_2,
                    Animations.AXE_AIRSLASH)
            .innateSkill(Styles.ONE_HAND, (stack) -> EpicFightSkills.SWEEPING_EDGE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
            .weaponCombinationPredicator((patch) -> true);

    public static final Function<Item, CapabilityItem.Builder> AGONY_SPEAR = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
            .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
            .collider(WOMWeaponColliders.AGONY)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    AnimsAgony.AGONY_AUTO_1,
                    AnimsAgony.AGONY_AUTO_2,
                    AnimsAgony.AGONY_AUTO_3,
                    AnimsAgony.AGONY_AUTO_4,
                    AnimsAgony.AGONY_CLAWSTRIKE,
                    AnimsAgony.AGONY_RIPPING_FANGS)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SPEAR_MOUNT_ATTACK)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
            .comboCancel((style) -> false)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsAgony.AGONY_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsAgony.AGONY_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsAgony.AGONY_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsAgony.AGONY_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> TORMENT_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingentitypatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.GREATSWORD)
            .swingSound(EpicFightSounds.WHOOSH_BIG.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    WOMAnimations.TORMENT_AUTO_1,
                    WOMAnimations.TORMENT_AUTO_2,
                    WOMAnimations.TORMENT_AUTO_3,
                    WOMAnimations.TORMENT_AUTO_4,
                    WOMAnimations.TORMENT_DASH,
                    WOMAnimations.TORMENT_AIRSLAM
            ).newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsSolar.SOLAR_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsSolar.SOLAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsSolar.SOLAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, CapabilityItem.Builder> ANTITHEUS_SPEAR = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
            .styleProvider((entitypatch) -> Styles.TWO_HAND)
            .collider(WOMWeaponColliders.ANTITHEUS)
            .hitSound((SoundEvent)EpicFightSounds.BLADE_HIT.get())
            .swingSound((SoundEvent)EpicFightSounds.WHOOSH.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    WOMAnimations.ANTITHEUS_AUTO_1,
                    WOMAnimations.ANTITHEUS_AUTO_2,
                    WOMAnimations.ANTITHEUS_AUTO_3,
                    WOMAnimations.ANTITHEUS_AUTO_4,
                    WOMAnimations.ANTITHEUS_AGRESSION,
                    WOMAnimations.ANTITHEUS_GUILLOTINE)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.TWO_HAND, (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
            .comboCancel((style) -> false)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ANTITHEUS_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.ANTITHEUS_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ANTITHEUS_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> SHADOW_OBSIDIAN_SWORD = (item) ->
            WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
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
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "legendary_sword"), AVWeaponCapability.LEGENDARY_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "axe"), AVWeaponCapability.AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "sword"), AVWeaponCapability.SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "spear"), AVWeaponCapability.SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "iron_fist"), AVWeaponCapability.IRON_FIST);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "tachi"), AVWeaponCapability.TACHI);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "longsword"), AVWeaponCapability.LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "torment_greatsword"), AVWeaponCapability.TORMENT_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "e_sword"), AVWeaponCapability.ESWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "e_trident"), AVWeaponCapability.ETRIDENT);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "knife"), AVWeaponCapability.KNIFE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "redgreatsword"), AVWeaponCapability.REDGREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "hardgreatsword"), AVWeaponCapability.HARD_GREAT_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "uchigatana"), AVWeaponCapability.UCHIGATANA);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "greatsword"), AVWeaponCapability.GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "sword_shield"), AVWeaponCapability.SWORD_SHIELD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "agony_spear"), AVWeaponCapability.AGONY_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "antitheus_spear"), AVWeaponCapability.ANTITHEUS_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath("epicfight", "shadow_obsidian_sword"), AVWeaponCapability.SHADOW_OBSIDIAN_SWORD);
    }
}
