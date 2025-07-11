//package com.pla.annoyingvillagers.potion;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeMap;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.TongjiDangXiaoGuoJieShuShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.TongjiDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.TongjiZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
//
//public class TongjiMobEffect extends MobEffect {
//
//    public TongjiMobEffect() {
//        super(MobEffectCategory.HARMFUL, -3407872);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.tongji";
//    }
//
//    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        TongjiDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
//    }
//
//    public void applyEffectTick(LivingEntity livingentity, int i) {
//        TongjiZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(livingentity);
//    }
//
//    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        super.removeAttributeModifiers(livingentity, attributemap, i);
//        TongjiDangXiaoGuoJieShuShiProcedure.execute(livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//}
