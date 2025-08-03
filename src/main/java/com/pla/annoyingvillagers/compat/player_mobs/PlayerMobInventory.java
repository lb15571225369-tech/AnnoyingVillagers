package com.pla.annoyingvillagers.compat.player_mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

public class PlayerMobInventory implements IPlayerMobInventory, INBTSerializable<CompoundTag> {
    private final ItemStackHandler inventory = new ItemStackHandler(27);

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public CompoundTag serializeNBT() {
        return inventory.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.deserializeNBT(nbt);
    }
}

