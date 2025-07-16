package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.EnduranceMobEffectEveryTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EnduranceMobEffect extends MobEffect {

    public EnduranceMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -65485);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.endurance";
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        EnduranceMobEffectEveryTickProcedure.execute(livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
