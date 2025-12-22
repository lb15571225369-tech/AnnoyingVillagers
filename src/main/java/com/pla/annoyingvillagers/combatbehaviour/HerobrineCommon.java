package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.PathfinderMobInventory;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Random;

public class HerobrineCommon {
    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canPerformHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            if (herobrineMob.getState() < 2) {
                return false;
            }
            return !herobrineMob.isHealing();
        }
        return false;
    }

    public static boolean canChangeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            return herobrineMob.getState() == 0;
        }
        return false;
    }

    public static boolean canPlaySecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            return herobrineMob.getState() != 0;
        }
        return false;
    }

    public static void performHealingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
    }

    public static void changeToSecondForm(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            herobrineMob.setState(1);
            herobrineMob.setSecondFormHitLeft(new Random().nextInt(2, 5));
            if (herobrineMob instanceof AegisHerobrineEntity || herobrineMob instanceof SwordsmanHerobrineEntity
                    || herobrineMob instanceof SledgehammerHerobrineEntity || herobrineMob instanceof ReaperHerobrineEntity
                    || herobrineMob instanceof GlaiveHerobrineEntity) {
                herobrineMob.playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 1.0F, 1.0F);
            }
        }
    }

    public static void playSecondFormAnimation(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            herobrineMob.setSecondFormHitLeft(herobrineMob.getSecondFormHitLeft() - 1);
            if (herobrineMob instanceof AegisHerobrineEntity && herobrineMob.level() instanceof ServerLevel serverLevel) {
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        EnderAegisItem.shieldShoot(serverLevel, herobrineMob);
                    }
                };
            }
        }
    }

    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.jump();
        }
    }

    public static void giveSlowFalling(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 1));
        }
    }
}
