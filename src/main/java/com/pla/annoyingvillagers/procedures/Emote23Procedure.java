//package com.pla.annoyingvillagers.procedures;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class Emote23Procedure {
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
//        if (entity != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/emoji/emote_23\" 0 1");
//            }
//
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "stopsound @s record annoyingvillagers:emote_23");
//            }
//
//            if (!entity.getPersistentData().getBoolean("emote") && levelaccessor instanceof Level) {
//                Level level = (Level)levelaccessor;
//
//                if (!level.isClientSide()) {
//                    level.playSound((Player)null, new BlockPos(d0, d1, d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":emote_23")), SoundSource.NEUTRAL, 2.0F, 1.0F);
//                } else {
//                    level.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(AnnoyingVillagers.MODID + ":emote_23")), SoundSource.NEUTRAL, 2.0F, 1.0F, false);
//                }
//            }
//
//            if (entity instanceof Player) {
//                Player player = (Player)entity;
//
//                player.closeContainer();
//            }
//
//            ((<undefinedtype>)(new Object() {
//                private int ticks = 0;
//                private float waitTicks;
//                private LevelAccessor world;
//
//                public void start(LevelAccessor levelaccessor1, int i) {
//                    this.waitTicks = (float)i;
//                    MinecraftForge.EVENT_BUS.register(this);
//                    this.world = levelaccessor1;
//                }
//
//                @SubscribeEvent
//                public void tick(ServerTickEvent servertickevent) {
//                    if (servertickevent.phase == Phase.END) {
//                        ++this.ticks;
//                        if ((float)this.ticks >= this.waitTicks) {
//                            this.run();
//                        }
//                    }
//
//                }
//
//                private void run() {
//                    Entity entity1 = entity;
//
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight mode battle @s");
//                    }
//
//                    MinecraftForge.EVENT_BUS.unregister(this);
//                }
//            })).start(levelaccessor, 6);
//        }
//    }
//}
