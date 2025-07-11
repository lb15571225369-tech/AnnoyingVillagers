package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import com.pla.annoyingvillagers.entity.DarkOBFarEntity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE.get(), HerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), Herobrine2Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_R.get(), BlueDemonRRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_END.get(), BlueDemonEndRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.DARK_OB_FAR.get(), context -> new ThrownItemRenderer<DarkOBFarEntity>(context));
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), BdTridentRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), BluedemontridentRenderer::new);
    }
}
