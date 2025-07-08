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
import com.pla.annoyingvillagers.procedures.Emote17Procedure;
import com.pla.annoyingvillagers.procedures.Emote18Procedure;
import com.pla.annoyingvillagers.procedures.Emote19Procedure;
import com.pla.annoyingvillagers.procedures.Emote20Procedure;
import com.pla.annoyingvillagers.procedures.Emote21Procedure;
import com.pla.annoyingvillagers.procedures.Emote22Procedure;
import com.pla.annoyingvillagers.procedures.Emote23Procedure;
import com.pla.annoyingvillagers.procedures.Emote24Procedure;
import com.pla.annoyingvillagers.procedures.Emote25Procedure;
import com.pla.annoyingvillagers.procedures.Emote26Procedure;
import com.pla.annoyingvillagers.procedures.Emote27Procedure;
import com.pla.annoyingvillagers.procedures.Emote28Procedure;
import com.pla.annoyingvillagers.procedures.Emote29Procedure;
import com.pla.annoyingvillagers.procedures.Emote30Procedure;
import com.pla.annoyingvillagers.procedures.OffEmoteMusicProcedure;
import com.pla.annoyingvillagers.procedures.Ya2Procedure;
import com.pla.annoyingvillagers.world.inventory.EmoteMenu;

@EventBusSubscriber(bus = Bus.MOD)
public class EmoteButtonMessage {

    private final int buttonID;
    private final int x;
    private final int y;
    private final int z;
    private HashMap<String, String> textstate;

    public EmoteButtonMessage(FriendlyByteBuf friendlybytebuf) {
        this.buttonID = friendlybytebuf.readInt();
        this.x = friendlybytebuf.readInt();
        this.y = friendlybytebuf.readInt();
        this.z = friendlybytebuf.readInt();
        this.textstate = readTextState(friendlybytebuf);
    }

    public EmoteButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
        this.buttonID = i;
        this.x = j;
        this.y = k;
        this.z = l;
        this.textstate = hashmap;
    }

    public static void buffer(EmoteButtonMessage emotebuttonmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(emotebuttonmessage.buttonID);
        friendlybytebuf.writeInt(emotebuttonmessage.x);
        friendlybytebuf.writeInt(emotebuttonmessage.y);
        friendlybytebuf.writeInt(emotebuttonmessage.z);
        writeTextState(emotebuttonmessage.textstate, friendlybytebuf);
    }

    public static void handler(EmoteButtonMessage emotebuttonmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer serverplayer = context.getSender();
            int i = emotebuttonmessage.buttonID;
            int j = emotebuttonmessage.x;
            int k = emotebuttonmessage.y;
            int l = emotebuttonmessage.z;
            HashMap<String, String> hashmap = emotebuttonmessage.textstate;

            handleButtonAction(serverplayer, i, j, k, l, hashmap);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
        Level level = player.level;
        HashMap hashmap1 = EmoteMenu.guistate;
        Iterator iterator = hashmap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry) iterator.next();
            String s = (String) entry.getKey();
            String s1 = (String) entry.getValue();

            hashmap1.put(s, s1);
        }

        if (level.hasChunkAt(new BlockPos(j, k, l))) {
            if (i == 0) {
                Emote17Procedure.execute(level, player);
            }

            if (i == 1) {
                Emote18Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 2) {
                Ya2Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 3) {
                Emote19Procedure.execute(level, player);
            }

            if (i == 4) {
                Emote20Procedure.execute(level, player);
            }

            if (i == 5) {
                Emote21Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 6) {
                Emote22Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 7) {
                Emote23Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 8) {
                Emote24Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 9) {
                Emote25Procedure.execute(level, player);
            }

            if (i == 10) {
                Emote26Procedure.execute(level, player);
            }

            if (i == 11) {
                Emote27Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 12) {
                OffEmoteMusicProcedure.execute(player);
            }

            if (i == 13) {
                Emote28Procedure.execute(level, player);
            }

            if (i == 14) {
                Emote29Procedure.execute(level, player);
            }

            if (i == 15) {
                Emote30Procedure.execute(level, player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagersMod.addNetworkMessage(EmoteButtonMessage.class, EmoteButtonMessage::buffer, EmoteButtonMessage::new, EmoteButtonMessage::handler);
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
