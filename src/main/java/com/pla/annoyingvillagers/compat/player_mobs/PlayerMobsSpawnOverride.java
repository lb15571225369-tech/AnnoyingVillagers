package com.pla.annoyingvillagers.compat.player_mobs;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import se.gory_moon.player_mobs.entity.EntityRegistry;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerMobsSpawnOverride {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: patching playermob spawning");
            SpawnPlacements.register(
                    EntityRegistry.PLAYER_MOB_ENTITY.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        });
    }
}
