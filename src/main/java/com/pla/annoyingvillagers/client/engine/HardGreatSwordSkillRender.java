package com.pla.annoyingvillagers.client.engine;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent.Add;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD, value = {Dist.CLIENT})
public class HardGreatSwordSkillRender {

    @SubscribeEvent
    public static void registerRenderer(Add add) {
        add.addItemRenderer((Item) AnnoyingVillagersModItems.HARD_GREAT_SWORD.get(), new HardGreatSwordRender());
    }
}
