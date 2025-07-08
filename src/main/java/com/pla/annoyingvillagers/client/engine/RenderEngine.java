package com.pla.annoyingvillagers.client.engine;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent.Add;

@EventBusSubscriber(modid = "annoying_villagers", bus = Bus.MOD, value = {Dist.CLIENT})
public class RenderEngine {

    @SubscribeEvent
    public static void registerRenderer(Add add) {
        add.addItemRenderer((Item) AnnoyingVillagersModItems.LEGENDARY_SWORD.get(), new RenderIncinerator());
    }
}
