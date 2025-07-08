package com.pla.annoyingvillagers.capabilities;

import java.util.function.Function;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum AVCategories implements WeaponCategory, Function<Item, Builder> {

    LEGENDARYSWORD, IRONFIST, DUALPISTOL, HARDGREATSWORD, KNIFE;

    final int id;

    private AVCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }

    public Builder apply(Item item) {
        return AVWeaponCategoryMapper.apply(item, this);
    }
}
