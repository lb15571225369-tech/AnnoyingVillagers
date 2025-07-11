//package com.pla.annoyingvillagers.potion;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeMap;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.HerobrinefushenbuffDangXiaoGuoJieShuShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.HerobrinefushenbuffDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.HerobrinefushenbuffZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
//
//public class HerobrinefushenbuffMobEffect extends MobEffect {
//
//    public HerobrinefushenbuffMobEffect() {
//        super(MobEffectCategory.HARMFUL, -6710887);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.herobrinefushenbuff";
//    }
//
//    public boolean isInstantenous() {
//        return true;
//    }
//
//    public void applyInstantenousEffect(Entity entity, Entity entity1, LivingEntity livingentity, int i, double d0) {
//        HerobrinefushenbuffDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
//    }
//
//    public void applyEffectTick(LivingEntity livingentity, int i) {
//        HerobrinefushenbuffZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute();
//    }
//
//    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        super.removeAttributeModifiers(livingentity, attributemap, i);
//        HerobrinefushenbuffDangXiaoGuoJieShuShiProcedure.execute(livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//}
