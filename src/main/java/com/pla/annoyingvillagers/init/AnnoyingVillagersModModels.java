package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerlayerdefinitions) {
        registerlayerdefinitions.registerLayerDefinition(ModelVillagerScoutHelmet.LAYER_LOCATION, ModelVillagerScoutHelmet::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelBlueDemonTrident.LAYER_LOCATION, ModelBlueDemonTrident::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelGreenVillagerGeneralArmor.LAYER_LOCATION, ModelGreenVillagerGeneralArmor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelVillagerGeneralArmor.LAYER_LOCATION, ModelVillagerGeneralArmor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineObsidianDiamondHelmet.LAYER_LOCATION, ModelHerobrineObsidianDiamondHelmet::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION, ModelHerobrineObsidianDiamondChestplate::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelSnakeBladeFragment.LAYER_LOCATION, ModelSnakeBladeFragment::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelSnakeBlade.LAYER_LOCATION, ModelSnakeBlade::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelBabyEnderDragon.LAYER_LOCATION, ModelBabyEnderDragon::createBodyLayer);
    }
}
