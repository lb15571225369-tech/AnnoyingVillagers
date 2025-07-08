package com.pla.annoyingvillagers.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class C4damageFangZhiFangKuaiShiProcedure {

    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
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
                this.world.setBlock(new BlockPos(d0, d1, d2), Blocks.AIR.defaultBlockState(), 3);
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        })).start(levelaccessor, 1200);
    }
}
