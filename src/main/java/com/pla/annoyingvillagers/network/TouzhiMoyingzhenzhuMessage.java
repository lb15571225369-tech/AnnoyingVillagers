package com.pla.annoyingvillagers.network;

import java.util.function.Supplier;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.procedures.TouzhiMoyingzhenzhuAnXiaAnJianShiProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = Bus.MOD)
public class TouzhiMoyingzhenzhuMessage {

    int type;
    int pressedms;

    public TouzhiMoyingzhenzhuMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public TouzhiMoyingzhenzhuMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(TouzhiMoyingzhenzhuMessage touzhimoyingzhenzhumessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(touzhimoyingzhenzhumessage.type);
        friendlybytebuf.writeInt(touzhimoyingzhenzhumessage.pressedms);
    }

    public static void handler(TouzhiMoyingzhenzhuMessage touzhimoyingzhenzhumessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            pressAction(context.getSender(), touzhimoyingzhenzhumessage.type, touzhimoyingzhenzhumessage.pressedms);
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
                TouzhiMoyingzhenzhuAnXiaAnJianShiProcedure.execute(level, player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(TouzhiMoyingzhenzhuMessage.class, TouzhiMoyingzhenzhuMessage::buffer, TouzhiMoyingzhenzhuMessage::new, TouzhiMoyingzhenzhuMessage::handler);
    }
}

