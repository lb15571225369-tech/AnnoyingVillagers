//package com.pla.annoyingvillagers.potion;
//
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeMap;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.TaotuoAnXiaAnJianShiProcedure;
//
//public class TaotuobuffMobEffect extends MobEffect {
//
//    public TaotuobuffMobEffect() {
//        super(MobEffectCategory.NEUTRAL, -1);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.taotuobuff";
//    }
//
//    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        TaotuoAnXiaAnJianShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//}
