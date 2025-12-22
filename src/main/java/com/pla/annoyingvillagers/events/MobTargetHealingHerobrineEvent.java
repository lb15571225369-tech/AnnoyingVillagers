package com.pla.annoyingvillagers.events;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTargetHealingHerobrineEvent {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Mob mob) {
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
        }
    }
}
