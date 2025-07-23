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
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}