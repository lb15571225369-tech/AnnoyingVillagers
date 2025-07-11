package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.ElectifyZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
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
        ElectifyZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
