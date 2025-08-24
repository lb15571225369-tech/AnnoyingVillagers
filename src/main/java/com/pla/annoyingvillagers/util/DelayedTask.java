package com.pla.annoyingvillagers.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public abstract class DelayedTask {
    private int ticks = 0;
    private final int waitTicks;
    private boolean done = false;

    public DelayedTask(int waitTicks) {
        this.waitTicks = waitTicks;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || done) return;

        if (++ticks >= waitTicks) {
            done = true;
            run();

            var server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                server.execute(() -> MinecraftForge.EVENT_BUS.unregister(this));
            } else {
            }
        }
    }

    public abstract void run();
}
