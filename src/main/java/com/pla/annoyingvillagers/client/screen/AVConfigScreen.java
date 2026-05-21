package com.pla.annoyingvillagers.client.screen;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersSpawnConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AVConfigScreen extends Screen {

    private static final int ROW_HEIGHT = 26;
    private static final int CONTENT_TOP = 60;
    private static final int CONTENT_BOTTOM_MARGIN = 36;

    private final Screen parent;
    private int activeTab = 0;
    private int scrollOffset = 0;
    private boolean cacheInitialized = false;

    private final Map<ForgeConfigSpec.ConfigValue<Boolean>, Boolean> boolCache = new LinkedHashMap<>();
    private final Map<ForgeConfigSpec.ConfigValue<Double>, Double> doubleCache = new LinkedHashMap<>();
    private final Map<ForgeConfigSpec.ConfigValue<Integer>, Integer> intCache = new LinkedHashMap<>();
    private final Map<ForgeConfigSpec.ConfigValue<List<? extends String>>, List<String>> listCache = new LinkedHashMap<>();
    private final Map<String, int[]> spawnCache = new LinkedHashMap<>();

    private final List<Row> rows = new ArrayList<>();

    public AVConfigScreen(Screen parent) {
        super(Component.translatable("annoyingvillagers.config.title"));
        this.parent = parent;
    }

    private static class Row {
        Component label;
        final List<AbstractWidget> widgets = new ArrayList<>();
        int labelX;
        int labelY;
    }

    @Override
    protected void init() {
        if (!cacheInitialized) {
            loadCache();
            cacheInitialized = true;
        }
        buildLayout();
    }

    private void loadCache() {
        boolCache.put(AnnoyingVillagersConfig.TRIDENT_FESTIVAL_CAN_BREAK_BLOCK, AnnoyingVillagersConfig.TRIDENT_FESTIVAL_CAN_BREAK_BLOCK.get());
        boolCache.put(AnnoyingVillagersConfig.TURN_ON_NPC_CHAT, AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get());
        boolCache.put(AnnoyingVillagersConfig.ARROW_CAN_BREAK_BLOCK, AnnoyingVillagersConfig.ARROW_CAN_BREAK_BLOCK.get());
        boolCache.put(AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB, AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB.get());
        boolCache.put(AnnoyingVillagersConfig.AV_MOB_CAN_EXECUTE, AnnoyingVillagersConfig.AV_MOB_CAN_EXECUTE.get());
        boolCache.put(AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM, AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM.get());
        boolCache.put(AnnoyingVillagersConfig.VANILLA_MOB_CAN_DRINK_HEALING_POTION, AnnoyingVillagersConfig.VANILLA_MOB_CAN_DRINK_HEALING_POTION.get());

        doubleCache.put(AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE, AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE.get());
        doubleCache.put(AnnoyingVillagersConfig.ANGRY_STEVE_CHANCE, AnnoyingVillagersConfig.ANGRY_STEVE_CHANCE.get());
        doubleCache.put(AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE, AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE.get());
        doubleCache.put(AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE, AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_MONSTER_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_MONSTER_HUNTER.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_VILLAGER_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_VILLAGER_HUNTER.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PLAYER_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PLAYER_HUNTER.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_HOSTILE_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_HOSTILE_HUNTER.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PASSIVE_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PASSIVE_HUNTER.get());
        doubleCache.put(AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_ANIMAL_HUNTER, AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_ANIMAL_HUNTER.get());

        intCache.put(AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME, AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get());
        intCache.put(AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME, AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get());
        intCache.put(AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MIN_TIME, AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MIN_TIME.get());
        intCache.put(AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MAX_TIME, AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MAX_TIME.get());
        intCache.put(AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MIN_TIME, AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MIN_TIME.get());
        intCache.put(AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MAX_TIME, AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MAX_TIME.get());
        intCache.put(AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE, AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get());

        List<String> affected = new ArrayList<>();
        for (String s : AnnoyingVillagersConfig.WEAPON_DISARMS_AFFECTED_ENTITY_TYPES.get()) affected.add(s);
        listCache.put(AnnoyingVillagersConfig.WEAPON_DISARMS_AFFECTED_ENTITY_TYPES, affected);
        List<String> blacklist = new ArrayList<>();
        for (String s : AnnoyingVillagersConfig.WEAPON_DISARMS_BLACKLIST.get()) blacklist.add(s);
        listCache.put(AnnoyingVillagersConfig.WEAPON_DISARMS_BLACKLIST, blacklist);

        for (var entry : AnnoyingVillagersSpawnConfig.getEntries()) {
            spawnCache.put(entry.entityId(), new int[] {
                    AnnoyingVillagersSpawnConfig.getRawWeight(entry.entityId()),
                    AnnoyingVillagersSpawnConfig.getRawMinCount(entry.entityId()),
                    AnnoyingVillagersSpawnConfig.getRawMaxCount(entry.entityId())
            });
        }
    }

    private void buildLayout() {
        this.clearWidgets();
        rows.clear();
        scrollOffset = 0;

        int tabY = 32;
        int tabW = 110;
        int tabSpacing = 4;
        int totalTabW = tabW * 3 + tabSpacing * 2;
        int tabStartX = (this.width - totalTabW) / 2;
        addTabButton(tabStartX, tabY, tabW, "annoyingvillagers.config.tab.toggle", 0);
        addTabButton(tabStartX + tabW + tabSpacing, tabY, tabW, "annoyingvillagers.config.tab.numeric", 1);
        addTabButton(tabStartX + (tabW + tabSpacing) * 2, tabY, tabW, "annoyingvillagers.config.tab.spawn", 2);

        int btnY = this.height - 26;
        this.addRenderableWidget(Button.builder(
                Component.translatable("annoyingvillagers.config.save"),
                b -> { saveAll(); this.minecraft.setScreen(this.parent); }
        ).bounds(this.width / 2 - 110, btnY, 100, 20).build());
        this.addRenderableWidget(Button.builder(
                Component.translatable("annoyingvillagers.config.cancel"),
                b -> this.minecraft.setScreen(this.parent)
        ).bounds(this.width / 2 + 10, btnY, 100, 20).build());

        if (activeTab == 0) buildToggleTab();
        else if (activeTab == 1) buildNumericTab();
        else buildSpawnTab();

        for (Row r : rows) {
            for (AbstractWidget w : r.widgets) {
                this.addRenderableWidget(w);
            }
        }
        layoutRows();
    }

    private void addTabButton(int x, int y, int w, String key, int idx) {
        Button b = Button.builder(Component.translatable(key), btn -> { activeTab = idx; buildLayout(); })
                .bounds(x, y, w, 20).build();
        b.active = activeTab != idx;
        this.addRenderableWidget(b);
    }

    private void buildToggleTab() {
        addBoolRow("annoyingvillagers.config.option.tridentFestivalCanBreakBlock", AnnoyingVillagersConfig.TRIDENT_FESTIVAL_CAN_BREAK_BLOCK);
        addBoolRow("annoyingvillagers.config.option.turnOnNpcChat", AnnoyingVillagersConfig.TURN_ON_NPC_CHAT);
        addBoolRow("annoyingvillagers.config.option.arrowCanBreakBlock", AnnoyingVillagersConfig.ARROW_CAN_BREAK_BLOCK);
        addBoolRow("annoyingvillagers.config.option.canExecuteAvMob", AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB);
        addBoolRow("annoyingvillagers.config.option.avMobCanExecute", AnnoyingVillagersConfig.AV_MOB_CAN_EXECUTE);
        addBoolRow("annoyingvillagers.config.option.avMobCanBurnItem", AnnoyingVillagersConfig.AV_MOB_CAN_BURN_ITEM);
        addBoolRow("annoyingvillagers.config.option.vanillaMobCanDrinkHealingPotion", AnnoyingVillagersConfig.VANILLA_MOB_CAN_DRINK_HEALING_POTION);
    }

    private void buildNumericTab() {
        addDoubleRow("annoyingvillagers.config.option.herobrinePossessRate", AnnoyingVillagersConfig.HEROBRINE_POSSESS_RATE, 0.0, 1.0);
        addDoubleRow("annoyingvillagers.config.option.angrySteveChance", AnnoyingVillagersConfig.ANGRY_STEVE_CHANCE, 0.0, 1.0);
        addDoubleRow("annoyingvillagers.config.option.mobGuardBreakWakeUpMinChance", AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE, 0.0, 1.0);
        addDoubleRow("annoyingvillagers.config.option.mobGuardBreakWakeUpMaxChance", AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE, 0.0, 1.0);

        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightMonsterHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_MONSTER_HUNTER, 0.0, 10.0);
        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightVillagerHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_VILLAGER_HUNTER, 0.0, 10.0);
        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightPlayerHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PLAYER_HUNTER, 0.0, 10.0);
        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightHostileHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_HOSTILE_HUNTER, 0.0, 10.0);
        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightPassiveHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_PASSIVE_HUNTER, 0.0, 10.0);
        addDoubleRow("annoyingvillagers.config.option.npcTargetWeightAnimalHunter", AnnoyingVillagersConfig.NPC_TARGET_WEIGHT_ANIMAL_HUNTER, 0.0, 10.0);

        addIntRow("annoyingvillagers.config.option.herobrineRecallMinTime", AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.herobrineRecallMaxTime", AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.angrySteveLeaveMinTime", AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MIN_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.angrySteveLeaveMaxTime", AnnoyingVillagersConfig.ANGRY_STEVE_LEAVE_MAX_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.blueDemonLeaveMinTime", AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MIN_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.blueDemonLeaveMaxTime", AnnoyingVillagersConfig.BLUE_DEMON_LEAVE_MAX_TIME, 1, 10080);
        addIntRow("annoyingvillagers.config.option.weaponBreakingMechanismValue", AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE, 0, 10000);

        addListRow("annoyingvillagers.config.option.affectedEntityTypes", AnnoyingVillagersConfig.WEAPON_DISARMS_AFFECTED_ENTITY_TYPES);
        addListRow("annoyingvillagers.config.option.weaponBlacklist", AnnoyingVillagersConfig.WEAPON_DISARMS_BLACKLIST);
    }

    private void buildSpawnTab() {
        for (AnnoyingVillagersSpawnConfig.Entry entry : AnnoyingVillagersSpawnConfig.getEntries()) {
            addSpawnRow(entry);
        }
    }

    private void addBoolRow(String key, ForgeConfigSpec.ConfigValue<Boolean> cv) {
        Row row = new Row();
        row.label = Component.translatable(key);
        boolean initial = boolCache.getOrDefault(cv, cv.get());
        CycleButton<Boolean> btn = CycleButton.onOffBuilder(initial)
                .displayOnlyValue()
                .create(0, 0, 60, 20, Component.empty(),
                        (cb, val) -> boolCache.put(cv, val));
        row.widgets.add(btn);
        rows.add(row);
    }

    private void addDoubleRow(String key, ForgeConfigSpec.ConfigValue<Double> cv, double min, double max) {
        Row row = new Row();
        MutableComponent label = Component.translatable(key);
        label.append(Component.literal(" [" + min + "," + max + "]").withStyle(ChatFormatting.GRAY));
        row.label = label;
        EditBox box = new EditBox(this.font, 0, 0, 90, 18, Component.translatable(key));
        double initial = doubleCache.getOrDefault(cv, cv.get());
        box.setValue(String.valueOf(initial));
        box.setResponder(text -> {
            try {
                double v = Double.parseDouble(text);
                if (v >= min && v <= max) {
                    doubleCache.put(cv, v);
                    box.setTextColor(0xE0E0E0);
                } else {
                    box.setTextColor(0xFF5555);
                }
            } catch (NumberFormatException e) {
                box.setTextColor(0xFF5555);
            }
        });
        row.widgets.add(box);
        rows.add(row);
    }

    private void addIntRow(String key, ForgeConfigSpec.ConfigValue<Integer> cv, int min, int max) {
        Row row = new Row();
        MutableComponent label = Component.translatable(key);
        label.append(Component.literal(" [" + min + "," + max + "]").withStyle(ChatFormatting.GRAY));
        row.label = label;
        EditBox box = new EditBox(this.font, 0, 0, 90, 18, Component.translatable(key));
        int initial = intCache.getOrDefault(cv, cv.get());
        box.setValue(String.valueOf(initial));
        box.setResponder(text -> {
            try {
                int v = Integer.parseInt(text);
                if (v >= min && v <= max) {
                    intCache.put(cv, v);
                    box.setTextColor(0xE0E0E0);
                } else {
                    box.setTextColor(0xFF5555);
                }
            } catch (NumberFormatException e) {
                box.setTextColor(0xFF5555);
            }
        });
        row.widgets.add(box);
        rows.add(row);
    }

    private void addListRow(String key, ForgeConfigSpec.ConfigValue<List<? extends String>> cv) {
        Row row = new Row();
        row.label = Component.translatable(key);
        Button btn = Button.builder(
                Component.translatable("annoyingvillagers.config.list.edit"),
                b -> {
                    List<String> current = new ArrayList<>(listCache.get(cv));
                    this.minecraft.setScreen(new AVStringListEditScreen(this, current,
                            updated -> listCache.put(cv, updated)));
                }
        ).bounds(0, 0, 90, 20).build();
        row.widgets.add(btn);
        rows.add(row);
    }

    private void addSpawnRow(AnnoyingVillagersSpawnConfig.Entry entry) {
        Row row = new Row();
        row.label = Component.translatable("entity.annoyingvillagers." + entry.entityId());
        final int[] cache = spawnCache.computeIfAbsent(entry.entityId(),
                id -> new int[] {
                        AnnoyingVillagersSpawnConfig.getRawWeight(id),
                        AnnoyingVillagersSpawnConfig.getRawMinCount(id),
                        AnnoyingVillagersSpawnConfig.getRawMaxCount(id)
                });
        EditBox weightBox = new EditBox(this.font, 0, 0, 50, 18, Component.literal("weight"));
        weightBox.setValue(String.valueOf(cache[0]));
        weightBox.setResponder(text -> {
            try {
                int v = Integer.parseInt(text);
                if (v >= 0 && v <= 1000) { cache[0] = v; weightBox.setTextColor(0xE0E0E0); }
                else weightBox.setTextColor(0xFF5555);
            } catch (NumberFormatException e) { weightBox.setTextColor(0xFF5555); }
        });
        row.widgets.add(weightBox);

        if (entry.groupSizeConfigurable()) {
            EditBox minBox = new EditBox(this.font, 0, 0, 40, 18, Component.literal("min"));
            minBox.setValue(String.valueOf(cache[1]));
            minBox.setResponder(text -> {
                try {
                    int v = Integer.parseInt(text);
                    if (v >= 1 && v <= 64) { cache[1] = v; minBox.setTextColor(0xE0E0E0); }
                    else minBox.setTextColor(0xFF5555);
                } catch (NumberFormatException e) { minBox.setTextColor(0xFF5555); }
            });
            row.widgets.add(minBox);

            EditBox maxBox = new EditBox(this.font, 0, 0, 40, 18, Component.literal("max"));
            maxBox.setValue(String.valueOf(cache[2]));
            maxBox.setResponder(text -> {
                try {
                    int v = Integer.parseInt(text);
                    if (v >= 1 && v <= 64) { cache[2] = v; maxBox.setTextColor(0xE0E0E0); }
                    else maxBox.setTextColor(0xFF5555);
                } catch (NumberFormatException e) { maxBox.setTextColor(0xFF5555); }
            });
            row.widgets.add(maxBox);
        }
        rows.add(row);
    }

    private void layoutRows() {
        int contentTop = CONTENT_TOP;
        int contentBottom = this.height - CONTENT_BOTTOM_MARGIN;
        int labelX = Math.max(20, this.width / 2 - 220);
        int widgetStartX = Math.min(this.width - 30, this.width / 2 + 30);
        int y = contentTop - scrollOffset;
        for (Row row : rows) {
            row.labelX = labelX;
            row.labelY = y + (ROW_HEIGHT - this.font.lineHeight) / 2;
            int wx = widgetStartX;
            for (AbstractWidget w : row.widgets) {
                w.setX(wx);
                w.setY(y + (ROW_HEIGHT - w.getHeight()) / 2);
                wx += w.getWidth() + 4;
                boolean visible = (y >= contentTop) && (y + ROW_HEIGHT <= contentBottom);
                w.visible = visible;
                w.active = visible;
            }
            y += ROW_HEIGHT;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int contentTop = CONTENT_TOP;
        int contentBottom = this.height - CONTENT_BOTTOM_MARGIN;
        if (mouseY >= contentTop && mouseY < contentBottom) {
            int totalHeight = rows.size() * ROW_HEIGHT;
            int viewport = contentBottom - contentTop;
            int maxScroll = Math.max(0, totalHeight - viewport);
            scrollOffset -= (int)(delta * 16);
            scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));
            layoutRows();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gg);
        super.render(gg, mouseX, mouseY, partialTick);
        gg.drawCenteredString(this.font, this.title, this.width / 2, 12, 0xFFFFFF);

        int contentTop = CONTENT_TOP;
        int contentBottom = this.height - CONTENT_BOTTOM_MARGIN;
        gg.fill(0, contentTop - 2, this.width, contentTop - 1, 0xFF606060);
        gg.fill(0, contentBottom, this.width, contentBottom + 1, 0xFF606060);

        for (Row row : rows) {
            if (row.labelY >= contentTop && row.labelY + this.font.lineHeight <= contentBottom) {
                gg.drawString(this.font, row.label, row.labelX, row.labelY, 0xFFFFFF);
            }
        }
    }

    private void saveAll() {
        for (Map.Entry<ForgeConfigSpec.ConfigValue<Boolean>, Boolean> e : boolCache.entrySet()) e.getKey().set(e.getValue());
        for (Map.Entry<ForgeConfigSpec.ConfigValue<Double>, Double> e : doubleCache.entrySet()) e.getKey().set(e.getValue());
        for (Map.Entry<ForgeConfigSpec.ConfigValue<Integer>, Integer> e : intCache.entrySet()) e.getKey().set(e.getValue());
        for (Map.Entry<ForgeConfigSpec.ConfigValue<List<? extends String>>, List<String>> e : listCache.entrySet()) {
            e.getKey().set(e.getValue());
        }
        for (Map.Entry<String, int[]> e : spawnCache.entrySet()) {
            int[] arr = e.getValue();
            AnnoyingVillagersSpawnConfig.setSpawn(e.getKey(), arr[0], arr[1], arr[2]);
        }
        AnnoyingVillagersConfig.SPEC.save();
        AnnoyingVillagersSpawnConfig.SPEC.save();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
