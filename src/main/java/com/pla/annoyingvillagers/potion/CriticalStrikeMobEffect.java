package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.CriticalStrikeMobEffectOnEndProcedure;
import com.pla.annoyingvillagers.procedures.CriticalStrikeMobEffectOnStartProcedure;
import com.pla.annoyingvillagers.procedures.CriticalStrikeMobEffectEveryTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
public class CriticalStrikeMobEffect extends MobEffect {

    public CriticalStrikeMobEffect() {
        super(MobEffectCategory.HARMFUL, -3407872);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.tongji";
    }

    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        CriticalStrikeMobEffectOnStartProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        CriticalStrikeMobEffectEveryTickProcedure.execute(livingentity);
    }

    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
        CriticalStrikeMobEffectOnEndProcedure.execute(livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}

