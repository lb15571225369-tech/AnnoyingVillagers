package com.pla.annoyingvillagers.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import com.pla.annoyingvillagers.procedures.HerobrineEffectDangXiaoGuoJieShuShiProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineEffectDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
import com.pla.annoyingvillagers.procedures.HerobrineEffectTickProcedure;

public class HerobrineEffectMobEffect extends MobEffect {

    public HerobrineEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -3355444);
    }

    public String getDescriptionId() {
        return "effect.modid = AnnoyingVillagers.MODID.herobrine_effect";
    }

    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        HerobrineEffectDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public void applyEffectTick(LivingEntity livingentity, int i) {
        HerobrineEffectTickProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
    }

    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
        HerobrineEffectDangXiaoGuoJieShuShiProcedure.execute(livingentity.level, livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
