package com.pla.annoyingvillagers.potion;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.procedures.ElectifyDuringEffectEveryTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ElectifyMobEffect extends MobEffect {

    public ElectifyMobEffect() {
        super(MobEffectCategory.HARMFUL, -16711681);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.electify";
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        try {
            ElectifyDuringEffectEveryTickProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
        } catch (CommandSyntaxException e) {
            
        }
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
