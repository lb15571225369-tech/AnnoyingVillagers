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
import com.pla.annoyingvillagers.AnnoyingVillagersMod;
import com.pla.annoyingvillagers.procedures.OpenEmojiAnXiaAnJianShiProcedure;

@EventBusSubscriber(bus = Bus.MOD)
public class OpenEmojiMessage {

    int type;
    int pressedms;

    public OpenEmojiMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public OpenEmojiMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(OpenEmojiMessage openemojimessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(openemojimessage.type);
        friendlybytebuf.writeInt(openemojimessage.pressedms);
    }

    public static void handler(OpenEmojiMessage openemojimessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            pressAction(context.getSender(), openemojimessage.type, openemojimessage.pressedms);
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
                OpenEmojiAnXiaAnJianShiProcedure.execute(level, d0, d1, d2, player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagersMod.addNetworkMessage(OpenEmojiMessage.class, OpenEmojiMessage::buffer, OpenEmojiMessage::new, OpenEmojiMessage::handler);
    }
}
