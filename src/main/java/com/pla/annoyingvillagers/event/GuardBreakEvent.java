package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.animations.KickAttackAnimation;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@Mod.EventBusSubscriber
public class GuardBreakEvent {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();

        if (!(victim.level() instanceof ServerLevel serverLevel)) return;
        if (!(attacker instanceof LivingEntity)) return;

        LivingEntityPatch<?> victimLivingEntityPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
        LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
        if (victimLivingEntityPatch != null
                && attackerLivingEntityPatch != null) {
            AssetAccessor<? extends DynamicAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation();
            if (attackerDynamicAnimation != null && attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
                float hpPct = victim.getHealth() / victim.getMaxHealth();

                double min = AnnoyingVillagersConfig.KICK_GUARD_BREAK_MIN_CHANCE.get();
                double max = AnnoyingVillagersConfig.KICK_GUARD_BREAK_MAX_CHANCE.get();
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
                    chance = min + t * (max - min);
                }

                if (victim.getRandom().nextFloat() < (float) chance) {
                    victim.playSound(AnnoyingVillagersModSounds.KICK_GUARD_BREAK.get());
                    victimLivingEntityPatch.playAnimationSynchronized(Animations.BIPED_COMMON_NEUTRALIZED, 0.0F);
                }
            }
        }
    }
}