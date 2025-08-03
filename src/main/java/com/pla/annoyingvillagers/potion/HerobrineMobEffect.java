package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.HerobrineMobEffectOnRemoveAttributeModifiersProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineMobEffectOnApplyInstantenousEffectProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class HerobrineMobEffect extends MobEffect {

    public HerobrineMobEffect() {
        super(MobEffectCategory.HARMFUL, -6710887);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.herobrine";
    }

    public boolean isInstantenous() {
        return true;
    }

    public void applyInstantenousEffect(Entity entity, Entity entity1, LivingEntity livingentity, int i, double d0) {
        HerobrineMobEffectOnApplyInstantenousEffectProcedure.execute(livingentity.level(), livingentity);
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        HerobrineMobEffectOnApplyInstantenousEffectProcedure.execute(livingentity.level(), livingentity);
    }

    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
        HerobrineMobEffectOnRemoveAttributeModifiersProcedure.execute(livingentity.level(), livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
