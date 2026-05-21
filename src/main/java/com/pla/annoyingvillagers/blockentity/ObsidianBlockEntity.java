package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class ObsidianBlockEntity extends BlockEntity {
    private @Nullable UUID owner;

    public ObsidianBlockEntity(BlockPos pos, BlockState state) {
        super(AnnoyingVillagersModBlockEntities.OBSIDIAN_BLOCK.get(), pos, state);
    }

    public void setOwner(@Nullable UUID id) {
        this.owner = id;
        setChanged();
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (owner != null) tag.putUUID("Owner", owner);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (owner != null) tag.putUUID("Owner", owner);
        return tag;
    }
}
