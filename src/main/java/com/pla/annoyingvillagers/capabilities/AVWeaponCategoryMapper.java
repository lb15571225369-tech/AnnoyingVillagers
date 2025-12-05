package com.pla.annoyingvillagers.capabilities;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class AVWeaponCategoryMapper {

    private static final Map<AVCategories, WeaponCategory> categoryMap = new HashMap<>();

    static {
        categoryMap.put(AVCategories.AV_AXE, CapabilityItem.WeaponCategories.AXE);
        categoryMap.put(AVCategories.AV_SWORD, CapabilityItem.WeaponCategories.SWORD);
        categoryMap.put(AVCategories.AV_LONGSWORD, CapabilityItem.WeaponCategories.LONGSWORD);
        categoryMap.put(AVCategories.AV_TACHI, CapabilityItem.WeaponCategories.TACHI);
        categoryMap.put(AVCategories.AV_SPEAR, CapabilityItem.WeaponCategories.SPEAR);
        categoryMap.put(AVCategories.AV_GREATSWORD, CapabilityItem.WeaponCategories.GREATSWORD);
        categoryMap.put(AVCategories.DEMONIAC_VOLTAGE_REAVER, CapabilityItem.WeaponCategories.GREATSWORD);
        categoryMap.put(AVCategories.OBSIDIAN_SLEDGEHAMMER, CapabilityItem.WeaponCategories.GREATSWORD);
        categoryMap.put(AVCategories.ENDER_AEGIS, CapabilityItem.WeaponCategories.SWORD);
        categoryMap.put(AVCategories.ENDER_GLAIVE, CapabilityItem.WeaponCategories.SPEAR);
        categoryMap.put(AVCategories.ENDER_SLAYER_SCYTHE, CapabilityItem.WeaponCategories.SPEAR);
        categoryMap.put(AVCategories.LEGENDARY_SWORD, CapabilityItem.WeaponCategories.GREATSWORD);
        categoryMap.put(AVCategories.HARD_GREATSWORD, CapabilityItem.WeaponCategories.SWORD);
    }

    public static CapabilityItem.Builder apply(Item item, AVCategories category) {
        WeaponCategory mappedCategory = categoryMap.getOrDefault(category, category);
        try {
            Method applyMethod = mappedCategory.getClass().getMethod("apply", Item.class);
            return (CapabilityItem.Builder) applyMethod.invoke(mappedCategory, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}