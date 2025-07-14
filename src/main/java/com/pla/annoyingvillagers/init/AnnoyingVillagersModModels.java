package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.model.ModelKnightH;
import com.pla.annoyingvillagers.client.model.ModelScoutH;
import com.pla.annoyingvillagers.client.model.Modelbluedemontrident;
import com.pla.annoyingvillagers.client.model.Modelgreenknight_armor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerlayerdefinitions) {
        registerlayerdefinitions.registerLayerDefinition(Modelbluedemontrident.LAYER_LOCATION, Modelbluedemontrident::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(Modelgreenknight_armor.LAYER_LOCATION, Modelgreenknight_armor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelKnightH.LAYER_LOCATION, ModelKnightH::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelScoutH.LAYER_LOCATION, ModelScoutH::createBodyLayer);
    }
}
