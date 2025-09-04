package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.GuardAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class NpcKickProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, Entity entity, final Entity entity1) {
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

                    if (!flag && entity1.isAlive()) {
                        HumanoidMobPatch<?> humanoidmobpatch = (HumanoidMobPatch)EpicFightCapabilities.getEntityPatch(entity1, HumanoidMobPatch.class);
                        LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                        if (humanoidmobpatch != null && livingentitypatch != null) {
                            DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                            DynamicAnimation dynamicanimation1 = humanoidmobpatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                            if (dynamicanimation1 instanceof AttackAnimation && (dynamicanimation instanceof HitAnimation || dynamicanimation instanceof DodgeAnimation || dynamicanimation instanceof GuardAnimation)) {
                                if (entity1.getPersistentData().getDouble("kick") < 1.0D) {
                                    entity1.getPersistentData().putDouble("kick", 2.0D);
                                    new DelayedTask(10) {
                                        @Override
                                        public void run() {
                                            if (entity1.isAlive()) {
                                                Entity entity2 = entity1;

                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                    try {
                                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                                "indestructible @s play \"annoyingvillagers:biped/combat/kick_1\" 0 1",
                                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    };
                                } else if (entity1.getPersistentData().getDouble("kick") == 2.0D) {
                                    entity1.getPersistentData().putDouble("kick", 3.0D);
                                    new DelayedTask(10) {
                                        @Override
                                        public void run() {
                                            if (entity1.isAlive()) {
                                                Entity entity2 = entity1;

                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                    try {
                                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                                "indestructible @s play \"annoyingvillagers:biped/combat/kick_2\" 0 1",
                                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    };
                                } else if (entity1.getPersistentData().getDouble("kick") == 3.0D) {
                                    entity1.getPersistentData().putDouble("kick", 0.0D);
                                    new DelayedTask(10) {
                                        @Override
                                        public void run() {
                                            if (entity1.isAlive()) {
                                                Entity entity2 = entity1;

                                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                                    try {
                                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                                "indestructible @s play \"annoyingvillagers:biped/combat/kick_3\" 0 1",
                                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    };
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
