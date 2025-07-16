package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import com.pla.annoyingvillagers.entity.DarkOBFarEntity;
import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
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
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_STAGING.get(), BlueDemonStagingRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_END_STAGING.get(), BlueDemonEndStagingRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.DARK_OB_FAR.get(), context -> new ThrownItemRenderer<DarkOBFarEntity>(context));
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), BlueDemonTridentParticleRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), BlueDemonTridentRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), VillagerScoutRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL.get(), BlueVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL.get(), PurpleVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get(), RedVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(), GreenVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), context -> new ThrownItemRenderer<EnchantedEnderPearlEntity>(context));
    }
}
