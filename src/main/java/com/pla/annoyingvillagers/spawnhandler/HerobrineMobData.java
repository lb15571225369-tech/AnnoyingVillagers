package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.Herobrine6Entity;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class HerobrineMobData extends SavedData {
    public static final String ID = "av_singleton_herobrine";
    private UUID activeId = null;

    public static HerobrineMobData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(HerobrineMobData::load, HerobrineMobData::new, ID);
    }
    public static HerobrineMobData load(CompoundTag compoundTag) {
        HerobrineMobData herobrineData = new HerobrineMobData();
        if (compoundTag.hasUUID("activeId")) {
            herobrineData.activeId = compoundTag.getUUID("activeId");
        }
        return herobrineData;
    }
    @Override public CompoundTag save(CompoundTag compoundTag) {
        if (activeId != null) {
            compoundTag.putUUID("activeId", activeId);
        }
        return compoundTag;
    }
    public boolean isOccupied(ServerLevel serverLevel) {
        if (activeId == null) {
            return false;
        }
        Entity entity = serverLevel.getEntity(activeId);
        if ((entity instanceof HerobrineMob || entity instanceof Herobrine6Entity) && entity.isAlive()) {
            return true;
        } else {
            activeId = null;
            setDirty();
            return false;
        }
    }
    public boolean tryClaim(ServerLevel serverLevel, UUID id) {
        if (isOccupied(serverLevel)) {
            return false;
        }
        activeId = id;
        setDirty();
        return true;
    }
    public void releaseIfMatches(UUID id) {
        if (activeId != null && activeId.equals(id)) {
            activeId = null;
            setDirty();
        }
    }
}
