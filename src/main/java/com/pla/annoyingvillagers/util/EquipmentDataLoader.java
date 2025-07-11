package com.pla.annoyingvillagers.util;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EquipmentDataLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Random RANDOM = new Random();
    private static final Map<String, List<String>> EQUIP_ITEMS = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public EquipmentDataLoader() {
        super(GSON, "player_mobs_equipment");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
        EQUIP_ITEMS.clear();
        map.forEach((id, json) -> {
            JsonObject root = GsonHelper.convertToJsonObject(json, "equipments.json");

            for (String slot : List.of("MAINHAND", "OFFHAND", "HEAD", "CHEST", "LEGS", "FEET")) {
                if (!root.has(slot)) continue;
                JsonArray array = root.getAsJsonArray(slot);
                List<String> items = new ArrayList<>();
                for (JsonElement el : array) {
                    items.add(el.getAsString());
                }
                EQUIP_ITEMS.put(slot, items);
            }
        });
    }

    public static List<String> getEquipCommands(float equipChanceArmor) {
        List<String> cmds = new ArrayList<>();

        for (String slot : List.of("MAINHAND", "OFFHAND", "HEAD", "CHEST", "LEGS", "FEET")) {
            List<String> pool = EQUIP_ITEMS.getOrDefault(slot, List.of());
            if (pool.isEmpty()) continue;

            boolean alwaysEquip = slot.equals("MAINHAND") || slot.equals("OFFHAND");
            if (!alwaysEquip && RANDOM.nextFloat() > equipChanceArmor) continue;

            String itemId = pool.get(RANDOM.nextInt(pool.size()));
            int damage = RANDOM.nextInt(25);

            cmds.add(String.format("item replace entity @s %s with %s{Damage:%d}", mapSlot(slot), itemId, damage));
        }

        return cmds;
    }

    private static String mapSlot(String slot) {
        return switch (slot) {
            case "MAINHAND" -> "weapon.mainhand";
            case "OFFHAND" -> "weapon.offhand";
            case "HEAD" -> "armor.head";
            case "CHEST" -> "armor.chest";
            case "LEGS" -> "armor.legs";
            case "FEET" -> "armor.feet";
            default -> slot.toLowerCase();
        };
    }
}

