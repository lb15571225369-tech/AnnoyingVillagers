//package com.pla.annoyingvillagers.client.gui;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import java.util.HashMap;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import com.pla.annoyingvillagers.network.EmoteButtonMessage;
//import com.pla.annoyingvillagers.world.inventory.EmoteMenu;
//
//public class EmoteScreen extends AbstractContainerScreen<EmoteMenu> {
//
//    private static final HashMap<String, Object> guistate = EmoteMenu.guistate;
//    private static final HashMap<String, String> textstate = new HashMap();
//    private final Level world;
//    private final int x;
//    private final int y;
//    private final int z;
//    private final Player entity;
//    Button button_da_zhao_hu;
//    Button button_ju_gong;
//    Button button_shang_ye;
//    Button button_peng_quan;
//    Button button_jie_wu;
//    Button button_criss_cross;
//    Button button_bei_yang;
//    Button button_ding_ding_ding_ding_ding;
//    Button button_shen_mi;
//    Button button_jing_li;
//    Button button_huan_hu;
//    Button button_dian_zi_yao_bai;
//    Button button_kai_guan_biao_qing_yin_le;
//    Button button_xian_chu_xin_zang;
//    Button button_xi_di_er_zuo;
//    Button button_nin_hao_qing;
//    private static final ResourceLocation texture = new ResourceLocation(AnnoyingVillagers.MODID + ":textures/screens/emote.png");
//
//    public EmoteScreen(EmoteMenu emotemenu, Inventory inventory, Component component) {
//        super(emotemenu, inventory, component);
//        this.world = emotemenu.world;
//        this.x = emotemenu.x;
//        this.y = emotemenu.y;
//        this.z = emotemenu.z;
//        this.entity = emotemenu.entity;
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
//        RenderSystem.setShaderTexture(0, EmoteScreen.texture);
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
//        this.button_da_zhao_hu = new Button(this.leftPos + 19, this.topPos + 13, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_da_zhao_hu"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(0, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_da_zhao_hu", this.button_da_zhao_hu);
//        this.addRenderableWidget(this.button_da_zhao_hu);
//        this.button_ju_gong = new Button(this.leftPos + 118, this.topPos + 13, 35, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_ju_gong"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(1, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_ju_gong", this.button_ju_gong);
//        this.addRenderableWidget(this.button_ju_gong);
//        this.button_shang_ye = new Button(this.leftPos + 184, this.topPos + 91, 31, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_shang_ye"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(2, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 2, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_shang_ye", this.button_shang_ye);
//        this.addRenderableWidget(this.button_shang_ye);
//        this.button_peng_quan = new Button(this.leftPos + 69, this.topPos + 13, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_peng_quan"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(3, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 3, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_peng_quan", this.button_peng_quan);
//        this.addRenderableWidget(this.button_peng_quan);
//        this.button_jie_wu = new Button(this.leftPos + 19, this.topPos + 41, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_jie_wu"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(4, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 4, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_jie_wu", this.button_jie_wu);
//        this.addRenderableWidget(this.button_jie_wu);
//        this.button_criss_cross = new Button(this.leftPos + 69, this.topPos + 41, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_criss_cross"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(5, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 5, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_criss_cross", this.button_criss_cross);
//        this.addRenderableWidget(this.button_criss_cross);
//        this.button_bei_yang = new Button(this.leftPos + 118, this.topPos + 41, 35, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_bei_yang"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(6, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 6, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_bei_yang", this.button_bei_yang);
//        this.addRenderableWidget(this.button_bei_yang);
//        this.button_ding_ding_ding_ding_ding = new Button(this.leftPos + 19, this.topPos + 68, 80, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_ding_ding_ding_ding_ding"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(7, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 7, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_ding_ding_ding_ding_ding", this.button_ding_ding_ding_ding_ding);
//        this.addRenderableWidget(this.button_ding_ding_ding_ding_ding);
//        this.button_shen_mi = new Button(this.leftPos + 118, this.topPos + 95, 35, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_shen_mi"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(8, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 8, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_shen_mi", this.button_shen_mi);
//        this.addRenderableWidget(this.button_shen_mi);
//        this.button_jing_li = new Button(this.leftPos + 19, this.topPos + 95, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_jing_li"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(9, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 9, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_jing_li", this.button_jing_li);
//        this.addRenderableWidget(this.button_jing_li);
//        this.button_huan_hu = new Button(this.leftPos + 69, this.topPos + 95, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_huan_hu"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(10, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 10, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_huan_hu", this.button_huan_hu);
//        this.addRenderableWidget(this.button_huan_hu);
//        this.button_dian_zi_yao_bai = new Button(this.leftPos + 107, this.topPos + 68, 46, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_dian_zi_yao_bai"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(11, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 11, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_dian_zi_yao_bai", this.button_dian_zi_yao_bai);
//        this.addRenderableWidget(this.button_dian_zi_yao_bai);
//        this.button_kai_guan_biao_qing_yin_le = new Button(this.leftPos + 54, this.topPos + -25, 66, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_kai_guan_biao_qing_yin_le"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(12, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 12, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_kai_guan_biao_qing_yin_le", this.button_kai_guan_biao_qing_yin_le);
//        this.addRenderableWidget(this.button_kai_guan_biao_qing_yin_le);
//        this.button_xian_chu_xin_zang = new Button(this.leftPos + 19, this.topPos + 122, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_xian_chu_xin_zang"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(13, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 13, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_xian_chu_xin_zang", this.button_xian_chu_xin_zang);
//        this.addRenderableWidget(this.button_xian_chu_xin_zang);
//        this.button_xi_di_er_zuo = new Button(this.leftPos + 69, this.topPos + 122, 37, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_xi_di_er_zuo"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(14, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 14, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_xi_di_er_zuo", this.button_xi_di_er_zuo);
//        this.addRenderableWidget(this.button_xi_di_er_zuo);
//        this.button_nin_hao_qing = new Button(this.leftPos + 118, this.topPos + 122, 35, 20, new TranslatableComponent("gui.annoyingvillagers.emote.button_nin_hao_qing"), (button) -> {
//            AnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(15, this.x, this.y, this.z, EmoteScreen.textstate));
//            EmoteButtonMessage.handleButtonAction(this.entity, 15, this.x, this.y, this.z, EmoteScreen.textstate);
//        });
//        EmoteScreen.guistate.put("button:button_nin_hao_qing", this.button_nin_hao_qing);
//        this.addRenderableWidget(this.button_nin_hao_qing);
//    }
//}
