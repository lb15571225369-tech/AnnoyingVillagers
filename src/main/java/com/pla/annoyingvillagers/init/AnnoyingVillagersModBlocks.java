package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, AnnoyingVillagers.MODID);
//    public static final RegistryObject<Block> C_4 = AnnoyingVillagersModBlocks.REGISTRY.register("c_4", () -> {
//        return new C4Block();
//    });
//    public static final RegistryObject<Block> C_4DAMAGE = AnnoyingVillagersModBlocks.REGISTRY.register("c_4damage", () -> {
//        return new C4damageBlock();
//    });
    public static final RegistryObject<Block> ENCHANT_BED = AnnoyingVillagersModBlocks.REGISTRY.register("enchant_bed", () -> {
        return new EnchantBedBlock();
    });
    public static final RegistryObject<Block> DARK_OB_SS = AnnoyingVillagersModBlocks.REGISTRY.register("dark_ob_ss", () -> {
        return new DarkObSsBlock();
    });
    public static final RegistryObject<Block> NONEOB = AnnoyingVillagersModBlocks.REGISTRY.register("noneob", () -> {
        return new NoneobBlock();
    });
    public static final RegistryObject<Block> DARKOB = AnnoyingVillagersModBlocks.REGISTRY.register("darkob", () -> {
        return new DarkobBlock();
    });
    public static final RegistryObject<Block> DARK_OB_UP = AnnoyingVillagersModBlocks.REGISTRY.register("dark_ob_up", () -> {
        return new DarkObUpBlock();
    });
    public static final RegistryObject<Block> ANYINGHEIYAOSHI = AnnoyingVillagersModBlocks.REGISTRY.register("anyingheiyaoshi", () -> {
        return new AnyingheiyaoshiBlock();
    });
    public static final RegistryObject<Block> PUTONGHEIYAOSHI = AnnoyingVillagersModBlocks.REGISTRY.register("putongheiyaoshi", () -> {
        return new PutongheiyaoshiBlock();
    });
    public static final RegistryObject<Block> PUTONGHEIYAOSHI_2 = AnnoyingVillagersModBlocks.REGISTRY.register("putongheiyaoshi_2", () -> {
        return new Putongheiyaoshi2Block();
    });
//    public static final RegistryObject<Block> DROP_ALL_ITEM_SPAWN = AnnoyingVillagersModBlocks.REGISTRY.register("drop_all_item_spawn", () -> {
//        return new DropAllItemSpawnBlock();
//    });
//    public static final RegistryObject<Block> C_4SPAWN = AnnoyingVillagersModBlocks.REGISTRY.register("c_4spawn", () -> {
//        return new C4spawnBlock();
//    });

    @EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
    public static class ClientSideHandler {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent fmlclientsetupevent) {
//            C4Block.registerRenderLayer();
//            C4damageBlock.registerRenderLayer();
            EnchantBedBlock.registerRenderLayer();
            DarkObSsBlock.registerRenderLayer();
            NoneobBlock.registerRenderLayer();
            DarkobBlock.registerRenderLayer();
            DarkObUpBlock.registerRenderLayer();
//            DropAllItemSpawnBlock.registerRenderLayer();
//            C4spawnBlock.registerRenderLayer();
        }

        @SubscribeEvent
        public static void blockColorLoad(net.minecraftforge.client.event.ColorHandlerEvent.Block net_minecraftforge_client_event_colorhandlerevent_block) {
            NoneobBlock.blockColorLoad(net_minecraftforge_client_event_colorhandlerevent_block);
        }
    }
}
