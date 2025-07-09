//package com.pla.annoyingvillagers.procedures;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import javax.annotation.Nullable;
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.loading.FMLPaths;
//import net.minecraftforge.server.ServerLifecycleHooks;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class BeduesdfixProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerInBed(PlayerSleepInBedEvent playersleepinbedevent) {
//        execute(playersleepinbedevent, playersleepinbedevent.getPlayer().level, (double) playersleepinbedevent.getPos().getX(), (double) playersleepinbedevent.getPos().getY(), (double) playersleepinbedevent.getPos().getZ(), playersleepinbedevent.getPlayer());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        execute((Event) null, levelaccessor, d0, d1, d2, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        if (entity != null) {
//            new File("");
//            JsonObject jsonobject = new JsonObject();
//            String s = FMLPaths.GAMEDIR.get().toString() + "/" + (levelaccessor.isClientSide() ? Minecraft.getInstance().getSingleplayerServer().getWorldData().getLevelName() : ServerLifecycleHooks.getCurrentServer().getWorldData().getLevelName()) + "/player_spawn/";
//            String s1 = File.separator;
//            File file = new File(s, s1 + entity.getUUID().toString() + "_spawn.json");
//
//            try {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            } catch (IOException ioexception) {
//                ioexception.printStackTrace();
//            }
//
//            Gson gson;
//            FileWriter filewriter;
//
//            if (!file.exists()) {
//                try {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                } catch (IOException ioexception1) {
//                    ioexception1.printStackTrace();
//                }
//
//                jsonobject.addProperty("name", entity.getDisplayName().getString());
//                jsonobject.addProperty("x", (Number) entity.getX());
//                jsonobject.addProperty("y", (Number) entity.getY());
//                jsonobject.addProperty("z", (Number) entity.getZ());
//                jsonobject.addProperty("used_bed", "true");
//                gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//                try {
//                    filewriter = new FileWriter(file);
//                    filewriter.write(gson.toJson((JsonElement) jsonobject));
//                    filewriter.close();
//                } catch (IOException ioexception2) {
//                    ioexception2.printStackTrace();
//                }
//            } else {
//                jsonobject.addProperty("name", entity.getDisplayName().getString());
//                jsonobject.addProperty("x", (Number) entity.getX());
//                jsonobject.addProperty("y", (Number) entity.getY());
//                jsonobject.addProperty("z", (Number) entity.getZ());
//                jsonobject.addProperty("used_bed", "true");
//                jsonobject.addProperty("bed_x", (Number) d0);
//                jsonobject.addProperty("bed_y", (Number) d1);
//                jsonobject.addProperty("bed_z", (Number) d2);
//                gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//                try {
//                    filewriter = new FileWriter(file);
//                    filewriter.write(gson.toJson((JsonElement) jsonobject));
//                    filewriter.close();
//                } catch (IOException ioexception3) {
//                    ioexception3.printStackTrace();
//                }
//            }
//
//            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.RADOM_SPAWN) && entity instanceof Player) {
//                Player player = (Player) entity;
//
//                if (!player.level.isClientSide()) {
//                    player.displayClientMessage(new TextComponent("\u00a7e\u4f60\u4fdd\u5b58\u4e86\u91cd\u751f\u70b9\uff0c\u5982\u679c\u4f60\u6b7b\u4e86\uff0c\u8bf7\u518d\u6b21\u53f3\u952e\u5e8a\u6765\u4fdd\u5b58\u91cd\u751f\u70b9\uff0c\u5426\u5219\u5c06\u968f\u673a\u4f20\u9001"), false);
//                }
//            }
//
//        }
//    }
//}
