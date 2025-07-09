package com.pla.annoyingvillagers.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedList;
import java.util.Queue;

public class QueuedTaskScheduler {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Queue<Integer> delays = new LinkedList<>();
    private int ticks = 0;
    private int currentDelay = 0;

    public QueuedTaskScheduler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public QueuedTaskScheduler schedule(Runnable task, int delayTicks) {
        tasks.add(task);
        delays.add(delayTicks);
        return this;
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (currentDelay > 0) {
                ticks++;
                if (ticks >= currentDelay) {
                    ticks = 0;
                    currentDelay = 0;
                } else {
                    return;
                }
            }

            if (!tasks.isEmpty() && currentDelay == 0) {
                Runnable task = tasks.poll();
                currentDelay = delays.poll();
                if (task != null) task.run();

                if (tasks.isEmpty()) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }
        }
    }
}
