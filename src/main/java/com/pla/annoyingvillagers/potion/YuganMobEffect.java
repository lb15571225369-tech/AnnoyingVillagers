//package com.pla.annoyingvillagers.potion;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.YuganDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
//
//public class YuganMobEffect extends MobEffect {
//
//    public YuganMobEffect() {
//        super(MobEffectCategory.NEUTRAL, -1);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.yugan";
//    }
//
//    public boolean isInstantenous() {
//        return true;
//    }
//
//    public void applyInstantenousEffect(Entity entity, Entity entity1, LivingEntity livingentity, int i, double d0) {
//        YuganDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//}
