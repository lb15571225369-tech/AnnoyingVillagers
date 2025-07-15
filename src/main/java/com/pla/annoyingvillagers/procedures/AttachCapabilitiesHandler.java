//package com.pla.annoyingvillagers.procedures;
//
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.compat.player_mobs.PlayerMobInventoryProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.event.entity.living.LivingDeathEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.items.CapabilityItemHandler;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;
//
//@Mod.EventBusSubscriber
//public class AttachCapabilitiesHandler {
//    public static final ResourceLocation ID = new ResourceLocation(AnnoyingVillagers.MODID, "player_mob_inventory");
//
//    @SubscribeEvent
//    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
//        if (event.getObject() instanceof PlayerMobEntity) {
//            event.addCapability(ID, new PlayerMobInventoryProvider());
//        }
//    }
//
//    @SubscribeEvent
//    public static void onPlayerMobDeath(LivingDeathEvent event) {
//        LivingEntity entity = (LivingEntity) event.getEntity();
//        if (!(entity instanceof PlayerMobEntity)) return;
//
//        entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
//            if (handler instanceof PlayerMobInventoryProvider inventory) {
//                inventory.dropAll(entity.level, entity.getX(), entity.getY(), entity.getZ());
//            }
//        });
//    }
//}
