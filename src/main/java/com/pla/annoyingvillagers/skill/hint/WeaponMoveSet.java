package com.pla.annoyingvillagers.skill.hint;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class WeaponMoveSet {
    private final String id;
    private final Component displayName;
    private final List<Move> moves;

    private WeaponMoveSet(String id, Component displayName, List<Move> moves) {
        this.id = id;
        this.displayName = displayName;
        this.moves = ImmutableList.copyOf(moves);
    }

    public String id() { return id; }
    public Component displayName() { return displayName; }
    public List<Move> moves() { return moves; }

    public List<Move> activeMoves(MoveContext ctx, int max) {
        if (ctx == null) return Collections.emptyList();
        List<Move> matched = new ArrayList<>();
        for (Move m : moves) {
            if (m.condition().test(ctx)) {
                matched.add(m);
            }
        }
        matched.sort((a, b) -> Integer.compare(b.priority(), a.priority()));
        if (matched.size() > max) {
            return matched.subList(0, max);
        }
        return matched;
    }

    /**
     * Returns up to {@code max} hints for the HUD.
     * Active moves (condition passes) come first; remaining slots are filled with
     * tutorial candidates drawn from the full move list, so the player always sees
     * what inputs are available even when standing still.
     *
     * USE_RELEASE (bow release) is excluded from tutorial candidates because it is
     * only meaningful while the bow is drawn.
     */
    public List<Move> hintMoves(MoveContext ctx, int max) {
        if (ctx == null) return Collections.emptyList();

        List<Move> active = activeMoves(ctx, max);
        if (active.size() >= max) return active;

        Set<String> activeIds = new HashSet<>();
        Set<String> activeInputKeys = new HashSet<>();
        for (Move m : active) {
            activeIds.add(m.id());
            activeInputKeys.add(m.input().key());
        }

        // Pick the highest-priority candidate per input type, skipping excluded inputs
        // and input types already covered by an active move.
        Map<String, Move> bestByInput = new LinkedHashMap<>();
        for (Move m : moves) {
            if (activeIds.contains(m.id())) continue;
            if (isTutorialExcluded(m)) continue;
            String inputKey = m.input().key();
            if (activeInputKeys.contains(inputKey)) continue;
            Move existing = bestByInput.get(inputKey);
            if (existing == null || m.priority() > existing.priority()) {
                bestByInput.put(inputKey, m);
            }
        }

        List<Move> candidates = new ArrayList<>(bestByInput.values());
        candidates.sort((a, b) -> Integer.compare(tutorialOrder(a), tutorialOrder(b)));

        List<Move> result = new ArrayList<>(active);
        for (Move m : candidates) {
            if (result.size() >= max) break;
            result.add(m);
        }
        return result;
    }

    private static boolean isTutorialExcluded(Move m) {
        return m.input() == MoveInputHint.USE_RELEASE;
    }

    private static int tutorialOrder(Move m) {
        MoveInputHint h = m.input();
        if (h == MoveInputHint.LMB)          return 0;
        if (h == MoveInputHint.SPRINT_LMB)   return 1;
        if (h == MoveInputHint.SPECIAL_TAP)  return 2;
        if (h == MoveInputHint.SPECIAL_HOLD) return 3;
        if (h == MoveInputHint.SNEAK_LMB)    return 4;
        if (h == MoveInputHint.AIR_LMB)      return 5;
        if (h == MoveInputHint.MOUNT_LMB)    return 6;
        if (h == MoveInputHint.USE_HOLD)     return 7;
        if (h == MoveInputHint.THROW_PEARL)  return 8;
        if (h == MoveInputHint.DESCEND)      return 9;
        if (h == MoveInputHint.RMB)          return 10;
        return 100;
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final String id;
        private Component displayName;
        private final List<Move> moves = new ArrayList<>();

        private Builder(String id) { this.id = id; }

        public Builder displayName(Component c) { this.displayName = c; return this; }

        public Builder add(Move move) {
            this.moves.add(Objects.requireNonNull(move));
            return this;
        }

        public Builder add(Move.Builder builder) {
            return add(builder.build());
        }

        public WeaponMoveSet build() {
            return new WeaponMoveSet(id, displayName, moves);
        }
    }
}
