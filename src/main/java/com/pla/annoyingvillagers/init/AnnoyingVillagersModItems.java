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
    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE, -10066330, -13421773, (new Properties()).tab(CreativeModeTab.TAB_MISC));
    });
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_DEMON, -16737895, -16777216, (new Properties()).tab(CreativeModeTab.TAB_MISC));
    });
    public static final RegistryObject<Item> HEROBRINE_2_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_2_spawn_egg", () -> {
        return new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_2, -10066330, -13421773, (new Properties()).tab(CreativeModeTab.TAB_MISC));
    });
    // public static final RegistryObject<Item> TIME_BOMB = AnnoyingVillagersModItems.REGISTRY.register("time_bomb", () -> {
    //     return new TimeBombItem();
    // });
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", () -> {
        return new EnchantBedItemItem();
    });
//    public static final RegistryObject<Item> DROP_ALL = AnnoyingVillagersModItems.REGISTRY.register("drop_all", () -> {
//        return new DropAllItem();
//    });
    // public static final RegistryObject<Item> BOMB_SPAWN_ITEM = AnnoyingVillagersModItems.REGISTRY.register("bomb_spawn_item", () -> {
    //     return new NoSoundBombItem();
    // });
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", () -> {
        return new VillagerHeadItem();
    });
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", () -> {
        return new LegendarySwordItem();
    });
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate_chestplate", () -> {
        return new BlueDemonChestplateItem.Chestplate();
    });
//    public static final RegistryObject<Item> C_4 = block(AnnoyingVillagersModBlocks.C_4, (CreativeModeTab) null);
//    public static final RegistryObject<Item> C_4DAMAGE = block(AnnoyingVillagersModBlocks.C_4DAMAGE, (CreativeModeTab) null);
    public static final RegistryObject<Item> ENCHANT_BED = block(AnnoyingVillagersModBlocks.ENCHANT_BED, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARK_OB_SS = block(AnnoyingVillagersModBlocks.DARK_OB_SS, (CreativeModeTab) null);
    public static final RegistryObject<Item> NONEOB = block(AnnoyingVillagersModBlocks.NONEOB, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARKOB = block(AnnoyingVillagersModBlocks.DARKOB, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARKOBITEM = AnnoyingVillagersModItems.REGISTRY.register("darkobitem", () -> {
        return new DarkobitemItem();
    });
    public static final RegistryObject<Item> DARKITEMLONG = AnnoyingVillagersModItems.REGISTRY.register("darkitemlong", () -> {
        return new DarkitemlongItem();
    });
    public static final RegistryObject<Item> DARK_OB_UP = block(AnnoyingVillagersModBlocks.DARK_OB_UP, (CreativeModeTab) null);
    public static final RegistryObject<Item> DARK_OB_FAR = AnnoyingVillagersModItems.REGISTRY.register("dark_ob_far", () -> {
        return new DarkOBFarItem();
    });
//    public static final RegistryObject<Item> DROP_ALL_ITEM_SPAWN = block(AnnoyingVillagersModBlocks.DROP_ALL_ITEM_SPAWN, (CreativeModeTab) null);
//    public static final RegistryObject<Item> C_4SPAWN = block(AnnoyingVillagersModBlocks.C_4SPAWN, (CreativeModeTab) null);
//    public static final RegistryObject<Item> NONE = AnnoyingVillagersModItems.REGISTRY.register("none", () -> {
//        return new NoneItem();
//    });
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword", () -> {
        return new HeavyAttackLegendarySwordItem();
    });
    public static final RegistryObject<Item> HARD_GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hard_great_sword", () -> {
        return new HardGreatSwordItem();
    });
    public static final RegistryObject<Item> HARD_GREAT_SWORD_SKILL = AnnoyingVillagersModItems.REGISTRY.register("hard_great_sword_skill", () -> {
        return new HardGreatSwordSkillItem();
    });
    public static final RegistryObject<Item> WAKE_UP_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("wake_up_legendary_sword", () -> {
        return new WakeUpLegendarySwordItem();
    });
//    public static final RegistryObject<Item> UPDATE = AnnoyingVillagersModItems.REGISTRY.register("update", () -> {
//        return new UpdateItem();
//    });
    public static final RegistryObject<Item> ANYINGHEIYAOSHIWUQI = AnnoyingVillagersModItems.REGISTRY.register("anyingheiyaoshiwuqi", () -> {
        return new AnyingheiyaoshiwuqiItem();
    });
    public static final RegistryObject<Item> OBSIDIANWEAPONS = AnnoyingVillagersModItems.REGISTRY.register("obsidianweapons", () -> {
        return new ObsidianweaponsItem();
    });
    public static final RegistryObject<Item> ANYINGHEIYAOSHI = block(AnnoyingVillagersModBlocks.ANYINGHEIYAOSHI, (CreativeModeTab) null);
    public static final RegistryObject<Item> PUTONGHEIYAOSHI = block(AnnoyingVillagersModBlocks.PUTONGHEIYAOSHI, (CreativeModeTab) null);
    public static final RegistryObject<Item> PUTONGHEIYAOSHI_2 = block(AnnoyingVillagersModBlocks.PUTONGHEIYAOSHI_2, (CreativeModeTab) null);
    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> {
            return new BlockItem((Block) registryobject.get(), (new Properties()).tab(creativemodetab));
        });
    }
}
