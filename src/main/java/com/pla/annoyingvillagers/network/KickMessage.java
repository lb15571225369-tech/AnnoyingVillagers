package com.pla.annoyingvillagers.network;

import java.util.function.Supplier;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.KickOnKeyPressedProcedure;
import com.pla.annoyingvillagers.procedures.KickOnKeyReleasedProcedure;

@EventBusSubscriber(bus = Bus.MOD)
public class KickMessage {

    int type;
    int pressedms;

    public KickMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public KickMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(KickMessage kickmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(kickmessage.type);
        friendlybytebuf.writeInt(kickmessage.pressedms);
    }

    public static void handler(KickMessage kickmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            pressAction(context.getSender(), kickmessage.type, kickmessage.pressedms);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player player, int i, int j) {
        Level level = player.level;
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        if (level.hasChunkAt(player.blockPosition())) {
            if (i == 0) {
                KickOnKeyPressedProcedure.execute(level, player);
            }

            if (i == 1) {
                KickOnKeyReleasedProcedure.execute(level, player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(KickMessage.class, KickMessage::buffer, KickMessage::new, KickMessage::handler);
    }
}
