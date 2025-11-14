package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.EnderAegisSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.utils.PacketBufferCodec;
import yesman.epicfight.skill.SkillDataKey;

public final class AVSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS =
            DeferredRegister.create(ResourceLocation.fromNamespaceAndPath("epicfight", "skill_data_keys"),
                    AnnoyingVillagers.MODID);

    public static final RegistryObject<SkillDataKey<Integer>> ENDER_AEGIS_COOLDOWN  =
            DATA_KEYS.register("ender_aegis_cooldown",
                    () -> SkillDataKey.createSkillDataKey(PacketBufferCodec.INTEGER, 0,
                            new Class[]{EnderAegisSkill.class}));

    private AVSkillDataKeys() {}
}
