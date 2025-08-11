package com.pla.annoyingvillagers.util;

import com.google.gson.*;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.*;

public class EquipmentDataLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Random RANDOM = new Random();
    private static final Map<String, List<String>> EQUIP_ITEMS = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public EquipmentDataLoader() {
        super(GSON, "mobs_equipment");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
        EQUIP_ITEMS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation fileId = entry.getKey();
            JsonObject root = GsonHelper.convertToJsonObject(entry.getValue(), "equipment");

            String modId = fileId.getPath().replace(".json", "");

            if (!ModList.get().isLoaded(modId)) {
                continue;
            }

            for (String slot : List.of("MAINHAND", "OFFHAND", "HEAD", "CHEST", "LEGS", "FEET")) {
                if (!root.has(slot)) continue;

                JsonArray array = root.getAsJsonArray(slot);
                List<String> items = EQUIP_ITEMS.computeIfAbsent(slot, k -> new ArrayList<>());

                for (JsonElement el : array) {
                    String itemName = el.getAsString();
                    items.add(modId + ":" + itemName);
                }
            }
        }
    }

    public static boolean canTwoHand(ItemStack stack) {
        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(stack);

        if (cap instanceof WeaponCapability weaponCap) {
            return weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.GREATSWORD ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.AXE ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.TACHI ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST ||
                    weaponCap.getWeaponCategory() == CapabilityItem.WeaponCategories.DAGGER;
        }

        return false;
    }

    public static int getRandomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        int min = maxDamage / 3;
        int max = maxDamage * 3 / 4;
        int damage = RANDOM.nextInt(max - min + 1) + min;
        return damage;
    }

    public static List<String> getEquipCommands(float equipChanceArmor, Entity entity) {
        List<String> cmds = new ArrayList<>();
        String oneHandWeaponInMainHand = null;

        for (String slot : List.of("MAINHAND", "OFFHAND", "HEAD", "CHEST", "LEGS", "FEET")) {
            List<String> pool = EQUIP_ITEMS.getOrDefault(slot, List.of());
            if (pool.isEmpty()) continue;

            boolean alwaysEquip = slot.equals("MAINHAND") || slot.equals("OFFHAND");
            if (!alwaysEquip && RANDOM.nextFloat() > equipChanceArmor) continue;

            String itemId;
            if (slot.equals("MAINHAND") || slot.equals("OFFHAND")) {
                itemId = "epicfight:glove";
//                if (new Random().nextFloat() < 0.25f) {
//                    itemId = oneHandWeaponInMainHand;
//                } else {
//                    continue;
//                }
            } else {
                itemId = pool.get(RANDOM.nextInt(pool.size()));
            }

            String[] parts = itemId.split(":", 2);
            String namespace = parts[0];
            String path = parts[1];
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, path));
            if (item == null) continue;
            int damage = 0;
            if (item.canBeDepleted()) {
                damage = getRandomDamage(new ItemStack(item));
            }
            cmds.add(String.format("item replace entity @s %s with %s{Damage:%d}", mapSlot(slot), itemId, damage));

            ItemStack itemStack = new ItemStack(item);
            if (slot.equals("MAINHAND")) {
                if (canTwoHand(itemStack)) {
                    oneHandWeaponInMainHand = itemId;
                }
            }
        }

        return cmds;
    }

    public static Optional<String> getRandomSpecificSlot(String slot) {
        List<String> pool = EQUIP_ITEMS.getOrDefault(slot, List.of());
        if (pool.isEmpty()) return Optional.empty();

        String itemId = pool.get(RANDOM.nextInt(pool.size()));
        String[] parts = itemId.split(":", 2);
        String namespace = parts[0];
        String path = parts[1];
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, path));
        if (item == null) return Optional.empty();

        int damage = 0;
        if (item.canBeDepleted()) {
            int maxDamage = new ItemStack(item).getMaxDamage();
            int min = maxDamage / 3;
            int max = maxDamage * 3 / 4;
            damage = RANDOM.nextInt(max - min + 1) + min;
        }

        String command = String.format("item replace entity @s %s with %s{Damage:%d}", mapSlot(slot), itemId, damage);
        return Optional.of(command);
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

