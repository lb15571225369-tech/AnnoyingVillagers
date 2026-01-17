package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.SledgehammerHerobrineEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Set;

public class EscapeUtil {
    private static final Set<String> DANGEROUS_ANIMATIONS = Set.of(
            "annoyingvillagers:biped/skill/ender_aegis_bull_charge",
            "annoyingvillagers:biped/combat/yellow_torment_charged_attack_3",
            "annoyingvillagers:biped/skill/ender_glaive_napoleon_shoot_3",
            "annoyingvillagers:biped/combat/ender_glaive_agony_auto_1",
            "annoyingvillagers:biped/skill/aegis_shield_shoot",
            "efn:biped/ng_greatsword/ng_great_charge1ing",
            "efn:biped/ng_greatsword/ng_great_charge1max",
            "efn:biped/ng_greatsword/ng_great_charge1max_2",
            "efn:biped/ng_greatsword/ng_great_charge1ing_mob",
            "efn:biped/nf_meen/nf_meen_dash",
            "efn:biped/nf_meen/nf_meen_charge_all",
            "efn:biped/nf_meen/nf_meen_charge_all_mob",
            "efn:biped/nf_meen/nf_meen_charge1",
            "efn:biped/nf_meen/nf_meen_charge2",
            "efn:biped/nf_meen/nf_meen_charge3",
            "efn:biped/nf_meen/nf_meen_skill_max",
            "efn:biped/yamato/dmcyamato_judgementcut_all",
            "efn:biped/yamato_judgementcut_end",
            "efn:biped/nf_skill/biped_execute",
            "wom:biped/skill/agony_sky_dive_x",
            "wom:biped/skill/agony_sky_dive",
            "wom:biped/combat/torment_charged_attack_2",
            "wom:biped/combat/torment_charged_attack_3",
            "wom:biped/skill/ruine_plunder",
            "wom:biped/skill/antitheus_lapse",
            "wom:biped/skill/antitheus_ascension",
            "wom:biped/skill/antitheus_ascended_blackhole",
            "wom:biped/skill/katana_gesshoku",
            "wom:biped/skill/gezets_auto_3",
            "wom:biped/skill/gezets_sprengkopf",
            "wom:biped/skill/gezets_widerstand",
            "wom:biped/skill/moonless_lunar_echo",
            "wom:biped/skill/moonless_lunar_eclipse",
            "wom:biped/skill/moonless_lunar_fullmoon",
            "wom:biped/skill/solar_brasero",
            "wom:biped/skill/solar_brasero_obscuridad",
            "wom:biped/skill/solar_brasero_crematorio",
            "wom:biped/skill/solar_brasero_infierno",
            "wom:biped/skill/napoleon_austerlitz_shoot",
            "wom:biped/skill/napoleon_waterlow_shoot",
            "wom:biped/skill/orbit_light_beam",
            "sword_soaring:screen_sword/kill_aura_1_summon_player",
            "sword_soaring:screen_sword/kill_aura_2_summon_player",
            "sword_soaring:screen_sword/screen_sword_summon_player",
            "sword_soaring:screen_sword/rain_sword_summon_player",
            "sword_soaring:babylon/babylon_shoot_owner",
            "sword_soaring:wan/wan_owner_1"
    );

    public static boolean checkEscape(Mob mob) {
        LivingEntity target = mob.getTarget();
        LivingEntityPatch<?> targetLivingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (target == null || targetLivingEntityPatch == null) return false;
        AssetAccessor<? extends DynamicAnimation> targetDynamicAnimation = Objects.requireNonNull(targetLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (target instanceof HerobrineMob herobrineMob) {
            if (herobrineMob instanceof SwordsmanHerobrineEntity) {
                return (herobrineMob.getState() > 0 && targetDynamicAnimation == AVAnimations.SNAKE_BLADE_GUARD)
                        || targetDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT;
            }
            if (herobrineMob instanceof SledgehammerHerobrineEntity) {
                return targetDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT || targetDynamicAnimation == WOMAnimations.TORMENT_DASH;
            }
        }
        return isAnimationDangerous(targetDynamicAnimation);
    }

    public static boolean isAnimationDangerous(AssetAccessor<? extends DynamicAnimation> targetDynamicAnimation) {
        if (targetDynamicAnimation != null && targetDynamicAnimation.get().getRegistryName() != null) {
            String animation = targetDynamicAnimation.get().getRegistryName().toString();
            return DANGEROUS_ANIMATIONS.contains(animation);
        }
        return false;
    }
}
