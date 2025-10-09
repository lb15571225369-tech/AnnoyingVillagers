package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.ChrisEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class ChrisData extends SavedData {
    public static final String ID = "av_singleton_chris";
    private UUID activeId = null;

    public static ChrisData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(ChrisData::load, ChrisData::new, ID);
    }
    public static ChrisData load(CompoundTag tag) {
        ChrisData chrisData = new ChrisData();
        if (tag.hasUUID("activeId")){
            chrisData.activeId = tag.getUUID("activeId");
        }
        return chrisData;
    }
    @Override public CompoundTag save(CompoundTag tag) {
        if (activeId != null){
            tag.putUUID("activeId", activeId);
        }
        return tag;
    }

    public boolean isOccupied(ServerLevel serverLevel) {
        if (activeId == null) {
            return false;
        }
        Entity entity = serverLevel.getEntity(activeId);
        if (entity instanceof ChrisEntity && entity.isAlive()) {
            return true;
        }
        activeId = null;
        setDirty();
        return false;
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
            activeId = null; setDirty();
        }
    }
}
