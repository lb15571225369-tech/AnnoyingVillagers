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
//import com.pla.annoyingvillagers.procedures.MusicSeacrhButtomProcedure;
//import com.pla.annoyingvillagers.world.inventory.MusicSearchMenu;
//
//@EventBusSubscriber(bus = Bus.MOD)
//public class MusicSearchButtonMessage {
//
//    private final int buttonID;
//    private final int x;
//    private final int y;
//    private final int z;
//    private HashMap<String, String> textstate;
//
//    public MusicSearchButtonMessage(FriendlyByteBuf friendlybytebuf) {
//        this.buttonID = friendlybytebuf.readInt();
//        this.x = friendlybytebuf.readInt();
//        this.y = friendlybytebuf.readInt();
//        this.z = friendlybytebuf.readInt();
//        this.textstate = readTextState(friendlybytebuf);
//    }
//
//    public MusicSearchButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        this.buttonID = i;
//        this.x = j;
//        this.y = k;
//        this.z = l;
//        this.textstate = hashmap;
//    }
//
//    public static void buffer(MusicSearchButtonMessage musicsearchbuttonmessage, FriendlyByteBuf friendlybytebuf) {
//        friendlybytebuf.writeInt(musicsearchbuttonmessage.buttonID);
//        friendlybytebuf.writeInt(musicsearchbuttonmessage.x);
//        friendlybytebuf.writeInt(musicsearchbuttonmessage.y);
//        friendlybytebuf.writeInt(musicsearchbuttonmessage.z);
//        writeTextState(musicsearchbuttonmessage.textstate, friendlybytebuf);
//    }
//
//    public static void handler(MusicSearchButtonMessage musicsearchbuttonmessage, Supplier<Context> supplier) {
//        Context context = (Context) supplier.get();
//
//        context.enqueueWork(() -> {
//            ServerPlayer serverplayer = context.getSender();
//            int i = musicsearchbuttonmessage.buttonID;
//            int j = musicsearchbuttonmessage.x;
//            int k = musicsearchbuttonmessage.y;
//            int l = musicsearchbuttonmessage.z;
//            HashMap<String, String> hashmap = musicsearchbuttonmessage.textstate;
//
//            handleButtonAction(serverplayer, i, j, k, l, hashmap);
//        });
//        context.setPacketHandled(true);
//    }
//
//    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        Level level = player.level;
//        HashMap hashmap1 = MusicSearchMenu.guistate;
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
//                MusicSeacrhButtomProcedure.execute(player, hashmap1);
//            }
//
//        }
//    }
//
//    @SubscribeEvent
//    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
//        AnnoyingVillagers.addNetworkMessage(MusicSearchButtonMessage.class, MusicSearchButtonMessage::buffer, MusicSearchButtonMessage::new, MusicSearchButtonMessage::handler);
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
