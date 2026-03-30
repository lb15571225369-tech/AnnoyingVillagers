package com.pla.annoyingvillagers.compat;

import net.corruptdog.cdm.gameasset.CorruptAnimations;

import java.util.HashSet;
import java.util.Set;

public class EpicFightResurrection {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                CorruptAnimations.YAMATO_JUDGEMENT_CUT.get().getRegistryName().toString(),
                CorruptAnimations.YAMATO_JUDGEMENT_CUT_JUST.get().getRegistryName().toString(),
                CorruptAnimations.YAMATO_JUDGEMENT_CUT_END.get().getRegistryName().toString()
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }
}
