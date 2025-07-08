package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@EventBusSubscriber
public class ExecuteNpcProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level, livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!(entity1 instanceof Player)) {
                boolean flag;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    flag = livingentity.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                } else {
                    flag = false;
                }

                if (!flag) {
                    if (entity1 instanceof LivingEntity) {
                        LivingEntity livingentity1 = (LivingEntity)entity1;

                        flag = livingentity1.hasEffect((MobEffect)AnnoyingVillagersModMobEffects.EC.get());
                    } else {
                        flag = false;
                    }

                    if (!flag && entity.isAlive()) {
                        final HumanoidMobPatch<?> humanoidmobpatch = (HumanoidMobPatch)EpicFightCapabilities.getEntityPatch(entity1, HumanoidMobPatch.class);
                        final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                        if (humanoidmobpatch != null && livingentitypatch != null) {
                            DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                            if (dynamicanimation instanceof LongHitAnimation || dynamicanimation == Animations.BIPED_COMMON_NEUTRALIZED || dynamicanimation == Animations.BIPED_KNOCKDOWN) {
                                if ((humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI) && (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER || humanoidmobpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.DAGGER)) {
                                    if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.SWORD && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.AXE && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.DAGGER && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TRIDENT) {
                                        if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.SPEAR) {
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
                                                    if (entity.isAlive() && entity1.isAlive()) {
                                                        Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);

                                                        humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                        entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.5D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.5D);
                                                        entity.getPersistentData().putBoolean("kick_x", true);
                                                        LivingEntity livingentity2;

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                            }
                                                        }

                                                        if (entity1 instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity1;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                            }
                                                        }

                                                        if (entity1 instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity1;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false));
                                                            }
                                                        }

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false));
                                                            }
                                                        }

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                            }
                                                        }

                                                        entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                        Entity entity2 = entity;

                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                        }

                                                        entity2 = entity1;
                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                        }

                                                        humanoidmobpatch.playAnimationSynchronized(WOMAnimations.TORMENT_CHARGED_ATTACK_1, 0.0F);
                                                        livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTED_SKILL, 0.0F);
                                                    }

                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 4);
                                        } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.LONGSWORD && humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() != WeaponCategories.TACHI) {
                                            if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD) {
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
                                                        if (entity.isAlive() && entity1.isAlive()) {
                                                            Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);

                                                            humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                            entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.5D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.5D);
                                                            entity.getPersistentData().putBoolean("kick_x", true);
                                                            LivingEntity livingentity2;

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity)entity;
                                                                if (!livingentity2.level.isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 140, 0, false, false));
                                                                }
                                                            }

                                                            if (entity1 instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity)entity1;
                                                                if (!livingentity2.level.isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                                }
                                                            }

                                                            if (entity1 instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity)entity1;
                                                                if (!livingentity2.level.isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                }
                                                            }

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity)entity;
                                                                if (!livingentity2.level.isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                }
                                                            }

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity2 = (LivingEntity)entity;
                                                                if (!livingentity2.level.isClientSide()) {
                                                                    livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                                }
                                                            }

                                                            entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                            entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                            Entity entity2 = entity1;

                                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_greatsword\" 0 1");
                                                            }

                                                            livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_GREATSWORD_HIT, 0.0F);
                                                            ((LivingEntity)livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, false, false));
                                                            entity2 = entity1;
                                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 6 5 true");
                                                            }

                                                            entity2 = entity;
                                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 6 5 true");
                                                            }
                                                        }

                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 4);
                                            } else if (humanoidmobpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.FIST) {
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
                                                        if (entity.isAlive()) {
                                                            float f;

                                                            if (entity1 instanceof LivingEntity) {
                                                                LivingEntity livingentity2 = (LivingEntity)entity1;

                                                                f = livingentity2.getMaxHealth();
                                                            } else {
                                                                f = -1.0F;
                                                            }

                                                            Vec3 vec3;
                                                            LivingEntity livingentity3;
                                                            Entity entity2;

                                                            if (f >= 37.0F) {
                                                                if (entity1.isAlive()) {
                                                                    vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);
                                                                    humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                                    entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 0.5D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 0.5D);
                                                                    entity.getPersistentData().putBoolean("kick_x", true);
                                                                    if (entity instanceof LivingEntity) {
                                                                        livingentity3 = (LivingEntity)entity;
                                                                        if (!livingentity3.level.isClientSide()) {
                                                                            livingentity3.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                                        }
                                                                    }

                                                                    if (entity1 instanceof LivingEntity) {
                                                                        livingentity3 = (LivingEntity)entity1;
                                                                        if (!livingentity3.level.isClientSide()) {
                                                                            livingentity3.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                                        }
                                                                    }

                                                                    if (entity1 instanceof LivingEntity) {
                                                                        livingentity3 = (LivingEntity)entity1;
                                                                        if (!livingentity3.level.isClientSide()) {
                                                                            livingentity3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                        }
                                                                    }

                                                                    if (entity instanceof LivingEntity) {
                                                                        livingentity3 = (LivingEntity)entity;
                                                                        if (!livingentity3.level.isClientSide()) {
                                                                            livingentity3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                        }
                                                                    }

                                                                    if (entity instanceof LivingEntity) {
                                                                        livingentity3 = (LivingEntity)entity;
                                                                        if (!livingentity3.level.isClientSide()) {
                                                                            livingentity3.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                                        }
                                                                    }

                                                                    entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                                    entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                                    entity2 = entity1;
                                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_boss\" 0 1");
                                                                    }

                                                                    livingentitypatch.playAnimationSynchronized(AVAnimations.BOSS_EXECUTE_HIT, 0.0F);
                                                                    entity2 = entity1;
                                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 3 5 true");
                                                                    }
                                                                }
                                                            } else if (entity1.isAlive()) {
                                                                vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);
                                                                humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                                entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.4D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.4D);
                                                                entity.getPersistentData().putBoolean("kick_x", true);
                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity3 = (LivingEntity)entity;
                                                                    if (!livingentity3.level.isClientSide()) {
                                                                        livingentity3.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                                    }
                                                                }

                                                                if (entity1 instanceof LivingEntity) {
                                                                    livingentity3 = (LivingEntity)entity1;
                                                                    if (!livingentity3.level.isClientSide()) {
                                                                        livingentity3.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                                    }
                                                                }

                                                                if (entity1 instanceof LivingEntity) {
                                                                    livingentity3 = (LivingEntity)entity1;
                                                                    if (!livingentity3.level.isClientSide()) {
                                                                        livingentity3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                    }
                                                                }

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity3 = (LivingEntity)entity;
                                                                    if (!livingentity3.level.isClientSide()) {
                                                                        livingentity3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                                    }
                                                                }

                                                                if (entity instanceof LivingEntity) {
                                                                    livingentity3 = (LivingEntity)entity;
                                                                    if (!livingentity3.level.isClientSide()) {
                                                                        livingentity3.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                                    }
                                                                }

                                                                entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                                entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                                entity2 = entity1;
                                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/wrestling\" 0 1");
                                                                }

                                                                livingentitypatch.playAnimationSynchronized(AVAnimations.WRESTLING_HIT, 0.0F);
                                                                entity2 = entity1;
                                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 3 5 true");
                                                                }
                                                            }
                                                        }

                                                        MinecraftForge.EVENT_BUS.unregister(this);
                                                    }
                                                })).start(levelaccessor, 4);
                                            }
                                        } else {
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
                                                    if (entity.isAlive() && entity1.isAlive()) {
                                                        Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);

                                                        humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                        entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.35D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.35D);
                                                        entity.getPersistentData().putBoolean("kick_x", true);
                                                        LivingEntity livingentity2;

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                            }
                                                        }

                                                        if (entity1 instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity1;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 80, 0, false, false));
                                                            }
                                                        }

                                                        if (entity1 instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity1;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                            }
                                                        }

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false));
                                                            }
                                                        }

                                                        if (entity instanceof LivingEntity) {
                                                            livingentity2 = (LivingEntity)entity;
                                                            if (!livingentity2.level.isClientSide()) {
                                                                livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                            }
                                                        }

                                                        entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                        entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                        Entity entity2 = entity1;

                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_longsword\" 0 1");
                                                        }

                                                        livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_LONGSWORD_HIT, 0.0F);
                                                        ((LivingEntity)livingentitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, false, false));
                                                        entity2 = entity1;
                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                        }

                                                        entity2 = entity;
                                                        if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                            entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 3 5 true");
                                                        }
                                                    }

                                                    MinecraftForge.EVENT_BUS.unregister(this);
                                                }
                                            })).start(levelaccessor, 4);
                                        }
                                    } else {
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
                                                if (entity.isAlive() && entity1.isAlive()) {
                                                    Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);

                                                    humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                    entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.45D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.45D);
                                                    entity.getPersistentData().putBoolean("kick_x", true);
                                                    LivingEntity livingentity2;

                                                    if (entity instanceof LivingEntity) {
                                                        livingentity2 = (LivingEntity)entity;
                                                        if (!livingentity2.level.isClientSide()) {
                                                            livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
                                                        }
                                                    }

                                                    if (entity1 instanceof LivingEntity) {
                                                        livingentity2 = (LivingEntity)entity1;
                                                        if (!livingentity2.level.isClientSide()) {
                                                            livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 40, 0, false, false));
                                                        }
                                                    }

                                                    if (entity1 instanceof LivingEntity) {
                                                        livingentity2 = (LivingEntity)entity1;
                                                        if (!livingentity2.level.isClientSide()) {
                                                            livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 0, false, false));
                                                        }
                                                    }

                                                    if (entity instanceof LivingEntity) {
                                                        livingentity2 = (LivingEntity)entity;
                                                        if (!livingentity2.level.isClientSide()) {
                                                            livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 0, false, false));
                                                        }
                                                    }

                                                    if (entity instanceof LivingEntity) {
                                                        livingentity2 = (LivingEntity)entity;
                                                        if (!livingentity2.level.isClientSide()) {
                                                            livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                        }
                                                    }

                                                    entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                    entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                    Entity entity2 = entity1;

                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 3 5 true");
                                                    }

                                                    entity2 = entity;
                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                    }

                                                    entity2 = entity1;
                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_one_hand\" 0 1");
                                                    }

                                                    entity2 = entity;
                                                    if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                        entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_dual_hit\" 0 1");
                                                    }
                                                }

                                                MinecraftForge.EVENT_BUS.unregister(this);
                                            }
                                        })).start(levelaccessor, 4);
                                    }
                                } else {
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
                                            if (entity.isAlive() && entity1.isAlive()) {
                                                Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).getViewVector(1.0F);

                                                humanoidmobpatch.setGrapplingTarget((LivingEntity)livingentitypatch.getOriginal());
                                                entity1.teleportTo(((LivingEntity)livingentitypatch.getOriginal()).getX() + vec3.x * 1.4D, ((LivingEntity)livingentitypatch.getOriginal()).getY(), ((LivingEntity)livingentitypatch.getOriginal()).getZ() + vec3.z * 1.4D);
                                                entity.getPersistentData().putBoolean("kick_x", true);
                                                LivingEntity livingentity2;

                                                if (entity instanceof LivingEntity) {
                                                    livingentity2 = (LivingEntity)entity;
                                                    if (!livingentity2.level.isClientSide()) {
                                                        livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 120, 0, false, false));
                                                    }
                                                }

                                                if (entity1 instanceof LivingEntity) {
                                                    livingentity2 = (LivingEntity)entity1;
                                                    if (!livingentity2.level.isClientSide()) {
                                                        livingentity2.addEffect(new MobEffectInstance((MobEffect)AnnoyingVillagersModMobEffects.EC.get(), 40, 0, false, false));
                                                    }
                                                }

                                                if (entity1 instanceof LivingEntity) {
                                                    livingentity2 = (LivingEntity)entity1;
                                                    if (!livingentity2.level.isClientSide()) {
                                                        livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 0, false, false));
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity2 = (LivingEntity)entity;
                                                    if (!livingentity2.level.isClientSide()) {
                                                        livingentity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 29, 0, false, false));
                                                    }
                                                }

                                                if (entity instanceof LivingEntity) {
                                                    livingentity2 = (LivingEntity)entity;
                                                    if (!livingentity2.level.isClientSide()) {
                                                        livingentity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 5, false, false));
                                                    }
                                                }

                                                entity1.lookAt(Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0D, entity.getZ()));
                                                entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                                                Entity entity2 = entity1;

                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                }

                                                entity2 = entity1;
                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "indestructible @s play \"annoying_villagers:biped/combat/execute_dual\" 0 1");
                                                }

                                                livingentitypatch.playAnimationSynchronized(AVAnimations.EXECUTE_DUAL_HIT, 0.0F);
                                                entity2 = entity;
                                                if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                    entity2.getServer().getCommands().performCommand(entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4), "effect give @s epicfight:stun_immunity 4 5 true");
                                                }
                                            }

                                            MinecraftForge.EVENT_BUS.unregister(this);
                                        }
                                    })).start(levelaccessor, 4);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
