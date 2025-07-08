package com.pla.annoyingvillagers.capabilities;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class AVWeaponCategoryMapper {

    private static final Map<AVCategories, WeaponCategory> categoryMap = new HashMap();

    public static Builder apply(Item item, AVCategories avcategories) {
        WeaponCategory weaponcategory = (WeaponCategory) AVWeaponCategoryMapper.categoryMap.getOrDefault(avcategories, avcategories);

        try {
            Method method = weaponcategory.getClass().getMethod("apply", Item.class);

            return (Builder) method.invoke(weaponcategory, item);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
