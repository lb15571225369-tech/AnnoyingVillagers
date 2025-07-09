//package com.pla.annoyingvillagers.procedures;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class C4KeHuDuanXianShiSuiJiKeProcedure {
//
//    public static void execute(LevelAccessor levelaccessor, final double d0, final double d1, final double d2) {
//        ((<undefinedtype>)(new Object() {
//            private int ticks = 0;
//            private float waitTicks;
//            private LevelAccessor world;
//
//            public void start(LevelAccessor levelaccessor1, int i) {
//                this.waitTicks = (float)i;
//                MinecraftForge.EVENT_BUS.register(this);
//                this.world = levelaccessor1;
//            }
//
//            @SubscribeEvent
//            public void tick(ServerTickEvent servertickevent) {
//                if (servertickevent.phase == Phase.END) {
//                    ++this.ticks;
//                    if ((float)this.ticks >= this.waitTicks) {
//                        this.run();
//                    }
//                }
//
//            }
//
//            private void run() {
//                LevelAccessor levelaccessor1 = this.world;
//
//                if (levelaccessor1 instanceof Level) {
//                    Level level = (Level)levelaccessor1;
//
//                    if (!level.isClientSide()) {
//                        level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F);
//                    } else {
//                        level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":ca_deep")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
//                    }
//                }
//
//                MinecraftForge.EVENT_BUS.unregister(this);
//            }
//        })).start(levelaccessor, 20);
//    }
//}
