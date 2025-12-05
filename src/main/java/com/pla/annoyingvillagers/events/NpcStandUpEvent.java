package com.pla.annoyingvillagers.events;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@EventBusSubscriber
public class NpcStandUpEvent {

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent livingattackevent) {
        if (livingattackevent != null && livingattackevent.getEntity() != null) {
            execute(livingattackevent, livingattackevent.getEntity().level(), livingattackevent.getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (entity instanceof PlayerNpcEntity) {
                LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

                if (livingentitypatch != null) {
                    final AssetAccessor<? extends DynamicAnimation> dynamicanimation = livingentitypatch.getAnimator().getPlayerFor(null).getAnimation();
                    new DelayedTask(10) {
                        @Override
                        public void run() {
                            if (dynamicanimation instanceof KnockdownAnimation) {
                                Entity entity1;

                                if (Math.random() <= 0.4D) {
                                    entity1 = entity;
                                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                                        }
                                    }
                                } else {
                                    entity1 = entity;
                                    if (!entity1.level().isClientSide() && entity1.getServer() != null) {
                                        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity1, LivingEntityPatch.class);
                                        if (livingEntityPatch != null) {
                                            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                                        }
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
