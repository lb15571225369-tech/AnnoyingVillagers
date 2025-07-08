package com.pla.annoyingvillagers.procedures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.loading.FMLPaths;

public class ComLogProcedure {

    public static void execute(Entity entity, String s) {
        if (entity != null && s != null) {
            new File("");
            String s1 = "";
            File file = new File(FMLPaths.GAMEDIR.get().toString(), File.separator + "command_log.txt");

            if (entity instanceof Player) {
                FileWriter filewriter;
                BufferedWriter bufferedwriter;
                String s2;

                if (file.exists()) {
                    try {
                        filewriter = new FileWriter(file, true);
                        bufferedwriter = new BufferedWriter(filewriter);
                        s2 = entity.getDisplayName().getString();
                        bufferedwriter.write("<" + s2 + "> /" + s);
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
                        s2 = entity.getDisplayName().getString();
                        bufferedwriter.write("<" + s2 + "> /" + s);
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
}
