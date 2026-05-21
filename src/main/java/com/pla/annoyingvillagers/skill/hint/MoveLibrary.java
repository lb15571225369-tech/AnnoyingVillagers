package com.pla.annoyingvillagers.skill.hint;

import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import net.minecraft.network.chat.Component;

public final class MoveLibrary {
    private MoveLibrary() {}

    public static Move groundedCombo(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.LMB)
                .priority(50)
                .when(ctx -> ctx.grounded() && !ctx.sprinting() && !ctx.sneaking() && !ctx.riding())
                .build();
    }

    public static Move dashAttack(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.SPRINT_LMB)
                .priority(80)
                .when(ctx -> ctx.grounded() && ctx.sprinting() && !ctx.riding())
                .build();
    }

    public static Move airAttack(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.AIR_LMB)
                .priority(90)
                .when(ctx -> ctx.inAir() && !ctx.riding())
                .build();
    }

    public static Move sneakHeavy(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.SNEAK_LMB)
                .priority(85)
                .when(ctx -> ctx.grounded() && ctx.sneaking() && !ctx.riding())
                .build();
    }

    public static Move mountAttack(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.MOUNT_LMB)
                .priority(95)
                .when(MoveContext::riding)
                .build();
    }

    public static Move specialTap(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.SPECIAL_TAP)
                .priority(70)
                .cooldownAware(true)
                .when(StyleConditions.hasInnateSkill())
                .build();
    }

    public static Move special(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.SPECIAL_TAP)
                .priority(70)
                .cooldownAware(true)
                .when(MoveCondition.ALWAYS)
                .build();
    }

    public static Move blueDemonChestSpecial() {
        return Move.builder("blue_demon_chestplate.antitheus_ascension")
                .input(MoveInputHint.SPECIAL_HOLD)
                .priority(72)
                .when(ctx -> BlueDemonChestplateItem.isBlueDemonChestplate(ctx.chestStack()))
                .displayKey("hud.annoyingvillagers.move.antitheus_ascension")
                .build();
    }

    public static Move bowAim(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.USE_HOLD)
                .priority(60)
                .when(ctx -> !ctx.usingItem())
                .build();
    }

    public static Move bowRelease(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.USE_RELEASE)
                .priority(95)
                .when(ctx -> ctx.drawingBow() && ctx.useTicks() >= 5)
                .build();
    }

    public static Move pearl(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.THROW_PEARL)
                .priority(40)
                .when(MoveCondition.ALWAYS)
                .build();
    }

    public static Move descend(String moveId) {
        return Move.builder(moveId)
                .input(MoveInputHint.DESCEND)
                .priority(45)
                .when(ctx -> ctx.inAir() || ctx.riding())
                .build();
    }

    public static Move custom(String moveId, MoveInputHint input, int priority, MoveCondition cond) {
        return Move.builder(moveId).input(input).priority(priority).when(cond).build();
    }

    public static Move custom(String moveId, MoveInputHint input, int priority, MoveCondition cond, Component description) {
        return Move.builder(moveId).input(input).priority(priority).when(cond).description(description).build();
    }
}
