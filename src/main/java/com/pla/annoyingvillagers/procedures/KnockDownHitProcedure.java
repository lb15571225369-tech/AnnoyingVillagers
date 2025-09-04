package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class KnockDownHitProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity(), livingattackevent.getSource().getDirectEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (entity.isAlive()) {
                float f;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;

                    f = livingentity.getMaxHealth();
                } else {
                    f = -1.0F;
                }

                LivingEntityPatch livingentitypatch;
                DynamicAnimation dynamicanimation;

                if (f >= 30.0F) {
                    if (!ForgeRegistries.ENTITY_TYPES.getKey(entity1.getType()).toString().equals("cgm:projectile")) {
                        livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingentitypatch != null) {
                            dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                            if (dynamicanimation instanceof KnockdownAnimation || dynamicanimation == AVAnimations.KNOCKDOWN_FORWRAD || dynamicanimation == AVAnimations.KNOCKDOWN_RIGHT || dynamicanimation == AVAnimations.KNOCKDOWN_RIGHT || dynamicanimation == AVAnimations.KNOCKDOWN_LEFT) {
                                if (Math.random() <= 0.4D) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_right\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {

                                        }
                                    }
                                } else if (Math.random() <= 0.4D) {
                                    if (!entity.level().isClientSide() && entity.getServer() != null) {
                                        try {
                                            entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_left\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {

                                        }
                                    }
                                } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    try {
                                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_forward\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {

                                    }
                                }
                            }
                        }
                    }
                } else {
                    livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (livingentitypatch != null) {
                        dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                        if (dynamicanimation instanceof KnockdownAnimation || dynamicanimation == AVAnimations.KNOCKDOWN_FORWRAD || dynamicanimation == AVAnimations.KNOCKDOWN_RIGHT || dynamicanimation == AVAnimations.KNOCKDOWN_RIGHT || dynamicanimation == AVAnimations.KNOCKDOWN_LEFT) {
                            if (Math.random() <= 0.4D) {
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    try {
                                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_right\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {

                                    }
                                }
                            } else if (Math.random() <= 0.4D) {
                                if (!entity.level().isClientSide() && entity.getServer() != null) {
                                    try {
                                        entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_left\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {

                                    }
                                }
                            } else if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute("indestructible @s play \"annoyingvillagers:biped/combat/knockdown_forward\" 0 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {

                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
