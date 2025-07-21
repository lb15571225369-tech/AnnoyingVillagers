package com.pla.annoyingvillagers.potion;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.procedures.WantedMobEffectOnEndProcedure;
import com.pla.annoyingvillagers.procedures.WantedMobEffectOnStartProcedure;
import com.pla.annoyingvillagers.procedures.WantedMobEffectEveryTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
public class WantedMobEffect extends MobEffect {

    public WantedMobEffect() {
        super(MobEffectCategory.HARMFUL, -3407872);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.wanted";
    }

    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        WantedMobEffectOnStartProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        WantedMobEffectEveryTickProcedure.execute(livingentity);
    }

    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
        WantedMobEffectOnEndProcedure.execute(livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}

