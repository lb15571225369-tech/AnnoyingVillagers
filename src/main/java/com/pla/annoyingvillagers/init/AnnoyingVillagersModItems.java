package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, AnnoyingVillagers.MODID);

    // Spawn egg
    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE, -10066330, -13421773, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_DEMON, -16737895, -16777216, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> HEROBRINE_2_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_2_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_2, -10066330, -13421773, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> VILLAGER_SCOUT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("cun_min_zhen_cha_bing_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT, -205, -26317, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> VILLAGER_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("cczdz_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN, -1, -1, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qi_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL, -16711681, -6710887, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("lu_cun_qi_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL, -16724992, -1, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("hong_cun_qi_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL, -3407821, -13421773, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("zi_cun_qi_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL, -3407668, -6710887, (new Properties()).tab(AnnoyingVillagers.ANNOYINGVILLAGERS_TAB));
    });
    // ------------------------------

    // Misc item
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", () -> {
        return new EnchantBedItemItem();
    });
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", () -> {
        return new VillagerHeadItem();
    });
    public static final RegistryObject<Item> ENCHANTED_ENDER_PEARL = AnnoyingVillagersModItems.REGISTRY.register("fumomoyingzhenzhu", () -> {
        return new EnchantedEnderPearlItem();
    });
    // ------------------------------

    // Weapon
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", () -> {
        return new LegendarySwordItem();
    });
    public static final RegistryObject<Item> HARD_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hard_great_sword", () -> {
        return new HardGreatSwordItem();
    });
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("anyingheiyaoshiwuqi", () -> {
        return new ShadowObsidianWeaponItem();
    });
    public static final RegistryObject<Item> OBSIDIAN_WEAPONS = AnnoyingVillagersModItems.REGISTRY.register("obsidianweapons", () -> {
        return new ObsidianWeaponItem();
    });
    public static final RegistryObject<Item> DIAMOND_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_chang_mao", () -> {
        return new DiamondSpearItem();
    });
    public static final RegistryObject<Item> DIAMOND_LONG_SWORD = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_chang_jian", () -> {
        return new DiamondLongSwordItem();
    });
    public static final RegistryObject<Item> DIAMOND_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_ju_jian", () -> {
        return new DiamondGreatSwordItem();
    });
    public static final RegistryObject<Item> CRAFTING_TABLE = AnnoyingVillagersModItems.REGISTRY.register("ggongzuotai", () -> {
        return new CraftingTableItem();
    });
    public static final RegistryObject<Item> ENCHANTED_WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("fumomumen", () -> {
        return new EnchantedWoodenDoorItem();
    });
    public static final RegistryObject<Item> WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("mumen", () -> {
        return new WoodenDoorItem();
    });
    public static final RegistryObject<Item> DIAMOND_BLADE = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_dao_pian", () -> {
        return new DiamondBladeItem();
    });
    public static final RegistryObject<Item> DIAMOND_DAGGER = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_bi_shou", () -> {
        return new DiamondDaggerItem();
    });
    public static final RegistryObject<Item> DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_dao", () -> {
        return new DiamondSwordItem();
    });
    public static final RegistryObject<Item> HOOKED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("gou_zhuang_zuan_shi_jian", () -> {
        return new HookedDiamondSwordItem();
    });
    public static final RegistryObject<Item> WOOPIE_THE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qi_fu_mo_jian", () -> {
        return new WoopieTheSwordItem();
    });
    // ------------------------------


    // Armor & Item not shown in creative tab
    public static final RegistryObject<Item> ENCHANT_BED = block(AnnoyingVillagersModBlocks.ENCHANT_BED, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARK_OB_SS = block(AnnoyingVillagersModBlocks.DARK_OB_SS, (CreativeModeTab) null);
    public static final RegistryObject<Item> NONEOB = block(AnnoyingVillagersModBlocks.NONEOB, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARKOB = block(AnnoyingVillagersModBlocks.DARKOB, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARKOBITEM = AnnoyingVillagersModItems.REGISTRY.register("darkobitem", () -> {
        return new DarkOBItem();
    });
    public static final RegistryObject<Item> DARKITEMLONG = AnnoyingVillagersModItems.REGISTRY.register("darkitemlong", () -> {
        return new DarkOBLongItem();
    });
    public static final RegistryObject<Item> DARK_OB_UP = block(AnnoyingVillagersModBlocks.DARK_OB_UP, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARK_OB_FAR = AnnoyingVillagersModItems.REGISTRY.register("dark_ob_far", () -> {
        return new DarkOBFarItem();
    });
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword", () -> {
        return new HeavyAttackLegendarySwordItem();
    });

    public static final RegistryObject<Item> HARD_GREAT_SWORD_SKILL = AnnoyingVillagersModItems.REGISTRY.register("hard_great_sword_skill", () -> {
        return new HardGreatSwordSkillItem();
    });
    public static final RegistryObject<Item> WAKE_UP_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("wake_up_legendary_sword", () -> {
        return new WakeUpLegendarySwordItem();
    });

    public static final RegistryObject<Item> SHADOW_OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK, (CreativeModeTab) null);
    public static final RegistryObject<Item> OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK, (CreativeModeTab) null);
    public static final RegistryObject<Item> BLUEDEMONTRIDENT = AnnoyingVillagersModItems.REGISTRY.register("bluedemontrident", () -> {
        return new BlueDemonTridentItem();
    });
    public static final RegistryObject<Item> LEGENDARY_SWORD_MOB = AnnoyingVillagersModItems.REGISTRY.register("dan_shou_zhan_shen_zhi_ren", () -> {
        return new LegendarySwordMobItem();
    });

    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate_chestplate", () -> {
        return new BlueDemonChestplateItem.Chestplate();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_helmet", () -> {
        return new CompressedDiamondArmorItem.Helmet();
    });
    public static final RegistryObject<Item> CCOMPRESSED_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_chestplate", () -> {
        return new CompressedDiamondArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_leggings", () -> {
        return new CompressedDiamondArmorItem.Leggings();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_boots", () -> {
        return new CompressedDiamondArmorItem.Boots();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND = AnnoyingVillagersModItems.REGISTRY.register("cchunduzuanshi", () -> {
        return new CompressedDiamondItem();
    });
    public static final RegistryObject<Item> EMERALD_HELMET = AnnoyingVillagersModItems.REGISTRY.register("llbstz_helmet", () -> {
        return new EmeraldArmorItem.Helmet();
    });
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("llbstz_chestplate", () -> {
        return new EmeraldArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> EMERALD_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("llbstz_leggings", () -> {
        return new EmeraldArmorItem.Leggings();
    });
    public static final RegistryObject<Item> EMERALD_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("llbstz_boots", () -> {
        return new EmeraldArmorItem.Boots();
    });
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET_WEARING = AnnoyingVillagersModItems.REGISTRY.register("cunzhentoukui", () -> {
        return new VillagerScoutHelmetWearingItem();
    });
    public static final RegistryObject<Item> CLASSICGOLDENA_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_chestplate", () -> {
        return new ClassicGoldenArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> CLASSICGOLDENA_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_leggings", () -> {
        return new ClassicGoldenArmorItem.Leggings();
    });
    public static final RegistryObject<Item> CLASSICGOLDENA_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_boots", () -> {
        return new ClassicGoldenArmorItem.Boots();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_HELMET_WEARING = AnnoyingVillagersModItems.REGISTRY.register("hongcunqitoukui", () -> {
        return new RedVillagerGeneralHelmetWearingItem();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("hong_cun_qihelmet_helmet", () -> {
        return new RedVillagerGeneralArmorItem.Armor();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("hong_cun_qihelmet_chestplate", () -> {
        return new RedVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("hong_cun_qihelmet_leggings", () -> {
        return new RedVillagerGeneralArmorItem.Leggings();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("hong_cun_qihelmet_boots", () -> {
        return new RedVillagerGeneralArmorItem.Boots();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_HELMET_WEARING = AnnoyingVillagersModItems.REGISTRY.register("lancunqitoukui", () -> {
        return new BlueVillagerGeneralHelmetWearingItem();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qiarmor_helmet", () -> {
        return new BlueVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qiarmor_chestplate", () -> {
        return new BlueVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qiarmor_leggings", () -> {
        return new BlueVillagerGeneralArmorItem.Leggings();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qiarmor_boots", () -> {
        return new BlueVillagerGeneralArmorItem.Boots();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("greenknightarmor_helmet", () -> {
        return new GreenVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("greenknightarmor_chestplate", () -> {
        return new GreenVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("greenknightarmor_leggings", () -> {
        return new GreenVillagerGeneralArmorItem.Leggings();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("greenknightarmor_boots", () -> {
        return new GreenVillagerGeneralArmorItem.Boots();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_HELMET_WEARING = AnnoyingVillagersModItems.REGISTRY.register("zicunqitoukui", () -> {
        return new PurpleVillagerGeneralHelmetWearingItem();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("zi_cun_qi_kui_jia_helmet", () -> {
        return new PurpleVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("zi_cun_qi_kui_jia_chestplate", () -> {
        return new PurpleVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("zi_cun_qi_kui_jia_leggings", () -> {
        return new PurpleVillagerGeneralArmorItem.Leggings();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("zi_cun_qi_kui_jia_boots", () -> {
        return new PurpleVillagerGeneralArmorItem.Boots();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("unlightdiamond_helmet", () -> {
        return new UnlightDiamondArmorItem.Helmet();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("unlightdiamond_chestplate", () -> {
        return new UnlightDiamondArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("unlightdiamond_leggings", () -> {
        return new UnlightDiamondArmorItem.Leggings();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("unlightdiamond_boots", () -> {
        return new UnlightDiamondArmorItem.Boots();
    });
    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> {
            return new BlockItem((Block) registryobject.get(), (new Properties()).tab(creativemodetab));
        });
    }
}
