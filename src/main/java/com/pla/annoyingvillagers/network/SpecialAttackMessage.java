package com.pla.annoyingvillagers.network;

import java.util.Objects;
import java.util.function.Supplier;

import com.pla.annoyingvillagers.event.SpecialAttackOnKeyHeldEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.event.SpecialAttackOnKeyPressedEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class SpecialAttackMessage {
    int type;
    int presses;

    public SpecialAttackMessage(int type, int presses) {
        this.type = type;
        this.presses = presses;
    }

    public SpecialAttackMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.presses = friendlybytebuf.readInt();
    }

    public static void buffer(SpecialAttackMessage specialAttackMessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(specialAttackMessage.type);
        friendlybytebuf.writeInt(specialAttackMessage.presses);
    }

    public static void handler(SpecialAttackMessage specialAttackMessage, Supplier<Context> supplier) {
        Context context = supplier.get();

        context.enqueueWork(() -> {
            pressAction(Objects.requireNonNull(context.getSender()), specialAttackMessage.type, specialAttackMessage.presses);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player player, int type, int presses) {
        Level level = player.level();
        if (type == 0) {
            SpecialAttackOnKeyPressedEvent.execute(level, player);
        } else if (type == 1) {
            SpecialAttackOnKeyHeldEvent.execute(level, player);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(SpecialAttackMessage.class, SpecialAttackMessage::buffer, SpecialAttackMessage::new, SpecialAttackMessage::handler);
    }
}
