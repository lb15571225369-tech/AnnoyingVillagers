package com.pla.annoyingvillagers.potion;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public class ScreenShakeMobEffect extends MobEffect {

    public ScreenShakeMobEffect() {
        super(MobEffectCategory.NEUTRAL, -11382190);
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            public boolean isVisibleInInventory(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public boolean renderInventoryText(MobEffectInstance mobeffectinstance, EffectRenderingInventoryScreen<?> effectrenderinginventoryscreen, GuiGraphics guigraphics, int i, int j, int k) {
                return false;
            }

            public boolean isVisibleInGui(MobEffectInstance mobeffectinstance) {
                return false;
            }
        });
    }
}