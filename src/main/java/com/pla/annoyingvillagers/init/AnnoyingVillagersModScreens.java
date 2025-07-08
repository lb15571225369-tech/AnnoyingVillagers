package com.pla.annoyingvillagers.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.pla.annoyingvillagers.client.gui.EmojiScreen;
import com.pla.annoyingvillagers.client.gui.EmoteScreen;
import com.pla.annoyingvillagers.client.gui.IdCheckScreen;
import com.pla.annoyingvillagers.client.gui.LoginMusicScreen;
import com.pla.annoyingvillagers.client.gui.MusicBoxSelectScreen;
import com.pla.annoyingvillagers.client.gui.MusicPlayerGuiScreen;
import com.pla.annoyingvillagers.client.gui.MusicSearchScreen;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModScreens {

    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent fmlclientsetupevent) {
        fmlclientsetupevent.enqueueWork(() -> {
            MenuScreens.register(AnnoyingVillagersModMenus.MUSIC_PLAYER_GUI, MusicPlayerGuiScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.LOGIN_MUSIC, LoginMusicScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.ID_CHECK, IdCheckScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.MUSIC_SEARCH, MusicSearchScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.EMOJI, EmojiScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.EMOTE, EmoteScreen::new);
            MenuScreens.register(AnnoyingVillagersModMenus.MUSIC_BOX_SELECT, MusicBoxSelectScreen::new);
        });
    }
}
