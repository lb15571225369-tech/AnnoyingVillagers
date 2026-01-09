package com.pla.annoyingvillagers.capabilities;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public final class SnakeBladeCapability {
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "snake_blade_cap");

    private static final String NBT_HAS_SNAKE_BLADE = "hasSnakeBlade";
    private static final String NBT_LAST_ID = "getLastSnakeBladeID";
    private static final String NBT_LAST_UUID = "getLastSnakeBladeUUID";

    private SnakeBladeCapability() {}

    public interface ISnakeBladeCapability extends INBTSerializable<CompoundTag> {
        void setHasSnakeBlade(boolean hasSnakeBlade);
        boolean hasSnakeBlade();

        void setLastSnakeBladeID(int id);
        int getLastSnakeBladeID();

        @Nullable UUID getLastSnakeBladeUUID();
        void setLastSnakeBladeUUID(@Nullable UUID uuid);
    }

    public static final class SnakeBladeCapabilityImp implements ISnakeBladeCapability {
        private boolean hasSnakeBlade;
        private int lastSnakeBladeId = -1;
        @Nullable private UUID lastSnakeBladeUuid;

        @Override
        public void setHasSnakeBlade(boolean hasSnakeBlade) {
            this.hasSnakeBlade = hasSnakeBlade;
        }

        @Override
        public boolean hasSnakeBlade() {
            return hasSnakeBlade;
        }

        @Override
        public void setLastSnakeBladeID(int id) {
            this.lastSnakeBladeId = id;
        }

        @Override
        public int getLastSnakeBladeID() {
            return lastSnakeBladeId;
        }

        @Override
        public @Nullable UUID getLastSnakeBladeUUID() {
            return lastSnakeBladeUuid;
        }

        @Override
        public void setLastSnakeBladeUUID(@Nullable UUID uuid) {
            this.lastSnakeBladeUuid = uuid;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean(NBT_HAS_SNAKE_BLADE, hasSnakeBlade);
            tag.putInt(NBT_LAST_ID, lastSnakeBladeId);
            if (lastSnakeBladeUuid != null) {
                tag.putUUID(NBT_LAST_UUID, lastSnakeBladeUuid);
            }
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            hasSnakeBlade = nbt.getBoolean(NBT_HAS_SNAKE_BLADE);
            lastSnakeBladeId = nbt.contains(NBT_LAST_ID) ? nbt.getInt(NBT_LAST_ID) : -1;
            lastSnakeBladeUuid = nbt.hasUUID(NBT_LAST_UUID) ? nbt.getUUID(NBT_LAST_UUID) : null;
        }
    }

    public static final class SnakeBladeProvider implements ICapabilitySerializable<CompoundTag> {
        private final SnakeBladeCapabilityImp impl = new SnakeBladeCapabilityImp();
        private final LazyOptional<ISnakeBladeCapability> optional = LazyOptional.of(() -> impl);

        @Override
        public CompoundTag serializeNBT() {
            return impl.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            impl.deserializeNBT(nbt);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY
                    ? optional.cast()
                    : LazyOptional.empty();
        }
    }
}