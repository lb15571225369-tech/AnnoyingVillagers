package com.pla.annoyingvillagers.damagesource;

public enum AVStunType {

    LONG("damage_source.annoying_villagers.stun_long", true);

    private String tooltip;
    private boolean fixedStunTime;

    private AVStunType(String s, boolean flag) {
        this.tooltip = s;
        this.fixedStunTime = flag;
    }

    public boolean hasFixedStunTime() {
        return this.fixedStunTime;
    }

    public String toString() {
        return this.tooltip;
    }
}
