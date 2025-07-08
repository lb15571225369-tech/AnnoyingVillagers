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
import com.pla.annoyingvillagers.network.IdCheckButtonMessage;
import com.pla.annoyingvillagers.world.inventory.IdCheckMenu;

public class IdCheckScreen extends AbstractContainerScreen<IdCheckMenu> {

    private static final HashMap<String, Object> guistate = IdCheckMenu.guistate;
    private static final HashMap<String, String> textstate = new HashMap();
    private final Level world;
    private final int x;
    private final int y;
    private final int z;
    private final Player entity;
    public static EditBox id_check;
    Button button_login;
    Button button_x;
    private static final ResourceLocation texture = new ResourceLocation("annoying_villagers:textures/screens/id_check.png");

    public IdCheckScreen(IdCheckMenu idcheckmenu, Inventory inventory, Component component) {
        super(idcheckmenu, inventory, component);
        this.world = idcheckmenu.world;
        this.x = idcheckmenu.x;
        this.y = idcheckmenu.y;
        this.z = idcheckmenu.z;
        this.entity = idcheckmenu.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    public void render(PoseStack posestack, int i, int j, float f) {
        this.renderBackground(posestack);
        super.render(posestack, i, j, f);
        this.renderTooltip(posestack, i, j);
        IdCheckScreen.id_check.render(posestack, i, j, f);
    }

    protected void renderBg(PoseStack posestack, float f, int i, int j) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, IdCheckScreen.texture);
        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    public boolean keyPressed(int i, int j, int k) {
        if (i == 256) {
            this.minecraft.player.closeContainer();
            return true;
        } else {
            return IdCheckScreen.id_check.isFocused() ? IdCheckScreen.id_check.keyPressed(i, j, k) : super.keyPressed(i, j, k);
        }
    }

    public void containerTick() {
        super.containerTick();
        IdCheckScreen.id_check.tick();
    }

    protected void renderLabels(PoseStack posestack, int i, int j) {
        this.font.draw(posestack, new TranslatableComponent("gui.annoying_villagers.id_check.label_id"), 28.0F, 28.0F, -12829636);
    }

    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        IdCheckScreen.id_check = new EditBox(this.font, this.leftPos + 28, this.topPos + 45, 120, 20, new TranslatableComponent("gui.annoying_villagers.id_check.id_check"));
        IdCheckScreen.id_check.setMaxLength(32767);
        IdCheckScreen.guistate.put("text:id_check", IdCheckScreen.id_check);
        this.addWidget(IdCheckScreen.id_check);
        this.button_login = new Button(this.leftPos + 62, this.topPos + 94, 51, 20, new TranslatableComponent("gui.annoying_villagers.id_check.button_login"), (button) -> {
            IdCheckScreen.textstate.put("textin:id_check", IdCheckScreen.id_check.getValue());
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new IdCheckButtonMessage(0, this.x, this.y, this.z, IdCheckScreen.textstate));
            IdCheckButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, IdCheckScreen.textstate);
        });
        IdCheckScreen.guistate.put("button:button_login", this.button_login);
        this.addRenderableWidget(this.button_login);
        this.button_x = new Button(this.leftPos + 62, this.topPos + 123, 51, 20, new TranslatableComponent("gui.annoying_villagers.id_check.button_x"), (button) -> {
            IdCheckScreen.textstate.put("textin:id_check", IdCheckScreen.id_check.getValue());
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new IdCheckButtonMessage(1, this.x, this.y, this.z, IdCheckScreen.textstate));
            IdCheckButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z, IdCheckScreen.textstate);
        });
        IdCheckScreen.guistate.put("button:button_x", this.button_x);
        this.addRenderableWidget(this.button_x);
    }
}
