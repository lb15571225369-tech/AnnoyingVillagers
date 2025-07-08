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
import com.pla.annoyingvillagers.procedures.Emoji10Procedure;
import com.pla.annoyingvillagers.procedures.Emoji11Procedure;
import com.pla.annoyingvillagers.procedures.Emoji12Procedure;
import com.pla.annoyingvillagers.procedures.Emoji13Procedure;
import com.pla.annoyingvillagers.procedures.Emoji14Procedure;
import com.pla.annoyingvillagers.procedures.Emoji15Procedure;
import com.pla.annoyingvillagers.procedures.Emoji16Procedure;
import com.pla.annoyingvillagers.procedures.Emoji17Procedure;
import com.pla.annoyingvillagers.procedures.Emoji18Procedure;
import com.pla.annoyingvillagers.procedures.Emoji1Procedure;
import com.pla.annoyingvillagers.procedures.Emoji2Procedure;
import com.pla.annoyingvillagers.procedures.Emoji3Procedure;
import com.pla.annoyingvillagers.procedures.Emoji4Procedure;
import com.pla.annoyingvillagers.procedures.Emoji5Procedure;
import com.pla.annoyingvillagers.procedures.Emoji6Procedure;
import com.pla.annoyingvillagers.procedures.Emoji7Procedure;
import com.pla.annoyingvillagers.procedures.Emoji8Procedure;
import com.pla.annoyingvillagers.procedures.Emoji9Procedure;
import com.pla.annoyingvillagers.procedures.OffEmoteMusicProcedure;
import com.pla.annoyingvillagers.procedures.YaProcedure;
import com.pla.annoyingvillagers.world.inventory.EmojiMenu;

@EventBusSubscriber(bus = Bus.MOD)
public class EmojiButtonMessage {

    private final int buttonID;
    private final int x;
    private final int y;
    private final int z;
    private HashMap<String, String> textstate;

    public EmojiButtonMessage(FriendlyByteBuf friendlybytebuf) {
        this.buttonID = friendlybytebuf.readInt();
        this.x = friendlybytebuf.readInt();
        this.y = friendlybytebuf.readInt();
        this.z = friendlybytebuf.readInt();
        this.textstate = readTextState(friendlybytebuf);
    }

    public EmojiButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
        this.buttonID = i;
        this.x = j;
        this.y = k;
        this.z = l;
        this.textstate = hashmap;
    }

    public static void buffer(EmojiButtonMessage emojibuttonmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(emojibuttonmessage.buttonID);
        friendlybytebuf.writeInt(emojibuttonmessage.x);
        friendlybytebuf.writeInt(emojibuttonmessage.y);
        friendlybytebuf.writeInt(emojibuttonmessage.z);
        writeTextState(emojibuttonmessage.textstate, friendlybytebuf);
    }

    public static void handler(EmojiButtonMessage emojibuttonmessage, Supplier<Context> supplier) {
        Context context = (Context) supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer serverplayer = context.getSender();
            int i = emojibuttonmessage.buttonID;
            int j = emojibuttonmessage.x;
            int k = emojibuttonmessage.y;
            int l = emojibuttonmessage.z;
            HashMap<String, String> hashmap = emojibuttonmessage.textstate;

            handleButtonAction(serverplayer, i, j, k, l, hashmap);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
        Level level = player.level;
        HashMap hashmap1 = EmojiMenu.guistate;
        Iterator iterator = hashmap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry) iterator.next();
            String s = (String) entry.getKey();
            String s1 = (String) entry.getValue();

            hashmap1.put(s, s1);
        }

        if (level.hasChunkAt(new BlockPos(j, k, l))) {
            if (i == 0) {
                Emoji1Procedure.execute(level, player);
            }

            if (i == 1) {
                Emoji2Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 2) {
                Emoji3Procedure.execute(level, player);
            }

            if (i == 3) {
                Emoji4Procedure.execute(level, player);
            }

            if (i == 4) {
                Emoji16Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 5) {
                Emoji5Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 6) {
                Emoji6Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 7) {
                Emoji7Procedure.execute(level, player);
            }

            if (i == 8) {
                Emoji8Procedure.execute(level, player);
            }

            if (i == 9) {
                Emoji9Procedure.execute(level, player);
            }

            if (i == 10) {
                Emoji10Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 11) {
                Emoji11Procedure.execute(level, player);
            }

            if (i == 12) {
                Emoji12Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 13) {
                Emoji13Procedure.execute(level, player);
            }

            if (i == 14) {
                Emoji14Procedure.execute(level, player);
            }

            if (i == 15) {
                Emoji15Procedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 16) {
                Emoji17Procedure.execute(level, player);
            }

            if (i == 17) {
                Emoji18Procedure.execute(level, player);
            }

            if (i == 18) {
                YaProcedure.execute(level, (double) j, (double) k, (double) l, player);
            }

            if (i == 19) {
                OffEmoteMusicProcedure.execute(player);
            }

        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
        AnnoyingVillagersMod.addNetworkMessage(EmojiButtonMessage.class, EmojiButtonMessage::buffer, EmojiButtonMessage::new, EmojiButtonMessage::handler);
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
