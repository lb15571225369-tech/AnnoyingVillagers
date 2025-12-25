package com.pla.annoyingvillagers.skill;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.passive.PassiveSkill;

import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;

public class KickSkill extends PassiveSkill {
    public static final String NBT_KICK_CD = "KickAttackCooldown";

    private int lastSynced = Integer.MIN_VALUE;

    public KickSkill(SkillBuilder<? extends PassiveSkill> builder) {
        super(builder);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (container.getExecutor().isLogicalClient()) return;

        CompoundTag data = container.getExecutor().getOriginal().getPersistentData();
        int remain = data.contains(NBT_KICK_CD) ? data.getInt(NBT_KICK_CD) - 1 : 0;
        if (container.getExecutor().getOriginal().tickCount % 20 != 0) return;

        if (remain == lastSynced) return;
        lastSynced = remain;

        if (remain > 0) {
            setSkillMaxResourceSynchronize(container, (float) remain);
            setSkillConsumptionSynchronize(container, 0.0F);
        } else {
            setSkillMaxResourceSynchronize(container, 0.0F);
            setSkillConsumptionSynchronize(container, 0.0F);
        }
    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return container.getMaxResource() > 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics gg,
                          float x, float y, float partialTick) {
        gg.pose().pushPose();
        gg.pose().translate(0.0F, gui.getSlidingProgression(), 0.0F);

        gg.blit(this.getSkillTexture(), (int)x, (int)y, 24, 24,
                0.0F, 0.0F, 1, 1, 1, 1);

        int remain = (int)container.getMaxResource();
        if (remain > 0) {
            String s = Integer.toString(remain);
            gg.drawString(gui.getFont(), s,
                    x + 12.0F - (4.0F * s.length()),
                    y + 6.0F,
                    0xFFFFFF, true);
        }

        gg.pose().popPose();
    }
}
