//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class Emote18Procedure {
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
//        if (entity != null) {
//            if (!entity.level.isClientSide() && entity.getServer() != null) {
//                entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/funny_idle\" 0 1");
//            }
//
//            Vec3 vec3 = new Vec3(d0, d1, d2);
//            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.0D), (entity1) -> {
//                return true;
//            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
//                return entity1.distanceToSqr(vec3);
//            })).collect(Collectors.toList());
//            Iterator iterator = list.iterator();
//
//            while(iterator.hasNext()) {
//                Entity entity1 = (Entity)iterator.next();
//
//                if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                    entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "execute as @s run execute if entity @s run title @p actionbar {\"text\":\"" + entity.getDisplayName().getString() + ": \u4f60\u770b\u770b\u4f60\u540e\u9762\u5462\",\"italic\":true,\"color\":\"red\"}");
//                }
//
//                if (entity1 instanceof Player) {
//                    Player player = (Player)entity1;
//
//                    if (!player.level.isClientSide()) {
//                        player.displayClientMessage(new TextComponent(entity.getDisplayName().getString() + ": \u4f60\u770b\u770b\u4f60\u540e\u9762\u5462"), true);
//                    }
//                }
//            }
//
//            if (entity instanceof Player) {
//                Player player1 = (Player)entity;
//
//                player1.closeContainer();
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
//                    Entity entity2 = entity;
//
//                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "epicfight mode battle @s");
//                    }
//
//                    MinecraftForge.EVENT_BUS.unregister(this);
//                }
//            })).start(levelaccessor, 6);
//        }
//    }
//}
