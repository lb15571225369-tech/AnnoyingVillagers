package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.model.Modelbluedemontrident;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerlayerdefinitions) {
        registerlayerdefinitions.registerLayerDefinition(Modelbluedemontrident.LAYER_LOCATION, Modelbluedemontrident::createBodyLayer);
    }
}
