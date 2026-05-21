package com.pla.annoyingvillagers.skill.hint;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Objects;

public final class Move {
    private final String id;
    private final MoveInputHint input;
    private final Component displayName;
    private final Component description;
    private final MoveCondition condition;
    private final int priority;
    private final boolean cooldownAware;

    private Move(Builder b) {
        this.id = Objects.requireNonNull(b.id);
        this.input = Objects.requireNonNull(b.input);
        this.displayName = b.displayName != null ? b.displayName
                : Component.translatable("hud." + AnnoyingVillagers.MODID + ".move." + b.id);
        this.description = b.description;
        this.condition = b.condition != null ? b.condition : MoveCondition.ALWAYS;
        this.priority = b.priority;
        this.cooldownAware = b.cooldownAware;
    }

    public String id() { return id; }
    public MoveInputHint input() { return input; }
    public MutableComponent displayName() { return displayName.copy(); }
    public Component description() { return description; }
    public MoveCondition condition() { return condition; }
    public int priority() { return priority; }
    public boolean cooldownAware() { return cooldownAware; }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final String id;
        private MoveInputHint input;
        private Component displayName;
        private Component description;
        private MoveCondition condition;
        private int priority;
        private boolean cooldownAware;

        private Builder(String id) { this.id = id; }

        public Builder input(MoveInputHint input) { this.input = input; return this; }
        public Builder displayName(Component c) { this.displayName = c; return this; }
        public Builder displayKey(String key) { this.displayName = Component.translatable(key); return this; }
        public Builder description(Component c) { this.description = c; return this; }
        public Builder when(MoveCondition c) { this.condition = c; return this; }
        public Builder priority(int p) { this.priority = p; return this; }
        public Builder cooldownAware(boolean v) { this.cooldownAware = v; return this; }
        public Move build() { return new Move(this); }
    }
}
