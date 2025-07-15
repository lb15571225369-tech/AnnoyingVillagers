package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.player_mobs.IPlayerMobInventory;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerMobsCapabilitiesRegister {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: registerCapabilities() is called");
        event.register(IPlayerMobInventory.class);
    }
}
