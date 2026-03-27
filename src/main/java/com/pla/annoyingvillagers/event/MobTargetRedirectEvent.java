package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTargetRedirectEvent {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Mob mob) {
            if (mob instanceof BlueDemonEntity || mob instanceof BbqEntity) return;
            if (mob.getTarget() instanceof HerobrineMob herobrineMob
                    && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
                if (herobrineMob.getFirstPossessedHerobrine() != null
                        && herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getSecondPossessedHerobrine() != null
                        && herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getThirdPossessedHerobrine() != null
                        && herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getFourthPossessedHerobrine() != null
                        && herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                }
            }

            if (mob.getTarget() instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                    && lowHerobrineCloneEntity.isHealing()
                    && lowHerobrineCloneEntity.getPossessedByEntity() != null) {
                if (!lowHerobrineCloneEntity.isAlive()) {
                    mob.setTarget(lowHerobrineCloneEntity.getPossessedByEntity());
                }
            }

            if (mob.getTarget() instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                    && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                    && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null) {
                if (!lowShadowHerobrineCloneEntity.isAlive()) {
                    mob.setTarget(lowShadowHerobrineCloneEntity.getPossessedByEntity());
                }
            }

            if (mob.getTarget() instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && reaperHerobrineEntity.isPassenger() && reaperHerobrineEntity.getVehicle() instanceof HerobrineDragonEntity herobrineDragonEntity) {
                mob.setTarget(herobrineDragonEntity);
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
                if (livingEntityPatch != null && (mob instanceof AVNpc || mob instanceof PlayerNpcEntity)) {
                    CombatCommon.swapToBow((MobPatch<?>) livingEntityPatch);
                }
            }

            if (mob.getTarget() instanceof HerobrineDragonEntity herobrineDragonEntity
                    && herobrineDragonEntity.getSummoner() instanceof ReaperHerobrineEntity reaperHerobrineEntity && !reaperHerobrineEntity.isPassenger()) {
                mob.setTarget(reaperHerobrineEntity);
            }
        }
    }
}
