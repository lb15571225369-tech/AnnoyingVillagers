//package com.pla.annoyingvillagers.client.gui;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import java.util.HashMap;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.client.gui.components.EditBox;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.network.LoginMusicButtonMessage;
//import com.pla.annoyingvillagers.world.inventory.LoginMusicMenu;
//
//public class LoginMusicScreen extends AbstractContainerScreen<LoginMusicMenu> {
//
//    private static final HashMap<String, Object> guistate = LoginMusicMenu.guistate;
//    private static final HashMap<String, String> textstate = new HashMap();
//    private final Level world;
//    private final int x;
//    private final int y;
//    private final int z;
//    private final Player entity;
//    public static EditBox code;
//    Button button_yes;
//    private static final ResourceLocation texture = new ResourceLocation(AnnoyingVillagers.MODID + ":textures/screens/login_music.png");
//
//    public LoginMusicScreen(LoginMusicMenu loginmusicmenu, Inventory inventory, Component component) {
//        super(loginmusicmenu, inventory, component);
//        this.world = loginmusicmenu.world;
//        this.x = loginmusicmenu.x;
//        this.y = loginmusicmenu.y;
//        this.z = loginmusicmenu.z;
//        this.entity = loginmusicmenu.entity;
//        this.imageWidth = 176;
//        this.imageHeight = 166;
//    }
//
//    public void render(PoseStack posestack, int i, int j, float f) {
//        this.renderBackground(posestack);
//        super.render(posestack, i, j, f);
//        this.renderTooltip(posestack, i, j);
//        LoginMusicScreen.code.render(posestack, i, j, f);
//    }
//
//    protected void renderBg(PoseStack posestack, float f, int i, int j) {
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.setShaderTexture(0, LoginMusicScreen.texture);
//        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
//        RenderSystem.disableBlend();
//    }
//
//    public boolean keyPressed(int i, int j, int k) {
//        if (i == 256) {
//            this.minecraft.player.closeContainer();
//            return true;
//        } else {
//            return LoginMusicScreen.code.isFocused() ? LoginMusicScreen.code.keyPressed(i, j, k) : super.keyPressed(i, j, k);
//        }
//    }
//
//    public void containerTick() {
//        super.containerTick();
//        LoginMusicScreen.code.tick();
//    }
//
//    protected void renderLabels(PoseStack posestack, int i, int j) {
//        this.font.draw(posestack, new TranslatableComponent("gui.annoyingvillagers.login_music.label_phoneid"), 27.0F, 31.0F, -12829636);
//    }
//
//    public void onClose() {
//        super.onClose();
//        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
//    }
//
//    public void init() {
//        super.init();
//        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
//        LoginMusicScreen.code = new EditBox(this.font, this.leftPos + 28, this.topPos + 45, 120, 20, new TranslatableComponent("gui.annoyingvillagers.login_music.code"));
//        LoginMusicScreen.code.setMaxLength(32767);
//        LoginMusicScreen.guistate.put("text:code", LoginMusicScreen.code);
//        this.addWidget(LoginMusicScreen.code);
//        this.button_yes = new Button(this.leftPos + 55, this.topPos + 82, 65, 20, new TranslatableComponent("gui.annoyingvillagers.login_music.button_yes"), (button) -> {
//            LoginMusicScreen.textstate.put("textin:code", LoginMusicScreen.code.getValue());
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new LoginMusicButtonMessage(0, this.x, this.y, this.z, LoginMusicScreen.textstate));
//            LoginMusicButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, LoginMusicScreen.textstate);
//        });
//        LoginMusicScreen.guistate.put("button:button_yes", this.button_yes);
//        this.addRenderableWidget(this.button_yes);
//    }
//}
