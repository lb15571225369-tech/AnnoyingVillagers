package com.pla.annoyingvillagers.init;

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
import com.pla.annoyingvillagers.network.SpecialAttackMessage;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModKeyMappings {
    public static final KeyMapping KICK = new KeyMapping(
            "key.annoyingvillagers.kick",
            GLFW.GLFW_KEY_X,
            "key.categories.annoyingvillagers"
    ) {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);

            if (this.isDownOld != flag && flag) {
                int strafe = readStrafeAD();
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new KickMessage(strafe));
            }

            this.isDownOld = flag;
        }
    };

    private static int readStrafeAD() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;

        boolean left = mc.options.keyLeft.isDown();
        boolean right = mc.options.keyRight.isDown();

        if (left == right) return 0;
        return left ? -1 : 1;
    }

    public static final KeyMapping SPECIAL_ATTACK = new KeyMapping(
            "key.annoyingvillagers.special_attack",
            GLFW.GLFW_KEY_C,
            "key.categories.annoyingvillagers") {
        private static final int HOLD_THRESHOLD_TICKS = 10; // 10 ticks = 0.5 second

        private boolean isDownOld = false;
        private int pressedAtTick = -1;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null) {
                this.isDownOld = flag;
                return;
            }

            if (!this.isDownOld && flag) {
                this.pressedAtTick = minecraft.player.tickCount;
            }

            if (this.isDownOld && !flag) {
                int heldTicks = this.pressedAtTick >= 0
                        ? minecraft.player.tickCount - this.pressedAtTick
                        : 0;

                int type = heldTicks >= HOLD_THRESHOLD_TICKS ? 1 : 0;
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new SpecialAttackMessage(type, heldTicks));
                this.pressedAtTick = -1;
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping THROW_ENDER_PEARL = new KeyMapping(
            "key.annoyingvillagers.throw_ender_pearl",
            GLFW.GLFW_KEY_F,
            "key.categories.annoyingvillagers") {
        private boolean isDownOld = false;

        public void setDown(boolean flag) {
            super.setDown(flag);
            if (this.isDownOld != flag && flag && Minecraft.getInstance().player != null) {
                AnnoyingVillagers.PACKET_HANDLER.sendToServer(new ThrowingEnderPearlMessage(0, 0));
                ThrowingEnderPearlMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }

            this.isDownOld = flag;
        }
    };

    public static final KeyMapping DRAGON_FLIGHT_DESCENT_KEY = new KeyMapping(
            "key.annoyingvillagers.dragon_flight_descent",
            GLFW.GLFW_KEY_Z,
            "key.categories.annoyingvillagers");

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(AnnoyingVillagersModKeyMappings.KICK);
        event.register(AnnoyingVillagersModKeyMappings.SPECIAL_ATTACK);
        event.register(AnnoyingVillagersModKeyMappings.THROW_ENDER_PEARL);
        event.register(AnnoyingVillagersModKeyMappings.DRAGON_FLIGHT_DESCENT_KEY);
    }

    @EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent clienttickevent) {
            if (Minecraft.getInstance().screen == null) {
                AnnoyingVillagersModKeyMappings.KICK.consumeClick();
                AnnoyingVillagersModKeyMappings.SPECIAL_ATTACK.consumeClick();
                AnnoyingVillagersModKeyMappings.THROW_ENDER_PEARL.consumeClick();
            }

        }
    }
}
