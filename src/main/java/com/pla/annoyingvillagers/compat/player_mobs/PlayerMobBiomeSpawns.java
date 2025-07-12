//package com.pla.annoyingvillagers.compat.player_mobs;
//
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraft.world.level.biome.MobSpawnSettings;
//import net.minecraftforge.common.BiomeManager;
//import net.minecraftforge.event.world.BiomeLoadingEvent;
//import net.minecraftforge.eventbus.api.EventPriority;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import se.gory_moon.player_mobs.entity.EntityRegistry;
//
//@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class PlayerMobBiomeSpawns {
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public static void onBiomeLoad(BiomeLoadingEvent event) {
//        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: patching PlayerMobBiomeSpawns");
//        if (event.getName().equals(new ResourceLocation("minecraft:plains"))) {
//            event.getSpawns().addSpawn(MobCategory.MONSTER,
//                    new MobSpawnSettings.SpawnerData(
//                            EntityRegistry.PLAYER_MOB_ENTITY.get(),
//                            100,
//                            5, 10
//                    )
//            );
//        }
//    }
//}
