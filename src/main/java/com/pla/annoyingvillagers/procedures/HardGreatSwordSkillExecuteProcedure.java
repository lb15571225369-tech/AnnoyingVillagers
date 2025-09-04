package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class HardGreatSwordSkillExecuteProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity(), livingattackevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            LivingEntityPatch livingentitypatch;
            DynamicAnimation dynamicanimation;

            if (entity1.isAlive()) {
                livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                if (livingentitypatch != null) {
                    dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                    boolean flag = false;

                    if (entity != null) {
                        Vec3 vec3 = entity1.position();
                        Vec3 vec31 = entity.getViewVector(1.0F);
                        Vec3 vec32 = vec3.subtract(entity.getEyePosition()).normalize();

                        if (vec32.dot(vec31) > 0.0D) {
                            flag = true;
                        }
                    }

                    if (flag && dynamicanimation == AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL) {
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }

                        entity.lookAt(Anchor.EYES, new Vec3(entity1.getX(), entity1.getY() + 1.0D, entity1.getZ()));
                        LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);

                        if (livingentitypatch1 != null) {
                            DynamicAnimation dynamicanimation1 = livingentitypatch1.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();

                            if (dynamicanimation1 instanceof AttackAnimation) {
                                entity1.setDeltaMovement(new Vec3(entity1.getLookAngle().x * -0.4D, 0.0D, entity1.getLookAngle().z * -0.4D));
                            }
                        }

                        livingentitypatch.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                    }
                }
            } else {
                livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                if (livingentitypatch != null) {
                    dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                    if (dynamicanimation == AVAnimations.HARD_GREAT_SWORD_GUARD_SKILL) {
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }

                        livingentitypatch.playSound((SoundEvent) EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle annoyingvillagers:spark ^ ^1.5 ^0.8 0 0 0 0.1 100", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));

                                entity.getServer().getCommands().getDispatcher().execute("execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1", entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {

                            }
                        }
                    }
                }
            }

        }
    }
}
