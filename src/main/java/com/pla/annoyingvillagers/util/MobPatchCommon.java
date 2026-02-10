package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

public class MobPatchCommon {
    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForPlayerNpc(CapabilityItem mainHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_DOUBLE_BIT_AXE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcAxe.AV_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcGreatsword.AV_GREATSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SPEAR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSpear.AV_SPEAR_SHIELD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSpear.AV_SPEAR;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LONG_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcLongsword.AV_LONGSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LONG_BLADE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcTachi.AV_TACHI;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcTachi.AV_DUAL_TACHI;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOODEN_DOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcWoodenDoor.WOODEN_DOOR;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CRAFTING_TABLE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcCraftingTable.CRAFTING_TABLE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.LADDER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcLadder.LADDER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TRAPDOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcTrapdoor.TRAPDOOR;
            }
        }

        return null;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForShadowHerobrine(CapabilityItem mainHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineObsidianWeapon.OBSIDIAN_WEAPON;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineShadowObsidianPillar.SHADOW_OBSIDIAN_PILLAR_WEAPON;
            } else if (style == CapabilityItem.Styles.OCHS) {
                return HerobrineShadowObsidianPillar.SHADOW_OBSIDIAN_PILLAR_SWORD_WEAPON;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineShadowObsidianSword.SHADOW_OBSIDIAN_SWORD;
            } else if (style == CapabilityItem.Styles.ONE_HAND) {
                return HerobrineShadowObsidianSword.SHADOW_OBSIDIAN_DUAL_SWORD;
            }
        }

        return null;
    }
}
