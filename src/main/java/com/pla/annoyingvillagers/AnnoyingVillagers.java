package com.pla.annoyingvillagers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.procedures.NpcGearLoadProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pla.annoyingvillagers.capabilities.LegendarySwordCapability;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkill;
import com.pla.annoyingvillagers.gameasset.AVSounds;

@Mod(AnnoyingVillagers.MODID)
public class AnnoyingVillagers {
    public static final Logger LOGGER = LogManager.getLogger(AnnoyingVillagers.class);
    public static final String MODID = "annoyingvillagers";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(AnnoyingVillagers.MODID, "main"), () -> {
        return "1";
    }, "1"::equals, "1"::equals);
    private static int messageID = 0;
    public static final RandomSource randomSource = RandomSource.create();

    public AnnoyingVillagers(FMLJavaModLoadingContext context) {
        IEventBus ieventbus = context.getModEventBus();
        AnnoyingVillagersModBlocks.REGISTRY.register(ieventbus);
        AnnoyingVillagersModItems.REGISTRY.register(ieventbus);
        ieventbus.addListener(LegendarySwordCapability::register);
        ieventbus.addListener(AVAnimations::registerAnimations);
        AnnoyingVillagersModEntities.REGISTRY.register(ieventbus);
        AnnoyingVillagersModEnchantments.REGISTRY.register(ieventbus);
        AnnoyingVillagersModMobEffects.REGISTRY.register(ieventbus);
        AnnoyingVillagersModParticleTypes.REGISTRY.register(ieventbus);
        AnnoyingVillagersModCreativeTabs.register(ieventbus);
        AnnoyingVillagersModBiomeModifiers.register(ieventbus);
        AnnoyingVillagersModSounds.register(ieventbus);
        AVSounds.SOUNDS.register(ieventbus);
        MinecraftForge.EVENT_BUS.register(new NpcGearLoadProcedure());
    }

    public static <T> void addNetworkMessage(Class<T> oclass, BiConsumer<T, FriendlyByteBuf> biconsumer, Function<FriendlyByteBuf, T> function, BiConsumer<T, Supplier<Context>> biconsumer1) {
        AnnoyingVillagers.PACKET_HANDLER.registerMessage(AnnoyingVillagers.messageID, oclass, biconsumer, function, biconsumer1);
        ++AnnoyingVillagers.messageID;
    }

    @EventBusSubscriber(bus = Bus.MOD)
    public static class initer {

        @SubscribeEvent
        public static void init(FMLCommonSetupEvent fmlcommonsetupevent) {
            AnnoyingVillagers.addNetworkMessage(AnnoyingVillagers.TextboxSetMessage.class, AnnoyingVillagers.TextboxSetMessage::buffer, AnnoyingVillagers.TextboxSetMessage::new, AnnoyingVillagers.TextboxSetMessage::handler);
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

        public static void buffer(AnnoyingVillagers.TextboxSetMessage annoyingvillagersmod_textboxsetmessage, FriendlyByteBuf friendlybytebuf) {
            friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.textboxid);
            friendlybytebuf.writeUtf(annoyingvillagersmod_textboxsetmessage.data);
        }

        public static void handler(AnnoyingVillagers.TextboxSetMessage annoyingvillagersmod_textboxsetmessage, Supplier<Context> supplier) {
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
}
