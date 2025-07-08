package com.pla.annoyingvillagers.init;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.IContainerFactory;
import com.pla.annoyingvillagers.world.inventory.EmojiMenu;
import com.pla.annoyingvillagers.world.inventory.EmoteMenu;
import com.pla.annoyingvillagers.world.inventory.IdCheckMenu;
import com.pla.annoyingvillagers.world.inventory.LoginMusicMenu;
import com.pla.annoyingvillagers.world.inventory.MusicBoxSelectMenu;
import com.pla.annoyingvillagers.world.inventory.MusicPlayerGuiMenu;
import com.pla.annoyingvillagers.world.inventory.MusicSearchMenu;

@EventBusSubscriber(bus = Bus.MOD)
public class AnnoyingVillagersModMenus {

    private static final List<MenuType<?>> REGISTRY = new ArrayList();
    public static final MenuType<MusicPlayerGuiMenu> MUSIC_PLAYER_GUI = register("music_player_gui", (i, inventory, friendlybytebuf) -> {
        return new MusicPlayerGuiMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<LoginMusicMenu> LOGIN_MUSIC = register("login_music", (i, inventory, friendlybytebuf) -> {
        return new LoginMusicMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<IdCheckMenu> ID_CHECK = register("id_check", (i, inventory, friendlybytebuf) -> {
        return new IdCheckMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<MusicSearchMenu> MUSIC_SEARCH = register("music_search", (i, inventory, friendlybytebuf) -> {
        return new MusicSearchMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<EmojiMenu> EMOJI = register("emoji", (i, inventory, friendlybytebuf) -> {
        return new EmojiMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<EmoteMenu> EMOTE = register("emote", (i, inventory, friendlybytebuf) -> {
        return new EmoteMenu(i, inventory, friendlybytebuf);
    });
    public static final MenuType<MusicBoxSelectMenu> MUSIC_BOX_SELECT = register("music_box_select", (i, inventory, friendlybytebuf) -> {
        return new MusicBoxSelectMenu(i, inventory, friendlybytebuf);
    });

    private static <T extends AbstractContainerMenu> MenuType<T> register(String s, IContainerFactory<T> icontainerfactory) {
        MenuType<T> menutype = new MenuType(icontainerfactory);

        menutype.setRegistryName(s);
        AnnoyingVillagersModMenus.REGISTRY.add(menutype);
        return menutype;
    }

    @SubscribeEvent
    public static void registerContainers(Register<MenuType<?>> register) {
        register.getRegistry().registerAll((MenuType[]) AnnoyingVillagersModMenus.REGISTRY.toArray(new MenuType[0]));
    }
}
