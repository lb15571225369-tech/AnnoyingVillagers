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
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.HEROBRINE_2_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_DEMON_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_SCOUT_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.GREEN_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.PURPLE_VILLAGER_GENERAL_SPAWN_EGG.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get());
                        pOutput.accept(AnnoyingVillagersModItems.VILLAGER_HEAD.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANTED_ENDER_PEARL.get());
                        pOutput.accept(AnnoyingVillagersModItems.LEGENDARY_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HARD_GREAT_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                        pOutput.accept(AnnoyingVillagersModItems.OBSIDIAN_WEAPONS.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SPEAR.get());
                        pOutput.accept(AnnoyingVillagersModItems.CRAFTING_TABLE.get());
                        pOutput.accept(AnnoyingVillagersModItems.ENCHANTED_WOODEN_DOOR.get());
                        pOutput.accept(AnnoyingVillagersModItems.WOODEN_DOOR.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_LONG_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_GREAT_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_BLADE.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_DAGGER.get());
                        pOutput.accept(AnnoyingVillagersModItems.DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get());
                        pOutput.accept(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_HELMET.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_CHESTPLATE.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_LEGGINGS.get());
                        pOutput.accept(AnnoyingVillagersModItems.COMPRESSED_DIAMOND_BOOTS.get());
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