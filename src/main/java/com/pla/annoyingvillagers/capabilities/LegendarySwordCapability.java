package com.pla.annoyingvillagers.capabilities;

import M6FGR.dualaxes.gameassets.DualAxesSkills;
import com.mojang.datafixers.util.Pair;
import java.util.function.Function;

import com.pla.annoyingvillagers.gameasset.AVCollider;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSounds;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.gameasset.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import M6FGR.dualaxes.gameassets.DualAxesAnimations;

public class LegendarySwordCapability {

    public static final Function<Item, Builder> LEGENDARYSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(AVCategories.LEGENDARYSWORD).styleProvider((livingentitypatch) -> {
            return Styles.TWO_HAND;
        }).collider(ColliderPreset.GREATSWORD).swingSound((SoundEvent) EpicFightSounds.WHOOSH_BIG.get()).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{WOMAnimations.TORMENT_AUTO_1, WOMAnimations.TORMENT_AUTO_2, WOMAnimations.SOLAR_AUTO_1, WOMAnimations.SOLAR_AUTO_2, WOMAnimations.SOLAR_AUTO_2, WOMAnimations.SOLAR_AUTO_3, WOMAnimations.SOLAR_AUTO_4, WOMAnimations.TORMENT_AUTO_4, WOMAnimations.TORMENT_CHARGED_ATTACK_2, AVAnimations.GREATSWORD_DUAL_AUTO_3, WOMAnimations.TORMENT_CHARGED_ATTACK_1, WOMAnimations.TORMENT_DASH, WOMAnimations.TORMENT_AIRSLAM}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.SOLAR_IDLE).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.SOLAR_WALK).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG).livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, WOMAnimations.SOLAR_IDLE).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.LEGENDARY_SWORD_GUARD).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER ? true : false));
        });

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> AXE = (item) -> {
        return WeaponCapability.builder().category(WeaponCategories.AXE).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.TWO_HAND : Styles.OCHS : Styles.ONE_HAND;
        }).collider(ColliderPreset.TOOLS).hitSound((SoundEvent)EpicFightSounds.BLADE_HIT.get()).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{DualAxesAnimations.AXE_AUTO_1, DualAxesAnimations.AXE_AUTO_2, DualAxesAnimations.AXE_AUTO_3, Animations.BIPED_MOB_TACHI, Animations.AXE_AIRSLASH}).innateSkill(Styles.ONE_HAND, (itemstack) -> EpicFightSkills.GUILLOTINE_AXE).livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, DualAxesAnimations.AXE_IDLE).livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK).livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN).livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP).livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL).livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK).livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, DualAxesAnimations.AXE_GUARD).collider(ColliderPreset.TOOLS).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, DualAxesAnimations.AXE_DUAL_AUTO_3, DualAxesAnimations.AXE_DUAL_DASH, DualAxesAnimations.AXE_DUAL_AIRSLASH}).innateSkill(Styles.TWO_HAND, (itemstack) -> DualAxesSkills.SPINNING_DEATH).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, DualAxesAnimations.AXE_DUAL_IDLE).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).newStyleCombo(Styles.OCHS, new StaticAnimation[]{DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, DualAxesAnimations.AXE_DUAL_AUTO_3, AVAnimations.GREATSWORD_DUAL_AUTO_3, AVAnimations.GREATSWORD_DUAL_AUTO_4, AVAnimations.DUAL_SWORD_AUTO4, Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(Styles.OCHS, (itemstack) -> {
            return DualAxesSkills.SPINNING_DEATH;
        }).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP,AVAnimations.GREATSWORD_DUAL_RUN).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD ? true : false)));
        });
    };
    public static final Function<Item, Builder> SWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.SWORD).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, AVAnimations.SWORD_DASH, Animations.SWORD_AIR_SLASH}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_AUTO2, AVAnimations.DUAL_SWORD_AUTO3, AVAnimations.DUAL_SWORD_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SWEEPING_EDGE;
        }).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.DANCING_EDGE;
        }).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_DUAL).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI ? true : false));
        });

        if (item instanceof TieredItem) {
            TieredItem tiereditem = (TieredItem) item;
            int i = tiereditem.getTier().getLevel();

            yesman_epicfight_world_capabilities_item_weaponcapability_builder.addStyleAttibutes(Styles.COMMON, Pair.of((Attribute) EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * (double) i)));
            yesman_epicfight_world_capabilities_item_weaponcapability_builder.addStyleAttibutes(Styles.COMMON, Pair.of((Attribute) EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            yesman_epicfight_world_capabilities_item_weaponcapability_builder.hitSound(tiereditem.getTier() == Tiers.WOOD ? (SoundEvent) EpicFightSounds.BLUNT_HIT.get() : (SoundEvent) EpicFightSounds.BLADE_HIT.get());
            yesman_epicfight_world_capabilities_item_weaponcapability_builder.hitParticle(tiereditem.getTier() == Tiers.WOOD ? (HitParticleType) EpicFightParticles.HIT_BLUNT.get() : (HitParticleType) EpicFightParticles.HIT_BLADE.get());
        }

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> SPEAR = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.SPEAR).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD ? Styles.ONE_HAND : Styles.TWO_HAND;
        }).collider(ColliderPreset.SPEAR).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.SPEAR_ONEHAND_AUTO, WOMAnimations.AGONY_AUTO_4, Animations.SPEAR_DASH, WOMAnimations.STAFF_KINKONG}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, WOMAnimations.STAFF_AUTO_2, WOMAnimations.STAFF_AUTO_3, Animations.SPEAR_DASH, WOMAnimations.STAFF_KINKONG}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SPEAR_MOUNT_ATTACK, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, WOMAnimations.STAFF_AUTO_2}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.HEARTPIERCER;
        }).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.GRASPING_SPIRE;
        }).livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.AGONY_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> IRONFIST = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.UCHIGATANA).collider(ColliderPreset.TOOLS).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.RELENTLESS_COMBO;
        }).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3}).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);

        if (item instanceof TieredItem) {
            TieredItem tiereditem = (TieredItem) item;
            int i = tiereditem.getTier().getLevel();

            yesman_epicfight_world_capabilities_item_weaponcapability_builder.addStyleAttibutes(Styles.COMMON, Pair.of((Attribute) EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.8D + 0.4D * (double) i)));
        }

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> TACHI = (item) -> {
        return WeaponCapability.builder().category(WeaponCategories.TACHI).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD ? Styles.TWO_HAND : Styles.OCHS;
        }).collider(ColliderPreset.TACHI).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD ? Styles.TWO_HAND : Styles.OCHS;
        }).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.TACHI_AUTO1, Animations.TACHI_AUTO2, Animations.TACHI_AUTO3, AVAnimations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.OCHS, new StaticAnimation[]{DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, AVAnimations.DUAL_SWORD_AUTO3, AVAnimations.GREATSWORD_DUAL_AUTO_4, AVAnimations.DUAL_SWORD_AUTO5, AVAnimations.DUAL_SWORD_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.RUSHING_TEMPO;
        })
                .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD).innateSkill(Styles.OCHS, (itemstack) -> {
            return EpicFightSkills.DANCING_EDGE;
        }).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI ? true : false));
        });
    };
    public static final Function<Item, Builder> LONGSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.LONGSWORD).styleProvider((livingentitypatch) -> {
            if (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                return Styles.ONE_HAND;
            } else if (livingentitypatch instanceof PlayerPatch) {
                PlayerPatch<?> playerpatch = (PlayerPatch) livingentitypatch;

                return playerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? Styles.OCHS : Styles.TWO_HAND;
            } else {
                return Styles.TWO_HAND;
            }
        }).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).collider(ColliderPreset.LONGSWORD).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).canBePlacedOffhand(false).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.OCHS, new StaticAnimation[]{Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, AVAnimations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SHARP_STAB;
        }).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.LIECHTENAUER;
        }).innateSkill(Styles.OCHS, (itemstack) -> {
            return EpicFightSkills.LIECHTENAUER;
        }).livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> HGSD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((livingentitypatch) -> {
            return Styles.TWO_HAND;
        })
                .collider(ColliderPreset.GREATSWORD)
                .swingSound((SoundEvent) EpicFightSounds.WHOOSH_BIG.get())
                .hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get())
                .canBePlacedOffhand(false)
                .newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{
                        WOMAnimations.TORMENT_AUTO_1,
                        WOMAnimations.TORMENT_AUTO_2,
                        WOMAnimations.TORMENT_AUTO_3,
                        WOMAnimations.TORMENT_AUTO_4,
                        WOMAnimations.TORMENT_DASH,
                        WOMAnimations.TORMENT_AIRSLAM
                }).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        })
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.SOLAR_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.SOLAR_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.SOLAR_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.SOLAR_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                .weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER ? true : false));
        });

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> ESWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.LONGSWORD).styleProvider((livingentitypatch) -> {
            if (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                return Styles.ONE_HAND;
            } else if (livingentitypatch instanceof PlayerPatch) {
                PlayerPatch<?> playerpatch = (PlayerPatch) livingentitypatch;

                return playerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? Styles.OCHS : Styles.TWO_HAND;
            } else {
                return Styles.TWO_HAND;
            }
        }).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).collider(ColliderPreset.LONGSWORD).canBePlacedOffhand(false).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{AVAnimations.DAGGER_AUTO1, AVAnimations.DAGGER_AUTO2, AVAnimations.DAGGER_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.OCHS, new StaticAnimation[]{Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, AVAnimations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SHARP_STAB;
        }).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.LIECHTENAUER;
        }).innateSkill(Styles.OCHS, (itemstack) -> {
            return EpicFightSkills.LIECHTENAUER;
        }).livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD).livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD).livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> ETRIDENT = (item) -> {
        return WeaponCapability.builder().category(WeaponCategories.TRIDENT).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TRIDENT ? Styles.TWO_HAND : Styles.ONE_HAND;
        }).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.TRIDENT_AUTO1, Animations.TRIDENT_AUTO2, Animations.TRIDENT_AUTO3, Animations.SWORD_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{AVAnimations.DAGGER_DUAL_AUTO1, AVAnimations.DAGGER_DUAL_AUTO2, AVAnimations.DAGGER_DUAL_AUTO3, AVAnimations.DAGGER_DUAL_AUTO4, AVAnimations.DAGGER_AUTO2, Animations.BIPED_MOB_DAGGER_TWOHAND1, AVAnimations.RUSH_SWORD, Animations.SPEAR_ONEHAND_AIR_SLASH}).livingMotionModifier(Styles.COMMON, LivingMotions.AIM, Animations.BIPED_JAVELIN_AIM).livingMotionModifier(Styles.COMMON, LivingMotions.SHOT, Animations.BIPED_JAVELIN_THROW).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? true : (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TRIDENT ? true : false));
        });
    };
    public static final Function<Item, Builder> KNIFE = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(AVCategories.KNIFE).styleProvider((livingentitypatch) -> {
            return Styles.COMMON;
        }).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).swingSound((SoundEvent) EpicFightSounds.WHOOSH_SMALL.get()).collider(ColliderPreset.DAGGER).newStyleCombo(Styles.COMMON, new StaticAnimation[]{Animations.DAGGER_AUTO1, Animations.DAGGER_AUTO2, Animations.DAGGER_AUTO3, Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.COMMON, (itemstack) -> {
            return EpicFightSkills.EVISCERATE;
        }).livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.WALK, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.KNIFE_RUN).livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.FLOAT, AVAnimations.KNIFE_IDLE).livingMotionModifier(Styles.COMMON, LivingMotions.FALL, AVAnimations.KNIFE_IDLE);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> REDGREATSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((livingentitypatch) -> {
            return Styles.TWO_HAND;
        }).collider(ColliderPreset.GREATSWORD).swingSound((SoundEvent) EpicFightSounds.WHOOSH_BIG.get()).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, WOMAnimations.SOLAR_AUTO_3, WOMAnimations.TORMENT_AUTO_3, WOMAnimations.TORMENT_AUTO_4, WOMAnimations.SOLAR_AUTO_4, WOMAnimations.TORMENT_DASH, WOMAnimations.TORMENT_AIRSLAM}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.SOLAR_IDLE).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.SOLAR_WALK).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.SOLAR_RUN).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.SOLAR_RUN).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> HARDGREATSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(AVCategories.HARDGREATSWORD).styleProvider((livingentitypatch) -> {
            return Styles.TWO_HAND;
        }).collider(ColliderPreset.TACHI).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, AVAnimations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.HARD_GREAT_SWORD_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> UCHIGATANA = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.UCHIGATANA).styleProvider((livingentitypatch) -> {
            if (livingentitypatch instanceof PlayerPatch) {
                PlayerPatch<?> playerpatch = (PlayerPatch) livingentitypatch;

                if (playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(SkillDataKeys.SHEATH.get()) && (Boolean) playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SkillDataKeys.SHEATH.get())) {
                    return Styles.SHEATH;
                }
            }

            return Styles.TWO_HAND;
        }).passiveSkill(EpicFightSkills.BATTOJUTSU_PASSIVE).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).collider(ColliderPreset.UCHIGATANA).swingSound((SoundEvent) AVSounds.SWORD_WHOOSH.get()).canBePlacedOffhand(false).newStyleCombo(Styles.SHEATH, new StaticAnimation[]{Animations.UCHIGATANA_SHEATHING_AUTO, Animations.UCHIGATANA_SHEATHING_DASH, Animations.UCHIGATANA_SHEATH_AIR_SLASH}).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.UCHIGATANA_DASH, Animations.UCHIGATANA_AIR_SLASH}).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(Styles.SHEATH, (itemstack) -> {
            return EpicFightSkills.BATTOJUTSU;
        }).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.BATTOJUTSU;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_WALK_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA).livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.CHASE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.SHEATH, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };
    public static final Function<Item, Builder> GREATSWORD = (item) -> {
        yesman.epicfight.world.capabilities.item.WeaponCapability.Builder yesman_epicfight_world_capabilities_item_weaponcapability_builder = WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD ? Styles.OCHS : Styles.TWO_HAND;
        }).collider(AVCollider.GREATSWORD).swingSound((SoundEvent) EpicFightSounds.WHOOSH_BIG.get()).hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get()).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD).newStyleCombo(Styles.OCHS, new StaticAnimation[]{AVAnimations.GREATSWORD_DUAL_AUTO_1, AVAnimations.GREATSWORD_DUAL_AUTO_2, AVAnimations.GREATSWORD_DUAL_AUTO_3, AVAnimations.GREATSWORD_DUAL_AUTO_4, AVAnimations.GREATSWORD_DUAL_DASH, AVAnimations.GREATSWORD_DUAL_AIRSLASH}).innateSkill(Styles.OCHS, (itemstack) -> {
            return AVSkill.EARTHQUAKE;
        }).livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, AVAnimations.GREATSWORD_DUAL_IDLE).livingMotionModifier(Styles.OCHS, LivingMotions.WALK, AVAnimations.GREATSWORD_DUAL_WALK).livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.GREATSWORD_DUAL_IDLE).livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.GREATSWORD_DUAL_RUN).livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, AVAnimations.GREATSWORD_DUAL_RUN).livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).weaponCombinationPredicator((livingentitypatch) -> {
            return livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD;
        });

        return yesman_epicfight_world_capabilities_item_weaponcapability_builder;
    };

    public static final Function<Item, Builder> SWORDSHIELD = (item) -> {
        return WeaponCapability.builder()
                .category(CorruptWeaponCategories.S_SWORD)
                .styleProvider((patch) -> Styles.ONE_HAND)
                .canBePlacedOffhand(false)
                .collider(ColliderPreset.SWORD)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        DualAxesAnimations.AXE_AUTO_1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO3,
                        CorruptAnimations.SWORD_ONEHAND_AUTO4,
                        DualAxesAnimations.AXE_AUTO_3,
                        CorruptAnimations.SWORD_ONEHAND_DASH,
                        Animations.AXE_AIRSLASH
                })
                .innateSkill(Styles.ONE_HAND, (stack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, CorruptAnimations.BIPED_HOLD_KATANA)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, CorruptAnimations.RUN_KATANA)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, CorruptAnimations.WALK_KATANA)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                .weaponCombinationPredicator((patch) -> true);
    };

    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "legendarysword"), LegendarySwordCapability.LEGENDARYSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "axe"), LegendarySwordCapability.AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "sword"), LegendarySwordCapability.SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "spear"), LegendarySwordCapability.SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "ironfist"), LegendarySwordCapability.IRONFIST);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "tachi"), LegendarySwordCapability.TACHI);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "longsword"), LegendarySwordCapability.LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "hgsd"), LegendarySwordCapability.HGSD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "e_sword"), LegendarySwordCapability.ESWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "e_trident"), LegendarySwordCapability.ETRIDENT);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "knife"), LegendarySwordCapability.KNIFE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "redgreatsword"), LegendarySwordCapability.REDGREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "hardgreatsword"), LegendarySwordCapability.HARDGREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "uchigatana"), LegendarySwordCapability.UCHIGATANA);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "greatsword"), LegendarySwordCapability.GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(new ResourceLocation("epicfight", "sword_shield"), LegendarySwordCapability.SWORDSHIELD);
    }
}
