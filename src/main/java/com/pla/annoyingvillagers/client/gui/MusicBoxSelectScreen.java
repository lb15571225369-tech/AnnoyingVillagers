//package com.pla.annoyingvillagers.client.gui;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import java.util.HashMap;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import com.pla.annoyingvillagers.world.inventory.MusicBoxSelectMenu;
//
//public class MusicBoxSelectScreen extends AbstractContainerScreen<MusicBoxSelectMenu> {
//
//    private static final HashMap<String, Object> guistate = MusicBoxSelectMenu.guistate;
//    private static final HashMap<String, String> textstate = new HashMap();
//    private final Level world;
//    private final int x;
//    private final int y;
//    private final int z;
//    private final Player entity;
//    private static final ResourceLocation texture = new ResourceLocation(AnnoyingVillagers.MODID + ":textures/screens/music_box_select.png");
//
//    public MusicBoxSelectScreen(MusicBoxSelectMenu musicboxselectmenu, Inventory inventory, Component component) {
//        super(musicboxselectmenu, inventory, component);
//        this.world = musicboxselectmenu.world;
//        this.x = musicboxselectmenu.x;
//        this.y = musicboxselectmenu.y;
//        this.z = musicboxselectmenu.z;
//        this.entity = musicboxselectmenu.entity;
//        this.imageWidth = 176;
//        this.imageHeight = 166;
//    }
//
//    public void render(PoseStack posestack, int i, int j, float f) {
//        this.renderBackground(posestack);
//        super.render(posestack, i, j, f);
//        this.renderTooltip(posestack, i, j);
//    }
//
//    protected void renderBg(PoseStack posestack, float f, int i, int j) {
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.setShaderTexture(0, MusicBoxSelectScreen.texture);
//        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
//        RenderSystem.disableBlend();
//    }
//
//    public boolean keyPressed(int i, int j, int k) {
//        if (i == 256) {
//            this.minecraft.player.closeContainer();
//            return true;
//        } else {
//            return super.keyPressed(i, j, k);
//        }
//    }
//
//    public void containerTick() {
//        super.containerTick();
//    }
//
//    protected void renderLabels(PoseStack posestack, int i, int j) {}
//
//    public void onClose() {
//        super.onClose();
//        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
//    }
//
//    public void init() {
//        super.init();
//        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
//    }
//}
