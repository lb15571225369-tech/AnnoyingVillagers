package com.pla.annoyingvillagers.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AVStringListEditScreen extends Screen {
    private final Screen parent;
    private final List<String> initialList;
    private final Consumer<List<String>> onApply;
    private MultiLineEditBox editBox;

    public AVStringListEditScreen(Screen parent, List<String> initialList, Consumer<List<String>> onApply) {
        super(Component.translatable("annoyingvillagers.config.list.title"));
        this.parent = parent;
        this.initialList = initialList;
        this.onApply = onApply;
    }

    @Override
    protected void init() {
        int boxWidth = Math.min(this.width - 40, 480);
        int boxHeight = this.height - 90;
        int boxX = (this.width - boxWidth) / 2;
        int boxY = 40;

        this.editBox = new MultiLineEditBox(
                this.font, boxX, boxY, boxWidth, boxHeight,
                Component.empty(), this.title
        );
        this.editBox.setCharacterLimit(100000);
        this.editBox.setValue(String.join("\n", this.initialList));
        this.addRenderableWidget(this.editBox);

        int btnY = this.height - 30;
        this.addRenderableWidget(Button.builder(
                Component.translatable("annoyingvillagers.config.save"),
                b -> {
                    String text = this.editBox.getValue();
                    List<String> result = Arrays.stream(text.split("\\r?\\n"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toList();
                    this.onApply.accept(result);
                    this.minecraft.setScreen(this.parent);
                }
        ).bounds(this.width / 2 - 110, btnY, 100, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.translatable("annoyingvillagers.config.cancel"),
                b -> this.minecraft.setScreen(this.parent)
        ).bounds(this.width / 2 + 10, btnY, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gg);
        super.render(gg, mouseX, mouseY, partialTick);
        gg.drawCenteredString(this.font, this.title, this.width / 2, 18, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
