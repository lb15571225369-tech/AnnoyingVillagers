//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.event.entity.living.LivingDeathEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.init.AnnoyingVillagersModGameRules;
//
//@EventBusSubscriber
//public class MB1V1KillProcedure {
//
//    @SubscribeEvent
//    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
//        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
//            execute(livingdeathevent, livingdeathevent.getEntity().level, livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
//        }
//
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
//        execute((Event) null, levelaccessor, entity, entity1);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity, final Entity entity1) {
//        if (entity != null && entity1 != null) {
//            if (levelaccessor.getLevelData().getGameRules().getBoolean(AnnoyingVillagersModGameRules.MUSIC_BOX) && entity instanceof Player && entity1 instanceof Player) {
//                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "stopsound @s record modid = AnnoyingVillagers.MODID:music_box");
//                }
//
//                ((<undefinedtype>)(new Object() {
//                    private int ticks = 0;
//                    private float waitTicks;
//                    private LevelAccessor world;
//
//                    public void start(LevelAccessor levelaccessor1, int i) {
//                        this.waitTicks = (float)i;
//                        MinecraftForge.EVENT_BUS.register(this);
//                        this.world = levelaccessor1;
//                    }
//
//                    @SubscribeEvent
//                    public void tick(ServerTickEvent servertickevent) {
//                        if (servertickevent.phase == Phase.END) {
//                            ++this.ticks;
//                            if ((float)this.ticks >= this.waitTicks) {
//                                this.run();
//                            }
//                        }
//
//                    }
//
//                    private void run() {
//                        Entity entity2 = entity1;
//
//                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "playsound modid = AnnoyingVillagers.MODID:music_box_win record @s");
//                        }
//
//                        MinecraftForge.EVENT_BUS.unregister(this);
//                    }
//                })).start(levelaccessor, 10);
//            }
//
//        }
//    }
//}
