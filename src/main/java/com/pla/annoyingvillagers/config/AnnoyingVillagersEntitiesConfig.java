package com.pla.annoyingvillagers.config;

import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersEntitiesConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> PLAYER_NPC_SPAWN_RATE;

    public static ForgeConfigSpec.IntValue STEVE_WEIGHT;
    public static ForgeConfigSpec.IntValue STEVE_MIN;
    public static ForgeConfigSpec.IntValue STEVE_MAX;

    public static ForgeConfigSpec.IntValue ALEX_WEIGHT;
    public static ForgeConfigSpec.IntValue ALEX_MIN;
    public static ForgeConfigSpec.IntValue ALEX_MAX;

    public static ForgeConfigSpec.IntValue CHRIS_WEIGHT;
    public static ForgeConfigSpec.IntValue CHRIS_MIN;
    public static ForgeConfigSpec.IntValue CHRIS_MAX;

    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_WEIGHT;
    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_MIN;
    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_MAX;

    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_CAPTAIN_WEIGHT;
    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_CAPTAIN_MIN;
    public static ForgeConfigSpec.IntValue VILLAGER_SCOUT_CAPTAIN_MAX;

    public static ForgeConfigSpec.IntValue RED_VILLAGER_GENERAL_WEIGHT;
    public static ForgeConfigSpec.IntValue RED_VILLAGER_GENERAL_MIN;
    public static ForgeConfigSpec.IntValue RED_VILLAGER_GENERAL_MAX;

    public static ForgeConfigSpec.IntValue BLUE_VILLAGER_GENERAL_WEIGHT;
    public static ForgeConfigSpec.IntValue BLUE_VILLAGER_GENERAL_MIN;
    public static ForgeConfigSpec.IntValue BLUE_VILLAGER_GENERAL_MAX;

    public static ForgeConfigSpec.IntValue GREEN_VILLAGER_GENERAL_WEIGHT;
    public static ForgeConfigSpec.IntValue GREEN_VILLAGER_GENERAL_MIN;
    public static ForgeConfigSpec.IntValue GREEN_VILLAGER_GENERAL_MAX;

    public static ForgeConfigSpec.IntValue PURPLE_VILLAGER_GENERAL_WEIGHT;
    public static ForgeConfigSpec.IntValue PURPLE_VILLAGER_GENERAL_MIN;
    public static ForgeConfigSpec.IntValue PURPLE_VILLAGER_GENERAL_MAX;

    public static ForgeConfigSpec.IntValue HEROBRINE_1_WEIGHT;
    public static ForgeConfigSpec.IntValue HEROBRINE_1_MIN;
    public static ForgeConfigSpec.IntValue HEROBRINE_1_MAX;

    public static ForgeConfigSpec.IntValue HEROBRINE_2_WEIGHT;
    public static ForgeConfigSpec.IntValue HEROBRINE_2_MIN;
    public static ForgeConfigSpec.IntValue HEROBRINE_2_MAX;

    public static ForgeConfigSpec.IntValue HEROBRINE_3_WEIGHT;
    public static ForgeConfigSpec.IntValue HEROBRINE_3_MIN;
    public static ForgeConfigSpec.IntValue HEROBRINE_3_MAX;

    public static ForgeConfigSpec.IntValue HEROBRINE_4_WEIGHT;
    public static ForgeConfigSpec.IntValue HEROBRINE_4_MIN;
    public static ForgeConfigSpec.IntValue HEROBRINE_4_MAX;

    public static ForgeConfigSpec.IntValue HEROBRINE_7_WEIGHT;
    public static ForgeConfigSpec.IntValue HEROBRINE_7_MIN;
    public static ForgeConfigSpec.IntValue HEROBRINE_7_MAX;

    public static ForgeConfigSpec.IntValue ARMORED_HEROBRINE_WEIGHT;
    public static ForgeConfigSpec.IntValue ARMORED_HEROBRINE_MIN;
    public static ForgeConfigSpec.IntValue ARMORED_HEROBRINE_MAX;

    public static ForgeConfigSpec.IntValue BLUE_DEMON_WEIGHT;
    public static ForgeConfigSpec.IntValue BLUE_DEMON_MIN;
    public static ForgeConfigSpec.IntValue BLUE_DEMON_MAX;

    static {
        PLAYER_NPC_SPAWN_RATE = BUILDER.comment(
                        "\n========== Spawn rate for player NPC. (From Player Mob mod) ==========\n",
                        "Set to 0 if you want to prevent them from spawning",
                        "Don't set it too high, the game will become a Death Match game")
                .defineInRange("playerNpcSpawnRate", 0.4, 0, 1);

        STEVE_WEIGHT = BUILDER
                .comment("\n========== Spawn rate for NPCs ==========\n",
                        "Spawn rate for Steve")
                .defineInRange("steveWeight", 1, 0, 100);
        STEVE_MIN = BUILDER
                .defineInRange("steveMin", 1, 0, 10);
        STEVE_MAX = BUILDER
                .defineInRange("steveMax", 1, 1, 20);

        ALEX_WEIGHT = BUILDER
                .comment("\nSpawn rate for Alex")
                .defineInRange("alexWeight", 1, 0, 100);
        ALEX_MIN = BUILDER
                .defineInRange("alexMin", 1, 0, 10);
        ALEX_MAX = BUILDER
                .defineInRange("alexMax", 1, 1, 20);

        CHRIS_WEIGHT = BUILDER
                .comment("\nSpawn rate for Chris")
                .defineInRange("chrisWeight", 1, 0, 100);
        CHRIS_MIN = BUILDER
                .defineInRange("chrisMin", 1, 0, 10);
        CHRIS_MAX = BUILDER
                .defineInRange("chrisMax", 1, 1, 20);

        VILLAGER_SCOUT_WEIGHT = BUILDER
                .comment("\n========== Spawn rate for Villager Knights ==========\n",
                        "Spawn rate for Villager Scout")
                .defineInRange("villagerScoutWeight", 13, 0, 100);
        VILLAGER_SCOUT_MIN = BUILDER
                .defineInRange("villagerScoutMin", 1, 0, 10);
        VILLAGER_SCOUT_MAX = BUILDER
                .defineInRange("villagerScoutMax", 4, 1, 20);

        VILLAGER_SCOUT_CAPTAIN_WEIGHT = BUILDER
                .comment("\nSpawn rate for Villager Scout Captain")
                .defineInRange("villagerScoutCaptainWeight", 6, 0, 100);
        VILLAGER_SCOUT_CAPTAIN_MIN = BUILDER
                .defineInRange("villagerScoutCaptainMin", 1, 0, 10);
        VILLAGER_SCOUT_CAPTAIN_MAX = BUILDER
                .defineInRange("villagerScoutCaptainMax", 2, 1, 20);

        RED_VILLAGER_GENERAL_WEIGHT = BUILDER
                .comment("\nSpawn rate for Red Villager General")
                .defineInRange("redVillagerGeneralWeight", 4, 0, 100);
        RED_VILLAGER_GENERAL_MIN = BUILDER
                .defineInRange("redVillagerGeneralMin", 1, 0, 10);
        RED_VILLAGER_GENERAL_MAX = BUILDER
                .defineInRange("redVillagerGeneralMax", 3, 1, 20);

        BLUE_VILLAGER_GENERAL_WEIGHT = BUILDER
                .comment("\nSpawn rate for Blue Villager General")
                .defineInRange("blueVillagerGeneralWeight", 5, 0, 100);
        BLUE_VILLAGER_GENERAL_MIN = BUILDER
                .defineInRange("blueVillagerGeneralMin", 1, 0, 10);
        BLUE_VILLAGER_GENERAL_MAX = BUILDER
                .defineInRange("blueVillagerGeneralMax", 3, 1, 20);

        GREEN_VILLAGER_GENERAL_WEIGHT = BUILDER
                .comment("\nSpawn rate for Green Villager General")
                .defineInRange("greenVillagerGeneralWeight", 3, 0, 100);
        GREEN_VILLAGER_GENERAL_MIN = BUILDER
                .defineInRange("greenVillagerGeneralMin", 1, 0, 10);
        GREEN_VILLAGER_GENERAL_MAX = BUILDER
                .defineInRange("greenVillagerGeneralMax", 1, 1, 20);

        PURPLE_VILLAGER_GENERAL_WEIGHT = BUILDER
                .comment("\nSpawn rate for Purple Villager General")
                .defineInRange("purpleVillagerGeneralWeight", 3, 0, 100);
        PURPLE_VILLAGER_GENERAL_MIN = BUILDER
                .defineInRange("purpleVillagerGeneralMin", 1, 0, 10);
        PURPLE_VILLAGER_GENERAL_MAX = BUILDER
                .defineInRange("purpleVillagerGeneralMax", 2, 1, 20);

        HEROBRINE_1_WEIGHT = BUILDER
                .comment("\n========== Spawn rate for Herobrines ==========\n",
                        "Spawn rate for Herobrine #1")
                .defineInRange("herobrine1Weight", 1, 0, 100);
        HEROBRINE_1_MIN = BUILDER
                .defineInRange("herobrine1Min", 1, 0, 10);
        HEROBRINE_1_MAX = BUILDER
                .defineInRange("herobrine1Max", 1, 1, 20);

        HEROBRINE_2_WEIGHT = BUILDER
                .comment("\nSpawn rate for Herobrine #2")
                .defineInRange("herobrine2Weight", 1, 0, 100);
        HEROBRINE_2_MIN = BUILDER
                .defineInRange("herobrine2Min", 1, 0, 10);
        HEROBRINE_2_MAX = BUILDER
                .defineInRange("herobrine2Max", 1, 1, 20);

        HEROBRINE_3_WEIGHT = BUILDER
                .comment("\nSpawn rate for Herobrine #3")
                .defineInRange("herobrine3Weight", 1, 0, 100);
        HEROBRINE_3_MIN = BUILDER
                .defineInRange("herobrine3Min", 1, 0, 10);
        HEROBRINE_3_MAX = BUILDER
                .defineInRange("herobrine3Max", 1, 1, 20);

        HEROBRINE_4_WEIGHT = BUILDER
                .comment("\nSpawn rate for Herobrine #4")
                .defineInRange("herobrine4Weight", 1, 0, 100);
        HEROBRINE_4_MIN = BUILDER
                .defineInRange("herobrine4Min", 1, 0, 10);
        HEROBRINE_4_MAX = BUILDER
                .defineInRange("herobrine4Max", 1, 1, 20);

        HEROBRINE_7_WEIGHT = BUILDER
                .comment("\nSpawn rate for Herobrine #7")
                .defineInRange("herobrine7Weight", 1, 0, 100);
        HEROBRINE_7_MIN = BUILDER
                .defineInRange("herobrine7Min", 1, 0, 10);
        HEROBRINE_7_MAX = BUILDER
                .defineInRange("herobrine7Max", 1, 1, 20);

        ARMORED_HEROBRINE_WEIGHT = BUILDER
                .comment("\nSpawn rate for Armored Herobrine")
                .defineInRange("armoredHerobrineWeight", 1, 0, 100);
        ARMORED_HEROBRINE_MIN = BUILDER
                .defineInRange("armoredHerobrineMin", 1, 0, 10);
        ARMORED_HEROBRINE_MAX = BUILDER
                .defineInRange("armoredHerobrineMax", 1, 1, 20);

        BLUE_DEMON_WEIGHT = BUILDER
                .comment("\n========== Spawn rate for Hostile NPCs ==========\n",
                        "Spawn rate for Blue Demon")
                .defineInRange("blueDemonWeight", 1, 0, 100);
        BLUE_DEMON_MIN = BUILDER
                .defineInRange("blueDemonMin", 1, 0, 10);
        BLUE_DEMON_MAX = BUILDER
                .defineInRange("blueDemonMax", 1, 1, 20);

        SPEC = BUILDER.build();
    }

    public static MobSpawnSettings.SpawnerData makeSpawner(MobSpawnSettings.SpawnerData base,
                                                           ForgeConfigSpec.IntValue weight,
                                                           ForgeConfigSpec.IntValue min,
                                                           ForgeConfigSpec.IntValue max) {
        int w = weight.get();
        if (w <= 0) return null;
        return new MobSpawnSettings.SpawnerData(base.type, w, min.get(), max.get());
    }
}
