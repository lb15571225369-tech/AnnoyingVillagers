package com.pla.annoyingvillagers.init;


import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AnnoyingVillagers.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("annoyingvillagers_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()))
                    .title(Component.translatable("creativetab.annoyingvillagers_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_1_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_2_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_3_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_5_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_6_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_7_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.NULL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.ARMORED_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.DARK_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.GLAIVE_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.REAPER_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.SWORDSMAN_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.SLEDGEHAMMER_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.AEGIS_HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_DEMON_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_SCOUT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.STEVE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.ANGRY_STEVE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.ALEX_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.CHRIS_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.JEV_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.BBQ_SAUCE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_HEAD.get());
                        pOutput.accept(AnnoyingVillagersModItems.JEV_GLASSES.get());
                        pOutput.accept(AnnoyingVillagersModItems.JEV_BOOK.get());
                        pOutput.accept(AnnoyingVillagersModItems.JEV_PENCIL.get());
                        pOutput.accept(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_HILT.get());
                        pOutput.accept(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_FRAGMENT.get());
                        pOutput.accept(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.POISON_EGG_ITEM.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get());
                        pOutput.accept(AnnoyingVillagersModItems.CRAFTING_TABLE.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());
                        pOutput.accept(AnnoyingVillagersModItems.WOODEN_DOOR.get());
                        pOutput.accept(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get());
                        pOutput.accept(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                        pOutput.accept(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get());
                        pOutput.accept(AnnoyingVillagersModItems.BEDROCK_WEAPON.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENDER_GLAIVE.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get());
                        pOutput.accept(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENDER_AEGIS.get());
                        pOutput.accept(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HARD_GREAT_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SPEAR.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_LONG_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_DAGGER.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.PALADIN_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_MAGNET_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SABER.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_GREATE_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_LONG_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_HALBERD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SCYTHE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_TWIN_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_GIANT_AXE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_BATTLE_AXE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_GLAIVE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_DOUBLE_BIT_AXE.get());
                        pOutput.accept(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.GOLDEN_LONG_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_LONG_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_CLEAVER.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_GLAIVE.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_SCYTHE.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_SPEAR.get());
                        pOutput.accept(AnnoyingVillagersModItems.IRON_POLEAXE.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_DOUBLE_BIT_AXE.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_GREAT_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.RED_DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_GEM_LONG_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_GEM.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_BOOTS.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.RUBY_BOOTS.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.EMERALD_BOOTS.get());
                        pOutput.accept(AnnoyingVillagersModItems.UNLIGHT_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.UNLIGHT_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.UNLIGHT_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.UNLIGHT_DIAMOND_BOOTS.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_VILLAGER_GENERAL_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_VILLAGER_GENERAL_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_GENERAL_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_GENERAL_BOOTS.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}