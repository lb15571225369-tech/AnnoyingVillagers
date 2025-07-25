package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.efdg.skill.EarthquakeSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

public class AVSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS =
            DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), AnnoyingVillagers.MODID);

    public static final RegistryObject<SkillDataKey<Boolean>> SUPERARMOR;
    public AVSkillDataKeys(){}
    static {
        SUPERARMOR = DATA_KEYS.register("superarmor", () ->
                SkillDataKey.createBooleanKey(false, true, new Class[]{EarthquakeSkill.class})
        );
    }
}
