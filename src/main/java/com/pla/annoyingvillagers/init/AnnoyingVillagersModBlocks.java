package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    public static final RegistryObject<Block> ENCHANT_BED = AnnoyingVillagersModBlocks.REGISTRY.register("enchant_bed", EnchantBedBlock::new);
    public static final RegistryObject<Block> DARK_OB_SS = AnnoyingVillagersModBlocks.REGISTRY.register("dark_ob_ss", DarkObSsBlock::new);
    public static final RegistryObject<Block> NONEOB = AnnoyingVillagersModBlocks.REGISTRY.register("noneob", NoneObBlock::new);
    public static final RegistryObject<Block> DARKOB = AnnoyingVillagersModBlocks.REGISTRY.register("darkob", DarkObBlock::new);
    public static final RegistryObject<Block> DARK_OB_UP = AnnoyingVillagersModBlocks.REGISTRY.register("dark_ob_up", DarkObUpBlock::new);
    public static final RegistryObject<Block> SHADOW_OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("shadow_obsidian", ShadowObsidianBlock::new);
    public static final RegistryObject<Block> OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("obsidian", ObsidianBlock::new);
    public static final RegistryObject<Block> CRYING_OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("crying_obsidian", CryingObsidianBlock::new);
    public static final RegistryObject<EndFireBlock> END_FIRE = AnnoyingVillagersModBlocks.REGISTRY.register(
            "end_fire",
            () -> new EndFireBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .replaceable().noCollission().instabreak()
                            .lightLevel(s -> 15)
                            .sound(SoundType.WOOL)
                            .pushReaction(PushReaction.DESTROY)
            )
    );

    @EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
    public static class ClientSideHandler {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent fmlclientsetupevent) {
        }

        @SubscribeEvent
        public static void blockColorLoad(net.minecraftforge.client.event.RegisterColorHandlersEvent.Block block) {
            NoneObBlock.blockColorLoad(block);
        }
    }
}
