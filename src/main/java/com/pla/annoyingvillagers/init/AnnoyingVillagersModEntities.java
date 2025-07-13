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
    public static final RegistryObject<EntityType<BlueDemonREntity>> BLUE_DEMON_R = register("blue_demon_r", Builder.<BlueDemonREntity>of(BlueDemonREntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonREntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemon2Entity>> BLUE_DEMON_2 = register("blue_demon_2", Builder.<BlueDemon2Entity>of(BlueDemon2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemon2Entity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueDemonEndEntity>> BLUE_DEMON_END = register("blue_demon_end", Builder.<BlueDemonEndEntity>of(BlueDemonEndEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEndEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<DarkOBFarEntity>> DARK_OB_FAR = register("projectile_dark_ob_far", Builder.<DarkOBFarEntity>of(DarkOBFarEntity::new, MobCategory.MISC).setCustomClientFactory(DarkOBFarEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<BdTridentEntity>> BD_TRIDENT = register("bd_trident", Builder.<BdTridentEntity>of(BdTridentEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BdTridentEntity::new).fireImmune().sized(0.0F, 1.8F));
    public static final RegistryObject<EntityType<BluedemontridentEntity>> BLUEDEMONTRIDENT = register("projectile_bluedemontrident", Builder.<BluedemontridentEntity>of(BluedemontridentEntity::new, MobCategory.MISC).setCustomClientFactory(BluedemontridentEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<CczdzEntity>> CCZDZ = register("cczdz", Builder.<CczdzEntity>of(CczdzEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(CczdzEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<CunMinZhenChaBingEntity>> CUN_MIN_ZHEN_CHA_BING = register("cun_min_zhen_cha_bing", Builder.<CunMinZhenChaBingEntity>of(CunMinZhenChaBingEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CunMinZhenChaBingEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<LanCunQiEntity>> LAN_CUN_QI = register("lan_cun_qi", Builder.<LanCunQiEntity>of(LanCunQiEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(LanCunQiEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<LuCunQiEntity>> LU_CUN_QI = register("lu_cun_qi", Builder.<LuCunQiEntity>of(LuCunQiEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(LuCunQiEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<HongCunQiEntity>> HONG_CUN_QI = register("hong_cun_qi", Builder.<HongCunQiEntity>of(HongCunQiEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(HongCunQiEntity::new).fireImmune().sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<ZiCunQiEntity>> ZI_CUN_QI = register("zi_cun_qi", Builder.<ZiCunQiEntity>of(ZiCunQiEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(ZiCunQiEntity::new).fireImmune().sized(0.6F, 1.8F));
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
            BlueDemonREntity.init();
            BlueDemon2Entity.init();
            BlueDemonEndEntity.init();
            BdTridentEntity.init();
            CczdzEntity.init();
            CunMinZhenChaBingEntity.init();
            LanCunQiEntity.init();
            LuCunQiEntity.init();
            HongCunQiEntity.init();
            ZiCunQiEntity.init();
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityattributecreationevent) {
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE.get(), HerobrineEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HEROBRINE_2.get(), Herobrine2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_R.get(), BlueDemonREntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_2.get(), BlueDemon2Entity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BLUE_DEMON_END.get(), BlueDemonEndEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.BD_TRIDENT.get(), BdTridentEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CCZDZ.get(), CczdzEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.CUN_MIN_ZHEN_CHA_BING.get(), CunMinZhenChaBingEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.LAN_CUN_QI.get(), LanCunQiEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.LU_CUN_QI.get(), LuCunQiEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.HONG_CUN_QI.get(), HongCunQiEntity.createAttributes().build());
        entityattributecreationevent.put((EntityType) AnnoyingVillagersModEntities.ZI_CUN_QI.get(), ZiCunQiEntity.createAttributes().build());
    }
}
