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
//import com.pla.annoyingvillagers.procedures.LoginPhoneIdProcedure;
//import com.pla.annoyingvillagers.world.inventory.LoginMusicMenu;
//
//@EventBusSubscriber(bus = Bus.MOD)
//public class LoginMusicButtonMessage {
//
//    private final int buttonID;
//    private final int x;
//    private final int y;
//    private final int z;
//    private HashMap<String, String> textstate;
//
//    public LoginMusicButtonMessage(FriendlyByteBuf friendlybytebuf) {
//        this.buttonID = friendlybytebuf.readInt();
//        this.x = friendlybytebuf.readInt();
//        this.y = friendlybytebuf.readInt();
//        this.z = friendlybytebuf.readInt();
//        this.textstate = readTextState(friendlybytebuf);
//    }
//
//    public LoginMusicButtonMessage(int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        this.buttonID = i;
//        this.x = j;
//        this.y = k;
//        this.z = l;
//        this.textstate = hashmap;
//    }
//
//    public static void buffer(LoginMusicButtonMessage loginmusicbuttonmessage, FriendlyByteBuf friendlybytebuf) {
//        friendlybytebuf.writeInt(loginmusicbuttonmessage.buttonID);
//        friendlybytebuf.writeInt(loginmusicbuttonmessage.x);
//        friendlybytebuf.writeInt(loginmusicbuttonmessage.y);
//        friendlybytebuf.writeInt(loginmusicbuttonmessage.z);
//        writeTextState(loginmusicbuttonmessage.textstate, friendlybytebuf);
//    }
//
//    public static void handler(LoginMusicButtonMessage loginmusicbuttonmessage, Supplier<Context> supplier) {
//        Context context = (Context) supplier.get();
//
//        context.enqueueWork(() -> {
//            ServerPlayer serverplayer = context.getSender();
//            int i = loginmusicbuttonmessage.buttonID;
//            int j = loginmusicbuttonmessage.x;
//            int k = loginmusicbuttonmessage.y;
//            int l = loginmusicbuttonmessage.z;
//            HashMap<String, String> hashmap = loginmusicbuttonmessage.textstate;
//
//            handleButtonAction(serverplayer, i, j, k, l, hashmap);
//        });
//        context.setPacketHandled(true);
//    }
//
//    public static void handleButtonAction(Player player, int i, int j, int k, int l, HashMap<String, String> hashmap) {
//        Level level = player.level;
//        HashMap hashmap1 = LoginMusicMenu.guistate;
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
//                LoginPhoneIdProcedure.execute(level, (double) j, (double) k, (double) l, player, hashmap1);
//            }
//
//        }
//    }
//
//    @SubscribeEvent
//    public static void registerMessage(FMLCommonSetupEvent fmlcommonsetupevent) {
//        AnnoyingVillagers.addNetworkMessage(LoginMusicButtonMessage.class, LoginMusicButtonMessage::buffer, LoginMusicButtonMessage::new, LoginMusicButtonMessage::handler);
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
