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
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_DEMON, -16737895, -16777216, (new Properties()));
    });
    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE, -10066330, -13421773, (new Properties()));
    });
    public static final RegistryObject<Item> HEROBRINE_2_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_2_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_2, -10066330, -13421773, (new Properties()));
    });
    public static final RegistryObject<Item> HEROBRINE_3_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_3_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_3, -10066330, -13421773, (new Properties()));
    });
    public static final RegistryObject<Item> VILLAGER_SCOUT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT, -205, -26317, (new Properties()));
    });
    public static final RegistryObject<Item> VILLAGER_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_captain_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN, -1, -1, (new Properties()));
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL, -16711681, -6710887, (new Properties()));
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL, -16724992, -1, (new Properties()));
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL, -3407821, -13421773, (new Properties()));
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL, -3407668, -6710887, (new Properties()));
    });
    public static final RegistryObject<Item> STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("steve_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.STEVE, -16724788, -13421569, (new Properties()));
    });
    public static final RegistryObject<Item> ANGRY_STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("angry_steve_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ANGRY_STEVE, -16724788, -13421569, (new Properties()));
    });
    public static final RegistryObject<Item> ALEX_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("alex_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ALEX, -3342439, -1  , (new Properties()));
    });
    public static final RegistryObject<Item> JEV_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("jev_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.JEV, -3342439, -1, (new Properties()));
    });
    public static final RegistryObject<Item> CHRIS_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("chris_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.CHRIS, -16737997, -6711040, (new Properties()));
    });
    public static final RegistryObject<Item> BBQ_SAUCE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("bbq_sauce_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BBQ, -1, -154, (new Properties()));
    });

    // ------------------------------

    // Misc item
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", () -> {
        return new EnchantBedItem();
    });
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", () -> {
        return new VillagerHeadItem();
    });
    public static final RegistryObject<Item> JEV_GLASSES = AnnoyingVillagersModItems.REGISTRY.register("jev_glasses", () -> {
        return new JevGlassesItem();
    });
    public static final RegistryObject<Item> ENCHANTED_ENDER_PEARL = AnnoyingVillagersModItems.REGISTRY.register("enchanted_ender_pearl", () -> {
        return new EnchantedEnderPearlItem();
    });
    public static final RegistryObject<Item> JEV_BOOK = AnnoyingVillagersModItems.REGISTRY.register("jev_book", () -> {
        return new JevBookItem();
    });
    public static final RegistryObject<Item> JEV_PENCIL = AnnoyingVillagersModItems.REGISTRY.register("jev_pencil", () -> {
        return new JevPencilItem();
    });
    public static final RegistryObject<Item> POISON_EGG_ITEM = AnnoyingVillagersModItems.REGISTRY.register("poison_egg", () -> {
        return new PoisonEggItem();
    });
    // ------------------------------

    // Gems
    public static final RegistryObject<Item> COMPRESSED_DIAMOND = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond", () -> {
        return new CompressedDiamondItem();
    });
    public static final RegistryObject<Item> RUBY = AnnoyingVillagersModItems.REGISTRY.register("ruby", () -> {
        return new RubyItem();
    });
    public static final RegistryObject<Item> PURPLE_GEM = AnnoyingVillagersModItems.REGISTRY.register("purple_gem", () -> {
        return new PurpleGemItem();
    });
    // ------------------------------

    // Weapon
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", () -> {
        return new LegendarySwordItem();
    });
    public static final RegistryObject<Item> HARD_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hard_great_sword", () -> {
        return new HardGreatSwordItem();
    });
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_weapon", () -> {
        return new ShadowObsidianWeaponItem();
    });
    public static final RegistryObject<Item> OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("obsidian_weapon", () -> {
        return new ObsidianWeaponItem();
    });
    public static final RegistryObject<Item> BEDROCK_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("bedrock_weapon", () -> {
        return new BedrockWeaponItem();
    });
    public static final RegistryObject<Item> DIAMOND_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("diamond_spear", () -> {
        return new DiamondSpearItem();
    });
    public static final RegistryObject<Item> DIAMOND_LONG_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_long_sword", () -> {
        return new DiamondLongSwordItem();
    });
    public static final RegistryObject<Item> DIAMOND_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_great_sword", () -> {
        return new DiamondGreatSwordItem();
    });
    public static final RegistryObject<Item> CRAFTING_TABLE = AnnoyingVillagersModItems.REGISTRY.register("crafting_table", () -> {
        return new CraftingTableItem();
    });
    public static final RegistryObject<Item> ENCHANTED_WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("enchanted_wooden_door", () -> {
        return new EnchantedWoodenDoorItem();
    });
    public static final RegistryObject<Item> WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("wooden_door", () -> {
        return new WoodenDoorItem();
    });
    public static final RegistryObject<Item> DIAMOND_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_blade", () -> {
        return new DiamondBladeItem();
    });
    public static final RegistryObject<Item> DIAMOND_DAGGER = AnnoyingVillagersModItems.REGISTRY.register("diamond_dagger", () -> {
        return new DiamondDaggerItem();
    });
    public static final RegistryObject<Item> DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_sword", () -> {
        return new DiamondSwordItem();
    });
    public static final RegistryObject<Item> HOOKED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_diamond_sword", () -> {
        return new HookedDiamondSwordItem();
    });
    public static final RegistryObject<Item> WOOPIE_THE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("woopie_the_sword", () -> {
        return new WoopieTheSwordItem();
    });
    public static final RegistryObject<Item> PALADIN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("paladin_sword", () -> {
        return new PaladinSwordItem();
    });
    public static final RegistryObject<Item> BLUE_FLAME_SWORD = AnnoyingVillagersModItems.REGISTRY.register("blue_flame_sword", () -> {
        return new BlueFlameSwordItem();
    });
    public static final RegistryObject<Item> DIAMOND_MAGNET_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_magnet_sword", () -> {
        return new DiamondMagnetSwordItem();
    });
    public static final RegistryObject<Item> DIAMOND_SABER = AnnoyingVillagersModItems.REGISTRY.register("diamond_saber", () -> {
        return new DiamondSaberItem();
    });
    public static final RegistryObject<Item> DIAMOND_GREATE_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_great_blade", () -> {
        return new DiamondGreatBladeItem();
    });
    public static final RegistryObject<Item> DIAMOND_LONG_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_long_blade", () -> {
        return new DiamondLongBladeItem();
    });
    public static final RegistryObject<Item> DIAMOND_HALBERD = AnnoyingVillagersModItems.REGISTRY.register("diamond_halberd", () -> {
        return new DiamondHalberdItem();
    });
    public static final RegistryObject<Item> DIAMOND_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("diamond_scythe", () -> {
        return new DiamondScytheItem();
    });
    public static final RegistryObject<Item> DIAMOND_TWIN_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_twinblade", () -> {
        return new DiamondTwinBladeItem();
    });
    public static final RegistryObject<Item> DIAMOND_GIANT_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_giant_axe", () -> {
        return new DiamondGiantAxeItem();
    });
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_battle_axe", () -> {
        return new DiamondBattleAxeItem();
    });
    public static final RegistryObject<Item> DIAMOND_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("diamond_glaive", () -> {
        return new DiamondGlaiveItem();
    });
    public static final RegistryObject<Item> DIAMOND_DOUBLE_BIT_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_double_bit_axe", () -> {
        return new DiamondDoubleBitAxeItem();
    });
    public static final RegistryObject<Item> HOOKED_GOLDEN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_golden_sword", () -> {
        return new HookedGoldenSwordItem();
    });
    public static final RegistryObject<Item> GOLDEN_LONG_SWORD = AnnoyingVillagersModItems.REGISTRY.register("golden_long_sword", () -> {
        return new GoldenLongSwordItem();
    });
    public static final RegistryObject<Item> IRON_LONG_SWORD = AnnoyingVillagersModItems.REGISTRY.register("iron_long_sword", () -> {
        return new IronLongSwordItem();
    });
    public static final RegistryObject<Item> HOOKED_IRON_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_iron_sword", () -> {
        return new HookedIronSwordItem();
    });
    public static final RegistryObject<Item> IRON_CLEAVER = AnnoyingVillagersModItems.REGISTRY.register("iron_cleaver", () -> {
        return new IronCleaverItem();
    });
    public static final RegistryObject<Item> IRON_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("iron_glaive", () -> {
        return new IronGlaiveItem();
    });
    public static final RegistryObject<Item> IRON_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("iron_scythe", () -> {
        return new IronScytheItem();
    });
    public static final RegistryObject<Item> IRON_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("iron_spear", () -> {
        return new IronSpearItem();
    });
    public static final RegistryObject<Item> IRON_POLEAXE = AnnoyingVillagersModItems.REGISTRY.register("iron_poleaxe", () -> {
        return new IronPoleAxeItem();
    });
    public static final RegistryObject<Item> EMERALD_DOUBLE_BIT_AXE = AnnoyingVillagersModItems.REGISTRY.register("emerald_double_bit_axe", () -> {
        return new EmeraldDoubleBitAxeItem();
    });
    public static final RegistryObject<Item> EMERALD_SWORD = AnnoyingVillagersModItems.REGISTRY.register("emerald_sword", () -> {
        return new EmeraldSwordItem();
    });
    public static final RegistryObject<Item> RUBY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_sword", () -> {
        return new RubySwordItem();
    });
    public static final RegistryObject<Item> RUBY_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_great_sword", () -> {
        return new RubyGreatSwordItem();
    });
    public static final RegistryObject<Item> RED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("red_diamond_sword", () -> {
        return new RedDiamondSwordItem();
    });
    public static final RegistryObject<Item> PURPLE_GEM_LONG_SWORD = AnnoyingVillagersModItems.REGISTRY.register("purple_gem_long_sword", () -> {
        return new PurpleGemLongSwordItem();
    });
    // ------------------------------


    // Item not shown in creative tab
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
    public static final RegistryObject<Item> LEGENDARY_SWORD_MOB = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword_mob", () -> {
        return new LegendarySwordMobItem();
    });
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD_MOB = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword_mob", () -> {
        return new HeavyAttackLegendarySwordMobItem();
    });
    public static final RegistryObject<Item> AXE_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED = AnnoyingVillagersModItems.REGISTRY.register("axe_attack_legendary_sword_mob_awakened", () -> {
        return new AxeAttackLegendarySwordMobAwakenedItem();
    });
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD_MOB_AWAKENED = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword_mob_awakened", () -> {
        return new HeavyAttackLegendarySwordMobAwakendedItem();
    });
    // ------------------------------

    // Armor
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate", () -> {
        return new BlueDemonChestplateItem.Chestplate();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_helmet", () -> {
        return new CompressedDiamondArmorItem.Helmet();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_chestplate", () -> {
        return new CompressedDiamondArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_leggings", () -> {
        return new CompressedDiamondArmorItem.Leggings();
    });
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_boots", () -> {
        return new CompressedDiamondArmorItem.Boots();
    });
    public static final RegistryObject<Item> RUBY_HELMET = AnnoyingVillagersModItems.REGISTRY.register("ruby_helmet", () -> {
        return new RubyArmorItem.Helmet();
    });
    public static final RegistryObject<Item> RUBY_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("ruby_chestplate", () -> {
        return new RubyArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> RUBY_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("ruby_leggings", () -> {
        return new RubyArmorItem.Leggings();
    });
    public static final RegistryObject<Item> RUBY_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("ruby_boots", () -> {
        return new RubyArmorItem.Boots();
    });
    public static final RegistryObject<Item> EMERALD_HELMET = AnnoyingVillagersModItems.REGISTRY.register("emerald_helmet", () -> {
        return new EmeraldArmorItem.Helmet();
    });
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("emerald_chestplate", () -> {
        return new EmeraldArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> EMERALD_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("emerald_leggings", () -> {
        return new EmeraldArmorItem.Leggings();
    });
    public static final RegistryObject<Item> EMERALD_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("emerald_boots", () -> {
        return new EmeraldArmorItem.Boots();
    });
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_helmet", () -> {
        return new VillagerScoutHelmetItem.Helmet();
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
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_helmet", () -> {
        return new RedVillagerGeneralArmorItem.Armor();
    });
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_chestplate", () -> {
        return new RedVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("villager_general_leggings", () -> {
        return new RedVillagerGeneralArmorItem.Leggings();
    });
    public static final RegistryObject<Item> VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("villager_general_boots", () -> {
        return new RedVillagerGeneralArmorItem.Boots();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_helmet", () -> {
        return new BlueVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_chestplate", () -> {
        return new BlueVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_helmet", () -> {
        return new GreenVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_chestplate", () -> {
        return new GreenVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_helmet", () -> {
        return new PurpleVillagerGeneralArmorItem.Helmet();
    });
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_chestplate", () -> {
        return new PurpleVillagerGeneralArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_helmet", () -> {
        return new UnlightDiamondArmorItem.Helmet();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_chestplate", () -> {
        return new UnlightDiamondArmorItem.Chestplate();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_leggings", () -> {
        return new UnlightDiamondArmorItem.Leggings();
    });
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_boots", () -> {
        return new UnlightDiamondArmorItem.Boots();
    });
    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> {
            return new BlockItem((Block) registryobject.get(), (new Properties()));
        });
    }
}
