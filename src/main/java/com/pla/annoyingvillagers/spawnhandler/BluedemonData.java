package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.BlueDemon2Entity;
import com.pla.annoyingvillagers.entity.BlueDemonEndStagingEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.BlueDemonStagingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class BluedemonData extends SavedData {
    public static final String ID = "av_singleton_blue_demon";
    private UUID activeId = null;

    public static BluedemonData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(BluedemonData::load, BluedemonData::new, ID);
    }
    public static BluedemonData load(CompoundTag compoundTag) {
        BluedemonData bluedemonData = new BluedemonData();
        if (compoundTag.hasUUID("activeId")) {
            bluedemonData.activeId = compoundTag.getUUID("activeId");
        }
        return bluedemonData;
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
        if ((entity instanceof BlueDemonEntity || entity instanceof BlueDemon2Entity
                || entity instanceof BlueDemonStagingEntity || entity instanceof BlueDemonEndStagingEntity) && entity.isAlive()) {
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
    public void forceClaim(ServerLevel serverLevel, UUID id) {
        activeId = id;
        setDirty();
    }
    public void releaseIfMatches(UUID id) {
        if (activeId != null && activeId.equals(id)) {
            activeId = null;
            setDirty();
        }
    }
}
