package com.pla.annoyingvillagers.client.overlay;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.hint.Move;
import com.pla.annoyingvillagers.skill.hint.MoveContext;
import com.pla.annoyingvillagers.skill.hint.MoveLibrary;
import com.pla.annoyingvillagers.skill.hint.MoveRegistry;
import com.pla.annoyingvillagers.skill.hint.WeaponMoveSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MoveHintOverlay implements IGuiOverlay {
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "move_hints");
    public static final MoveHintOverlay INSTANCE = new MoveHintOverlay();

    private static final int MAX_HINTS = 3;
    private static final int FADE_IN_TICKS = 5;
    private static final int FADE_OUT_TICKS = 8;
    private static final int LINE_HEIGHT = 11;
    private static final int PADDING_X = 6;
    private static final int PADDING_Y = 4;
    private static final int BG_COLOR = 0x80000000;
    private static final int BORDER_COLOR = 0x40FFFFFF;

    private static final List<Move> ARMOR_HINT_TEMPLATES = List.of(MoveLibrary.blueDemonChestSpecial());

    private final LinkedHashMap<String, HintState> active = new LinkedHashMap<>();
    private ItemStack lastStack = ItemStack.EMPTY;
    private int lastTickSeen = -1;

    private MoveHintOverlay() {}

    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "move_hints", INSTANCE);
    }

    @Override
    public void render(net.minecraftforge.client.gui.overlay.ForgeGui gui,
                       GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;
        if (mc.options.renderDebug) return;
        LocalPlayer player = mc.player;
        if (player == null || player.isSpectator() || mc.screen != null) return;

        MoveContext ctx = MoveContext.snapshot(player);
        if (ctx == null) return;

        ItemStack stack = ctx.mainHand();
        WeaponMoveSet set = MoveRegistry.get(stack);
        if (lastStack == null || !ItemStack.isSameItem(lastStack, stack)) {
            active.clear();
        }
        lastStack = stack;

        List<Move> armorHints = collectArmorHints(ctx);
        List<Move> weaponHints = set != null ? set.hintMoves(ctx, MAX_HINTS) : java.util.Collections.emptyList();
        List<Move> merged = mergeHints(weaponHints, armorHints, MAX_HINTS);

        if (merged.isEmpty()) {
            decayAll();
            renderActive(graphics, mc.font, screenWidth, screenHeight, partialTick);
            return;
        }

        tickStates(merged, mc.player.tickCount);
        renderActive(graphics, mc.font, screenWidth, screenHeight, partialTick);
    }

    private static List<Move> collectArmorHints(MoveContext ctx) {
        List<Move> result = new ArrayList<>();
        for (Move m : ARMOR_HINT_TEMPLATES) {
            if (m.condition().test(ctx)) result.add(m);
        }
        return result;
    }

    private static List<Move> mergeHints(List<Move> weaponHints, List<Move> armorHints, int max) {
        if (armorHints.isEmpty()) {
            return weaponHints.size() > max ? weaponHints.subList(0, max) : weaponHints;
        }
        int weaponBudget = Math.max(0, max - armorHints.size());
        List<Move> result = new ArrayList<>();
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (int i = 0; i < weaponHints.size() && result.size() < weaponBudget; i++) {
            Move m = weaponHints.get(i);
            if (seen.add(m.id())) result.add(m);
        }
        for (Move m : armorHints) {
            if (result.size() >= max) break;
            if (seen.add(m.id())) result.add(m);
        }
        return result;
    }

    private void tickStates(List<Move> targetHints, int tick) {
        if (lastTickSeen != tick) {
            for (HintState s : active.values()) {
                s.advance(tick);
            }
            lastTickSeen = tick;
        }

        java.util.Set<String> targetIds = new java.util.HashSet<>();
        for (Move m : targetHints) targetIds.add(m.id());

        for (Map.Entry<String, HintState> e : active.entrySet()) {
            if (!targetIds.contains(e.getKey())) {
                e.getValue().fadeOut();
            }
        }

        for (int i = 0; i < targetHints.size(); i++) {
            Move m = targetHints.get(i);
            HintState s = active.get(m.id());
            if (s == null) {
                s = new HintState(m, tick);
                active.put(m.id(), s);
            } else {
                s.refresh(m);
                s.fadeIn();
            }
            s.order = i;
        }

        active.values().removeIf(HintState::isDead);
    }

    private void decayAll() {
        for (HintState s : active.values()) {
            s.fadeOut();
            s.advance(s.lastTick + 1);
        }
        active.values().removeIf(HintState::isDead);
    }

    private void renderActive(GuiGraphics gg, Font font, int sw, int sh, float partialTick) {
        if (active.isEmpty()) return;
        List<HintState> ordered = new ArrayList<>(active.values());
        ordered.sort((a, b) -> Integer.compare(a.order, b.order));

        int maxWidth = 0;
        List<Component> lines = new ArrayList<>(ordered.size());
        List<Float> alphas = new ArrayList<>(ordered.size());
        for (HintState s : ordered) {
            Component line = s.composeLine();
            int w = font.width(line);
            if (w > maxWidth) maxWidth = w;
            lines.add(line);
            alphas.add(s.alpha(partialTick));
        }

        int boxWidth = maxWidth + PADDING_X * 2;
        int boxHeight = ordered.size() * LINE_HEIGHT + PADDING_Y * 2;
        int x = sw - boxWidth - 8;
        int y = sh - boxHeight - 56;

        gg.fill(x, y, x + boxWidth, y + boxHeight, BG_COLOR);
        gg.hLine(x, x + boxWidth - 1, y, BORDER_COLOR);
        gg.hLine(x, x + boxWidth - 1, y + boxHeight - 1, BORDER_COLOR);
        gg.vLine(x, y, y + boxHeight - 1, BORDER_COLOR);
        gg.vLine(x + boxWidth - 1, y, y + boxHeight - 1, BORDER_COLOR);

        for (int i = 0; i < ordered.size(); i++) {
            float a = alphas.get(i);
            int alphaByte = (int) (a * 255.0F);
            if (alphaByte <= 0) continue;
            int color = (alphaByte << 24) | 0xFFFFFF;
            int textY = y + PADDING_Y + i * LINE_HEIGHT;
            gg.drawString(font, lines.get(i), x + PADDING_X, textY, color, true);
        }
    }

    private static final class HintState {
        Move move;
        int order;
        int lastTick;
        int fadeTicks;
        boolean fadingOut;

        HintState(Move move, int tick) {
            this.move = move;
            this.lastTick = tick;
            this.fadeTicks = 0;
        }

        void refresh(Move m) { this.move = m; }

        void fadeIn() {
            this.fadingOut = false;
            if (this.fadeTicks > FADE_IN_TICKS) this.fadeTicks = FADE_IN_TICKS;
        }

        void fadeOut() {
            if (!this.fadingOut) {
                this.fadingOut = true;
                this.fadeTicks = FADE_OUT_TICKS;
            }
        }

        void advance(int tick) {
            this.lastTick = tick;
            if (this.fadingOut) {
                if (this.fadeTicks > 0) this.fadeTicks--;
            } else {
                if (this.fadeTicks < FADE_IN_TICKS) this.fadeTicks++;
            }
        }

        boolean isDead() { return this.fadingOut && this.fadeTicks <= 0; }

        float alpha(float partialTick) {
            float base;
            if (this.fadingOut) {
                base = (this.fadeTicks - partialTick) / (float) FADE_OUT_TICKS;
            } else {
                base = (this.fadeTicks + partialTick) / (float) FADE_IN_TICKS;
            }
            if (base < 0F) base = 0F;
            if (base > 1F) base = 1F;
            return base;
        }

        Component composeLine() {
            MutableComponent input = move.input().label();
            MutableComponent name = move.displayName();
            return Component.empty()
                    .append(input.withStyle(s -> s.withBold(true).withColor(0xFFEFA8)))
                    .append(Component.literal("  "))
                    .append(name.withStyle(s -> s.withColor(0xEAEAEA)));
        }
    }
}
