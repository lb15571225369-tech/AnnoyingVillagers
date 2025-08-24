package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import se.gory_moon.player_mobs.client.render.PlayerMobRenderer;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE_1.get(), Herobrine1Renderer::new);
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
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), context -> new ThrownItemRenderer<ThrownPoisonEggEntity>(context));
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), context -> new ThrownItemRenderer<StealthAttackEntity>(context));
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.ALEX_DEAD.get(), AlexDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.ALEX.get(), AlexRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.JEV.get(), JevRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BBQ.get(), BbqRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.CHRIS.get(), ChrisRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), InfectedChrisRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE_3.get(), Herobrine3Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE_7.get(), Herobrine7Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), ArmoredHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.STEVE_2.get(), Steve2Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.STEVE_DEAD.get(), SteveDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.STEVE.get(), SteveRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngrySteveRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.CHRIS_DEAD.get(), ChrisDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.INFECTED_CHRIS_DEAD.get(), InfectedChrisDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.JEV_DEAD.get(), JevDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_DEAD.get(), VillagerScoutDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL_DEAD.get(), RedVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL_DEAD.get(), BlueVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL_DEAD.get(), GreenVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL_DEAD.get(), PurpleVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_DEAD.get(), BlueDemonDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.HEROBRINE_DEAD.get(), HerobrineDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE_DEAD.get(), DarkHerobrineDeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), PlayerMobRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), PlayerMobRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), InfectedTheMostMoistBurrit0Renderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0_DEAD.get(), InfectedTheMostMoistBurrit0DeadRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), DarkHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SUMMON_PORTAL.get(), SummonPortalRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), GlaiveHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), ReaperHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SNAKE_BLADE.get(), SnakeBladeRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), SwordsmanHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), SledgehammerHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), AegisHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.DRAGON_BEAM.get(), DragonBeamRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), BabyEnderDragonRenderer::new);
        registerrenderers.registerEntityRenderer((EntityType) AnnoyingVillagersModEntities.BABY_DRAGON_BEAM.get(), BabyDragonBeamRenderer::new);
    }
}
