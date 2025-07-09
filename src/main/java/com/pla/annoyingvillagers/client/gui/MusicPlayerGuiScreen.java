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
//import com.pla.annoyingvillagers.network.MusicPlayerGuiButtonMessage;
//import com.pla.annoyingvillagers.world.inventory.MusicPlayerGuiMenu;
//
//public class MusicPlayerGuiScreen extends AbstractContainerScreen<MusicPlayerGuiMenu> {
//
//    private static final HashMap<String, Object> guistate = MusicPlayerGuiMenu.guistate;
//    private static final HashMap<String, String> textstate = new HashMap();
//    private final Level world;
//    private final int x;
//    private final int y;
//    private final int z;
//    private final Player entity;
//    public static EditBox music_id;
//    Button button_empty;
//    Button button_login;
//    Button button_music_list;
//    Button button_vote;
//    Button button_search;
//    private static final ResourceLocation texture = new ResourceLocation(AnnoyingVillagers.MODID + ":textures/screens/music_player_gui.png");
//
//    public MusicPlayerGuiScreen(MusicPlayerGuiMenu musicplayerguimenu, Inventory inventory, Component component) {
//        super(musicplayerguimenu, inventory, component);
//        this.world = musicplayerguimenu.world;
//        this.x = musicplayerguimenu.x;
//        this.y = musicplayerguimenu.y;
//        this.z = musicplayerguimenu.z;
//        this.entity = musicplayerguimenu.entity;
//        this.imageWidth = 176;
//        this.imageHeight = 166;
//    }
//
//    public boolean isPauseScreen() {
//        return true;
//    }
//
//    public void render(PoseStack posestack, int i, int j, float f) {
//        this.renderBackground(posestack);
//        super.render(posestack, i, j, f);
//        this.renderTooltip(posestack, i, j);
//        MusicPlayerGuiScreen.music_id.render(posestack, i, j, f);
//    }
//
//    protected void renderBg(PoseStack posestack, float f, int i, int j) {
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.setShaderTexture(0, MusicPlayerGuiScreen.texture);
//        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
//        RenderSystem.disableBlend();
//    }
//
//    public boolean keyPressed(int i, int j, int k) {
//        if (i == 256) {
//            this.minecraft.player.closeContainer();
//            return true;
//        } else {
//            return MusicPlayerGuiScreen.music_id.isFocused() ? MusicPlayerGuiScreen.music_id.keyPressed(i, j, k) : super.keyPressed(i, j, k);
//        }
//    }
//
//    public void containerTick() {
//        super.containerTick();
//        MusicPlayerGuiScreen.music_id.tick();
//    }
//
//    protected void renderLabels(PoseStack posestack, int i, int j) {
//        this.font.draw(posestack, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.label_qing_shu_ru_yin_le_id"), 28.0F, 28.0F, -12829636);
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
//        MusicPlayerGuiScreen.music_id = new EditBox(this.font, this.leftPos + 28, this.topPos + 40, 120, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.music_id"));
//        MusicPlayerGuiScreen.music_id.setMaxLength(32767);
//        MusicPlayerGuiScreen.guistate.put("text:music_id", MusicPlayerGuiScreen.music_id);
//        this.addWidget(MusicPlayerGuiScreen.music_id);
//        this.button_empty = new Button(this.leftPos + 63, this.topPos + 80, 46, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.button_empty"), (button) -> {
//            MusicPlayerGuiScreen.textstate.put("textin:music_id", MusicPlayerGuiScreen.music_id.getValue());
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new MusicPlayerGuiButtonMessage(0, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate));
//            MusicPlayerGuiButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate);
//        });
//        MusicPlayerGuiScreen.guistate.put("button:button_empty", this.button_empty);
//        this.addRenderableWidget(this.button_empty);
//        this.button_login = new Button(this.leftPos + 44, this.topPos + 126, 84, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.button_login"), (button) -> {
//            MusicPlayerGuiScreen.textstate.put("textin:music_id", MusicPlayerGuiScreen.music_id.getValue());
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new MusicPlayerGuiButtonMessage(1, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate));
//            MusicPlayerGuiButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate);
//        });
//        MusicPlayerGuiScreen.guistate.put("button:button_login", this.button_login);
//        this.addRenderableWidget(this.button_login);
//        this.button_music_list = new Button(this.leftPos + 91, this.topPos + 5, 77, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.button_music_list"), (button) -> {
//        });
//        MusicPlayerGuiScreen.guistate.put("button:button_music_list", this.button_music_list);
//        this.addRenderableWidget(this.button_music_list);
//        this.button_vote = new Button(this.leftPos + 8, this.topPos + 126, 29, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.button_vote"), (button) -> {
//            MusicPlayerGuiScreen.textstate.put("textin:music_id", MusicPlayerGuiScreen.music_id.getValue());
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new MusicPlayerGuiButtonMessage(3, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate));
//            MusicPlayerGuiButtonMessage.handleButtonAction(this.entity, 3, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate);
//        });
//        MusicPlayerGuiScreen.guistate.put("button:button_vote", this.button_vote);
//        this.addRenderableWidget(this.button_vote);
//        this.button_search = new Button(this.leftPos + 6, this.topPos + 5, 76, 20, new TranslatableComponent("gui.annoyingvillagers.music_player_gui.button_search"), (button) -> {
//            MusicPlayerGuiScreen.textstate.put("textin:music_id", MusicPlayerGuiScreen.music_id.getValue());
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new MusicPlayerGuiButtonMessage(4, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate));
//            MusicPlayerGuiButtonMessage.handleButtonAction(this.entity, 4, this.x, this.y, this.z, MusicPlayerGuiScreen.textstate);
//        });
//        MusicPlayerGuiScreen.guistate.put("button:button_search", this.button_search);
//        this.addRenderableWidget(this.button_search);
//    }
//}
