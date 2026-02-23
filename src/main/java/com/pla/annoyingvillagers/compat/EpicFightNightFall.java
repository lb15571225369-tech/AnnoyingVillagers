package com.pla.annoyingvillagers.compat;

import com.hm.efn.animations.types.stun.EFNStunAnimation;
import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.animations.*;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashSet;
import java.util.Set;

public class EpicFightNightFall {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_FIRST.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_SECOND.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING_MOB.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_DASH.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING_MOB.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE1.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE2.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE3.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_FINISHER.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_ALL.get().getRegistryName().toString(),
                EFNAnimations.DMC5_V_JC.get().getRegistryName().toString(),
                EFNSkillAnimations.EXECUTION.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y_SP.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_KICK_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_THROUGH.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXX.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY_CHARGE.get().getRegistryName().toString(),
                EFNSekiroAnimations.DRAGON_FLASH.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_1.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_2.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START_N.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP_N.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_HARVEST.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_SCARLET_END.get().getRegistryName().toString()
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }

    public static boolean isEFNStun(AssetAccessor<? extends StaticAnimation> assetAccessor) {
        return assetAccessor.get() instanceof EFNStunAnimation;
    }

    public static void playEfnGuardHit(LivingEntityPatch<?> livingEntityPatch, int index) {
        if (index == 0) {
            livingEntityPatch.playAnimationSynchronized(EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1, 0.0F);
        } else if (index == 1) {
            livingEntityPatch.playAnimationSynchronized(EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2, 0.0F);
        } else {
            livingEntityPatch.playAnimationSynchronized(EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3, 0.0F);
        }
    }

    public static boolean isPlayingEfnGuardHit(CEHumanoidPatch ceHumanoidPatch) {
        AnimationPlayer animationPlayer = ceHumanoidPatch.getAnimator().getPlayerFor(null);
        if (animationPlayer != null) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
            return dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1 || dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2 || dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3;
        }
        return false;
    }
}
