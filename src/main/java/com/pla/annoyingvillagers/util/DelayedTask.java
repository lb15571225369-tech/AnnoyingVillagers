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

    private boolean pendingUnregister = false;
    private boolean unregistered = false;

    public DelayedTask(int waitTicks) {
        this.waitTicks = waitTicks;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (pendingUnregister && !unregistered) {
                var server = ServerLifecycleHooks.getCurrentServer();
                if (server != null) {
                    server.execute(() -> {
                        if (!unregistered) {
                            MinecraftForge.EVENT_BUS.unregister(this);
                            unregistered = true;
                        }
                    });
                } else {
                    MinecraftForge.EVENT_BUS.unregister(this);
                    unregistered = true;
                }
                pendingUnregister = false;
            }
            return;
        }

        if (done) return;
        if (++ticks >= waitTicks) {
            done = true;
            try {
                run();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            pendingUnregister = true;
        }
    }

    public abstract void run();
}
