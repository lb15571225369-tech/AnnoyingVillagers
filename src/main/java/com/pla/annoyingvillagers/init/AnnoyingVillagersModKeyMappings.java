package com.pla.annoyingvillagers.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.pla.annoyingvillagers.AnnoyingVillagersMod;
import com.pla.annoyingvillagers.network.CheckMessage;
import com.pla.annoyingvillagers.network.KickMessage;
import com.pla.annoyingvillagers.network.MusicPlayerOpenMessage;
import com.pla.annoyingvillagers.network.OpenEmojiMessage;
import com.pla.annoyingvillagers.network.WeaponsMoreAttackMessage;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModKeyMappings {

    public static final KeyMapping CHECK = new KeyMapping("key.annoying_villagers.check", 78, "key.categories.gameplay") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new CheckMessage(0, 0));
                CheckMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };
    public static final KeyMapping MUSIC_PLAYER_OPEN = new KeyMapping("key.annoying_villagers.music_player_open", 74, "key.categories.misc") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new MusicPlayerOpenMessage(0, 0));
                MusicPlayerOpenMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };
    public static final KeyMapping KICK = new KeyMapping("key.annoying_villagers.kick", 88, "key.categories.misc") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new KickMessage(0, 0));
                KickMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                AnnoyingVillagersModKeyMappings.KICK_LASTPRESS = System.currentTimeMillis();
            } else if (this.isDownOld != flag && !flag) {
                int i = (int) (System.currentTimeMillis() - AnnoyingVillagersModKeyMappings.KICK_LASTPRESS);

                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new KickMessage(1, i));
                KickMessage.pressAction(Minecraft.getInstance().player, 1, i);
            }

            this.isDownOld = flag;
        }
    };
    public static final KeyMapping WEAPONS_MORE_ATTACK = new KeyMapping("key.annoying_villagers.weapons_more_attack", 67, "key.categories.misc") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new WeaponsMoreAttackMessage(0, 0));
                WeaponsMoreAttackMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };
    public static final KeyMapping OPEN_EMOJI = new KeyMapping("key.annoying_villagers.open_emoji", 320, "key.categories.misc") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new OpenEmojiMessage(0, 0));
                OpenEmojiMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };
    private static long KICK_LASTPRESS = 0L;

    @SubscribeEvent
    public static void registerKeyBindings(FMLClientSetupEvent fmlclientsetupevent) {
        ClientRegistry.registerKeyBinding(AnnoyingVillagersModKeyMappings.CHECK);
        ClientRegistry.registerKeyBinding(AnnoyingVillagersModKeyMappings.MUSIC_PLAYER_OPEN);
        ClientRegistry.registerKeyBinding(AnnoyingVillagersModKeyMappings.KICK);
        ClientRegistry.registerKeyBinding(AnnoyingVillagersModKeyMappings.WEAPONS_MORE_ATTACK);
        ClientRegistry.registerKeyBinding(AnnoyingVillagersModKeyMappings.OPEN_EMOJI);
    }

    @EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent clienttickevent) {
            if (Minecraft.getInstance().screen == null) {
                AnnoyingVillagersModKeyMappings.CHECK.consumeClick();
                AnnoyingVillagersModKeyMappings.MUSIC_PLAYER_OPEN.consumeClick();
                AnnoyingVillagersModKeyMappings.KICK.consumeClick();
                AnnoyingVillagersModKeyMappings.WEAPONS_MORE_ATTACK.consumeClick();
                AnnoyingVillagersModKeyMappings.OPEN_EMOJI.consumeClick();
            }

        }
    }
}
