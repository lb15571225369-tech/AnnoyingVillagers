package com.pla.annoyingvillagers.skill.hint;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class MoveInputHint {
    private static final String LANG_PREFIX = "hud." + AnnoyingVillagers.MODID + ".input.";

    public static final MoveInputHint LMB = new MoveInputHint("lmb");
    public static final MoveInputHint RMB = new MoveInputHint("rmb");
    public static final MoveInputHint SPRINT_LMB = new MoveInputHint("sprint_lmb");
    public static final MoveInputHint SNEAK_LMB = new MoveInputHint("sneak_lmb");
    public static final MoveInputHint AIR_LMB = new MoveInputHint("air_lmb");
    public static final MoveInputHint MOUNT_LMB = new MoveInputHint("mount_lmb");
    public static final MoveInputHint SPECIAL_TAP = new MoveInputHint("special_tap");
    public static final MoveInputHint SPECIAL_HOLD = new MoveInputHint("special_hold");
    public static final MoveInputHint THROW_PEARL = new MoveInputHint("throw_pearl");
    public static final MoveInputHint DESCEND = new MoveInputHint("descend");
    public static final MoveInputHint USE_HOLD = new MoveInputHint("use_hold");
    public static final MoveInputHint USE_RELEASE = new MoveInputHint("use_release");

    private final String key;
    private final Component label;

    private MoveInputHint(String key) {
        this.key = key;
        this.label = Component.translatable(LANG_PREFIX + key);
    }

    public String key() {
        return this.key;
    }

    public MutableComponent label() {
        return this.label.copy();
    }
}
