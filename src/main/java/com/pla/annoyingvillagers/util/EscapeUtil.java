package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.compat.EpicFightResurrection;
import com.pla.annoyingvillagers.compat.EpicFightSwordSoaring;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EscapeUtil {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                AVAnimations.ENDER_AEGIS_BULL_CHARGE.get().getRegistryName().toString(),
                AVAnimations.YELLOW_TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AVAnimations.ENDER_GLAIVE_NAPOLEON_SHOOT_3.get().getRegistryName().toString(),
                AVAnimations.ENDER_GLAIVE_AGONY_AUTO_1.get().getRegistryName().toString(),
                AVAnimations.AEGIS_SHIELD_SHOOT.get().getRegistryName().toString(),
                AVAnimations.CLONE_NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AVAnimations.TRIDENT_FESTIVAL.get().getRegistryName().toString(),
                AVAnimations.TRIDENT_ATTACK.get().getRegistryName().toString(),
                AVAnimations.BLUE_DEMON_STATE_TRANSFORM.get().getRegistryName().toString(),
                AVAnimations.ELECTRIC_FIELD.get().getRegistryName().toString(),
                AVAnimations.SNAKE_BLADE_GUARD.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE_X.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_2.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AnimsRuine.RUINE_PLUNDER.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_LAPSE.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENSION.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENDED_BLACKHOLE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_BERSERK_CONVERT.get().getRegistryName().toString(),
                AnimsSatsujin.SATSUJIN_GESSHOKU.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_AUTO_3.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_SPRENGKOPF.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_WIDERSTAND.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECHO.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECLIPSE.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_FULLMOON.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_OBSCURIDAD.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_CREMATORIO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_INFIERNO.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_AUSTERLITZ_SHOOT.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AnimsOrbit.ORBIT_LIGHT_BEAM.get().getRegistryName().toString()
        ));

        if (ModList.get().isLoaded("efn")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightNightFall.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (ModList.get().isLoaded("sword_soaring")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightSwordSoaring.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (ModList.get().isLoaded("cdmoveset")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightResurrection.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    public static boolean isAnimationDangerous(AssetAccessor<? extends StaticAnimation> targetDynamicAnimation) {
        if (targetDynamicAnimation != null && targetDynamicAnimation.get().getRegistryName() != null) {
            String animation = targetDynamicAnimation.get().getRegistryName().toString();
            return DANGEROUS_ANIMATIONS.contains(animation);
        }
        return false;
    }

    public static boolean checkEscape(Mob mob) {
        LivingEntity target = mob.getTarget();
        LivingEntityPatch<?> targetLivingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (target == null || targetLivingEntityPatch == null) return false;
        AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        return isAnimationDangerous(targetDynamicAnimation) || targetDynamicAnimation.get() instanceof ExecutionAttackAnimation;
    }
}
