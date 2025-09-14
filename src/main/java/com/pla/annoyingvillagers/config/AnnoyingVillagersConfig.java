package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> PLAYER_NPC_SPAWN_RATE;
    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHYSIC_MOD_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXPLOSION_BREAK_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> HEROBRINE_OBSIDIAN_BREAK_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_PLAYER;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_NPC;

    static {
        PLAYER_NPC_SPAWN_RATE = BUILDER.comment(
                        "Set to 0 if you want to prevent them from spawning",
                        "Don't set it too high, the game will become a Death Match game")
                .defineInRange("playerNpcSpawnRate", 0.4, 0, 1);
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc into Herobrine #5 and Herobrine #6")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);
        PHYSIC_MOD_COMPAT = BUILDER.comment(
                        "Spawn dead body for the mob on killed",
                        "Install Physic Mod to see the effect")
                .define("physicModCompat", false);
        EXPLOSION_BREAK_ARMOR = BUILDER.comment(
                        "Break your armor on explosion")
                .define("explosionBreakArmor", true);
        HEROBRINE_OBSIDIAN_BREAK_ARMOR = BUILDER.comment(
                        "Break your armor on Herobrine's obsidian")
                .define("herobrineObsidianBreakArmor", false);
        EXECUTION_PLAYER = BUILDER.comment(
                        "This mod tweaked the Execution from Resurrection",
                        "Set to false if you want to disable Execution for Player")
                .define("executionPlayer", true);
        EXECUTION_NPC = BUILDER.comment(
                        "Set to false if you want to disable Execution for NPC")
                .define("executionNpc", true);
        SPEC = BUILDER.build();
    }
}
