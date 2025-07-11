//package com.pla.annoyingvillagers.potion;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeMap;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.HerobrineDangXiaoGuoJieShuShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.HerobrineZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
//
//public class HerobrineMobEffect extends MobEffect {
//
//    public HerobrineMobEffect() {
//        super(MobEffectCategory.HARMFUL, -6710887);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.herobrine";
//    }
//
//    public boolean isInstantenous() {
//        return true;
//    }
//
//    public void applyInstantenousEffect(Entity entity, Entity entity1, LivingEntity livingentity, int i, double d0) {
//        HerobrineZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(livingentity.level, livingentity);
//    }
//
//    public void applyEffectTick(LivingEntity livingentity, int i) {
//        HerobrineZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(livingentity.level, livingentity);
//    }
//
//    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        super.removeAttributeModifiers(livingentity, attributemap, i);
//        HerobrineDangXiaoGuoJieShuShiProcedure.execute(livingentity.level, livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//}
