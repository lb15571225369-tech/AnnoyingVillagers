package com.pla.annoyingvillagers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pla.annoyingvillagers.capabilities.LegendarySwordCapability;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import com.pla.annoyingvillagers.gameasset.AVSounds;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEnchantments;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;

@Mod("annoying_villagers")
public class AnnoyingVillagersMod {

    public static final Logger LOGGER = LogManager.getLogger(AnnoyingVillagersMod.class);
    public static final String MODID = "annoyingvillagers";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("annoying_villagers", "annoying_villagers"), () -> {
        return "1";
    }, "1"::equals, "1"::equals);
    private static int messageID = 0;

    public AnnoyingVillagersMod() {
        IEventBus ieventbus = FMLJavaModLoadingContext.get().getModEventBus();

        AnnoyingVillagersModBlocks.REGISTRY.register(ieventbus);
        AnnoyingVillagersModItems.REGISTRY.register(ieventbus);
        ieventbus.addListener(LegendarySwordCapability::register);
        ieventbus.addListener(AVAnimations::registerAnimations);
        AnnoyingVillagersModEntities.REGISTRY.register(ieventbus);
        AnnoyingVillagersModEnchantments.REGISTRY.register(ieventbus);
        AnnoyingVillagersModMobEffects.REGISTRY.register(ieventbus);
        AnnoyingVillagersModParticleTypes.REGISTRY.register(ieventbus);
        AVSkill.registerSkills();
        AVSounds.SOUNDS.register(ieventbus);
    }

    public static <T> void addNetworkMessage(Class<T> oclass, BiConsumer<T, FriendlyByteBuf> biconsumer, Function<FriendlyByteBuf, T> function, BiConsumer<T, Supplier<Context>> biconsumer1) {
        AnnoyingVillagersMod.PACKET_HANDLER.registerMessage(AnnoyingVillagersMod.messageID, oclass, biconsumer, function, biconsumer1);
        ++AnnoyingVillagersMod.messageID;
    }

    @EventBusSubscriber(bus = Bus.MOD)
    public static class initer {

        @SubscribeEvent
        public static void init(FMLCommonSetupEvent fmlcommonsetupevent) {
            AnnoyingVillagersMod.addNetworkMessage(AnnoyingVillagersMod.TextboxSetMessage.class, AnnoyingVillagersMod.TextboxSetMessage::buffer, AnnoyingVillagersMod.TextboxSetMessage::new, AnnoyingVillagersMod.TextboxSetMessage::handler);
        }
    }

    public static class TextboxSetMessage {

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

        public static void buffer(AnnoyingVillagersMod.TextboxSetMessage annoyingvillagersmod_textboxsetmessage, FriendlyByteBuf friendlybytebuf) {
            friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.textboxid);
            friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.data);
        }

        public static void handler(AnnoyingVillagersMod.TextboxSetMessage annoyingvillagersmod_textboxsetmessage, Supplier<Context> supplier) {
            Context context = (Context) supplier.get();

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

                                    AnnoyingVillagersMod.LOGGER.error(s);
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
}
