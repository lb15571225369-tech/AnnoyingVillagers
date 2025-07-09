package com.pla.annoyingvillagers.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.CheckAnXiaAnJianShiProcedure;

@EventBusSubscriber(bus = Bus.MOD)
public class CheckMessage {

    int type;
    int pressedms;

    public CheckMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public CheckMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(CheckMessage checkmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(checkmessage.type);
        friendlybytebuf.writeInt(checkmessage.pressedms);
    }

    public static void handler(CheckMessage checkmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            pressAction(context.getSender(), checkmessage.type, checkmessage.pressedms);
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
                CheckAnXiaAnJianShiProcedure.execute(player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(CheckMessage.class, CheckMessage::buffer, CheckMessage::new, CheckMessage::handler);
    }
}
