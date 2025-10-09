package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.Herobrine4Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class AlexData extends SavedData {
    public static final String ID = "av_singleton_alex";
    private UUID activeId = null;

    public static AlexData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(AlexData::load, AlexData::new, ID);
    }
    public static AlexData load(CompoundTag compoundTag) {
        AlexData alexData = new AlexData();
        if (compoundTag.hasUUID("activeId")) {
            alexData.activeId = compoundTag.getUUID("activeId");
        }
        return alexData;
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
        if (entity instanceof AlexEntity && entity.isAlive()) {
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
