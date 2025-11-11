package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RenderEngine {
    @SubscribeEvent
    public static void registerRenderer(PatchedRenderersEvent.RegisterItemRenderer add) {
        add.addItemRenderer(AnnoyingVillagersModItems.LEGENDARY_SWORD.getId(), RenderIncinerator::new);
        add.addItemRenderer(AnnoyingVillagersModItems.HARD_GREAT_SWORD.getId(), HardGreatSwordRender::new);
    }
}
