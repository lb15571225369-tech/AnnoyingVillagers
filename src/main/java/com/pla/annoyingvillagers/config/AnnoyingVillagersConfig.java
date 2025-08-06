package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> PLAYER_NPC_SPAWN_RATE;
    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHYSIC_MOD_COMPAT;

    static {
        PLAYER_NPC_SPAWN_RATE = BUILDER.comment(
                        "Spawn rate for player npc.",
                        "Set to 0 if you want to prevent them from spawning",
                        "Don't set it too high, the game will become a Death Match game",
                        "Minimum: 0",
                        "Maximum: 1")
                .defineInRange("playerNpcSpawnRate", 0.4, 0, 1);
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc to be Herobrine on killed",
                        "Set to 0 if you want to disable the possessing feature",
                        "Don't set it too high, the game will become Herobrine apocalypse",
                        "Minimum: 0",
                        "Maximum: 1")
                .defineInRange("herobrinePossessRate", 0.2, 0, 1);
        PHYSIC_MOD_COMPAT = BUILDER.comment(
                        "Spawn dead body for the mob on killed",
                        "Install Physic Mod to see the effect")
                .define("physicModCompat", true);
        SPEC = BUILDER.build();
    }
}
