//package com.pla.annoyingvillagers.procedures;
//
//import javax.annotation.Nullable;
//import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.TickEvent.Phase;
//import net.minecraftforge.event.TickEvent.ServerTickEvent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import com.pla.annoyingvillagers.gameasset.AVAnimations;
//import yesman.epicfight.api.animation.types.DynamicAnimation;
//import yesman.epicfight.world.capabilities.EpicFightCapabilities;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//
//@EventBusSubscriber
//public class ShakeHandProcedure {
//
//    @SubscribeEvent
//    public static void onRightClickEntity(EntityInteract entityinteract) {
//        if (entityinteract.getHand() == entityinteract.getPlayer().getUsedItemHand()) {
//            execute(entityinteract, entityinteract.getWorld(), entityinteract.getTarget(), entityinteract.getPlayer());
//        }
//    }
//
//    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
//        execute((Event) null, levelaccessor, entity, entity1);
//    }
//
//    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
//        if (entity != null && entity1 != null) {
//            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
//
//            if (livingentitypatch != null) {
//                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
//                Vec3 vec3;
//
//                if (dynamicanimation == AVAnimations.SHAKE_HAND_TRY) {
//                    vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);
//                    entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.4D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.4D);
//                    entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
//                    entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
//                    if (!entity.level.isClientSide() && entity.getServer() != null) {
//                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/shake_hand\" 0 1");
//                    }
//
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/shake_hand\" 0 1");
//                    }
//
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
//                            Player player;
//
//                            if (entity instanceof Player) {
//                                player = (Player)entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent(entity1.getDisplayName().getString() + ": \u5408\u4f5c\u6109\u5feb"), true);
//                                }
//                            }
//
//                            if (entity1 instanceof Player) {
//                                player = (Player)entity1;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent(entity.getDisplayName().getString() + ": \u5408\u4f5c\u6109\u5feb"), true);
//                                }
//                            }
//
//                            MinecraftForge.EVENT_BUS.unregister(this);
//                        }
//                    })).start(levelaccessor, 12);
//                } else if (dynamicanimation == AVAnimations.FIST_TRY) {
//                    vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);
//                    entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.5D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.5D);
//                    entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
//                    entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
//                    if (!entity.level.isClientSide() && entity.getServer() != null) {
//                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/fisting\" 0 1");
//                    }
//
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/other/fisting\" 0 1");
//                    }
//
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
//                            Player player;
//
//                            if (entity instanceof Player) {
//                                player = (Player)entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent(entity1.getDisplayName().getString() + ": \u52a0\u6cb9"), true);
//                                }
//                            }
//
//                            if (entity1 instanceof Player) {
//                                player = (Player)entity1;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent(entity.getDisplayName().getString() + ": \u52a0\u6cb9"), true);
//                                }
//                            }
//
//                            MinecraftForge.EVENT_BUS.unregister(this);
//                        }
//                    })).start(levelaccessor, 12);
//                } else if (dynamicanimation == AVAnimations.EMOTE_22_TRY) {
//                    vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);
//                    entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 0.1D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 0.1D);
//                    entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
//                    entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY(), entity.getZ()));
//                    if (!entity.level.isClientSide() && entity.getServer() != null) {
//                        entity.getServer().getCommands().performCommand(entity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/emoji/emote_22_b\" 0 1");
//                    }
//
//                    if (!entity1.level.isClientSide() && entity1.getServer() != null) {
//                        entity1.getServer().getCommands().performCommand(entity1.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoyingvillagers:biped/emoji/emote_22\" 0 1");
//                    }
//
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
//                            Player player;
//
//                            if (entity instanceof Player) {
//                                player = (Player)entity;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u4f60\u88ab" + entity1.getDisplayName().getString() + "\u8e29\u4e86\u80cc"), true);
//                                }
//                            }
//
//                            if (entity1 instanceof Player) {
//                                player = (Player)entity1;
//                                if (!player.level.isClientSide()) {
//                                    player.displayClientMessage(new TextComponent("\u4f60\u7ed9" + entity.getDisplayName().getString() + "\u8e29\u4e86\u80cc"), true);
//                                }
//                            }
//
//                            MinecraftForge.EVENT_BUS.unregister(this);
//                        }
//                    })).start(levelaccessor, 10);
//                }
//            }
//
//        }
//    }
//}
