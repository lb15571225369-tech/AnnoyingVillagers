package com.pla.annoyingvillagers.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import com.pla.annoyingvillagers.AnnoyingVillagersMod;
import com.pla.annoyingvillagers.procedures.ButtonLoginProcedure;
import com.pla.annoyingvillagers.procedures.XProcedure;
import com.pla.annoyingvillagers.world.inventory.IdCheckMenu;

@EventBusSubscriber(bus = Bus.MOD)
public class IdCheckButtonMessage {

    private final int buttonID;
    private final int x;
    private final int y;
    private final int z;
    private HashMap<String, String> textstate;

    public IdCheckButtonMessage(FriendlyByteBuf friendlybytebuf) {
        this.buttonID = friendlybytebuf.readInt();
        this.x = friendlybytebuf.readInt();
        this.y = friendlybytebuf.readInt();
        this.z = friendlybytebuf.readInt();
        this.textstate = readTextState(friendlybytebuf);
    }

    public IdCheckButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
        this.buttonID = i;
        this.x = j;
        this.y = k;
        this.z = l;
        this.textstate = hashmap;
    }

    public static void buffer(IdCheckButtonMessage idcheckbuttonmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(idcheckbuttonmessage.buttonID);
        friendlybytebuf.writeInt(idcheckbuttonmessage.x);
        friendlybytebuf.writeInt(idcheckbuttonmessage.y);
        friendlybytebuf.writeInt(idcheckbuttonmessage.z);
        writeTextState(idcheckbuttonmessage.textstate, friendlybytebuf);
    }

    public static void handler(IdCheckButtonMessage idcheckbuttonmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer serverplayer = context.getSender();
            int i = idcheckbuttonmessage.buttonID;
            int j = idcheckbuttonmessage.x;
            int k = idcheckbuttonmessage.y;
            int l = idcheckbuttonmessage.z;
            HashMap<String, String> hashmap = idcheckbuttonmessage.textstate;

            handleButtonAction(serverplayer, i, j, k, l, hashmap);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
        Level level = player.level;
        HashMap hashmap1 = IdCheckMenu.guistate;
        Iterator iterator = hashmap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry) iterator.next();
            String s = (String) entry.getKey();
            String s1 = (String) entry.getValue();

            hashmap1.put(s, s1);
        }

        if (level.hasChunkAt(new BlockPos(j, k, l))) {
            if (i == 0) {
                ButtonLoginProcedure.execute(player, hashmap1);
            }

            if (i == 1) {
                XProcedure.execute(player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagersMod.addNetworkMessage(IdCheckButtonMessage.class, IdCheckButtonMessage::buffer, IdCheckButtonMessage::new, IdCheckButtonMessage::handler);
    }

    public static void writeTextState(HashMap<String, String> hashmap, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(hashmap.size());
        Iterator iterator = hashmap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry) iterator.next();

            friendlybytebuf.writeUtf((String) entry.getKey());
            friendlybytebuf.writeUtf((String) entry.getValue());
        }

    }

    public static HashMap<String, String> readTextState(FriendlyByteBuf friendlybytebuf) {
        int i = friendlybytebuf.readInt();
        HashMap<String, String> hashmap = new HashMap();

        for (int j = 0; j < i; ++j) {
            String s = friendlybytebuf.readUtf();
            String s1 = friendlybytebuf.readUtf();

            hashmap.put(s, s1);
        }

        return hashmap;
    }
}
