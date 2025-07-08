package com.pla.annoyingvillagers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.pla.annoyingvillagers.AnnoyingVillagersMod;
import com.pla.annoyingvillagers.network.EmojiButtonMessage;
import com.pla.annoyingvillagers.world.inventory.EmojiMenu;

public class EmojiScreen extends AbstractContainerScreen<EmojiMenu> {

    private static final HashMap<String, Object> guistate = EmojiMenu.guistate;
    private static final HashMap<String, String> textstate = new HashMap();
    private final Level world;
    private final int x;
    private final int y;
    private final int z;
    private final Player entity;
    Button button_ju_shou_jia_you;
    Button button_fa_dian;
    Button button_feng_kuang_ke_tou;
    Button button_fa_dian_2;
    Button button_yao_bai_zhan_jia;
    Button button_cheng_se_zheng_yi;
    Button button_shua_lai;
    Button button_da_zhao_hu;
    Button button_ju_gong;
    Button button_chao_xiao;
    Button button_sieg_heil;
    Button button_dou_tun;
    Button button_duo_shan_yao;
    Button button_tiao_xin;
    Button button_fu_wo_cheng;
    Button button_lue_you_shi_zhong;
    Button button_yao_bai_wu_dao;
    Button button_gu_zhang;
    Button button_xia_ye;
    Button button_kai_guan_biao_qing_yin_le;
    private static final ResourceLocation texture = new ResourceLocation("annoying_villagers:textures/screens/emoji.png");

    public EmojiScreen(EmojiMenu emojimenu, Inventory inventory, Component component) {
        super(emojimenu, inventory, component);
        this.world = emojimenu.world;
        this.x = emojimenu.x;
        this.y = emojimenu.y;
        this.z = emojimenu.z;
        this.entity = emojimenu.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    public void render(PoseStack posestack, int i, int j, float f) {
        this.renderBackground(posestack);
        super.render(posestack, i, j, f);
        this.renderTooltip(posestack, i, j);
    }

    protected void renderBg(PoseStack posestack, float f, int i, int j) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, EmojiScreen.texture);
        blit(posestack, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    public boolean keyPressed(int i, int j, int k) {
        if (i == 256) {
            this.minecraft.player.closeContainer();
            return true;
        } else {
            return super.keyPressed(i, j, k);
        }
    }

    public void containerTick() {
        super.containerTick();
    }

    protected void renderLabels(PoseStack posestack, int i, int j) {}

    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.button_ju_shou_jia_you = new Button(this.leftPos + 114, this.topPos + 64, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_ju_shou_jia_you"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(0, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_ju_shou_jia_you", this.button_ju_shou_jia_you);
        this.addRenderableWidget(this.button_ju_shou_jia_you);
        this.button_fa_dian = new Button(this.leftPos + 114, this.topPos + 13, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_fa_dian"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(1, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_fa_dian", this.button_fa_dian);
        this.addRenderableWidget(this.button_fa_dian);
        this.button_feng_kuang_ke_tou = new Button(this.leftPos + 67, this.topPos + 39, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_feng_kuang_ke_tou"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(2, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 2, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_feng_kuang_ke_tou", this.button_feng_kuang_ke_tou);
        this.addRenderableWidget(this.button_feng_kuang_ke_tou);
        this.button_fa_dian_2 = new Button(this.leftPos + 19, this.topPos + 39, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_fa_dian_2"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(3, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 3, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_fa_dian_2", this.button_fa_dian_2);
        this.addRenderableWidget(this.button_fa_dian_2);
        this.button_yao_bai_zhan_jia = new Button(this.leftPos + 67, this.topPos + 64, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_yao_bai_zhan_jia"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(4, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 4, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_yao_bai_zhan_jia", this.button_yao_bai_zhan_jia);
        this.addRenderableWidget(this.button_yao_bai_zhan_jia);
        this.button_cheng_se_zheng_yi = new Button(this.leftPos + 114, this.topPos + 39, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_cheng_se_zheng_yi"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(5, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 5, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_cheng_se_zheng_yi", this.button_cheng_se_zheng_yi);
        this.addRenderableWidget(this.button_cheng_se_zheng_yi);
        this.button_shua_lai = new Button(this.leftPos + 19, this.topPos + 64, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_shua_lai"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(6, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 6, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_shua_lai", this.button_shua_lai);
        this.addRenderableWidget(this.button_shua_lai);
        this.button_da_zhao_hu = new Button(this.leftPos + 19, this.topPos + 13, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_da_zhao_hu"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(7, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 7, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_da_zhao_hu", this.button_da_zhao_hu);
        this.addRenderableWidget(this.button_da_zhao_hu);
        this.button_ju_gong = new Button(this.leftPos + 67, this.topPos + 13, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_ju_gong"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(8, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 8, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_ju_gong", this.button_ju_gong);
        this.addRenderableWidget(this.button_ju_gong);
        this.button_chao_xiao = new Button(this.leftPos + 19, this.topPos + 89, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_chao_xiao"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(9, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 9, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_chao_xiao", this.button_chao_xiao);
        this.addRenderableWidget(this.button_chao_xiao);
        this.button_sieg_heil = new Button(this.leftPos + 67, this.topPos + 89, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_sieg_heil"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(10, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 10, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_sieg_heil", this.button_sieg_heil);
        this.addRenderableWidget(this.button_sieg_heil);
        this.button_dou_tun = new Button(this.leftPos + 114, this.topPos + 89, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_dou_tun"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(11, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 11, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_dou_tun", this.button_dou_tun);
        this.addRenderableWidget(this.button_dou_tun);
        this.button_duo_shan_yao = new Button(this.leftPos + 19, this.topPos + 114, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_duo_shan_yao"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(12, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 12, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_duo_shan_yao", this.button_duo_shan_yao);
        this.addRenderableWidget(this.button_duo_shan_yao);
        this.button_tiao_xin = new Button(this.leftPos + 67, this.topPos + 114, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_tiao_xin"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(13, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 13, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_tiao_xin", this.button_tiao_xin);
        this.addRenderableWidget(this.button_tiao_xin);
        this.button_fu_wo_cheng = new Button(this.leftPos + 114, this.topPos + 114, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_fu_wo_cheng"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(14, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 14, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_fu_wo_cheng", this.button_fu_wo_cheng);
        this.addRenderableWidget(this.button_fu_wo_cheng);
        this.button_lue_you_shi_zhong = new Button(this.leftPos + 19, this.topPos + 139, 37, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_lue_you_shi_zhong"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(15, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 15, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_lue_you_shi_zhong", this.button_lue_you_shi_zhong);
        this.addRenderableWidget(this.button_lue_you_shi_zhong);
        this.button_yao_bai_wu_dao = new Button(this.leftPos + 67, this.topPos + 139, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_yao_bai_wu_dao"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(16, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 16, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_yao_bai_wu_dao", this.button_yao_bai_wu_dao);
        this.addRenderableWidget(this.button_yao_bai_wu_dao);
        this.button_gu_zhang = new Button(this.leftPos + 114, this.topPos + 139, 35, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_gu_zhang"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(17, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 17, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_gu_zhang", this.button_gu_zhang);
        this.addRenderableWidget(this.button_gu_zhang);
        this.button_xia_ye = new Button(this.leftPos + 184, this.topPos + 114, 33, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_xia_ye"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(18, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 18, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_xia_ye", this.button_xia_ye);
        this.addRenderableWidget(this.button_xia_ye);
        this.button_kai_guan_biao_qing_yin_le = new Button(this.leftPos + 52, this.topPos + -26, 66, 20, new TranslatableComponent("gui.annoying_villagers.emoji.button_kai_guan_biao_qing_yin_le"), (button) -> {
            AnnoyingVillagersMod.PACKET_HANDLER.sendToServer(new EmojiButtonMessage(19, this.x, this.y, this.z, EmojiScreen.textstate));
            EmojiButtonMessage.handleButtonAction(this.entity, 19, this.x, this.y, this.z, EmojiScreen.textstate);
        });
        EmojiScreen.guistate.put("button:button_kai_guan_biao_qing_yin_le", this.button_kai_guan_biao_qing_yin_le);
        this.addRenderableWidget(this.button_kai_guan_biao_qing_yin_le);
    }
}
