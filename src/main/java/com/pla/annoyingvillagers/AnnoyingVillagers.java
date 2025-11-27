package com.pla.annoyingvillagers;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.TextboxSetMessage;
import com.pla.annoyingvillagers.procedures.NpcGearLoadProcedure;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pla.annoyingvillagers.capabilities.AVWeaponCapabilityPresets;
import com.pla.annoyingvillagers.gameasset.AVSounds;

@Mod(AnnoyingVillagers.MODID)
public class AnnoyingVillagers {
    public static final Logger LOGGER = LogManager.getLogger(AnnoyingVillagers.class);
    public static final String MODID = "annoyingvillagers";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry
            .newSimpleChannel(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "main"), () -> "1", "1"::equals, "1"::equals);
    private static int messageID = 0;;

    public AnnoyingVillagers(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        AnnoyingVillagersModBlocks.REGISTRY.register(modEventBus);
        AnnoyingVillagersModBlockEntities.REGISTRY.register(modEventBus);
        AnnoyingVillagersModItems.REGISTRY.register(modEventBus);
        modEventBus.addListener(AVWeaponCapabilityPresets::register);
        AnnoyingVillagersModEntities.REGISTRY.register(modEventBus);
        AnnoyingVillagersModEnchantments.REGISTRY.register(modEventBus);
        AnnoyingVillagersModMobEffects.REGISTRY.register(modEventBus);
        AnnoyingVillagersModParticleTypes.REGISTRY.register(modEventBus);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, AnnoyingVillagersModCapabilities::attachEntityCapability);
        modEventBus.addListener(AnnoyingVillagersModCapabilities::registerCapabilities);
        AnnoyingVillagersModCreativeTabs.register(modEventBus);
        AnnoyingVillagersModSounds.register(modEventBus);
        AVSounds.SOUNDS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new NpcGearLoadProcedure());
        context.registerConfig(ModConfig.Type.COMMON, AnnoyingVillagersConfig.SPEC, "annoyingvillagers-server.toml");
    }

    public static <T> void addNetworkMessage(Class<T> oclass, BiConsumer<T, FriendlyByteBuf> biconsumer, Function<FriendlyByteBuf, T> function, BiConsumer<T, Supplier<Context>> biconsumer1) {
        AnnoyingVillagers.PACKET_HANDLER.registerMessage(AnnoyingVillagers.messageID, oclass, biconsumer, function, biconsumer1);
        ++AnnoyingVillagers.messageID;
    }

    @EventBusSubscriber(bus = Bus.MOD)
    public static class initer {
        @SubscribeEvent
        public static void init(FMLCommonSetupEvent fmlcommonsetupevent) {
            AnnoyingVillagers.addNetworkMessage(TextboxSetMessage.class, TextboxSetMessage::buffer, TextboxSetMessage::new, TextboxSetMessage::handler);
            AnnoyingVillagers.addNetworkMessage(
                    ClientboundGlaiveExplosionFx.class,
                    ClientboundGlaiveExplosionFx::encode,
                    ClientboundGlaiveExplosionFx::decode,
                    ClientboundGlaiveExplosionFx::handle
            );
            AnnoyingVillagers.addNetworkMessage(
                    ClientboundMuteExplosionAtPos.class,
                    ClientboundMuteExplosionAtPos::encode,
                    ClientboundMuteExplosionAtPos::decode,
                    ClientboundMuteExplosionAtPos::handle
            );
            AnnoyingVillagers.addNetworkMessage(
                    ClientboundHerobrinePortalFx.class,
                    ClientboundHerobrinePortalFx::encode,
                    ClientboundHerobrinePortalFx::decode,
                    ClientboundHerobrinePortalFx::handle
            );
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "second_form"),
                        (stack, level, entity, seed) -> {
                            if (stack.hasTag() && stack.getTag().getBoolean("SecondForm")) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "snake_animation"),
                        (stack, level, entity, seed) -> {
                            if (stack.hasTag() && stack.getTag().getBoolean("SnakeAnimation")) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "snake_animation_ready"),
                        (stack, level, entity, seed) -> {
                            if (stack.hasTag() && stack.getTag().getInt("HitCount") == 5) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.ENDER_AEGIS.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "second_form"),
                        (stack, level, entity, seed) -> {
                            if (stack.hasTag() && stack.getTag().getBoolean("SecondForm")) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
            });
        }
    }
}
