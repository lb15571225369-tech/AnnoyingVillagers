package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class DelayedTask {
    private int ticks = 0;
    private final int waitTicks;

    public DelayedTask(int waitTicks) {
        this.waitTicks = waitTicks;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(ServerTickEvent event) throws CommandSyntaxException {
        if (event.phase == TickEvent.Phase.END) {
            ticks++;
            if (ticks >= waitTicks) {
                run();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }

    public abstract void run() throws CommandSyntaxException;
}
