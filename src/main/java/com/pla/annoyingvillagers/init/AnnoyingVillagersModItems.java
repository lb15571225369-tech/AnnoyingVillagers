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
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_DEMON, -16737895, -16777216, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> SHADOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("shadow_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_CHRIS_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_chris_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_CHRIS, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_GREG_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_greg_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_GREG, -3355648, -103, (new Properties())));
    public static final RegistryObject<Item> LOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("low_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> LOW_SHADOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("low_shadow_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_7_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_7_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_7, -1, -1, (new Properties())));
    public static final RegistryObject<Item> NULL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("null_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.NULL, -16777216, -10066330, (new Properties())));
    public static final RegistryObject<Item> ARMORED_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("armored_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ARMORED_HEROBRINE, -16777216, -1, (new Properties())));
    public static final RegistryObject<Item> DARK_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("shadow_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SHADOW_HEROBRINE, -1, -1, (new Properties())));
    public static final RegistryObject<Item> GLAIVE_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("glaive_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE, -1, -1, (new Properties())));
    public static final RegistryObject<Item> REAPER_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("reaper_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.REAPER_HEROBRINE, -13434727, -1, (new Properties())));
    public static final RegistryObject<Item> SWORDSMAN_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("swordsman_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE, -1, -1, (new Properties())));
    public static final RegistryObject<Item> AEGIS_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("aegis_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.AEGIS_HEROBRINE, -13434727, -1, (new Properties())));
    public static final RegistryObject<Item> SLEDGEHAMMER_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("sledgehammer_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE, -1, -1, (new Properties())));
    public static final RegistryObject<Item> VILLAGER_SCOUT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT, -205, -26317, (new Properties())));
    public static final RegistryObject<Item> VILLAGER_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_captain_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN, -1, -1, (new Properties())));
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL, -16711681, -6710887, (new Properties())));
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL, -16724992, -1, (new Properties())));
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL, -3407821, -13421773, (new Properties())));
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL, -3407668, -6710887, (new Properties())));
    public static final RegistryObject<Item> STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("steve_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.STEVE, -16724788, -13421569, (new Properties())));
    public static final RegistryObject<Item> ANGRY_STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("angry_steve_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ANGRY_STEVE, -16724788, -13421569, (new Properties())));
    public static final RegistryObject<Item> ALEX_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("alex_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ALEX, -3342439, -1  , (new Properties())));
    public static final RegistryObject<Item> JEV_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("jev_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.JEV, -3342439, -1, (new Properties())));
    public static final RegistryObject<Item> CHRIS_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("chris_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.CHRIS, -16737997, -6711040, (new Properties())));
    public static final RegistryObject<Item> BBQ_SAUCE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("bbq_sauce_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BBQ, -1, -154, (new Properties())));
    public static final RegistryObject<Item> PLAYER_NPC_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("player_npc_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.PLAYER_NPC, -16737895, -16777216, (new Properties())));

    // ------------------------------

    // Misc item
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", EnchantBedItem::new);
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", VillagerHeadItem::new);
    public static final RegistryObject<Item> JEV_GLASSES = AnnoyingVillagersModItems.REGISTRY.register("jev_glasses", JevGlassesItem::new);
    public static final RegistryObject<Item> ENCHANTED_ENDER_PEARL = AnnoyingVillagersModItems.REGISTRY.register("enchanted_ender_pearl", EnchantedEnderPearlItem::new);
    public static final RegistryObject<Item> JEV_BOOK = AnnoyingVillagersModItems.REGISTRY.register("jev_book", JevBookItem::new);
    public static final RegistryObject<Item> JEV_PENCIL = AnnoyingVillagersModItems.REGISTRY.register("jev_pencil", JevPencilItem::new);
    public static final RegistryObject<Item> POISON_EGG_ITEM = AnnoyingVillagersModItems.REGISTRY.register("poison_egg", PoisonEggItem::new);
    public static final RegistryObject<Item> ENDER_AEGIS_PROJECTILE = AnnoyingVillagersModItems.REGISTRY.register("ender_aegis_projectile", EnderAegisProjectileItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_BURST = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_burst", ShadowObsidianBurstItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_STRAIGHT = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_straight", ShadowObsidianStraightItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_BLADE = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_blade", DemoniacVoltageReaverBladeItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_FRAGMENT = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_fragment", DemoniacVoltageReaverFragmentItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_HILT = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_hilt", DemoniacVoltageReaverHiltItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian", EliteObsidianItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN_LONG = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian_long", EliteObsidianLongItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN_BIG = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian_big", EliteObsidianBigItem::new);
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_helmet_fix", VillagerScoutHelmetFixItem::new);
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_helmet_fix", RedVillagerGeneralHelmetFixItem::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_helmet_fix", BlueVillagerGeneralHelmetFixItem::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_helmet_fix", GreenVillagerGeneralHelmetFixItem::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_helmet_fix", PurpleVillagerGeneralHelmetFixItem::new);
    // ------------------------------

    // Gems
    public static final RegistryObject<Item> COMPRESSED_DIAMOND = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond", CompressedDiamondItem::new);
    public static final RegistryObject<Item> RUBY = AnnoyingVillagersModItems.REGISTRY.register("ruby", RubyItem::new);
    public static final RegistryObject<Item> PURPLE_GEM = AnnoyingVillagersModItems.REGISTRY.register("purple_gem", PurpleGemItem::new);
    // ------------------------------

    // Weapon
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", LegendarySwordItem::new);
    public static final RegistryObject<Item> HARD_GREATSWORD = AnnoyingVillagersModItems.REGISTRY.register("hard_greatsword", HardGreatSwordItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_weapon", ShadowObsidianWeaponItem::new);
    public static final RegistryObject<Item> OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("obsidian_weapon", ObsidianWeaponItem::new);
    public static final RegistryObject<Item> BEDROCK_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("bedrock_weapon", BedrockWeaponItem::new);
    public static final RegistryObject<Item> DIAMOND_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("diamond_spear", DiamondSpearItem::new);
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_longsword", DiamondLongSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_GREATSWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_greatsword", DiamondGreatSwordItem::new);
    public static final RegistryObject<Item> CRAFTING_TABLE = AnnoyingVillagersModItems.REGISTRY.register("crafting_table", CraftingTableItem::new);
    public static final RegistryObject<Item> WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("wooden_door", WoodenDoorItem::new);
    public static final RegistryObject<Item> LADDER = AnnoyingVillagersModItems.REGISTRY.register("ladder", LadderItem::new);
    public static final RegistryObject<Item> TRAPDOOR = AnnoyingVillagersModItems.REGISTRY.register("trapdoor", TrapdoorItem::new);
    public static final RegistryObject<Item> DIAMOND_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_blade", DiamondBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_DAGGER = AnnoyingVillagersModItems.REGISTRY.register("diamond_dagger", DiamondDaggerItem::new);
    public static final RegistryObject<Item> DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_sword", DiamondSwordItem::new);
    public static final RegistryObject<Item> HOOKED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_diamond_sword", HookedDiamondSwordItem::new);
    public static final RegistryObject<Item> WOOPIE_THE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("woopie_the_sword", WoopieTheSwordItem::new);
    public static final RegistryObject<Item> PALADIN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("paladin_sword", PaladinSwordItem::new);
    public static final RegistryObject<Item> BLUE_FLAME_SWORD = AnnoyingVillagersModItems.REGISTRY.register("blue_flame_sword", BlueFlameSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_MAGNET_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_magnet_sword", DiamondMagnetSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_SABER = AnnoyingVillagersModItems.REGISTRY.register("diamond_saber", DiamondSaberItem::new);
    public static final RegistryObject<Item> DIAMOND_GREATBLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_greatblade", DiamondGreatBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_LONGBLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_longblade", DiamondLongBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_HALBERD = AnnoyingVillagersModItems.REGISTRY.register("diamond_halberd", DiamondHalberdItem::new);
    public static final RegistryObject<Item> DIAMOND_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("diamond_scythe", DiamondScytheItem::new);
    public static final RegistryObject<Item> DIAMOND_TWIN_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_twinblade", DiamondTwinBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_GIANT_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_giant_axe", DiamondGiantAxeItem::new);
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_battle_axe", DiamondBattleAxeItem::new);
    public static final RegistryObject<Item> DIAMOND_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("diamond_glaive", DiamondGlaiveItem::new);
    public static final RegistryObject<Item> DIAMOND_DOUBLE_BIT_AXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_double_bit_axe", DiamondDoubleBitAxeItem::new);
    public static final RegistryObject<Item> HOOKED_GOLDEN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_golden_sword", HookedGoldenSwordItem::new);
    public static final RegistryObject<Item> GOLDEN_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("golden_longsword", GoldenLongSwordItem::new);
    public static final RegistryObject<Item> IRON_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("iron_longsword", IronLongSwordItem::new);
    public static final RegistryObject<Item> HOOKED_IRON_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_iron_sword", HookedIronSwordItem::new);
    public static final RegistryObject<Item> IRON_CLEAVER = AnnoyingVillagersModItems.REGISTRY.register("iron_cleaver", IronCleaverItem::new);
    public static final RegistryObject<Item> IRON_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("iron_glaive", IronGlaiveItem::new);
    public static final RegistryObject<Item> IRON_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("iron_scythe", IronScytheItem::new);
    public static final RegistryObject<Item> IRON_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("iron_spear", IronSpearItem::new);
    public static final RegistryObject<Item> IRON_POLEAXE = AnnoyingVillagersModItems.REGISTRY.register("iron_poleaxe", IronPoleAxeItem::new);
    public static final RegistryObject<Item> EMERALD_DOUBLE_BIT_AXE = AnnoyingVillagersModItems.REGISTRY.register("emerald_double_bit_axe", EmeraldDoubleBitAxeItem::new);
    public static final RegistryObject<Item> EMERALD_SWORD = AnnoyingVillagersModItems.REGISTRY.register("emerald_sword", EmeraldSwordItem::new);
    public static final RegistryObject<Item> RUBY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_sword", RubySwordItem::new);
    public static final RegistryObject<Item> RUBY_GREATSWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_greatsword", RubyGreatSwordItem::new);
    public static final RegistryObject<Item> RED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("red_diamond_sword", RedDiamondSwordItem::new);
    public static final RegistryObject<Item> PURPLE_GEM_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("purple_gem_longsword", PurpleGemLongSwordItem::new);
    public static final RegistryObject<Item> NULL_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("null_weapon", NullWeaponItem::new);
    public static final RegistryObject<Item> NULL_SWORD = AnnoyingVillagersModItems.REGISTRY.register("null_sword", NullSwordItem::new);
    public static final RegistryObject<Item> NULL_AXE = AnnoyingVillagersModItems.REGISTRY.register("null_axe", NullAxeItem::new);
    public static final RegistryObject<Item> NULL_PICKAXE = AnnoyingVillagersModItems.REGISTRY.register("null_pickaxe", NullPickaxeItem::new);
    public static final RegistryObject<Item> NULL_SHOVEL = AnnoyingVillagersModItems.REGISTRY.register("null_shovel", NullShovelItem::new);
    public static final RegistryObject<Item> NULL_HOE = AnnoyingVillagersModItems.REGISTRY.register("null_hoe", NullHoeItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_PILLAR = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_pillar", ShadowObsidianPillarItem::new);
    public static final RegistryObject<Item> ENDER_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("ender_glaive", EnderGlaiveItem::new);
    public static final RegistryObject<Item> ENDER_SLAYER_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("ender_slayer_scythe", EnderSlayerScytheItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver", DemoniacVoltageReaverItem::new);
    public static final RegistryObject<Item> OBSIDIAN_SLEDGEHAMMER = AnnoyingVillagersModItems.REGISTRY.register("obsidian_sledgehammer", ObsidianSledgehammerItem::new);
    public static final RegistryObject<Item> ENDER_AEGIS = AnnoyingVillagersModItems.REGISTRY.register("ender_aegis", EnderAegisItem::new);
    public static final RegistryObject<Item> HEROBRINE_ENDER_EYE = AnnoyingVillagersModItems.REGISTRY.register("herobrine_ender_eye", HerobrineEnderEyeItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_sword", ShadowObsidianSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_SHEAR = AnnoyingVillagersModItems.REGISTRY.register("diamond_shear", DiamondShearItem::new);
    public static final RegistryObject<Item> JESSICA_THE_DARK_SHIELD = AnnoyingVillagersModItems.REGISTRY.register("jessica_the_dark_shield", JessicaTheDarkShieldItem::new);
    // ------------------------------


    // Item not shown in creative tab
    public static final RegistryObject<Item> ENCHANT_BED = block(AnnoyingVillagersModBlocks.ENCHANT_BED, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_SHORT_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_MIDDLE_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_LONG_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR, null);
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword", HeavyAttackLegendarySwordItem::new);

    public static final RegistryObject<Item> HARD_GREATSWORD_SKILL = AnnoyingVillagersModItems.REGISTRY.register("hard_greatsword_skill", HardGreatSwordSkillItem::new);

    public static final RegistryObject<Item> SHADOW_OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK, null);
    public static final RegistryObject<Item> OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK, null);
    public static final RegistryObject<Item> BLUEDEMONTRIDENT = AnnoyingVillagersModItems.REGISTRY.register("bluedemontrident", BlueDemonTridentItem::new);
    // ------------------------------

    // Armor
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate", BlueDemonChestplateItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_helmet", CompressedDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_chestplate", CompressedDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_leggings", CompressedDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_boots", CompressedDiamondArmorItem.Boots::new);
    public static final RegistryObject<Item> RUBY_HELMET = AnnoyingVillagersModItems.REGISTRY.register("ruby_helmet", RubyArmorItem.Helmet::new);
    public static final RegistryObject<Item> RUBY_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("ruby_chestplate", RubyArmorItem.Chestplate::new);
    public static final RegistryObject<Item> RUBY_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("ruby_leggings", RubyArmorItem.Leggings::new);
    public static final RegistryObject<Item> RUBY_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("ruby_boots", RubyArmorItem.Boots::new);
    public static final RegistryObject<Item> EMERALD_HELMET = AnnoyingVillagersModItems.REGISTRY.register("emerald_helmet", EmeraldArmorItem.Helmet::new);
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("emerald_chestplate", EmeraldArmorItem.Chestplate::new);
    public static final RegistryObject<Item> EMERALD_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("emerald_leggings", EmeraldArmorItem.Leggings::new);
    public static final RegistryObject<Item> EMERALD_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("emerald_boots", EmeraldArmorItem.Boots::new);
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_helmet", VillagerScoutHelmetItem.Helmet::new);
    public static final RegistryObject<Item> CLASSICGOLDENA_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_chestplate", ClassicGoldenArmorItem.Chestplate::new);
    public static final RegistryObject<Item> CLASSICGOLDENA_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_leggings", ClassicGoldenArmorItem.Leggings::new);
    public static final RegistryObject<Item> CLASSICGOLDENA_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("classicgoldena_boots", ClassicGoldenArmorItem.Boots::new);
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_helmet", RedVillagerGeneralArmorItem.Armor::new);
    public static final RegistryObject<Item> RED_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("red_villager_general_chestplate", RedVillagerGeneralArmorItem.Chestplate::new);
    public static final RegistryObject<Item> VILLAGER_GENERAL_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("villager_general_leggings", RedVillagerGeneralArmorItem.Leggings::new);
    public static final RegistryObject<Item> VILLAGER_GENERAL_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("villager_general_boots", RedVillagerGeneralArmorItem.Boots::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_helmet", BlueVillagerGeneralArmorItem.Helmet::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_general_chestplate", BlueVillagerGeneralArmorItem.Chestplate::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_helmet", GreenVillagerGeneralArmorItem.Helmet::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("green_villager_general_chestplate", GreenVillagerGeneralArmorItem.Chestplate::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_HELMET = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_helmet", PurpleVillagerGeneralArmorItem.Helmet::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_GENERAL_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_general_chestplate", PurpleVillagerGeneralArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_helmet", UnlightDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_chestplate", UnlightDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_leggings", UnlightDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_boots", UnlightDiamondArmorItem.Boots::new);
    public static final RegistryObject<Item> HEROBRINE_OBSIDIAN_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("herobrine_obsidian_diamond_helmet", HerobrineObsidianDiamondArmorHelmetItem.Helmet::new);
    public static final RegistryObject<Item> HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("herobrine_obsidian_diamond_chestplate", HerobrineObsidianDiamondArmorChestplateItem.Chestplate::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_helmet", BrokenDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_chestplate", BrokenDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_leggings", BrokenDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_boots", BrokenDiamondArmorItem.Boots::new);

    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> new BlockItem(registryobject.get(), (new Properties())));
    }
}
