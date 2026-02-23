package com.pla.annoyingvillagers.compat;

import com.hm.efn.animations.types.stun.EFNStunAnimation;
import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.animations.*;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

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
                EFNSkillAnimations.EXECUTION.get().getRegistryName().toString()
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }

    public static boolean isEFNStun(AssetAccessor<? extends StaticAnimation> assetAccessor) {
        return assetAccessor.get() instanceof EFNStunAnimation;
    }
}
