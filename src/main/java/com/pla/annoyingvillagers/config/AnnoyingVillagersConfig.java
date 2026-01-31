package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MIN_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MAX_TIME;
    public static ForgeConfigSpec.ConfigValue<Double> KICK_STAMINA_DECREASE_PERCENTAGE;
    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Boolean> TURN_ON_NPC_CHAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ARROW_CAN_BREAK_BLOCK;
    public static ForgeConfigSpec.ConfigValue<Boolean> CAN_EXECUTE_AV_MOB;
    public static ForgeConfigSpec.ConfigValue<Boolean> AV_MOB_CAN_EXECUTE;

    // ==== NPC Behaviour ====
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_MONSTER_HUNTER;
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_VILLAGER_HUNTER;
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_PLAYER_HUNTER;
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_HOSTILE_HUNTER;
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_PASSIVE_HUNTER;
    public static ForgeConfigSpec.ConfigValue<Double> NPC_TARGET_WEIGHT_ANIMAL_HUNTER;

    static {
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc into Herobrine #5 and Herobrine #6")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);

        HEROBRINE_RECALL_MIN_TIME = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's random recall time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMinTime", 60, 1, 10080);

        HEROBRINE_RECALL_MAX_TIME = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's random recall time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMaxTime", 300, 1, 10080);

        KICK_STAMINA_DECREASE_PERCENTAGE = BUILDER.comment(
                        "Mob's stamina will be decreased by this percentage when get hit by Kick")
                .defineInRange("kickStaminaDecreasePercentage", 0.3D, 0.0D, 1.0D);

        MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE = BUILDER.comment(
                        "Min chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMinChance", 0.05D, 0.0D, 1.0D);

        MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE = BUILDER.comment(
                        "Max chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMaxChance", 0.4D, 0.0D, 1.0D);

        TURN_ON_NPC_CHAT = BUILDER.comment(
                        "Turn on all chatting for NPC")
                .define("turnOnNpcChat", true);

        ARROW_CAN_BREAK_BLOCK = BUILDER.comment(
                        "Make arrow can break block")
                .define("arrowCanBreakBlock", true);

        CAN_EXECUTE_AV_MOB = BUILDER.comment(
                        "Make all of AV NPCs and Mobs can be executed")
                .define("canExecuteAvMob", true);
        AV_MOB_CAN_EXECUTE = BUILDER.comment(
                        "Enable execute ability for all of Av NPCs and Mobs")
                .define("AvMobCanExecute", true);

        // ===== NPC Behaviour =====
        BUILDER.comment(
                "==== NPC Behaviour ===="
        ).push("npcBehaviour");

        NPC_TARGET_WEIGHT_MONSTER_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to target and attack all monsters in the world")
                .defineInRange("npcTargetWeightMonsterHunter", 1.0D, 0.0D, 10.0D);

        NPC_TARGET_WEIGHT_VILLAGER_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to target and attack all villagers in the world")
                .defineInRange("npcTargetWeightVillagerHunter", 1.0D, 0.0D, 10.0D);

        NPC_TARGET_WEIGHT_PLAYER_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to target and attack players and other Player NPCs")
                .defineInRange("npcTargetWeightPlayerHunter", 1.0D, 0.0D, 10.0D);

        NPC_TARGET_WEIGHT_HOSTILE_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to target and attack everything in the world")
                .defineInRange("npcTargetWeightHostileHunter", 1.0D, 0.0D, 10.0D);

        NPC_TARGET_WEIGHT_PASSIVE_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to run away from everything in the world")
                .defineInRange("npcTargetWeightPassiveHunter", 1.0D, 0.0D, 10.0D);

        NPC_TARGET_WEIGHT_ANIMAL_HUNTER = BUILDER.comment(
                        "Weight for Player NPC to target and attack all animals in the world")
                .defineInRange("npcTargetWeightAnimalHunter", 1.0D, 0.0D, 10.0D);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
