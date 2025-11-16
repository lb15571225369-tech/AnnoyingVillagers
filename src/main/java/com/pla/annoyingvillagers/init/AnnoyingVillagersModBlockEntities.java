package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.CryingObsidianSpikeBlockEntity;
import com.pla.annoyingvillagers.blockentity.DarkObUpBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AnnoyingVillagersModBlockEntities {
    private AnnoyingVillagersModBlockEntities() {}
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AnnoyingVillagers.MODID);

    public static final RegistryObject<BlockEntityType<DarkObUpBlockEntity>> DARK_OB_UP =
            REGISTRY.register("dark_ob_up",
                    () -> BlockEntityType.Builder.of(
                            DarkObUpBlockEntity::new,
                            AnnoyingVillagersModBlocks.DARK_OB_UP.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ShadowObsidianBlockEntity>> SHADOW_OBSIDIAN_BLOCK =
            REGISTRY.register("shadow_obsidian",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ObsidianBlockEntity>> OBSIDIAN_BLOCK =
            REGISTRY.register("obsidian",
                    () -> BlockEntityType.Builder.of(
                            ObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<CryingObsidianSpikeBlockEntity>> CRYING_OBSIDIAN_SPIKE_BLOCK =
            REGISTRY.register("crying_obsidian_spike",
                    () -> BlockEntityType.Builder.of(
                            CryingObsidianSpikeBlockEntity::new,
                            AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get()
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}