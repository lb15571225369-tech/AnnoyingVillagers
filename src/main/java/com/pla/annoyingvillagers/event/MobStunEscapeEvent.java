package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.combat_evolution.ai.iml.CustomExecuteEntity;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber
public class MobStunEscapeEvent {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        if (!((victim instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.getStunEscapeCooldown() != 0)
                || (victim instanceof HerobrineMob herobrineMob && herobrineMob.getStunEscapeCooldown() != 0)
                || (victim instanceof AVNpc avNpc && avNpc.getStunEscapeCooldown() != 0))) return;

        if (victim.level().isClientSide) return;

        LivingEntityPatch<?> victimLivingEntityPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
        if (victimLivingEntityPatch instanceof CustomExecuteEntity customExecuteEntity
                && customExecuteEntity.canBeExecuted(victimLivingEntityPatch)) {
            return;
        }

        if (victimLivingEntityPatch != null) {
            AssetAccessor<? extends DynamicAnimation> victimDynamicAnimation = Objects.requireNonNull(victimLivingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
            if (victimDynamicAnimation != null
                    && (EpicfightUtil.isLongHitAnimation(victimDynamicAnimation) && victim.isAlive())) {
                float hpPct = victim.getHealth() / victim.getMaxHealth();

                double min = AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE.get();
                double max = AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE.get();

                if (max < min) {
                    double tmp = max;
                    max = min;
                    min = tmp;
                }

                double chance;
                if (max == min) {
                    chance = max;
                } else {
                    double t = (1.0D - hpPct) / 0.5D;
                    t = Mth.clamp(t, 0.0D, 1.0D);
                    chance = max - t * (max - min);
                }

                if (victim.getRandom().nextFloat() < chance) {
                    new DelayedTask(5) {
                        @Override
                        public void run() {
                            double chooseAnimation = new Random().nextDouble(0.0D, 1.0D);
                            if (chooseAnimation <= 0.4D) {
                                victimLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                            } else if (chooseAnimation <= 0.8D) {
                                victimLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                            } else {
                                victimLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                            }
                            if (victim instanceof PlayerNpcEntity playerNpcEntity) {
                                playerNpcEntity.setStunEscapeCooldown(100);
                            }
                            if (victim instanceof HerobrineMob herobrineMob) {
                                herobrineMob.setStunEscapeCooldown(100);
                            }
                            if (victim instanceof AVNpc avNpc) {
                                avNpc.setStunEscapeCooldown(100);
                            }
                            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, false));
                            victim.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
                            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false));
                        }
                    };
                }
            }
        }
    }
}