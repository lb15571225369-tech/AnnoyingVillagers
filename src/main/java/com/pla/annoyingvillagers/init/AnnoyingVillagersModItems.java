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
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", () -> {
        return new EnchantBedItemItem();
    });
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", () -> {
        return new VillagerHeadItem();
    });
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", () -> {
        return new LegendarySwordItem();
    });
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate_chestplate", () -> {
        return new BlueDemonChestplateItem.Chestplate();
    });
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
    public static final RegistryObject<Item> ANYINGHEIYAOSHIWUQI = AnnoyingVillagersModItems.REGISTRY.register("anyingheiyaoshiwuqi", () -> {
        return new AnyingheiyaoshiwuqiItem();
    });
    public static final RegistryObject<Item> OBSIDIANWEAPONS = AnnoyingVillagersModItems.REGISTRY.register("obsidianweapons", () -> {
        return new ObsidianweaponsItem();
    });
    public static final RegistryObject<Item> ANYINGHEIYAOSHI = block(AnnoyingVillagersModBlocks.ANYINGHEIYAOSHI, (CreativeModeTab) null);
    public static final RegistryObject<Item> PUTONGHEIYAOSHI = block(AnnoyingVillagersModBlocks.PUTONGHEIYAOSHI, (CreativeModeTab) null);
    public static final RegistryObject<Item> BLUEDEMONTRIDENT = AnnoyingVillagersModItems.REGISTRY.register("bluedemontrident", () -> {
        return new BluedemontridentItem();
    });
    public static final RegistryObject<Item> DAN_SHOU_ZHAN_SHEN_ZHI_REN = AnnoyingVillagersModItems.REGISTRY.register("dan_shou_zhan_shen_zhi_ren", () -> {
        return new DanShouZhanShenZhiRenItem();
    });
    public static final RegistryObject<Item> ZUAN_SHI_CHANG_MAO = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_chang_mao", () -> {
        return new ZuanShiChangMaoItem();
    });
    public static final RegistryObject<Item> CCHUNZUANTAO_HELMET = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_helmet", () -> {
        return new CchunzuantaoItem.Helmet();
    });
    public static final RegistryObject<Item> CCHUNZUANTAO_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_chestplate", () -> {
        return new CchunzuantaoItem.Chestplate();
    });
    public static final RegistryObject<Item> CCHUNZUANTAO_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_leggings", () -> {
        return new CchunzuantaoItem.Leggings();
    });
    public static final RegistryObject<Item> CCHUNZUANTAO_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("cchunzuantao_boots", () -> {
        return new CchunzuantaoItem.Boots();
    });
    public static final RegistryObject<Item> CCHUNDUZUANSHI = AnnoyingVillagersModItems.REGISTRY.register("cchunduzuanshi", () -> {
        return new CchunduzuanshiItem();
    });
    public static final RegistryObject<Item> ZUAN_SHI_CHANG_JIAN = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_chang_jian", () -> {
        return new ZuanShiChangJianItem();
    });
    public static final RegistryObject<Item> CHUNCUIDEMOFAZUANSHI_HELMET = AnnoyingVillagersModItems.REGISTRY.register("chuncuidemofazuanshi_helmet", () -> {
        return new ChuncuidemofazuanshiItem.Helmet();
    });
    public static final RegistryObject<Item> CHUNCUIDEMOFAZUANSHI_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("chuncuidemofazuanshi_chestplate", () -> {
        return new ChuncuidemofazuanshiItem.Chestplate();
    });
    public static final RegistryObject<Item> CHUNCUIDEMOFAZUANSHI_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("chuncuidemofazuanshi_leggings", () -> {
        return new ChuncuidemofazuanshiItem.Leggings();
    });
    public static final RegistryObject<Item> CHUNCUIDEMOFAZUANSHI_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("chuncuidemofazuanshi_boots", () -> {
        return new ChuncuidemofazuanshiItem.Boots();
    });
    public static final RegistryObject<Item> ENCHANED_DIAMOND = AnnoyingVillagersModItems.REGISTRY.register("enchaned_diamond", () -> {
        return new EnchanedDiamondItem();
    });
    public static final RegistryObject<Item> ZUAN_SHI_JU_JIAN = AnnoyingVillagersModItems.REGISTRY.register("zuan_shi_ju_jian", () -> {
        return new ZuanShiJuJianItem();
    });
    public static final RegistryObject<Item> LLBSTZ_HELMET = AnnoyingVillagersModItems.REGISTRY.register("llbstz_helmet", () -> {
        return new LlbstzItem.Helmet();
    });
    public static final RegistryObject<Item> LLBSTZ_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("llbstz_chestplate", () -> {
        return new LlbstzItem.Chestplate();
    });
    public static final RegistryObject<Item> LLBSTZ_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("llbstz_leggings", () -> {
        return new LlbstzItem.Leggings();
    });
    public static final RegistryObject<Item> LLBSTZ_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("llbstz_boots", () -> {
        return new LlbstzItem.Boots();
    });
    public static final RegistryObject<Item> LAN_CUN_QI_JIAN = AnnoyingVillagersModItems.REGISTRY.register("lan_cun_qi_jian", () -> {
        return new LanCunQiJianItem();
    });
    public static final RegistryObject<Item> GGONGZUOTAI = AnnoyingVillagersModItems.REGISTRY.register("ggongzuotai", () -> {
        return new GgongzuotaiItem();
    });
    public static final RegistryObject<Item> FUMOMUMEN = AnnoyingVillagersModItems.REGISTRY.register("fumomumen", () -> {
        return new FumomumenItem();
    });
    public static final RegistryObject<Item> MUMEN = AnnoyingVillagersModItems.REGISTRY.register("mumen", () -> {
        return new MumenItem();
    });
    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> {
            return new BlockItem((Block) registryobject.get(), (new Properties()).tab(creativemodetab));
        });
    }
}
