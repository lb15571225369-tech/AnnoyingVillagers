package com.pla.annoyingvillagers.init;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.network.ThrowingEnderPearlMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.KickMessage;
import com.pla.annoyingvillagers.network.WeaponsMoreAttackMessage;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModKeyMappings {
    public static final KeyMapping KICK = new KeyMapping("key.annoyingvillagers.kick", GLFW.GLFW_KEY_X, "key.categories.annoyingvillagers") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new KickMessage(0, 0));
                KickMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                AnnoyingVillagersModKeyMappings.KICK_LASTPRESS = System.currentTimeMillis();
            } else if (this.isDownOld != flag && !flag) {
                int i = (int) (System.currentTimeMillis() - AnnoyingVillagersModKeyMappings.KICK_LASTPRESS);

                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new KickMessage(1, i));
                KickMessage.pressAction(Minecraft.getInstance().player, 1, i);
            }

            this.isDownOld = flag;
        }
    };
    public static final KeyMapping WEAPONS_MORE_ATTACK = new KeyMapping("key.annoyingvillagers.weapons_more_attack", GLFW.GLFW_KEY_C, "key.categories.annoyingvillagers") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new WeaponsMoreAttackMessage(0, 0));
                WeaponsMoreAttackMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };
    private static long KICK_LASTPRESS = 0L;

    public static final KeyMapping THROW_ENDER_PEARL = new KeyMapping("key.annoyingvillagers.throw_ender_pearl", GLFW.GLFW_KEY_F, "key.categories.annoyingvillagers") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag) {
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new ThrowingEnderPearlMessage(0, 0));
                ThrowingEnderPearlMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(AnnoyingVillagersModKeyMappings.KICK);
        event.register(AnnoyingVillagersModKeyMappings.WEAPONS_MORE_ATTACK);
        event.register(AnnoyingVillagersModKeyMappings.THROW_ENDER_PEARL);
    }

    @EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent clienttickevent) {
            if (Minecraft.getInstance().screen == null) {
                AnnoyingVillagersModKeyMappings.KICK.consumeClick();
                AnnoyingVillagersModKeyMappings.WEAPONS_MORE_ATTACK.consumeClick();
                AnnoyingVillagersModKeyMappings.THROW_ENDER_PEARL.consumeClick();
            }

        }
    }
}
