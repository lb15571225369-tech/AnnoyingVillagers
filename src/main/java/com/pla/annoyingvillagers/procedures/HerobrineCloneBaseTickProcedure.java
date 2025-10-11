package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import yesman.epicfight.api.animation.types.*;
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
                final DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();

                if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                    if (dynamicanimation instanceof KnockdownAnimation) {
                        new DelayedTask(10) {
                            @Override
                            public void run() {
                                if (dynamicanimation instanceof KnockdownAnimation) {
                                    Entity entity2 = entity;

                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "indestructible @s play \"epicfight:biped/skill/knockdown_wakeup_left\" 0 1",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                            
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
