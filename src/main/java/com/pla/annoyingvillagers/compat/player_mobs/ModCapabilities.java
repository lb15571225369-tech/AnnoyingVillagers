package com.pla.annoyingvillagers.compat.player_mobs;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static Capability<IPlayerMobInventory> PLAYER_MOB_INVENTORY = CapabilityManager.get(new CapabilityToken<>() {});
}

