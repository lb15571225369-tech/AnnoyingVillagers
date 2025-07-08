package com.pla.annoyingvillagers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.AnnoyingVillagersMod;
import com.pla.annoyingvillagers.network.MusicSearchButtonMessage;
import com.pla.annoyingvillagers.world.inventory.MusicSearchMenu;

public class MusicSearchScreen extends AbstractContainerScreen<MusicSearchMenu> {

    private static final HashMap<String, Object> guistate = MusicSearchMenu.guistate;
    private static final HashMap<String, String> textstate = new HashMap();
    private final Level world;
    private final int x;
    private final int y;
    private final int z;
    private final Player entity;
    public static EditBox music_seacrh;
    Button button_seacrh;
    private static final ResourceLocation texture = new ResourceLocation("annoying_villagers:textures/screens/music_search.png");

    public MusicSearchScreen(MusicSearchMenu musicsearchmenu, Inventory inventory, Component component) {
        super(musicsearchmenu, inventory, component);
        this.world = musicsearchmenu.world;
        this.x = musicsearchmenu.x;
        this.y = musicsearchmenu.y;
        this.z = musicsearchmenu.z;
        this.entity = musicsearchmenu.entity;
        this.imageWidth = 119;
        this.imageHeight = 121;
    }

    public void render(PoseStack posestack, int i, int j, float f) {
        this.renderBackground(posestack);
        super.render(posestack, i, j, f);
        this.renderTooltip(posestack, i, j);
        MusicSearchScreen.music_seacrh.render(posestack, i, j, f);
    }

    protected void renderBg(PoseStack posestack, float f, int i, int j) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, MusicSearchScreen.texture);
        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    public boolean keyPressed(int i, int j, int k) {
        if (i == 256) {
            this.minecraft.player.closeContainer();
            return true;
        } else {
            return MusicSearchScreen.music_seacrh.isFocused() ? MusicSearchScreen.music_seacrh.keyPressed(i, j, k) : super.keyPressed(i, j, k);
        }
    }

    public void containerTick() {
        super.containerTick();
        MusicSearchScreen.music_seacrh.tick();
    }

    protected void renderLabels(PoseStack posestack, int i, int j) {
        this.font.draw(posestack, new TranslatableComponent("gui.annoying_villagers.music_search.label_name"), 13.0F, 12.0F, -12829636);
    }

    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        MusicSearchScreen.music_seacrh = new EditBox(this.font, this.leftPos + 13, this.topPos + 24, 93, 20, new TranslatableComponent("gui.annoying_villagers.music_search.music_seacrh")) {
            {
                this.setSuggestion((new TranslatableComponent("gui.annoying_villagers.music_search.music_seacrh")).getString());
            }

            public void insertText(String s) {
                super.insertText(s);
                if (this.getValue().isEmpty()) {
                    this.setSuggestion((new TranslatableComponent("gui.annoying_villagers.music_search.music_seacrh")).getString());
                } else {
                    this.setSuggestion((String) null);
                }

            }

            public void moveCursorTo(int i) {
                super.moveCursorTo(i);
                if (this.getValue().isEmpty()) {
                    this.setSuggestion((new TranslatableComponent("gui.annoying_villagers.music_search.music_seacrh")).getString());
                } else {
                    this.setSuggestion((String) null);
                }

            }
        };
        MusicSearchScreen.music_seacrh.setMaxLength(32767);
        MusicSearchScreen.guistate.put("text:music_seacrh", MusicSearchScreen.music_seacrh);
        this.addWidget(MusicSearchScreen.music_seacrh);
        this.button_seacrh = new Button(this.leftPos + 30, this.topPos + 60, 56, 20, new TranslatableComponent("gui.annoying_villagers.music_search.button_seacrh"), (button) -> {
            MusicSearchScreen.textstate.put("textin:music_seacrh", MusicSearchScreen.music_seacrh.getValue());
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new MusicSearchButtonMessage(0, this.x, this.y, this.z, MusicSearchScreen.textstate));
            MusicSearchButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, MusicSearchScreen.textstate);
        });
        MusicSearchScreen.guistate.put("button:button_seacrh", this.button_seacrh);
        this.addRenderableWidget(this.button_seacrh);
    }
}
