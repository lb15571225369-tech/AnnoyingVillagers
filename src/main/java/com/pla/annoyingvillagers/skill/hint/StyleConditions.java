package com.pla.annoyingvillagers.skill.hint;

import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public final class StyleConditions {
    private StyleConditions() {}

    public static MoveCondition style(CapabilityItem.Styles style) {
        return ctx -> {
            if (ctx.mainCap() == null) return false;
            LivingEntityPatch<?> patch = ctx.patch();
            if (patch == null) return false;
            try {
                return ctx.mainCap().getStyle(patch) == style;
            } catch (Throwable ignored) {
                return false;
            }
        };
    }

    public static MoveCondition oneHand() { return style(CapabilityItem.Styles.ONE_HAND); }
    public static MoveCondition twoHand() { return style(CapabilityItem.Styles.TWO_HAND); }
    public static MoveCondition mountStyle() { return style(CapabilityItem.Styles.MOUNT); }
    public static MoveCondition ochsStyle() { return style(CapabilityItem.Styles.OCHS); }
    public static MoveCondition rangedStyle() { return style(CapabilityItem.Styles.RANGED); }

    public static MoveCondition offhandIs(CapabilityItem.WeaponCategories category) {
        return ctx -> {
            CapabilityItem off = ctx.offCap();
            if (off == null) return false;
            return off.getWeaponCategory() == category;
        };
    }

    public static MoveCondition offhandEmpty() {
        return ctx -> {
            ItemStack off = ctx.offHand();
            return off == null || off.isEmpty();
        };
    }

    public static MoveCondition hasInnateSkill() {
        return ctx -> {
            PlayerPatch<?> pp = ctx.playerPatch();
            if (pp == null || ctx.mainCap() == null) return false;
            try {
                return ctx.mainCap().getInnateSkill(pp, ctx.mainHand()) != null;
            } catch (Throwable ignored) {
                return false;
            }
        };
    }
}
