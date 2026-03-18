package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import net.minecraft.world.item.ItemStack;

public final class ColoredGlintState {
    public static final int NONE = 0;
    public static final int ORANGE = 1;
    public static final int CYAN = 2;

    private static final ThreadLocal<Integer> MODE = ThreadLocal.withInitial(() -> NONE);

    private ColoredGlintState() {
    }

    public static void setTargetStack(ItemStack stack) {
        int mode = NONE;

        if (stack.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
            mode = ORANGE;
        } else if (
                (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) && BlueDemonTridentItem.isFullyCharged(stack))
                        || (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get()) && BlueDemonChestplateItem.isFullyCharged(stack))
        ) {
            mode = CYAN;
        }

        MODE.set(mode);
    }

    public static int getMode() {
        return MODE.get();
    }

    public static void clear() {
        MODE.remove();
    }
}
