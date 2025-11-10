package com.pla.annoyingvillagers.procedures;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import com.pla.annoyingvillagers.animations.types.KickAttackAnimation;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class NpcRollUpProcedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livinghurtevent) {
        if (livinghurtevent != null && livinghurtevent.getEntity() != null) {
            execute(livinghurtevent, livinghurtevent.getEntity(), livinghurtevent.getSource().getEntity());
        }

    }

    public static void execute(Entity entity, Entity entity1) {
        execute((Event) null, entity, entity1);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity entity1) {
        if (entity != null && entity1 != null) {
            if (!(entity instanceof Player)) {
                LivingEntity livingentity;
                boolean flag;
                LivingEntity livingentity1;

                if (entity instanceof PlayerNpcEntity) {
                    if (entity.getPersistentData().getDouble("npc_level") != 0.0D) {
                        if (entity instanceof LivingEntity) {
                            livingentity = (LivingEntity) entity;
                            flag = livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                        } else {
                            flag = false;
                        }

                        if (!flag) {
                            if (entity1 instanceof LivingEntity) {
                                livingentity1 = (LivingEntity) entity1;
                                flag = livingentity1.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                            } else {
                                flag = false;
                            }

                            if (!flag && entity.isAlive()) {
                                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
                                LivingEntityPatch<?> livingentitypatch1 = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                                if (livingentitypatch != null && livingentitypatch1 != null) {
                                    AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                                    if (dynamicanimation instanceof KickAttackAnimation && entity instanceof LivingEntity) {
                                        LivingEntity livingentity2 = (LivingEntity) entity;

                                        if (!livingentity2.level().isClientSide()) {
                                            livingentity2.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 12, 0, false, false));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (entity.getPersistentData().getBoolean("a_player")) {
                    if (entity instanceof LivingEntity) {
                        livingentity = (LivingEntity) entity;
                        flag = livingentity.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                    } else {
                        flag = false;
                    }

                    if (!flag) {
                        if (entity1 instanceof LivingEntity) {
                            livingentity1 = (LivingEntity) entity1;
                            flag = livingentity1.hasEffect((MobEffect) AnnoyingVillagersModMobEffects.EC.get());
                        } else {
                            flag = false;
                        }

                        if (!flag && entity.isAlive() && entity instanceof LivingEntity) {
                            LivingEntity livingentity3 = (LivingEntity) entity;

                            if (!livingentity3.level().isClientSide()) {
                                livingentity3.addEffect(new MobEffectInstance((MobEffect) AnnoyingVillagersModMobEffects.NPC_KICK_EFFECT.get(), 10, 0, false, false));
                            }
                        }
                    }
                }
            }

        }
    }
}
