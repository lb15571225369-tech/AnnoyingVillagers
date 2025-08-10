package com.pla.annoyingvillagers.compat.dual_greatsword.world.loot;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "efdg")
public class EFModLootTables {

    @SubscribeEvent
    public static void modifyVanillaLootPools(LootTableLoadEvent loottableloadevent) {}
}
