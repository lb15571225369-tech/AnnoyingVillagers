package com.pla.annoyingvillagers.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMenus;

public class MusicBoxSelectMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {

    public static final HashMap<String, Object> guistate = new HashMap();
    public final Level world;
    public final Player entity;
    public int x;
    public int y;
    public int z;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap();
    private boolean bound = false;

    public MusicBoxSelectMenu(int i, Inventory inventory, FriendlyByteBuf friendlybytebuf) {
        super(AnnoyingVillagersModMenus.MUSIC_BOX_SELECT, i);
        this.entity = inventory.player;
        this.world = inventory.player.level;
        this.internal = new ItemStackHandler(0);
        BlockPos blockpos = null;

        if (friendlybytebuf != null) {
            blockpos = friendlybytebuf.readBlockPos();
            this.x = blockpos.getX();
            this.y = blockpos.getY();
            this.z = blockpos.getZ();
        }

    }

    public boolean stillValid(Player player) {
        return true;
    }

    public Map<Integer, Slot> get() {
        return this.customSlots;
    }
}
