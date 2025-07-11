package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.procedures.NailiZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class NailiMobEffect extends MobEffect {

    public NailiMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -65485);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.naili";
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        NailiZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
