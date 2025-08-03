package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent.Add;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RenderEngine {
    @SubscribeEvent
    public static void registerRenderer(Add add) {
        add.addItemRenderer((Item) AnnoyingVillagersModItems.LEGENDARY_SWORD.get(), new RenderIncinerator());
        add.addItemRenderer((Item) AnnoyingVillagersModItems.HARD_GREAT_SWORD.get(), new HardGreatSwordRender());
    }
}
