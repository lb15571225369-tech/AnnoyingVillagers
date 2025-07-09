//package com.pla.annoyingvillagers.procedures;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Calendar;
//import javax.annotation.Nullable;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.loading.FMLPaths;
//import com.pla.annoyingvillagers.AnnoyingVillagersMod;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class GameRestartEventProcedure {
//
//    @SubscribeEvent
//    public static void onPlayerLoggedIn(PlayerLoggedInEvent playerloggedinevent) {
//        execute(playerloggedinevent, playerloggedinevent.getPlayer().level, playerloggedinevent.getPlayer());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity) {
//        execute((Event) null, levelaccessor, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity) {
//        if (entity != null) {
//            new File("");
//            JsonObject jsonobject = new JsonObject();
//
//            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.GAME_RESTART) && Calendar.getInstance().get(5) == 1) {
//                if (!entity.level.isClientSide() && entity.getServer() != null) {
//                    entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "dt ss restart");
//                }
//
//                File file = new File(FMLPaths.GAMEDIR.get().toString() + "/aaa/", File.separator + "game.json");
//
//                try {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                } catch (IOException ioexception) {
//                    ioexception.printStackTrace();
//                }
//
//                Gson gson;
//                FileWriter filewriter;
//
//                if (!file.exists()) {
//                    try {
//                        file.getParentFile().mkdirs();
//                        file.createNewFile();
//                    } catch (IOException ioexception1) {
//                        ioexception1.printStackTrace();
//                    }
//
//                    jsonobject.addProperty("s", (Number) (jsonobject.get("s").getAsDouble() + 1.0D));
//                    gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//                    try {
//                        filewriter = new FileWriter(file);
//                        filewriter.write(gson.toJson((JsonElement) jsonobject));
//                        filewriter.close();
//                    } catch (IOException ioexception2) {
//                        ioexception2.printStackTrace();
//                    }
//                } else {
//                    jsonobject.addProperty("s", (int) 1);
//                    gson = (new GsonBuilder()).setPrettyPrinting().create();
//
//                    try {
//                        filewriter = new FileWriter(file);
//                        filewriter.write(gson.toJson((JsonElement) jsonobject));
//                        filewriter.close();
//                    } catch (IOException ioexception3) {
//                        ioexception3.printStackTrace();
//                    }
//
//                    AnnoyingVillagersMod.LOGGER.info(jsonobject.get("s").getAsDouble() + "\u65b0\u8d5b\u5b63\u5df2\u5230\u6765");
//                }
//            }
//
//        }
//    }
//}
