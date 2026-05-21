package com.pla.annoyingvillagers.client.screen;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class AVConfigButtonHandler {
    private AVConfigButtonHandler() {}

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen titleScreen)) return;

        AbstractWidget realmsButton = null;
        AbstractWidget modsButton = null;
        for (var widget : event.getListenersList()) {
            if (!(widget instanceof AbstractWidget aw) || !(widget instanceof Button btn)) continue;
            String msg = btn.getMessage().getString();
            if (msg.equalsIgnoreCase("Minecraft Realms")) realmsButton = aw;
            else if (msg.equalsIgnoreCase("Mods") || msg.equalsIgnoreCase("Mod")) modsButton = aw;
        }
        AbstractWidget anchor = realmsButton != null ? realmsButton : modsButton;
        if (anchor == null) return;

        int x = anchor.getX() + anchor.getWidth() + 4;
        int y = anchor.getY();
        Button avButton = Button.builder(
                Component.literal("AV"),
                btn -> titleScreen.getMinecraft().setScreen(new AVConfigScreen(titleScreen))
        ).bounds(x, y, 24, 20).build();
        avButton.setTooltip(Tooltip.create(Component.translatable("annoyingvillagers.config.button.tooltip")));
        event.addListener(avButton);
    }
}
