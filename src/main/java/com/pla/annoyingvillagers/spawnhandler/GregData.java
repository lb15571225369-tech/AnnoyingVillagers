package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.Herobrine4Entity;
import com.pla.annoyingvillagers.entity.Herobrine6Entity;
import com.pla.annoyingvillagers.util.HerobrineMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class GregData extends SavedData {
    public static final String ID = "av_singleton_greg";
    private UUID activeId = null;

    public static GregData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(GregData::load, GregData::new, ID);
    }
    public static GregData load(CompoundTag compoundTag) {
        GregData gregData = new GregData();
        if (compoundTag.hasUUID("activeId")) {
            gregData.activeId = compoundTag.getUUID("activeId");
        }
        return gregData;
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
        if (entity instanceof Herobrine4Entity && entity.isAlive()) {
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
