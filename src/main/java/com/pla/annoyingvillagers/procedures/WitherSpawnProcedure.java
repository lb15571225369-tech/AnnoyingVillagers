//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.Util;
//import net.minecraft.network.chat.ChatType;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.registries.ForgeRegistries;
//
//@EventBusSubscriber
//public class WitherSpawnProcedure {
//
//    @SubscribeEvent
//    public static void onEntityJoin(EntityJoinWorldEvent entityjoinworldevent) {
//        execute(entityjoinworldevent, entityjoinworldevent.getWorld(), entityjoinworldevent.getEntity());
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity) {
//        execute((Event) null, levelaccessor, entity);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
//        if (entity != null) {
//            if (ForgeRegistries.ENTITIES.getKey(entity.getType()).toString().equals("minecraft:wither")) {
//                LivingEntity livingentity;
//
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    if (!livingentity.level.isClientSide()) {
//                        livingentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 999999, 2, false, false));
//                    }
//                }
//
//                if (entity instanceof LivingEntity) {
//                    livingentity = (LivingEntity)entity;
//                    livingentity.setHealth(800.0F);
//                }
//
//                if (Math.random() <= 0.4D) {
//                    ((<undefinedtype>)(new Object() {
//                        private int ticks = 0;
//                        private float waitTicks;
//                        private LevelAccessor world;
//
//                        public void start(LevelAccessor levelaccessor1, int i) {
//                            this.waitTicks = (float)i;
//                            MinecraftForge.EVENT_BUS.register(this);
//                            this.world = levelaccessor1;
//                        }
//
//                        @SubscribeEvent
//                        public void tick(ServerTickEvent servertickevent) {
//                            if (servertickevent.phase == Phase.END) {
//                                ++this.ticks;
//                                if ((float)this.ticks >= this.waitTicks) {
//                                    this.run();
//                                }
//                            }
//
//                        }
//
//                        private void run() {
//                            Entity entity1 = entity;
//
//                            if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                                entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon annoying_villagersbychentu:ling_zhi ~ ~3 ~");
//                            }
//
//                            if (!this.world.isClientSide() && this.world.getServer() != null) {
//                                this.world.getServer().getPlayerList().broadcastMessage(new TextComponent("\u9ed1\u6697\u9886\u4e3b\u5df2\u964d\u4e34"), ChatType.SYSTEM, Util.NIL_UUID);
//                            }
//
//                            MinecraftForge.EVENT_BUS.unregister(this);
//                        }
//                    })).start(levelaccessor, 40);
//                }
//            }
//
//        }
//    }
//}
