package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.EscapeMobEffectOnAddAttributeModifiersProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class EscapeMobEffect extends MobEffect {

    public EscapeMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.escape";
    }

    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        EscapeMobEffectOnAddAttributeModifiersProcedure.execute(livingentity.level(), livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
