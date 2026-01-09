package com.pla.annoyingvillagers.capabilities;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SnakeBladeCapability {
    public static ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "snake_blade_cap");

    public interface ISnakeBladeCapability extends INBTSerializable<CompoundTag> {

        void setHasSnakeBlade(boolean hasTentacle);

        boolean hasSnakeBlade();

        void setLastSnakeBladeID(int id);

        int getLastSnakeBladeID();

        UUID getLastSnakeBladeUUID();

        void setLastSnakeBladeUUID(UUID livingEntity);

    }

    public static class SnakeBladeCapabilityImp implements ISnakeBladeCapability {
        private UUID lastSnakeBlade;
        private boolean snakeBlade;
        public int id;

        @Override
        public void setHasSnakeBlade(boolean snakeBlade) {
            this.snakeBlade = snakeBlade;
        }

        @Override
        public boolean hasSnakeBlade() {
            return this.snakeBlade;
        }

        @Override
        public void setLastSnakeBladeID(int frozenPitch) {
            this.id = frozenPitch;
        }

        @Override
        public int getLastSnakeBladeID() {
            return id;
        }

        @Override
        public void setLastSnakeBladeUUID(UUID livingEntity) {
            lastSnakeBlade = livingEntity;
        }

        @Override
        public UUID getLastSnakeBladeUUID() {
            return lastSnakeBlade;
        }



        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("hasSnakeBlade", this.hasSnakeBlade());
            tag.putInt("getLastSnakeBladeID", this.getLastSnakeBladeID());
            if (getLastSnakeBladeUUID() != null) {
                tag.putUUID("getLastSnakeBladeUUID", getLastSnakeBladeUUID());
            }
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.setHasSnakeBlade(nbt.getBoolean("hasSnakeBlade"));
            this.setLastSnakeBladeID(nbt.getInt("getLastSnakeBladeID"));
            try {
                setLastSnakeBladeUUID(nbt.getUUID("getLastSnakeBladeUUID"));
            }
            catch (NullPointerException ignored) {}
        }

        public static class SnakeBladeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
            private final LazyOptional<ISnakeBladeCapability> instance = LazyOptional.of(SnakeBladeCapability.SnakeBladeCapabilityImp::new);

            @Override
            public CompoundTag serializeNBT() {
                return instance.orElseThrow(NullPointerException::new).serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                instance.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
            }

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
                return AnnoyingVillagersModCapabilities.SNAKE_BLADE_CAPABILITY.orEmpty(cap, instance.cast());
            }
        }
    }
}
