package com.pla.annoyingvillagers.network;

import java.util.function.Supplier;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.ThrowingPearlKeyPressedProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = Bus.MOD)
public class ThrowingEnderPearlMessage {

    int type;
    int pressedms;

    public ThrowingEnderPearlMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public ThrowingEnderPearlMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(ThrowingEnderPearlMessage throwingEnderPearlMessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(throwingEnderPearlMessage.type);
        friendlybytebuf.writeInt(throwingEnderPearlMessage.pressedms);
    }

    public static void handler(ThrowingEnderPearlMessage throwingEnderPearlMessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            pressAction(context.getSender(), throwingEnderPearlMessage.type, throwingEnderPearlMessage.pressedms);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player player, int i, int j) {
        Level level = player.level();

        if (level.hasChunkAt(player.blockPosition()) && !level.isClientSide()) {
            if (i == 0) {
                ThrowingPearlKeyPressedProcedure.execute(level, player);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(ThrowingEnderPearlMessage.class, ThrowingEnderPearlMessage::buffer, ThrowingEnderPearlMessage::new, ThrowingEnderPearlMessage::handler);
    }
}

