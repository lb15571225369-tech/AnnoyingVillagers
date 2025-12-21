package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHYSIC_MOD_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_PLAYER;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_NPC;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MIN_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MAX_TIME;

    static {
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc into Herobrine #5 and Herobrine #6")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);
        PHYSIC_MOD_COMPAT = BUILDER.comment(
                        "Spawn dead body for the mob on killed",
                        "Install Physic Mod to see the effect")
                .define("physicModCompat", false);
        EXECUTION_PLAYER = BUILDER.comment(
                        "This mod tweaked the Execution from Resurrection",
                        "Set to false if you want to disable Execution for Player")
                .define("executionPlayer", true);
        EXECUTION_NPC = BUILDER.comment(
                        "Set to false if you want to disable Execution for NPC")
                .define("executionNpc", true);
        HEROBRINE_RECALL_MIN_TIME = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's random recall time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMinTime", 60, 1, 10080);
        HEROBRINE_RECALL_MAX_TIME = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's random recall time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMaxTime", 300, 1, 10080);
        SPEC = BUILDER.build();
    }
}
