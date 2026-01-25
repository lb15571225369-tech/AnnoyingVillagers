package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import se.gory_moon.player_mobs.client.render.PlayerMobRenderer;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PIllagerRenderer;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), HerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(), ShadowHerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_STAGING.get(), BlueDemonStagingRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Renderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_END_STAGING.get(), BlueDemonEndStagingRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BD_TRIDENT.get(), BlueDemonTridentParticleRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUEDEMONTRIDENT.get(), BlueDemonTridentRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), VillagerScoutRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL.get(), BlueVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL.get(), PurpleVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get(), RedVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(), GreenVillagerGeneralRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ENCHANTED_ENDER_PEARL_PROJECTILE.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.STEALTH_ATTACK_PROJECTILE.get(), ThrownItemRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ALEX_DEAD.get(), AlexDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ALEX.get(), AlexRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.JEV.get(), JevRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BBQ.get(), BbqRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.CHRIS.get(), ChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), InfectedChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_CHRIS.get(), HerobrineChrisRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_7.get(), Herobrine7Renderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), ArmoredHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.STEVE_DEAD.get(), SteveDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.STEVE.get(), SteveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngrySteveRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.CHRIS_DEAD.get(), ChrisDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_CHRIS_DEAD.get(), InfectedChrisDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.JEV_DEAD.get(), JevDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT_DEAD.get(), VillagerScoutDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL_DEAD.get(), RedVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL_DEAD.get(), BlueVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL_DEAD.get(), GreenVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL_DEAD.get(), PurpleVillagerGeneralDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON_DEAD.get(), BlueDemonDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_DEAD.get(), HerobrineDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_DEAD.get(), DarkHerobrineDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), PlayerMobDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), PlayerMobRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), InfectedTheMostMoistBurrit0Renderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0_DEAD.get(), InfectedTheMostMoistBurrit0DeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), DarkHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), GlaiveHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), ReaperHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SNAKE_BLADE.get(), SnakeBladeRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), SwordsmanHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), SledgehammerHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), AegisHerobrineRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.DRAGON_BEAM.get(), DragonBeamRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), EliteHerobrineKnockedRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.ELITE_HEROBRINE_DEAD.get(), EliteHerobrineDeadRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), PlayerMobRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), LowShadowHerobrineCloneRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL.get(), NullRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SWORD.get(), NullSwordRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_AXE.get(), NullAxeRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullPickaxeRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullShovelRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_HOE.get(), NullHoeRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), BlockProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(), HerobrineGregRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.PLAYER_NPC.get(), PlayerMobRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_WARDEN.get(), HerobrineWardenRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), HerobrineDragonRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.DRAGON_METEORITE.get(), DragonMeteoriteRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), ObsidianSledgehammerProjectileRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.SHOCKWAVE_BLOCK.get(), ShockWaveBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingVillagersModEntities.NULL_SKELETON.get(), WitherSkeletonRenderer::new);
    }

    @SubscribeEvent
    public static void onPatchedRenderer(PatchedRenderersEvent.Add add) {
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.PLAYER_NPC.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.STEVE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ANGRY_STEVE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ALEX.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.ALEX, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.JEV.get(),
                (entitytype) -> (new PIllagerRenderer<>(add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.CHRIS.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SWORD.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_AXE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_PICKAXE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SHOVEL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_HOE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SKELETON.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.SKELETON, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
    }
}
