package com.pla.annoyingvillagers.potion;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.client.EffectRenderer;
import com.pla.annoyingvillagers.procedures.NpcKickEffectDangXiaoGuoJieShuShiProcedure;

public class NpcKickEffectMobEffect extends MobEffect {

    public NpcKickEffectMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    public String getDescriptionId() {
        return "effect.modid = AnnoyingVillagers.MODID.npc_kick_effect";
    }

    public void removeAttributeModifiers(LivingEntity livingentity, AttributeMap attributemap, int i) {
        super.removeAttributeModifiers(livingentity, attributemap, i);
        NpcKickEffectDangXiaoGuoJieShuShiProcedure.execute(livingentity);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }

    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(new EffectRenderer() {
            public boolean shouldRender(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public boolean shouldRenderInvText(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public boolean shouldRenderHUD(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public void renderInventoryEffect(MobEffectInstance mobeffectinstance, EffectRenderingInventoryScreen<?> effectrenderinginventoryscreen, PoseStack posestack, int i, int j, float f) {}

            public void renderHUDEffect(MobEffectInstance mobeffectinstance, GuiComponent guicomponent, PoseStack posestack, int i, int j, float f, float f1) {}
        });
    }
}
