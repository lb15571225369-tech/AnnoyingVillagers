package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import yesman.epicfight.skill.SkillDataKey;

public final class AVSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS =
            DeferredRegister.create(ResourceLocation.fromNamespaceAndPath("epicfight", "skill_data_keys"),
                    AnnoyingVillagers.MODID);

    private AVSkillDataKeys() {}
}
