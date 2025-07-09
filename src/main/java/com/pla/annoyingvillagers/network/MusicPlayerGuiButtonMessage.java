//package com.pla.annoyingvillagers.network;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map.Entry;
//import java.util.function.Supplier;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.network.NetworkEvent.Context;
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.procedures.LoginBProcedure;
//import com.pla.annoyingvillagers.procedures.MusicIDProcedure;
//import com.pla.annoyingvillagers.procedures.SearchButtomProcedure;
//import com.pla.annoyingvillagers.procedures.VoteProcedure;
//import com.pla.annoyingvillagers.world.inventory.MusicPlayerGuiMenu;
//
//@EventBusSubscriber(bus = Bus.MOD)
//public class MusicPlayerGuiButtonMessage {
//
//    private final int buttonID;
//    private final int x;
//    private final int y;
//    private final int z;
//    private HashMap<String, String> textstate;
//
//    public MusicPlayerGuiButtonMessage(FriendlyByteBuf friendlybytebuf) {
//        this.buttonID = friendlybytebuf.readInt();
//        this.x = friendlybytebuf.readInt();
//        this.y = friendlybytebuf.readInt();
//        this.z = friendlybytebuf.readInt();
//        this.textstate = readTextState(friendlybytebuf);
//    }
//
//    public MusicPlayerGuiButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        this.buttonID = i;
//        this.x = j;
//        this.y = k;
//        this.z = l;
//        this.textstate = hashmap;
//    }
//
//    public static void buffer(MusicPlayerGuiButtonMessage musicplayerguibuttonmessage, FriendlyByteBuf friendlybytebuf) {
//        friendlybytebuf.writeInt(musicplayerguibuttonmessage.buttonID);
//        friendlybytebuf.writeInt(musicplayerguibuttonmessage.x);
//        friendlybytebuf.writeInt(musicplayerguibuttonmessage.y);
//        friendlybytebuf.writeInt(musicplayerguibuttonmessage.z);
//        writeTextState(musicplayerguibuttonmessage.textstate, friendlybytebuf);
//    }
//
//    public static void handler(MusicPlayerGuiButtonMessage musicplayerguibuttonmessage, Supplier<Context> supplier) {
//        Context context = (Context) supplier.get();
//
//        context.enqueueWork(() -> {
//            ServerPlayer serverplayer = context.getSender();
//            int i = musicplayerguibuttonmessage.buttonID;
//            int j = musicplayerguibuttonmessage.x;
//            int k = musicplayerguibuttonmessage.y;
//            int l = musicplayerguibuttonmessage.z;
//            HashMap<String, String> hashmap = musicplayerguibuttonmessage.textstate;
//
//            handleButtonAction(serverplayer, i, j, k, l, hashmap);
//        });
//        context.setPacketHandled(true);
//    }
//
//    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        Level level = player.level;
//        HashMap hashmap1 = MusicPlayerGuiMenu.guistate;
//        Iterator iterator = hashmap.entrySet().iterator();
//
//        while (iterator.hasNext()) {
//            Entry<String, String> entry = (Entry) iterator.next();
//            String s = (String) entry.getKey();
//            String s1 = (String) entry.getValue();
//
//            hashmap1.put(s, s1);
//        }
//
//        if (level.hasChunkAt(new BlockPos(j, k, l))) {
//            if (i == 0) {
//                MusicIDProcedure.execute(player, hashmap1);
//            }
//
//            if (i == 1) {
//                LoginBProcedure.execute(level, (double) j, (double) k, (double) l, player);
//            }
//
//            if (i == 3) {
//                VoteProcedure.execute(player);
//            }
//
//            if (i == 4) {
//                SearchButtomProcedure.execute(level, (double) j, (double) k, (double) l, player);
//            }
//
//        }
//    }
//
//    @SubscribeEvent
//    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
//        AnnoyingVillagers.addNetworkMessage(MusicPlayerGuiButtonMessage.class, MusicPlayerGuiButtonMessage::buffer, MusicPlayerGuiButtonMessage::new, MusicPlayerGuiButtonMessage::handler);
//    }
//
//    public static void writeTextState(HashMap<String, String> hashmap, FriendlyByteBuf friendlybytebuf) {
//        friendlybytebuf.writeInt(hashmap.size());
//        Iterator iterator = hashmap.entrySet().iterator();
//
//        while (iterator.hasNext()) {
//            Entry<String, String> entry = (Entry) iterator.next();
//
//            friendlybytebuf.writeUtf((String) entry.getKey());
//            friendlybytebuf.writeUtf((String) entry.getValue());
//        }
//
//    }
//
//    public static HashMap<String, String> readTextState(FriendlyByteBuf friendlybytebuf) {
//        int i = friendlybytebuf.readInt();
//        HashMap<String, String> hashmap = new HashMap();
//
//        for (int j = 0; j < i; ++j) {
//            String s = friendlybytebuf.readUtf();
//            String s1 = friendlybytebuf.readUtf();
//
//            hashmap.put(s, s1);
//        }
//
//        return hashmap;
//    }
//}
