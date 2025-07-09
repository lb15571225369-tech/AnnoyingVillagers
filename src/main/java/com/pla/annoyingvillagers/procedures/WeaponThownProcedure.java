//package com.pla.annoyingvillagers.procedures;
//
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//import javax.annotation.Nullable;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.registries.ForgeRegistries;
//import yesman.epicfight.api.animation.types.ActionAnimation;
//import yesman.epicfight.api.animation.types.AttackAnimation;
//import yesman.epicfight.api.animation.types.DynamicAnimation;
//import yesman.epicfight.api.animation.types.LongHitAnimation;
//import yesman.epicfight.world.capabilities.EpicFightCapabilities;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//
//@EventBusSubscriber
//public class WeaponThownProcedure {
//
//    @SubscribeEvent
//    public static void onEntityJoin(EntityJoinWorldEvent entityjoinworldevent) {
//        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity().getX(), entityjoinworldevent.getEntity().getY(), entityjoinworldevent.getEntity().getZ(), entityjoinworldevent.getEntity());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        execute((Event) null, levelaccessor, d0, d1, d2, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
//        if (entity != null) {
//            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("weaponthrow:weaponthrow")) {
//                Vec3 vec3 = new Vec3(d0, d1, d2);
//                List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(1.5D), (entity1) -> {
//                    return true;
//                }).stream().sorted(Comparator.comparingDouble((entity1) -> {
//                    return entity1.distanceToSqr(vec3);
//                })).collect(Collectors.toList());
//                Iterator iterator = list.iterator();
//
//                while(iterator.hasNext()) {
//                    final Entity entity1 = (Entity)iterator.next();
//
//                    if (entity != entity1) {
//                        LivingEntityPatch livingentitypatch;
//                        DynamicAnimation dynamicanimation;
//
//                        if (entity1 instanceof Player) {
//                            livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
//                            if (livingentitypatch != null) {
//                                dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                                if (!(dynamicanimation instanceof LongHitAnimation) && (!(dynamicanimation instanceof ActionAnimation) || dynamicanimation instanceof AttackAnimation)) {
//                                    ((<undefinedtype>)(new Object() {
//                                        private int ticks = 0;
//                                        private float waitTicks;
//                                        private LevelAccessor world;
//
//                                        public void start(LevelAccessor levelaccessor1, int i) {
//                                            this.waitTicks = (float)i;
//                                            MinecraftForge.EVENT_BUS.register(this);
//                                            this.world = levelaccessor1;
//                                        }
//
//                                        @SubscribeEvent
//                                        public void tick(ServerTickEvent servertickevent) {
//                                            if (servertickevent.phase == Phase.END) {
//                                                ++this.ticks;
//                                                if ((float)this.ticks >= this.waitTicks) {
//                                                    this.run();
//                                                }
//                                            }
//
//                                        }
//
//                                        private void run() {
//                                            Entity entity2 = entity1;
//
//                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/javelin_throw_mid\" 0 1");
//                                            }
//
//                                            entity2 = entity1;
//                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/javelin_throw_mid\" 0 1");
//                                            }
//
//                                            MinecraftForge.EVENT_BUS.unregister(this);
//                                        }
//                                    })).start(levelaccessor, 1);
//                                } else if (entity1 instanceof Player) {
//                                    Player player = (Player)entity1;
//
//                                    player.closeContainer();
//                                }
//                            }
//                        } else if (entity1 instanceof ServerPlayer) {
//                            livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
//                            if (livingentitypatch != null) {
//                                dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                                if (!(dynamicanimation instanceof LongHitAnimation) && (!(dynamicanimation instanceof ActionAnimation) || dynamicanimation instanceof AttackAnimation)) {
//                                    ((<undefinedtype>)(new Object() {
//                                        private int ticks = 0;
//                                        private float waitTicks;
//                                        private LevelAccessor world;
//
//                                        public void start(LevelAccessor levelaccessor1, int i) {
//                                            this.waitTicks = (float)i;
//                                            MinecraftForge.EVENT_BUS.register(this);
//                                            this.world = levelaccessor1;
//                                        }
//
//                                        @SubscribeEvent
//                                        public void tick(ServerTickEvent servertickevent) {
//                                            if (servertickevent.phase == Phase.END) {
//                                                ++this.ticks;
//                                                if ((float)this.ticks >= this.waitTicks) {
//                                                    this.run();
//                                                }
//                                            }
//
//                                        }
//
//                                        private void run() {
//                                            Entity entity2 = entity1;
//
//                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/javelin_throw_mid\" 0 1");
//                                            }
//
//                                            entity2 = entity1;
//                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
//                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"epicfight:biped/combat/javelin_throw_mid\" 0 1");
//                                            }
//
//                                            MinecraftForge.EVENT_BUS.unregister(this);
//                                        }
//                                    })).start(levelaccessor, 1);
//                                } else {
//                                    entity1.clearFire();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//}
