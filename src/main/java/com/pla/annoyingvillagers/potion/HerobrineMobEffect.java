package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.HerobrineMobEffectOnApplyInstantenousEffectProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public class HerobrineMobEffect extends MobEffect {

    public HerobrineMobEffect() {
        super(MobEffectCategory.HARMFUL, -6710887);
    }

    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.herobrine";
    }

    public boolean isInstantenous() {
        return true;
    }

    public void applyInstantenousEffect(Entity entity, Entity entity1, @NotNull LivingEntity livingentity, int i, double d0) {
        HerobrineMobEffectOnApplyInstantenousEffectProcedure.execute(livingentity.level(), livingentity);
    }

    public void applyEffectTick(@NotNull LivingEntity livingentity, int i) {
        HerobrineMobEffectOnApplyInstantenousEffectProcedure.execute(livingentity.level(), livingentity);
    }

    public void removeAttributeModifiers(@NotNull LivingEntity livingentity, @NotNull AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }

}
