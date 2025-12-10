package com.pla.annoyingvillagers.capabilities;

import java.util.function.Function;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum AVCategories implements WeaponCategory, Function<Item, Builder> {

    ENDER_AEGIS, ENDER_GLAIVE, DEMONIAC_VOLTAGE_REAVER, OBSIDIAN_SLEDGEHAMMER, ENDER_SLAYER_SCYTHE, LEGENDARY_SWORD, WOOPIE_THE_SWORD, HARD_GREATSWORD, AV_SWORD, AV_AXE, AV_SPEAR, AV_TACHI, AV_LONGSWORD, AV_GREATSWORD, WOODEN_DOOR, CRAFTING_TABLE, TRAPDOOR, LADDER;

    final int id;

    AVCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }

    public Builder apply(Item item) {
        return AVWeaponCategoryMapper.apply(item, this);
    }
}