//package com.pla.annoyingvillagers.potion;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import java.util.function.Consumer;
//import net.minecraft.client.gui.GuiComponent;
//import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeMap;
//import net.minecraftforge.client.EffectRenderer;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.KuijiaxiaoguoDangXiaoGuoJieShuShiProcedure;
//import pugilist_steve.mod.annoying_villagersbychentu.procedures.KuijiaxiaoguoDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
//
//public class KuijiaxiaoguoMobEffect extends MobEffect {
//
//    public KuijiaxiaoguoMobEffect() {
//        super(MobEffectCategory.HARMFUL, -16737844);
//    }
//
//    public String getDescriptionId() {
//        return "effect.annoyingvillagers.kuijiaxiaoguo";
//    }
//
//    public void addAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        KuijiaxiaoguoDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
//    }
//
//    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
//        super.removeAttributeModifiers(livingentity, attributemap, i);
//        KuijiaxiaoguoDangXiaoGuoJieShuShiProcedure.execute(livingentity.level, livingentity.getX(), livingentity.getY(), livingentity.getZ(), livingentity);
//    }
//
//    public boolean isDurationEffectTick(int i, int j) {
//        return true;
//    }
//
//    public void initializeClient(Consumer<EffectRenderer> consumer) {
//        consumer.accept(new EffectRenderer() {
//            public boolean shouldRenderHUD(MobEffectInstance mobeffectinstance) {
//                return false;
//            }
//
//            public void renderInventoryEffect(MobEffectInstance mobeffectinstance, EffectRenderingInventoryScreen<?> effectrenderinginventoryscreen, PoseStack posestack, int i, int j, float f) {}
//
//            public void renderHUDEffect(MobEffectInstance mobeffectinstance, GuiComponent guicomponent, PoseStack posestack, int i, int j, float f, float f1) {}
//        });
//    }
//}
