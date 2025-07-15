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

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, AnnoyingVillagers.MODID);
    public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = register("herobrine", Builder.<HerobrineEntity>of(HerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(HerobrineEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonEntity>> BLUE_DEMON = register("blue_demon", Builder.<BlueDemonEntity>of(BlueDemonEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<Herobrine2Entity>> HEROBRINE_2 = register("herobrine_2", Builder.<Herobrine2Entity>of(Herobrine2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(Herobrine2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonStagingEntity>> BLUE_DEMON_R = register("blue_demon_r", Builder.<BlueDemonStagingEntity>of(BlueDemonStagingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonStagingEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemon2Entity>> BLUE_DEMON_2 = register("blue_demon_2", Builder.<BlueDemon2Entity>of(BlueDemon2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemon2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonEndStagingEntity>> BLUE_DEMON_END = register("blue_demon_end", Builder.<BlueDemonEndStagingEntity>of(BlueDemonEndStagingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEndStagingEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<DarkOBFarEntity>> DARK_OB_FAR = register("projectile_dark_ob_far", Builder.<DarkOBFarEntity>of(DarkOBFarEntity::new, MobCategory.MISC).setCustomClientFactory(DarkOBFarEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<BlueDemonTridentParticleEntity>> BD_TRIDENT = register("bd_trident", Builder.<BlueDemonTridentParticleEntity>of(BlueDemonTridentParticleEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BlueDemonTridentParticleEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonTridentEntity>> BLUEDEMONTRIDENT = register("projectile_bluedemontrident", Builder.<BlueDemonTridentEntity>of(BlueDemonTridentEntity::new, MobCategory.MISC).setCustomClientFactory(BlueDemonTridentEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<VillagerScoutCaptainEntity>> CCZDZ = register("cczdz", Builder.<VillagerScoutCaptainEntity>of(VillagerScoutCaptainEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(VillagerScoutCaptainEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<VillagerScoutEntity>> CUN_MIN_ZHEN_CHA_BING = register("cun_min_zhen_cha_bing", Builder.<VillagerScoutEntity>of(VillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(VillagerScoutEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueVillagerGeneralEntity>> LAN_CUN_QI = register("lan_cun_qi", Builder.<BlueVillagerGeneralEntity>of(BlueVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(BlueVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<PurpleVillagerGeneralEntity>> LU_CUN_QI = register("lu_cun_qi", Builder.<PurpleVillagerGeneralEntity>of(PurpleVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PurpleVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<RedVillagerGeneralEntity>> HONG_CUN_QI = register("hong_cun_qi", Builder.<RedVillagerGeneralEntity>of(RedVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(RedVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<GreenVillagerGeneralEntity>> ZI_CUN_QI = register("zi_cun_qi", Builder.<GreenVillagerGeneralEntity>of(GreenVillagerGeneralEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(GreenVillagerGeneralEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<EnchantedEnderPearlEntity>> FUMOMOYINGZHENZHU = register("projectile_fumomoyingzhenzhu", Builder.<EnchantedEnderPearlEntity>of(EnchantedEnderPearlEntity::new, MobCategory.MISC).setCustomClientFactory(EnchantedEnderPearlEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    private static <T extends Entity> RegistryObject<EntityType<T>> register(String s, Builder<T> builder) {
        return AnnoyingVillagersModEntities.REGISTRY.register(s, () -> {
            return builder.build(s);
        });
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent fmlcommonsetupevent) {
        fmlcommonsetupevent.enqueueWork(() -> {
            HerobrineEntity.init();
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
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityattributecreationevent) {
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE.get(), HerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), Herobrine2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_R.get(), BlueDemonStagingEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_END.get(), BlueDemonEndStagingEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), BlueDemonTridentParticleEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CCZDZ.get(), VillagerScoutCaptainEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CUN_MIN_ZHEN_CHA_BING.get(), VillagerScoutEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.LAN_CUN_QI.get(), BlueVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.LU_CUN_QI.get(), PurpleVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HONG_CUN_QI.get(), RedVillagerGeneralEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ZI_CUN_QI.get(), GreenVillagerGeneralEntity.createAttributes().build());
    }
}
