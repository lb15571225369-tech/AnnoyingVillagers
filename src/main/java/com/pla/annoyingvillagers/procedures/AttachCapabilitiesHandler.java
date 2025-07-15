package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.player_mobs.PlayerMobInventoryProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.gory_moon.player_mobs.entity.PlayerMobEntity;

@Mod.EventBusSubscriber
public class AttachCapabilitiesHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerMobEntity) {
            AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: onAttachCapabilitiesEntity() is called");
            event.addCapability(new ResourceLocation(AnnoyingVillagers.MODID, "player_mob_inventory"),
                    new PlayerMobInventoryProvider());
        }
    }
}
