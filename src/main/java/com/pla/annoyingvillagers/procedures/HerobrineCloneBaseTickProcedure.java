package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HerobrineCloneBaseTickProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (entity.isPassenger()) {
                entity.stopRiding();
            }

            LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingentitypatch != null) {
                final AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                    if (dynamicanimation instanceof KnockdownAnimation) {
                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                if (dynamicanimation instanceof KnockdownAnimation) {
                                    Entity entity2 = entity;

                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity2, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                        }
                                    }
                                }
                            }
                        };
                    }
                } else {
                    entity.clearFire();
                }
            }

        }
    }
}
