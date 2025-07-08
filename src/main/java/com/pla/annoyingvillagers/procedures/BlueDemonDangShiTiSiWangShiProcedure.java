package com.pla.annoyingvillagers.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlueDemonDangShiTiSiWangShiProcedure {

    public static void execute(LevelAccessor levelaccessor, Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon annoying_villagers:blue_demon_r");
            }

            if (!entity.level.isClientSide()) {
                entity.discard();
            }

            entity1.getPersistentData().putBoolean("b_d", true);
            ((<undefinedtype>)(new Object() {
                private int ticks = 0;
                private float waitTicks;
                private LevelAccessor world;

                public void start(LevelAccessor levelaccessor1, int i) {
                    this.waitTicks = (float)i;
                    MinecraftForge.EVENT_BUS.register(this);
                    this.world = levelaccessor1;
                }

                @SubscribeEvent
                public void tick(ServerTickEvent servertickevent) {
                    if (servertickevent.phase == Phase.END) {
                        ++this.ticks;
                        if ((float)this.ticks >= this.waitTicks) {
                            this.run();
                        }
                    }

                }

                private void run() {
                    if (entity1.isAlive()) {
                        entity1.getPersistentData().putBoolean("b_d", false);
                    }

                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            })).start(levelaccessor, 120);
        }
    }
}
