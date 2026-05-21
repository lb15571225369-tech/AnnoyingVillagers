package com.pla.annoyingvillagers.skill.hint;

import java.util.function.Predicate;

@FunctionalInterface
public interface MoveCondition extends Predicate<MoveContext> {
    MoveCondition GROUNDED = MoveContext::grounded;
    MoveCondition SPRINTING = MoveContext::sprinting;
    MoveCondition SNEAKING = MoveContext::sneaking;
    MoveCondition IN_AIR = MoveContext::inAir;
    MoveCondition RIDING = MoveContext::riding;
    MoveCondition SWIMMING = MoveContext::swimming;
    MoveCondition DRAWING_BOW = MoveContext::drawingBow;
    MoveCondition HAS_TARGET = MoveContext::hasTarget;
    MoveCondition ALWAYS = ctx -> true;

    default MoveCondition and(MoveCondition other) {
        return ctx -> this.test(ctx) && other.test(ctx);
    }

    default MoveCondition or(MoveCondition other) {
        return ctx -> this.test(ctx) || other.test(ctx);
    }

    default MoveCondition negate() {
        return ctx -> !this.test(ctx);
    }

    static MoveCondition not(MoveCondition c) {
        return c.negate();
    }

    static MoveCondition all(MoveCondition... conds) {
        return ctx -> {
            for (MoveCondition c : conds) if (!c.test(ctx)) return false;
            return true;
        };
    }
}
