package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class JoinEndProcedure {

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent entitytraveltodimensionevent) {
        execute(entitytraveltodimensionevent);
    }

    public static void execute() {
        execute((Event) null);
    }

    private static void execute(@Nullable Event event) {}
}
