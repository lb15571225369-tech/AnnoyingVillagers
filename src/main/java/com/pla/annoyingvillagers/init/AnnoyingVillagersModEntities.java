package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class AnnoyingVillagersModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AnnoyingVillagers.MODID);
    public static final RegistryObject<EntityType<BlueDemonEntity>> BLUE_DEMON = register("blue_demon", Builder.<BlueDemonEntity>of(BlueDemonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonStagingEntity>> BLUE_DEMON_STAGING = register("blue_demon_staging", Builder.<BlueDemonStagingEntity>of(BlueDemonStagingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonStagingEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemon2Entity>> BLUE_DEMON_2 = register("blue_demon_2", Builder.<BlueDemon2Entity>of(BlueDemon2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemon2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonEndStagingEntity>> BLUE_DEMON_END_STAGING = register("blue_demon_end_staging", Builder.<BlueDemonEndStagingEntity>of(BlueDemonEndStagingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEndStagingEntity::new).fireImmune().sized(0.0F, 1.8F));

    public static final RegistryObject<EntityType<VillagerScoutCaptainEntity>> VILLAGER_SCOUT_CAPTAIN = register("villager_scout_captain", Builder.<VillagerScoutCaptainEntity>of(VillagerScoutCaptainEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(VillagerScoutCaptainEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<VillagerScoutEntity>> VILLAGER_SCOUT = register("villager_scout", Builder.<VillagerScoutEntity>of(VillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(VillagerScoutEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueVillagerGeneralEntity>> BLUE_VILLAGER_GENERAL = register("blue_villager_general", Builder.<BlueVillagerGeneralEntity>of(BlueVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(BlueVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<PurpleVillagerGeneralEntity>> GREEN_VILLAGER_GENERAL = register("green_villager_general", Builder.<PurpleVillagerGeneralEntity>of(PurpleVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PurpleVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<RedVillagerGeneralEntity>> RED_VILLAGER_GENERAL = register("red_villager_general", Builder.<RedVillagerGeneralEntity>of(RedVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(RedVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<GreenVillagerGeneralEntity>> PURPLE_VILLAGER_GENERAL = register("purple_villager_general", Builder.<GreenVillagerGeneralEntity>of(GreenVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(GreenVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<AlexEntity>> ALEX = register("alex", Builder.<AlexEntity>of(AlexEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(AlexEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<JevEntity>> JEV = register("jev", Builder.<JevEntity>of(JevEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(JevEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BbqEntity>> BBQ = register("bbq", Builder.<BbqEntity>of(BbqEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(BbqEntity::new).fireImmune().sized(0.4F, 0.7F));
    public static final RegistryObject<EntityType<ChrisEntity>> CHRIS = register("chris", Builder.<ChrisEntity>of(ChrisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(ChrisEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<SteveEntity>> STEVE = register("steve", Builder.<SteveEntity>of(SteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(SteveEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Steve2Entity>> STEVE_2 = register("steve_2", Builder.<Steve2Entity>of(Steve2Entity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(Steve2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<AngrySteveEntity>> ANGRY_STEVE = register("angry_steve", Builder.<AngrySteveEntity>of(AngrySteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(AngrySteveEntity::new).fireImmune().sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<InfectedTheMostMoistBurrit0Entity>> INFECTED_THEMOSTMOISTBURRIT0 = register("infected_the_moi_moist_burrit0", Builder.<InfectedTheMostMoistBurrit0Entity>of(InfectedTheMostMoistBurrit0Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(InfectedTheMostMoistBurrit0Entity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<InfectedPlayerMobEntity>> INFECTED_PLAYER_MOB = register("infected_player_mob", Builder.<InfectedPlayerMobEntity>of(InfectedPlayerMobEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(InfectedPlayerMobEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<InfectedChrisEntity>> INJECTED_CHRIS = register("infected_chris", Builder.<InfectedChrisEntity>of(InfectedChrisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(InfectedChrisEntity::new).sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<StealthAttackEntity>> STEALTH_ATTACK_PROJECTILE = register("projectile_stealth_attack", Builder.<StealthAttackEntity>of(StealthAttackEntity::new, MobCategory.MISC).setCustomClientFactory(StealthAttackEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<EnchantedEnderPearlEntity>> ENCHANTED_ENDER_PEARL_PROJECTILE = register("projectile_enchanted_ender_pearl", Builder.<EnchantedEnderPearlEntity>of(EnchantedEnderPearlEntity::new, MobCategory.MISC).setCustomClientFactory(EnchantedEnderPearlEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<ThrownPoisonEggEntity>> THROWN_POISON_EGG = register("thrown_poison_egg", Builder.<ThrownPoisonEggEntity>of(ThrownPoisonEggEntity::new, MobCategory.MISC).setCustomClientFactory(ThrownPoisonEggEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<BlueDemonTridentEntity>> BLUEDEMONTRIDENT = register("projectile_bluedemontrident", Builder.<BlueDemonTridentEntity>of(BlueDemonTridentEntity::new, MobCategory.MISC).setCustomClientFactory(BlueDemonTridentEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<BlueDemonTridentParticleEntity>> BD_TRIDENT = register("bd_trident", Builder.<BlueDemonTridentParticleEntity>of(BlueDemonTridentParticleEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BlueDemonTridentParticleEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<DarkOBFarEntity>> DARK_OB_FAR = register("projectile_dark_ob_far", Builder.<DarkOBFarEntity>of(DarkOBFarEntity::new, MobCategory.MISC).setCustomClientFactory(DarkOBFarEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));

    public static final RegistryObject<EntityType<Herobrine1Entity>> HEROBRINE_1 = register("herobrine_1", Builder.<Herobrine1Entity>of(Herobrine1Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(Herobrine1Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine2Entity>> HEROBRINE_2 = register("herobrine_2", Builder.<Herobrine2Entity>of(Herobrine2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(Herobrine2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine3Entity>> HEROBRINE_3 = register("herobrine_3", Builder.<Herobrine3Entity>of(Herobrine3Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(Herobrine3Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine4Entity>> HEROBRINE_4 = register("herobrine_4", Builder.<Herobrine4Entity>of(Herobrine4Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(Herobrine4Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine5Entity>> HEROBRINE_5 = register("herobrine_5", Builder.<Herobrine5Entity>of(Herobrine5Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(Herobrine5Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine6Entity>> HEROBRINE_6 = register("herobrine_6", Builder.<Herobrine6Entity>of(Herobrine6Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(Herobrine6Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine7Entity>> HEROBRINE_7 = register("herobrine_7", Builder.<Herobrine7Entity>of(Herobrine7Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(Herobrine7Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ArmoredHerobrineEntity>> ARMORED_HEROBRINE = register("armored_herobrine", Builder.<ArmoredHerobrineEntity>of(ArmoredHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(ArmoredHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ShadowHerobrineEntity>> SHADOW_HEROBRINE = register("shadow_herobrine", Builder.<ShadowHerobrineEntity>of(ShadowHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(ShadowHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<GlaiveHerobrineEntity>> GLAIVE_HEROBRINE = register("glaive_herobrine", Builder.<GlaiveHerobrineEntity>of(GlaiveHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(GlaiveHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ReaperHerobrineEntity>> REAPER_HEROBRINE = register("reaper_herobrine", Builder.<ReaperHerobrineEntity>of(ReaperHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(ReaperHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<SwordsManHerobrineEntity>> SWORDSMAN_HEROBRINE = register("swordsman_herobrine", Builder.<SwordsManHerobrineEntity>of(SwordsManHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(SwordsManHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<SledgehammerHerobrineEntity>> SLEDGEHAMMER_HEROBRINE = register("sledgehammer_herobrine", Builder.<SledgehammerHerobrineEntity>of(SledgehammerHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(SledgehammerHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<AegisHerobrineEntity>> AEGIS_HEROBRINE = register("aegis_herobrine", Builder.<AegisHerobrineEntity>of(AegisHerobrineEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(AegisHerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<EliteHerobrineKnockedEntity>> ELITE_HEROBRINE_KNOCKED = register("elite_herobrine_knocked", Builder.<EliteHerobrineKnockedEntity>of(EliteHerobrineKnockedEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(EliteHerobrineKnockedEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullEntity>> NULL = register("null", Builder.<NullEntity>of(NullEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullSwordEntity>> NULL_SWORD = register("null_sword", Builder.<NullSwordEntity>of(NullSwordEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullSwordEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullAxeEntity>> NULL_AXE = register("null_axe", Builder.<NullAxeEntity>of(NullAxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullAxeEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullPickaxeEntity>> NULL_PICKAXE = register("null_pickaxe", Builder.<NullPickaxeEntity>of(NullPickaxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullPickaxeEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullShovelEntity>> NULL_SHOVEL = register("null_shovel", Builder.<NullShovelEntity>of(NullShovelEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullShovelEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<NullHoeEntity>> NULL_HOE = register("null_hoe", Builder.<NullHoeEntity>of(NullHoeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NullHoeEntity::new).fireImmune().sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<SnakeBladeEntity>> SNAKE_BLADE = register("snake_blade", Builder.<SnakeBladeEntity>of(SnakeBladeEntity::new, MobCategory.MISC).sized(0.1F, 0.1F));
    public static final RegistryObject<EntityType<DragonBeamEntity>> DRAGON_BEAM = register("dragon_beam", Builder.<DragonBeamEntity>of(DragonBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<BabyEnderDragonEntity>> BABY_ENDER_DRAGON = register("baby_ender_dragon", Builder.<BabyEnderDragonEntity>of(BabyEnderDragonEntity::new, MobCategory.CREATURE).sized(0.9F, 0.5F).clientTrackingRange(8).updateInterval(3));
    public static final RegistryObject<EntityType<BabyDragonBeamEntity>> BABY_DRAGON_BEAM = register("baby_dragon_beam", Builder.<BabyDragonBeamEntity>of(BabyDragonBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<ObsidianSledgehammerHitEntity>> OBSIDIAN_SLEDGEHAMMER_HIT = register("obsidian_sledgehammer_hit", Builder.<ObsidianSledgehammerHitEntity>of(ObsidianSledgehammerHitEntity::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(6).updateInterval(2).fireImmune());
    public static final RegistryObject<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE = register("block_projectile", Builder.<BlockProjectileEntity>of(BlockProjectileEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(64).updateInterval(2).fireImmune());

    public static final RegistryObject<EntityType<AlexDeadEntity>> ALEX_DEAD = register("alex_dead", Builder.<AlexDeadEntity>of(AlexDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(AlexDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<SteveDeadEntity>> STEVE_DEAD = register("steve_dead", Builder.<SteveDeadEntity>of(SteveDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(SteveDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ChrisDeadEntity>> CHRIS_DEAD = register("chris_dead", Builder.<ChrisDeadEntity>of(ChrisDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(ChrisDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<InfectedChrisDeadEntity>> INFECTED_CHRIS_DEAD = register("infected_chris_dead", Builder.<InfectedChrisDeadEntity>of(InfectedChrisDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(InfectedChrisDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<JevDeadEntity>> JEV_DEAD = register("jev_dead", Builder.<JevDeadEntity>of(JevDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(JevDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<VillagerScoutDeadEntity>> VILLAGER_SCOUT_DEAD = register("villager_scout_dead", Builder.<VillagerScoutDeadEntity>of(VillagerScoutDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(VillagerScoutDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<RedVillagerGeneralDeadEntity>> RED_VILLAGER_GENERAL_DEAD = register("red_villager_general_dead", Builder.<RedVillagerGeneralDeadEntity>of(RedVillagerGeneralDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(RedVillagerGeneralDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueVillagerGeneralDeadEntity>> BLUE_VILLAGER_GENERAL_DEAD = register("blue_villager_general_dead", Builder.<BlueVillagerGeneralDeadEntity>of(BlueVillagerGeneralDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(BlueVillagerGeneralDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<GreenVillagerGeneralDeadEntity>> GREEN_VILLAGER_GENERAL_DEAD = register("green_villager_general_dead", Builder.<GreenVillagerGeneralDeadEntity>of(GreenVillagerGeneralDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(GreenVillagerGeneralDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<PurpleVillagerGeneralDeadEntity>> PURPLE_VILLAGER_GENERAL_DEAD = register("purple_villager_general_dead", Builder.<PurpleVillagerGeneralDeadEntity>of(PurpleVillagerGeneralDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(PurpleVillagerGeneralDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonDeadEntity>> BLUE_DEMON_DEAD = register("blue_demon_dead", Builder.<BlueDemonDeadEntity>of(BlueDemonDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(BlueDemonDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<HerobrineDeadEntity>> HEROBRINE_DEAD = register("herobrine_dead", Builder.<HerobrineDeadEntity>of(HerobrineDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(HerobrineDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ShadowHerobrineDeadEntity>> SHADOW_HEROBRINE_DEAD = register("shadow_herobrine_dead", Builder.<ShadowHerobrineDeadEntity>of(ShadowHerobrineDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(ShadowHerobrineDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<PlayerMobDeadEntity>> PLAYER_MOB_DEAD = register("player_mob_dead", Builder.<PlayerMobDeadEntity>of(PlayerMobDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(PlayerMobDeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<InfectedTheMostMoistBurrit0DeadEntity>> INFECTED_THEMOSTMOISTBURRIT0_DEAD = register("infected_the_moi_moist_burrit0_dead", Builder.<InfectedTheMostMoistBurrit0DeadEntity>of(InfectedTheMostMoistBurrit0DeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(InfectedTheMostMoistBurrit0DeadEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<EliteHerobrineDeadEntity>> ELITE_HEROBRINE_DEAD = register("elite_herobrine_dead", Builder.<EliteHerobrineDeadEntity>of(EliteHerobrineDeadEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(EliteHerobrineDeadEntity::new).fireImmune().sized(0.6F, 1.8F));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String s, Builder<T> builder) {
        return AnnoyingVillagersModEntities.REGISTRY.register(s, () -> {
            return builder.build(s);
        });
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent fmlcommonsetupevent) {
        fmlcommonsetupevent.enqueueWork(() -> {
            Herobrine1Entity.init();
            BlueDemonEntity.init();
            Herobrine2Entity.init();
            BlueDemonStagingEntity.init();
            BlueDemon2Entity.init();
            BlueDemonEndStagingEntity.init();
            BlueDemonTridentParticleEntity.init();
            VillagerScoutCaptainEntity.init();
            VillagerScoutEntity.init();
            BlueVillagerGeneralEntity.init();
            PurpleVillagerGeneralEntity.init();
            RedVillagerGeneralEntity.init();
            GreenVillagerGeneralEntity.init();
            AlexEntity.init();
            JevEntity.init();
            BbqEntity.init();
            ChrisEntity.init();
            InfectedChrisEntity.init();
            Herobrine3Entity.init();
            Steve2Entity.init();
            SteveEntity.init();
            AngrySteveEntity.init();
            AlexDeadEntity.init();
            SteveDeadEntity.init();
            ChrisDeadEntity.init();
            InfectedChrisDeadEntity.init();
            JevDeadEntity.init();
            VillagerScoutDeadEntity.init();
            RedVillagerGeneralDeadEntity.init();
            BlueVillagerGeneralDeadEntity.init();
            GreenVillagerGeneralDeadEntity.init();
            PurpleVillagerGeneralDeadEntity.init();
            BlueDemonDeadEntity.init();
            HerobrineDeadEntity.init();
            ShadowHerobrineDeadEntity.init();
            PlayerMobDeadEntity.init();
            Herobrine7Entity.init();
            ArmoredHerobrineEntity.init();
            InfectedTheMostMoistBurrit0Entity.init();
            InfectedTheMostMoistBurrit0DeadEntity.init();
            ShadowHerobrineEntity.init();
            GlaiveHerobrineEntity.init();
            InfectedPlayerMobEntity.init();
            ReaperHerobrineEntity.init();
            SwordsManHerobrineEntity.init();
            SledgehammerHerobrineEntity.init();
            AegisHerobrineEntity.init();
            EliteHerobrineKnockedEntity.init();
            EliteHerobrineDeadEntity.init();
            Herobrine5Entity.init();
            Herobrine6Entity.init();
            Herobrine4Entity.init();
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityattributecreationevent) {
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_1.get(), Herobrine1Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), Herobrine2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_7.get(), Herobrine7Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_STAGING.get(), BlueDemonStagingEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_END_STAGING.get(), BlueDemonEndStagingEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), BlueDemonTridentParticleEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), VillagerScoutEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL.get(), BlueVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL.get(), PurpleVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL.get(), RedVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL.get(), GreenVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ALEX_DEAD.get(), AlexDeadEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ALEX.get(), AlexEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.JEV.get(), JevEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BBQ.get(), BbqEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CHRIS.get(), ChrisEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.INJECTED_CHRIS.get(), InfectedChrisEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_3.get(), Herobrine3Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), ArmoredHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.STEVE_2.get(), Steve2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.STEVE_DEAD.get(), SteveDeadEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.STEVE.get(), SteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CHRIS_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.INFECTED_CHRIS_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.VILLAGER_SCOUT_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.JEV_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.RED_VILLAGER_GENERAL_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.GREEN_VILLAGER_GENERAL_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_VILLAGER_GENERAL_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.PURPLE_VILLAGER_GENERAL_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE_DEAD.get(), AngrySteveEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.PLAYER_MOB_DEAD.get(), PlayerMobDeadEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), InfectedTheMostMoistBurrit0Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0_DEAD.get(), InfectedTheMostMoistBurrit0DeadEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), ShadowHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), GlaiveHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), InfectedPlayerMobEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), ReaperHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), SwordsManHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), SledgehammerHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), AegisHerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), BabyEnderDragonEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), EliteHerobrineKnockedEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ELITE_HEROBRINE_DEAD.get(), EliteHerobrineDeadEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_5.get(), Herobrine5Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_6.get(), Herobrine6Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL.get(), NullEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL_SWORD.get(), NullSwordEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL_AXE.get(), NullAxeEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullShovelEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.NULL_HOE.get(), NullHoeEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_4.get(), Herobrine4Entity.createAttributes().build());
    }
}
