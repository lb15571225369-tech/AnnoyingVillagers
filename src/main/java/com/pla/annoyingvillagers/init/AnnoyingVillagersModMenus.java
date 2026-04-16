package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.world.EmoteMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, AnnoyingVillagers.MODID);

    public static final RegistryObject<MenuType<EmoteMenu>> EMOTE =
            MENUS.register("emote", () -> IForgeMenuType.create(EmoteMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}