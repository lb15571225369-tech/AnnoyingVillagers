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
import com.pla.annoyingvillagers.procedures.WeaponsMoreAttackOnKeyPressedProcedure;

@EventBusSubscriber(bus = Bus.MOD)
public class WeaponsMoreAttackMessage {

    int type;
    int pressedms;

    public WeaponsMoreAttackMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public WeaponsMoreAttackMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(WeaponsMoreAttackMessage weaponsmoreattackmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(weaponsmoreattackmessage.type);
        friendlybytebuf.writeInt(weaponsmoreattackmessage.pressedms);
    }

    public static void handler(WeaponsMoreAttackMessage weaponsmoreattackmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            try {
                pressAction(context.getSender(), weaponsmoreattackmessage.type, weaponsmoreattackmessage.pressedms);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player player, int i, int j) throws CommandSyntaxException {
        Level level = player.level;
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        if (level.hasChunkAt(player.blockPosition())) {
            if (i == 0) {
                WeaponsMoreAttackOnKeyPressedProcedure.execute(level, player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagers.addNetworkMessage(WeaponsMoreAttackMessage.class, WeaponsMoreAttackMessage::buffer, WeaponsMoreAttackMessage::new, WeaponsMoreAttackMessage::handler);
    }
}
