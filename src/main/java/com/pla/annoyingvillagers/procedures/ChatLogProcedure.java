package com.pla.annoyingvillagers.procedures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLPaths;

@EventBusSubscriber
public class ChatLogProcedure {

    @SubscribeEvent
    public static void onChat(ServerChatEvent serverchatevent) {
        execute(serverchatevent, serverchatevent.getPlayer(), serverchatevent.getMessage());
    }

    public static void execute(Entity entity, String s) {
        execute((Event) null, entity, s);
    }

    private static void execute(@Nullable Event event, Entity entity, String s) {
        if (entity != null && s != null) {
            new File("");
            String s1 = "";
            File file = new File(FMLPaths.GAMEDIR.get().toString(), File.separator + "chat_log.txt");
            FileWriter filewriter;
            BufferedWriter bufferedwriter;
            int i;

            if (file.exists()) {
                try {
                    filewriter = new FileWriter(file, true);
                    bufferedwriter = new BufferedWriter(filewriter);
                    i = Calendar.getInstance().get(1);
                    bufferedwriter.write("[" + i + "\u5e74" + Calendar.getInstance().get(2) + "\u6708" + Calendar.getInstance().get(5) + "\u65e5 " + Calendar.getInstance().get(11) + ":" + Calendar.getInstance().get(12) + "]<" + entity.getDisplayName().getString() + "> " + s);
                    bufferedwriter.newLine();
                    bufferedwriter.close();
                    filewriter.close();
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            } else {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (IOException ioexception1) {
                    ioexception1.printStackTrace();
                }

                try {
                    filewriter = new FileWriter(file, true);
                    bufferedwriter = new BufferedWriter(filewriter);
                    i = Calendar.getInstance().get(1);
                    bufferedwriter.write("[" + i + "\u5e74" + Calendar.getInstance().get(2) + "\u6708" + Calendar.getInstance().get(5) + "\u65e5 " + Calendar.getInstance().get(11) + ":" + Calendar.getInstance().get(12) + "]<" + entity.getDisplayName().getString() + "> " + s);
                    bufferedwriter.newLine();
                    bufferedwriter.close();
                    filewriter.close();
                } catch (IOException ioexception2) {
                    ioexception2.printStackTrace();
                }
            }

        }
    }
}
