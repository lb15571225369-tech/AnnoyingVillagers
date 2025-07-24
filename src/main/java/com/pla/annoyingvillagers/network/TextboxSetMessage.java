package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TextboxSetMessage {

    private final String textboxid;
    private final String data;

    public TextboxSetMessage(FriendlyByteBuf friendlybytebuf) {
        this.textboxid = friendlybytebuf.readUtf();
        this.data = friendlybytebuf.readUtf();
    }

    public TextboxSetMessage(String s, String s1) {
        this.textboxid = s;
        this.data = s1;
    }

    public static void buffer(TextboxSetMessage annoyingvillagersmod_textboxsetmessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.textboxid);
        friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.data);
    }

    public static void handler(TextboxSetMessage annoyingvillagersmod_textboxsetmessage, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = (NetworkEvent.Context) supplier.get();

        context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer() && annoyingvillagersmod_textboxsetmessage.data != null) {
                Screen screen = Minecraft.getInstance().screen;
                Map<String, EditBox> map = new HashMap();

                if (screen != null) {
                    Field[] afield = screen.getClass().getDeclaredFields();
                    Field[] afield1 = afield;
                    int i = afield.length;

                    for (int j = 0; j < i; ++j) {
                        Field field = afield1[j];

                        if (EditBox.class.isAssignableFrom(field.getType())) {
                            try {
                                field.setAccessible(true);
                                EditBox editbox = (EditBox) field.get(screen);

                                if (editbox != null) {
                                    map.put(field.getName(), editbox);
                                }
                            } catch (IllegalAccessException illegalaccessexception) {
                                StringWriter stringwriter = new StringWriter();
                                PrintWriter printwriter = new PrintWriter(stringwriter);

                                illegalaccessexception.printStackTrace(printwriter);
                                String s = stringwriter.toString();

                                AnnoyingVillagers.LOGGER.error(s);
                            }
                        }
                    }
                }

                if (map.get(annoyingvillagersmod_textboxsetmessage.textboxid) != null) {
                    ((EditBox) map.get(annoyingvillagersmod_textboxsetmessage.textboxid)).setValue(annoyingvillagersmod_textboxsetmessage.data);
                }
            }

        });
        context.setPacketHandled(true);
    }
}