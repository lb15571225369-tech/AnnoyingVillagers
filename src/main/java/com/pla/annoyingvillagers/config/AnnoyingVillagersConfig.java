package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHYSIC_MOD_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MIN_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MAX_TIME;
    public static ForgeConfigSpec.ConfigValue<Double> KICK_GUARD_BREAK_MIN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> KICK_GUARD_BREAK_MAX_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Boolean> TURN_ON_NPC_CHAT;

    static {
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc into Herobrine #5 and Herobrine #6")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);
        PHYSIC_MOD_COMPAT = BUILDER.comment(
                        "Spawn dead body for the mob on killed",
                        "Install Physic Mod to see the effect")
                .define("physicModCompat", false);
        HEROBRINE_RECALL_MIN_TIME = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's random recall time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMinTime", 60, 1, 10080);
        HEROBRINE_RECALL_MAX_TIME = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's random recall time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMaxTime", 300, 1, 10080);
        KICK_GUARD_BREAK_MIN_CHANCE = BUILDER.comment(
                        "Min chance for mob and player can guard break enemy on kick")
                .defineInRange("kickGuardBreakMinChance", 0.05D, 0.0D, 1.0D);
        KICK_GUARD_BREAK_MAX_CHANCE = BUILDER.comment(
                        "Max chance for mob and player can guard break enemy on kick")
                .defineInRange("kickGuardBreakMaxChance", 0.4D, 0.0D, 1.0D);
        MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE = BUILDER.comment(
                        "Min chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMinChance", 0.05D, 0.0D, 1.0D);
        MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE = BUILDER.comment(
                        "Max chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMaxChance", 0.4D, 0.0D, 1.0D);
        TURN_ON_NPC_CHAT = BUILDER.comment(
                        "Turn on all chatting for NPC")
                .define("turnOnNpcChat", true);
        SPEC = BUILDER.build();
    }
}
